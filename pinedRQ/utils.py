import logging
from typing import Sequence, Generator, TypeVar

import numpy as np


class LoggingHandler:
    """
    Internal use. Provides a class-specific logger as attribute
    """

    @property
    def logger(self):
        """
        logger is named <current class name>
        :return:
        """
        return logging.getLogger(f"{self.__class__.__name__}")


# for fun. Generic type used to annotate chunks below
_T = TypeVar("_T")


def chunks(seq: Sequence[_T], n) -> Generator[Sequence[_T], None, None]:
    """
    Split a sequence into evenly-sized chunks. See https://stackoverflow.com/a/312464 .

    :param seq: The sequence to split.
    :param n: The size of a chunk.
    :return:
    """
    for i in range(0, len(seq), n):
        yield seq[i : i + n]


def load_adult():
    """
    Load the adult dataset (csv) as numpy records.

    :return: A numpy array containing the rows of the CSV dataset as numpy records.
    """
    adult = np.genfromtxt(
        "adult.data",
        delimiter=", ",  # strip space
        dtype=None,  # infer data type
        names=True,  # use first row as header
        encoding="utf-8",
        missing_values="?",  # ? as nan
    )
    return adult
