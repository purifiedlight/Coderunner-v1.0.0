# CodeRunner: A Backend Service for Code Execution

CodeRunner is a backend service designed for executing code submitted via a REST API. It is built on the principles of a microservice architecture, where two independent components written in different technologies (Node.js and Java) collaborate to complete a single task.

## 🏛️ Architecture and Design

The application is divided into two main parts, allowing for better scalability and development flexibility.

- Node.js API Gateway: Receives HTTP requests, validates the input, and serves as the entry point to the system.

- Java Execution Engine: Handles the more intensive task—compiling and safely executing the code in an isolated environment.

### Flow Diagram:
````User --> (POST /api/execute) --> [Node.js API Gateway] --> (HTTP POST) --> [Java Execution Engine] --> (Executes Code) --> Returns Result````

## ⚠️ Important Security Warning

This project is a prototype and is not secure for deployment in a production environment.

Executing user-submitted code directly on the host system poses a serious security risk (Remote Code Execution). In a real-world application, it would be essential to use advanced sandboxing techniques, ideally by running each code snippet in a separate and ephemeral Docker container that is destroyed after execution.

## ✨ Key Features

  - Java Code Execution via a simple REST API.
  
  - Microservice Architecture for better scalability.
  
  - Clear Separation of Concerns between services.
  
  - Execution Timeout to protect against infinite loops.
  
  - Clear output and error messages in JSON format.

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

  - Node.js (v18.x or newer) and npm
  
  - Java Development Kit (JDK) (v17 or newer)
  
  - Apache Maven (v3.6 or newer)

## 🚀 Installation and Setup

  1. Run the Java Service (Execution Engine):
  
      - In a terminal, navigate to the java-execution-engine directory.
      
        ````
        cd java-execution-engine
        ````
      
      - Run the service:
      
        ````
        mvn spring-boot:run
        ````
  
      - The service will be running on http://localhost:8080. Keep this terminal open.

  2. Run the Node.js Service (API Gateway):

      - Open a second terminal and navigate to the node-api-gateway directory.
    
        ````
        cd node-api-gateway
        ````
    
      - Install the dependencies:
    
        ````
        npm install
        ````
    
      - Start the server:
    
        ````
        npm start
        ````
    
      - The server will be running on http://localhost:3000.

  ## 📖 API Documentation

  ### Execute Code
  
  Executes the provided Java code and returns its output or an error.
  
  - Endpoint: ````POST /api/execute````
  
  - Request Body:
  ````
  {
    "language": "java",
    "code": "public class Main { ... }"
  }
  ````

  - ````language```` (string, required): The programming language. Currently supported: ````"java"````.

  - ````code```` (string, required): The code to execute. Must contain a public class named ````Main```` with a ````main```` method.

  ### ✅ Success Response (200 OK)

  - Condition: The code was compiled and executed successfully without errors.
  
  - Response Body:
  
  ````
  {
      "output": "This is the program's output.",
      "error": null,
      "isSuccess": true
  }
  ````

  ### ❌ Error Response (Compilation or Internal Error)

  - Condition: The code failed to compile, or another server-side error occurred.

  - Response Body:

  ````
  {
      "output": null,
      "error": "Main.java:4: error: ';' expected\n        System.out.println(\"Error\")\n",
      "isSuccess": false
  }
  ````