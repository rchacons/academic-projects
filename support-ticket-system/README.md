# Support Ticket System

## Overview
A comprehensive support ticket management system with a Java backend REST API and an Angular frontend. This project was developed as part of the "Systèmes d'Information Répartis" (SIR) class in collaboration with Manh-Huan Nguyen ([manh-huan](https://github.com/manh-huan)) . It serves as an introduction to APIs and demonstrates the integration of frontend and backend technologies.

## Project Structure
The project consists of two main components:
- **SIR-TP2-backend**: Java-based REST API built with JAX-RS for ticket management
- **ticket-ui**: Angular-based user interface for interacting with the API

## Features
- Create and manage support tickets
- User management (regular users and support team members)
- View all tickets in the system
- Assign tickets to support team members

## Technologies Used
- **Backend**:
  - Java
  - JAX-RS for REST API
  - HSQLDB for database
  - Maven for dependency management
- **Frontend**:
  - Angular
  - TypeScript
  - Bootstrap for styling

## Setup Instructions

### Backend Setup
1. Navigate to the `SIR-TP2-backend` directory
2. Start the database server by running the script:
   ```bash
   ./run-hsqldb-server.sh   # For Linux/Mac
   # OR
   run-hsqldb-server.bat    # For Windows
   ```
3. Run the `RestServer` class in your IDE, or build and run using Maven:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="fr.istic.sir.rest.RestServer"
   ```
4. The API will be available at `http://localhost:8090`
5. API documentation can be accessed at `http://localhost:8090/api/`

### Frontend Setup
1. Navigate to the `ticket-ui` directory
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```
4. Access the application at `http://localhost:4200`


## CORS Configuration

To solve CORS issues, we created a `proxy.config.json` file with the API route configuration:

```json
{
    "/ticket-api": {
        "target": "http://127.0.0.1:8090",
        "secure": "false",
        "pathRewrite": {
            "^/ticket-api": ""
        },
        "logLevel": "debug"
    }
}
```

We added the proxy configuration to the Angular CLI configuration in `angular.json`:

```json
"serve": {
    "builder": "@angular-devkit/build-angular:dev-server",
    "options": {  
        "proxyConfig": "src/proxy.conf.json"
    }
}
```

And created a global environment variable in `environments.ts` for easier access throughout the code:

```typescript
export const environment = {
    production: false,
    ticketApi: "/ticket-api",
}
```

## API Models

- **Person**: Base class for users
  - **User**: End users who create tickets
  - **SupportMember**: Support team members who resolve tickets
- **Ticket**: Support requests with details, status, and assignments

The API documentation can be found at:
```
http://localhost:8090/api/
```

## Angular Services

The application has two main services that enable communication with the API:

- **Ticket Service**: Allows operations such as:
  - GET a ticket by its ID
  - GET all tickets
  - POST a ticket to the database

- **Person Service**: Allows operations such as:
  - GET a person by their ID
  - GET all people of type USER
  - GET all people of type SUPPORT-MEMBER
  - POST a user to the database
  - POST a support member to the database

## Angular Components
Our application implements various components based on different application pages:

- **Display Components**: 
  - `person-list`
  - `user-list` (inherits from person-list)
  - `support-member-list` (inherits from person-list)
  - `ticket-list`
  
  These components communicate with services to get data and display it on their respective pages.

- **Form Components**: 
  - `person-form`
  - `ticket-form`
  
  These are forms that allow sending POST requests to store data in the database.

- **Navigation Components**: 
  - `home`
  - `person`
  - `ticket`
  
  These components are menus that handle routing to display or registration components.

## Routing
The application implements Angular routing to navigate between different views. A route in its most basic form is the association of a component and a URL. When this URL is requested, the routing module renders the associated component.

To make this association of a URL to a component, we define a variable of type Routes. This type is declared as an array where each element is an object of type Route.

A route consists of a path attribute that represents the URL (relative or absolute) associated with this route and a component attribute that is the component to load when this route is called.

![Routing Diagram](./route.png)

## Contributors
- Roberto Chacon
- Manh-Huan Nguyen

## Acknowledgements
This project was developed as part of the "Systèmes d'Information Répartis" class, based on initial work by Professor Barais.

![Routing Diagram](./route.png)

## Contributors
- Roberto Chacon ([rchacons](https://github.com/rchacons))
- Manh-Huan Nguyen ([manh-huan](https://github.com/manh-huan))

## Acknowledgements
This project was developed as part of the "Systèmes d'Information Répartis" class.