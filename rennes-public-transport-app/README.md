# Rennes Public Transport App

An Android application for tracking and viewing schedules for the STAR public transportation system in Rennes, France. This project was developed as part of the "MOB" (Mobile Development) university course in my 1st Year of Master.

## Project Overview

This application allows users to access real-time data from the STAR transportation network in Rennes. It downloads and processes the official GTFS (General Transit Feed Specification) data, stores it locally in a database, and provides a user-friendly interface for viewing routes, stops, and schedules.

## Features

- **Transit Data Download**: Automatically downloads the latest GTFS data from the official STAR transportation API
- **Offline Access**: Stores all transit data locally using Room database for offline access
- **Route Information**: View all available bus/metro routes with their details
- **Stop Lookup**: Find nearby stops or search for specific stops
- **Schedule Viewing**: Access timetables for any stop and route
- **Search Functionality**: Find routes and stops by name or location
- **Real-time Updates**: Check for and download updated transit data when available

## Technologies Used

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence Library (SQLite)
- **Concurrency**: Kotlin Coroutines for asynchronous operations
- **File Processing**: CSV parsing and ZIP file handling
- **UI Components**: Fragments, RecyclerViews, and Material Design components

## Project Structure

- **Database Package**: Contains Room database implementation with DAOs and entity classes
  - Entity classes for routes, stops, trips, calendars, and stop times
  - DAOs for database operations
- **Fragments**: UI components for different app sections
  - Route listings
  - Stop details
  - Schedule viewing
  - Search functionality
- **Adapters**: RecyclerView adapters for displaying data
- **Network**: Handles downloading and processing GTFS data files

## Development Challenges

This project required solving several challenges:
- Efficiently processing large CSV files from the GTFS feed
- Designing a database schema optimized for transit data queries
- Implementing a responsive UI that works well with large datasets
- Managing asynchronous operations for data downloading and processing
- Providing a smooth user experience with minimal loading times

## Learning Outcomes

Through this project, I gained experience with:
- Modern Android development using Kotlin
- Implementing MVVM architecture
- Working with Room database for local data storage
- Using Coroutines for asynchronous programming
- Processing and displaying real-world transportation data
- Designing intuitive user interfaces for data-heavy applications

---

*Note: This project was developed for educational purposes during my university studies and showcases my mobile application development skills.*
