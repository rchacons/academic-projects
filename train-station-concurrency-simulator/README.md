# Train Station Concurrency Simulator

A Java-based concurrent programming project that simulates a train station with multiple trains, passengers, and platform management using thread synchronization and concurrency control.

## Overview

This project demonstrates advanced concurrency concepts through a realistic train station simulation where:

- **Trains** arrive at the station, wait for available platforms, load passengers, and depart
- **Passengers** (Voyageurs) purchase tickets and board available trains
- **Platform management** (EspaceQuai) controls access to limited platform spaces
- **Ticket sales** (EspaceVente) handle concurrent ticket purchasing

## Project Structure

The project includes two versions:

### Version 1 (v1)
- Basic console-based simulation
- Located in `src/main/java/fr/istic/csr/gare/v1/`
- Simple thread-based implementation

### Version 2 (v2) 
- Enhanced version with REST API
- Located in `src/main/java/fr/istic/csr/gare/v2/`
- RESTful web service using Restlet framework
- Real-time monitoring and control via HTTP endpoints

## Key Components

### Core Classes

- **`Gare`**: Main station controller managing trains and passengers
- **`Train`**: Thread representing train entities with states (en route, waiting, at platform, departed)
- **`Voyageur`**: Thread representing passengers with states (en route, ticketed, boarded)
- **`EspaceQuai`**: Platform space manager with limited capacity
- **`EspaceVente`**: Ticket sales system

### Concurrency Features

- **Semaphore-based platform access control** - Limited number of trains can be at platforms simultaneously
- **Thread synchronization** for ticket purchasing and passenger boarding
- **Producer-consumer patterns** between trains and passengers
- **Resource management** with capacity constraints

## Technical Implementation

- **Language**: Java 11
- **Framework**: Restlet 2.4.3 for REST API (v2)
- **Build Tool**: Maven
- **Concurrency**: Java Threading, Semaphores, Synchronization

## API Endpoints (Version 2)

- `GET /trains` - View all trains and their status
- `POST /trains` - Add new trains to the simulation
- `GET /voyageurs` - View all passengers and their status  
- `POST /voyageurs` - Add new passengers to the simulation

## Running the Project

### Prerequisites
- Java 11 or higher
- Maven

### Build and Run
```bash
# Compile the project
mvn compile

# Run Version 1 (Console-based)
mvn exec:java -Dexec.mainClass="fr.istic.csr.gare.v1.MainV1"

# Run Version 2 (REST API)
mvn exec:java -Dexec.mainClass="fr.istic.csr.gare.v2.main.Main"
```

### Testing API (Version 2)
After starting the server on port 8124, use the provided test script:
```bash
chmod +x testCommands.sh
./testCommands.sh
```

Or test manually:
```bash
# View initial state
curl http://localhost:8124/trains
curl http://localhost:8124/voyageurs

# Add new train
curl -X POST -H 'Content-type:application/json' -d '{"capacite":100,"vitesse":250}' http://localhost:8124/trains

# Add new passenger
curl -X POST -H 'Content-type:application/json' -d '{"name":"PassengerA"}' http://localhost:8124/voyageurs
```

## Configuration

Key simulation parameters (defined in `Gare.java`):
- `GARE_CAPACITE_MAX = 5` - Maximum trains in station simultaneously
- `TRAIN_CAPACITE_MAX = 200` - Maximum passenger capacity per train
- `VOYAGEUR_MAX = 100` - Maximum number of passengers
- `ARRET_TRAIN = 5000ms` - Train boarding time

## Educational Value

This project demonstrates:
- **Thread lifecycle management** and coordination
- **Resource contention** and synchronization solutions
- **Deadlock prevention** strategies
- **Real-world concurrency scenarios** in transportation systems
- **REST API development** with concurrent backend processing

## Files

- `src/main/java/` - Source code for both versions
- `testCommands.sh` - API testing script
- `pom.xml` - Maven configuration
- `Diagramme V1.png`, `Diagramme V2.png` - Architecture diagrams
- `Rapport TP5 CHACON NGUYEN.pdf` - Project report (French)

## Contributors
- Roberto Chacon ([rchacons](https://github.com/rchacons))
- Manh-Huan Nguyen ([manh-huan](https://github.com/manh-huan))

## Academic Context

This project was developed as part of a Concurrent and Distributed Systems (CSR) course, focusing on practical implementation of concurrency concepts in a realistic simulation environment.
