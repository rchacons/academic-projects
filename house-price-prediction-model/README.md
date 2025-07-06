# House Price Prediction Model

## Overview
A machine learning project that implements various regression models to predict house prices based on multiple features. This project was developed as part of the "Méthodes de prévision à court terme" (MPC) course during my first year of Master's degree, in collaboration with Manh-Huan Nguyen ([manh-huan](https://github.com/manh-huan)).

## Project Description
This small project analyzes a housing dataset to build and evaluate different regression models for predicting house prices. The implementation explores:

- Simple linear regression
- Multiple linear regression
- Feature selection techniques (exhaustive search, forward selection, backward selection)
- Polynomial regression

The project follows a complete machine learning workflow from data exploration and preprocessing to model evaluation and deployment, culminating in a submission to a Kaggle competition.

## Dataset
The dataset contains information about 13,397 houses with 20 features including:
- Number of bedrooms and bathrooms
- Square footage of living space and lot
- Number of floors
- View quality
- Building condition and grade
- Year built and renovated
- Geographic location (zipcode, latitude, longitude)

## Key Techniques Implemented
- Data exploration and visualization
- Train/test split for model evaluation
- Feature importance analysis
- R² and MSE (Mean Squared Error) metrics for model evaluation
- Cross-validation
- Polynomial feature generation
- Model selection based on generalization error

## Technologies Used
- Python
- NumPy & Pandas for data processing
- Matplotlib for visualization
- Scikit-learn for machine learning models
- Statsmodels for statistical analysis
- Jupyter Notebook for interactive development

## Results
The analysis showed that:
1. Simple linear regression with square footage as a predictor achieved moderate predictive power
2. Multiple regression with all features improved performance significantly
3. Feature selection helped identify the most important predictors
4. Polynomial regression (degree 2) provided the best overall performance with an MSE of 3.57

Our final model was submitted to a Kaggle competition with satisfactory results, as shown in the included screenshot.

## Project Structure
- `tpFinal.ipynb`: Jupyter notebook containing all analysis and models
- `houses.csv`: Training dataset
- `houses_competition.csv`: Test dataset for predictions
- `my_submission.csv`: Final predictions for Kaggle submission
- `scoreKaggle.png`: Screenshot of competition results
- `RapportTPFinal.pdf`: Detailed project report (in French)
- `requirements.txt`: Required Python libraries