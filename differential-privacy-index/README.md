# Differential Privacy Index (PinedRQ)

## Overview
A Python implementation of PinedRQ, a differential privacy-preserving index for secure range queries on outsourced data in cloud environments. This project was developed as part of the "Sécurité des Bases de Données" (SBD) class in my M1 MIAGE program, in collaboration with Manh-Huan Nguyen ([manh-huan](https://github.com/manh-huan)) and Hugo Thomas ([HugoThoma](https://github.com/HugoThoma)).

## Project Description
This project implements a differential privacy index for encrypted databases that enables secure range queries while maintaining privacy guarantees. The implementation uses:

- **AES encryption** for securing the data records
- **Laplace noise** to achieve differential privacy
- **Hierarchical histogram structure** for efficient range queries
- **Bucket-based storage** for encrypted records

The project demonstrates the trade-offs between security, privacy, and query efficiency in cloud-based database systems.

## Key Concepts
- **Differential Privacy**: A mathematical framework that guarantees privacy by adding controlled noise to query results
- **Range Queries**: Database queries that return records within a specified range of values
- **Encrypted Indexes**: Data structures that allow searching encrypted data without decryption

## Technologies Used
- Python
- NumPy & Pandas for data processing
- Matplotlib for visualization
- PyCryptodome for AES encryption
- Jupyter Notebook for interactive analysis

## Features
- Creation of a privacy-preserving index on encrypted data
- Implementation of range queries over encrypted data
- Analysis of precision and recall for different privacy parameters
- Visualization of query performance metrics

## Setup Instructions

### Environment Setup
1. Create a virtual environment:
```
python3 -m venv venv
```

2. Activate the virtual environment:
```
source venv/bin/activate
```

3. Install Python dependencies:
```
pip install -r pinedRQ/requirements.txt
```

### Running the Project
1. Launch Jupyter Notebook:
```
jupyter notebook
```

2. Open `pinedRQ/RunMe.ipynb` to run the experiments and visualize results

## Project Structure
- `pinedRQ/pinedrq.py`: Core implementation of the PinedRQ index
- `pinedRQ/RunMe.ipynb`: Jupyter notebook with experiments and visualizations
- `pinedRQ/utils.py`: Utility functions
- `pinedRQ/adult.data`: Adult dataset for testing
- `pinedRQ/requirements.txt`: Python dependencies

## Academic Context
This project was based on the research paper: "A Differentially Private Index for Range Query Processing in Clouds" by Cetin Sahin, Tristan Allard, Reza Akbarinia, Amr El Abbadi, and Esther Pacitti, presented at the 34th IEEE International Conference on Data Engineering (ICDE), 2018.

It was adapted to be part of a TP.