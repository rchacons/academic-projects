# PinedRQ Implementation

This directory contains the implementation of PinedRQ, a differentially private index for range query processing in cloud environments.

## Project Components

- `pinedrq.py`: Core implementation of the PinedRQ index, including:
  - `Keychain` class for AES encryption and decryption
  - `Bucket` class for storing encrypted records
  - `Node` class for the hierarchical histogram structure
  - `Index` class implementing the differentially private index
  - `Cloud` class simulating a cloud service provider
  - `Client` class for sending queries and processing responses
  - `Manager` class for setting up the cryptographic materials and index

- `RunMe.ipynb`: Jupyter notebook with experiments that:
  - Demonstrates the functioning of the PinedRQ index
  - Analyzes precision and recall under different parameter settings
  - Visualizes the results

- `utils.py`: Utility functions for data loading and processing
- `adult.data` and `adult-small.data`: Datasets for testing

## Key Parameters

- **bins**: Number of nodes in the histogram. More nodes results in more perturbation.
- **degree**: Influences the amount of noise added. Higher values add more noise but reduce precision.
- **epsilon**: Differential privacy parameter controlling the Laplace noise. Larger values mean more noise and stronger privacy.
- **proba_dp**: Probability of satisfying differential privacy criteria. Higher values mean higher complexity.
- **frac**: Fraction of the domain definition that the query interrogates.

## Running the Code

The implementation can be explored by running the Jupyter notebook:

```bash
jupyter notebook RunMe.ipynb
```
