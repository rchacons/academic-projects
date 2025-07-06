from dataclasses import dataclass
from itertools import chain
from typing import Sequence, Optional, List
from Crypto.Cipher import AES
import json

import numpy as np

import secrets
from scipy.stats import laplace

from utils import LoggingHandler, chunks, load_adult


class Keychain(LoggingHandler):
    """
    Stores AES encryption key and initialization vector, provides encryption and decryption functions.
    """
    # Initialisation de la clé et du vecteur à la création d'un objet
    def __init__(self) -> None:
        super().__init__()
        self.generate_key()
        self.generate_iVector()

    # Generate a secret key (of default length 16 bytes) and stores it in the object.
    def generate_key(self):
        self.key = secrets.token_bytes(16)
        print("Clé : "+str(self.key))

    # Generate an initialization vector (same length as the key) and stores it in the object.
    def generate_iVector(self):
        self.iVector = secrets.token_bytes(16)
        print("iVector : "+str(self.iVector))


    # noinspection PyMethodMayBeStatic
    def encrypt(self, data: bytes) -> bytes:
        """
        Pads and encrypts data using internal key and initialization vector.

        :param data: The data to encrypt (byte representation).
        :return: The encrypted data (byte representation).
        """

        aes_cipher = AES.new(self.key, AES.MODE_CBC, self.iVector)
        
        # Pad data such that it is a multiple of AES.block_size.
        block_size = AES.block_size
        padding_size = block_size - len(data) % block_size # quantite de padding à ajouter à la fin de données
        padding = bytes([padding_size] * padding_size) # on cree une sequence de 'padding_size' octets où chaque octet a pour valeur 'padding size'
        padded_data = data + padding # on ajout le padding aux données

        # Encrypt data by the AES cipher based on the AES.MODE_CBC mode.
        encrypted_data = aes_cipher.encrypt(padded_data)

        self.logger.info("Data encrypted")
        return encrypted_data

    # noinspection PyMethodMayBeStatic
    def decrypt(self, data: bytes) -> bytes:
        """
        Decrypts and unpads data using internal key and initialization vector.


        :param data: The encrypted data to decrypt (byte representation).
        :return: The data decrypted (byte representation).
        """

        aes_cipher = AES.new(self.key, AES.MODE_CBC, self.iVector)

        # decrypt each encrypted sequence of bytes.
        decrypted_data = aes_cipher.decrypt(data)

        # unpad each sequence of bytes.
        padding_size = decrypted_data[-1]  # on extrait le dernier octet des donnes, qui contient le padding size utilisee lors du chiffrement
        unpadded_data = decrypted_data[:-padding_size]

        # TODO deserialize each sequence of bytes to a record.
        
        self.logger.info("Data decrypted.")
        return unpadded_data


@dataclass
class Bucket(LoggingHandler):
    """
    Holds encrypted records and overflow arrays, along with the original data type
    (the original data type is useful for un-serializing bytes when decrypting data).
    """

    records: bytes
    overflow: bytes
    dtype: np.dtype

    @classmethod
    def from_records(
        cls,
        records: np.ndarray,
        records_len: int,
        overflow_len: int,
        kc: Keychain,
        dummy_value: int,
    ):
        """
        Creates a new Bucket from a numpy array containing the cleartext records to store.

        :param records: The cleartext records to store (either as encrypted records or in the overflow array)
        :param records_len: Number of encrypted records (i.e., equal to the perturbed count of the node).
        :param overflow_len: Number of records in the overflow array.
        :param kc: Cryptographic material.
        :param dummy_value: The value with which dummy records must be filled.
        :return: The corresponding bucket.
        """
        dtype = records.dtype  # store dtype
        records, overflow = np.split(
            records, [records_len]
        )  # split between records and overflow
        overflow = np.pad(
            overflow,
            (0, max(0, overflow_len - len(overflow))),
            mode="constant",
            constant_values=dummy_value,
        )  # pad if less than total

        # Convert to bytes and encrypt the two data structures holding the records.
        records = kc.encrypt(records.tobytes())
        overflow = kc.encrypt(overflow.tobytes())
        # new bucket
        bucket = Bucket(records=records, overflow=overflow, dtype=dtype)
        bucket.logger.info(f"new Bucket({records_len}, {overflow_len})")
        return bucket

    def to_records(
        self, keychain: Keychain, index_col="age", dummy_value=-1
    ) -> np.ndarray:
        """
        Decrypts to a numpy array the encrypted records stored in a Bucket
        (both in the encrypted records and in the overflow array). Filters
        out dummy records.

        :param keychain: The cryptographic material
        :param index_col: The index of the column on which the PinedRQ index is built.
        :param dummy_value: The value indicating a dummy record.
        :return: The cleartext records decrypted without any dummies.
        """
        # restore dtype after decryption and stack
        r = np.hstack(
            [
                np.frombuffer(keychain.decrypt(self.records), dtype=self.dtype),
                np.frombuffer(keychain.decrypt(self.overflow), dtype=self.dtype),
            ]
        )
        # We do not remove dummies in order to count them as false positives.
        # Remove dummies.
        # dummies = r[index_col] == dummy_value
        # self.logger.info(f"skipping {np.sum(dummies)} dummies")
        # r = r[~dummies]
        return r


@dataclass(repr=False)
class Node(LoggingHandler):
    """
    Internal nodes and leaves.
    Warning: we consider that all the higher bounds of nodes are exclusive,
    whereas the higher bounds of the rightmost nodes of the hierarchy of histograms should be inclusive.
    """

    left: float  # Lower bound (inclusive)
    right: float  # Higher bound (exclusive). WARNING : should be inclusive for the rightmost node of each level.
    count: int  # number of items
    children: Sequence["Node"]  # if intermediate, else []
    bucket: Optional[Bucket]  # if node, else None

    def __iter__(self):
        """
        Recursive iterator over nodes and children.

        :return: The next children.
        """
        yield self
        yield from chain.from_iterable(self.children)

    @property
    def is_node(self):
        """
        Attribute is true iff node is a node, ie has no children.

        :return: True if self is a node, False otherwise.
        """
        return not self.children

    @classmethod
    def from_children(cls, children: Sequence["Node"]):
        """
        Builds new Node from a sequence of children Nodes. Propagate left, right and count.

        :param children: The sequence of children of the Node being built.
        :return: The Node.
        """
        left = min(child.left for child in children)
        right = max(child.right for child in children)
        count = sum(child.count for child in children)
        node = cls(left=left, right=right, count=count, children=children, bucket=None)
        node.logger.info(f"new Node({left}, {right}, {count})")
        return node

    def leaves(self):
        """
        Recursive iterator over the leaves accessible from any Node.

        :return: Next node.
        """
        if self.is_node:
            yield self
        else:
            yield from chain.from_iterable(child.leaves() for child in self.children)

    @property
    def levels(self) -> int:
        """
        Attribute of (inclusive) depth from node.

        :return:
        """
        return 1 if self.is_node else 1 + self.children[0].levels

    def __repr__(self):
        """
        Recursive pretty-print.

        :return: A string representation of a hierarchy of Nodes.
        """

        def pretty(node: Node, indent=0, precision=2):
            s = "\t" * indent
            s += f"[{round(node.left, precision)}, {round(node.right, precision)}[ {node.count}"
            if not node.is_node:
                for child in node.children:
                    s += "\n" + pretty(child, indent + 1)
            return s

        return pretty(self)

        

class Index(LoggingHandler):
    """
    The PinedRQ index.
    """

    def __init__(
        self,
        keychain: Keychain,
        data: np.ndarray,
        index_col: str,
        bins: int,
        degree: int,
        epsilon: float,
        proba_dp: float,
        dummy_value: int,
    ):
        """
        Create index.

        :param keychain: Cryptographic material.
        :param data: The dataset to index.
        :param index_col: The column on which the index must be built.
        :param bins: The number of bins.
        :param degree: The degree of the hierarchy of histrograms.
        :param epsilon: The differential privacy epsilan parameter.
        :param proba_dp: The probability that differential privacy holds.
        """

        # Column to index.
        index = data[index_col]
        # Step 1: build the leaves.
        leaves = self._build_histogram_leaves(index, bins)
        # Step 2: build the hierarchy on top of the leaves.
        self.root = root = self._build_histogram_tree(leaves, degree)
        # Compute the number of levels of the hierarchy.
        nlevels = root.levels
        # Step 3: Perturb the counts of all nodes.
        self._perturb_histogram(root, epsilon, nlevels)
        # Step 4: build the buckets at the bottom of the hierarchy.
        print("type(keychain)=", type(Keychain))
        print("type(data)=", type(data))
        print("type(index)=", type(index))
        print("type(root)=", type(root))
        print("type(proba_dp)=", type(proba_dp))
        print("type(epsilon)=", type(epsilon))
        print("type(nlevels)=", type(nlevels))
        print("type(dummy_value)=", type(dummy_value))
        self._build_buckets(
            keychain, data, index, root, proba_dp, epsilon, nlevels, dummy_value
        )


    def _build_histogram_leaves(self, index: np.ndarray, bins: int) -> List[Node]:
        """
        Build the leaves of the hierarchy of histograms by
        computing the lowest level histogram of the hierarchy.

        :param index: The indexed column.
        :param bins: The number of bins.
        :return: The list of leaves.
        """
        self.logger.info(f"Building leaves.")
        counts, bin_edges = np.histogram(index, bins)
        leaves = [
            Node(left=left, right=right, count=count, children=[], bucket=None)
            for left, right, count in zip(bin_edges[:-1], bin_edges[1:], counts)
        ]
        return leaves

    def _build_histogram_tree(self, leaves: List[Node], degree: int) -> Node:
        """
        Builds a hierarchy of histograms bottom-up from a set of leaves.

        :param leaves: The leaves as a list of Nodes.
        :param degree: The maximum degree of the hierarchy.
        :return: The root of the hierarchy (a single Node).
        """
        self.logger.info(f"Building the hierarchy of histograms.")
        level = leaves
        # For each level, form the corresponding set of nodes from the level below.
        while len(level) > degree:
            level = [
                Node.from_children(list(children)) for children in chunks(level, degree)
            ]
        # Terminate by forming the root.
        root = Node.from_children(level)

        return root

    def _perturb_histogram(self, root: Node, epsilon: float, nlevels: int):
        """
        Add Laplace perturbations to the counts of all nodes in the hierarchy of histograms
        so that epsilon-differential privacy is satisfied.

        distribute the privacy budget
        generate the perturbations.
        apply the perturbations to the counts of the nodes.

        :param root: The root of the hierarchy of histograms.
        :param epsilon: The differential privacy epsilon parameter.
        :param nlevels: The number of levels of the hierarchy of histograms.
        :return: Nothing
        """

        self.logger.info(f"Perturbing the counts with epsilon = ",epsilon)
        print(f"Perturbing the counts with epsilon = {epsilon}")

        # If we wanted to use an uniform privacy budget for each level
        # privacy_budget_per_level = epsilon / nlevels
        # And then we could have calculated the scale_factor = 1 / privacy_budget_per_level 

        # We compute the different scale factors for each level (using the formula seen in class : Scount / 1/2^i x Etotal)
        scale_factors = [ 1 /((1/2**i)* epsilon) for i in range(nlevels) ]
        print(f"Scale factors : {scale_factors}")
        print(f"Levels : {nlevels}")
        
        for i in range(nlevels):

            # We obtain only the nodes for the level i
            nodes_at_level = [node for node in root if node.levels == i + 1]

            for node in nodes_at_level:
                scale = scale_factors[i] # The corresponding scale factor of the level

                # Generate the perturbation
                #noise = np.random.laplace(0,scale)  # Using numpy module
                noise = laplace.rvs(scale=scale)  # Using the scipy module

                # Apply the perturbations to the count of the node
                count_before_noise = node.count
                node.count += int(round(noise))

                if node.count < 0:
                    node.count = 0

                self.logger.info(f"Level: {i+1}, Node's count perturbed by {round(noise)}")

                print(f"Level: {i+1}, Node's count : {count_before_noise} perturbed by {round(noise)}, new count : {node.count}")

 
    def _build_buckets(self, kc: Keychain, data: np.ndarray, index: np.ndarray, root: Node, proba_dp: float, epsilon: float, nlevels: int, dummy_value: int):
        """
        Build the buckets used for storing encrypted records and overflow arrays.

        :param kc: The cryptographic material for encrypting records.
        :param data: The dataset.
        :param index: The indexed column.
        :param root: The root of the hierarchy of histograms.
        :param proba_dp: The probability that differential privacy  is satisfied.
        :param epsilon: The differential privacy epsilon parameter
        :param nlevels: The number of levels of the hierarchy of histograms.
        :return: Nothing.
        """
        
        self.logger.info(f"Building buckets.")
        overflow_len = max(
            0, int(np.ceil(laplace.ppf(proba_dp, loc=0, scale=nlevels / epsilon)))
        )

        for node in root.leaves():
            r = data[(node.left <= index) & (index < node.right)]
            node.bucket = Bucket.from_records(
                r, node.count, overflow_len, kc, dummy_value
            )

    def __repr__(self):
        """
        The string representation of the index is the string representation of its root.

        :return: The string representation of the root.
        """
        return repr(self.root)

    def query(self, left: float, right: float) -> Sequence[Bucket]:
        """
        Improved query execution strategy that improves precision.
        The naive query processing has been kept in the method naive_query.

        :param left: The lower bound of the query (inclusive).
        :param right: The higher bound of the query (inclusive).
        :return: A sequence of buckets.
        """
        self.logger.info(f"Improved query received: [{left}, {right}].")

        # We will collect only the buckets whose nodes are in the interval
        relevant_buckets = []
        
        leaves = self.root.leaves()
        for leaf in leaves:
            if left <= leaf.right and  leaf.left < right : 
                relevant_buckets.append(leaf.bucket)

        return relevant_buckets

    def naive_query(self, left: float, right: float) -> Sequence[Bucket]:
        """
        Naive query execution strategy. Returns the full list of buckets.

        TODO: design and implement a less naive query execution strategy (keep the naive query execution strategy in a dedicated function in order to facilitate comparison).

        :param left: The lower bound of the query (inclusive).
        :param right: The higher bound of the query (inclusive).
        :return: A sequence of buckets.
        """
        self.logger.info(f"Query received: [{left}, {right}].")
        self.logger.info("Naive query execution: return the full sequence of buckets.")
        leaves = self.root.leaves()
        buckets = [node.bucket for node in leaves]
        return buckets



class Manager:
    """
    The data manager holds the raw dataset, computes the PinedRQ index, and sends it to the cloud.
    It sends to clients the cryptographic material needed for decrypting records.
    """

    def __init__(
        self,
        data: np.array,
        index_col: str,
        bins: int,
        degree: int,
        epsilon: float,
        proba_dp: float,
        dummy_value: int,
    ):
        """
        Initialize the data manager by setting up the cryptographic material
        (keychain) and generating the PinedRQ index.

        :param data: The dataset.
        :param index_col: The column that will be indexed by PinedRQ (a string).
        :param bins: Number of unit bins to build at the bottom of the hierarchy
        (i.e., equal to the number of leaves).
        :param degree: Maximum degree of the hierarchy.
        :param epsilon: Differential privacy epsilon parameter.
        :param proba_dp: Probability to satisfy differential privacy.
        """
        self.data = data
        self.index_col = index_col
        self.bins = bins
        self.degree = degree
        self.epsilon = epsilon
        self.proba_dp = proba_dp

        # Setup the cryptographic material
        self.keychain = Keychain()

        # Build the index.
        self.index = Index(
            self.keychain, data, index_col, bins, degree, epsilon, proba_dp, dummy_value
        )


class DemoManager(Manager):
    """
    Data manager dedicated to build a PinedRQ index on the age column of the adult dataset.
    """

    def __init__(self, bins: int, degree: int, epsilon: float, proba_dp: float):
        """
        Same as the superclass except that it loads the adult dataset and that it sets dummy values (to -1).

        :param bins: Number of unit bins to build at the bottom of the hierarchy
        (i.e., equal to the number of leaves).
        :param degree: Maximum degree of the hierarchy.
        :param epsilon: Differential privacy epsilon parameter.
        :param proba_dp: Probability to satisfy differential privacy.
        """
        data = load_adult()
        index_col = "age"
        super().__init__(
            data, index_col, bins, degree, epsilon, proba_dp, dummy_value=-1
        )


class Cloud(LoggingHandler):
    """
    The cloud stores the index and answers to range queries from clients on it.
    """

    def __init__(self, index: Index):
        self.index = index

    def query(self,left: float, right: float) -> Sequence[Bucket]:
        """
        Range query execution strategy. Its goal is to find the buckets whose records fall within the given range.

        :param left: The minimum value of the range (included).
        :param right: The maximum value of the range (included).
        :return: A sequence of buckets.
        """
        self.logger.info(f"Query received: [{left}, {right}]")
        return self.index.query(left, right)


class Client(LoggingHandler):
    """
    A client sends range queries to the cloud, decrypts the encrypted records stored in the buckets received,
    and filters out dummies and false positives.
    """

    def __init__(self, cloud: Cloud, keychain: Keychain, index_col: str):
        """
        The client must have a cloud to query, the cryptographic material necessary for decrypting the encrypted
        records, and must know the column on which the PinedRQ index is built.

        :param cloud: The cloud.
        :param keychain: The cryptographic material.
        :param index_col: The indexed column.
        """
        self.cloud = cloud
        self.keychain = keychain
        self.index_col = index_col

    def query(self, left: float, right: float) -> Optional[np.ndarray]:
        """
        Sends a range query to the cloud, decrypts the encrypted records stored in the buckets received
        and filters out dummies and false positives. Returns the decrypted records that are true positives.

        :param left: Lower bound of the range query (inclusive).
        :param right: Higher bound of the range query (inclusive).
        :return: The array containing the decrypted records retrieved or None.
        """

        self.logger.info(f"Query received: [{left}, {right}]")
        # Send the query to the cloud.
        buckets = self.cloud.query(left, right)
        # Decrypts the encrypted records, unpads them, un-serialize them (does not filter out the dummies).
        records = [
            bucket.to_records(self.keychain, self.index_col) for bucket in buckets
        ]
        if records:
            # Stack the records column-wise to form a single array.
            data = np.hstack(records)
            return data
