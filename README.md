```markdown
# Java Backend & CLI Assignment

## Overview

This project implements a Java backend REST API with in-memory storage and a standalone Java CLI client that communicates with the backend over HTTP. The system is designed to demonstrate client-server architecture without using external database systems or heavy frameworks.

The system is divided into two main components:

* **Backend Server:** A Java Servlet-based application deployed on Apache Tomcat, using in-memory storage (Lists/Maps). No database and no authentication are used.
* **CLI Client:** A standalone Java application that sends HTTP requests to the backend and displays results in the terminal.

This implementation follows the strict requirements described in the assignment document.

---

## Project Structure

```text
java-Assignment/
├── src/                      # Backend source code
│   ├── controller/           # Servlets (API layer)
│   ├── model/                # Domain models
│   └── services/             # In-memory services
│
├── Java-Assignment/
│   └── WEB-INF/
│       ├── classes/          # Compiled backend classes
│       └── lib/              # gson.jar
│
├── cli/
│   ├── Main.java             # CLI source code
│   └── out/                  # Compiled CLI classes
│
└── README.md

```

## Technologies Used

* **Language:** Java (JDK 11+)
* **Server:** Apache Tomcat 10.x
* **Servlet API:** Jakarta Servlets
* **JSON Processing:** Gson (for serialization/deserialization)
* **Storage:** In-memory storage (Map, List)
* **Networking:** HTTP (`HttpURLConnection` / `HttpClient`)

---

## Backend API

**Base URL:** `http://localhost:8080/Java-Assignment`

### Endpoints

#### 1. Create Car

* **Method:** `POST`
* **URL:** `/api/cars`
* **Content-Type:** `application/json`
* **Body:**
```json
{
  "brand": "Toyota",
  "model": "Corolla",
  "year": 2018
}

```



#### 2. List Cars

* **Method:** `GET`
* **URL:** `/api/cars`

#### 3. Add Fuel Entry

* **Method:** `POST`
* **URL:** `/api/fuel`
* **Content-Type:** `application/json`
* **Body:**
```json
{
  "carId": 1,
  "liters": 40,
  "price": 52.5,
  "odometer": 45000
}

```



#### 4. Fuel Statistics

* **Method:** `GET`
* **URL:** `/api/fuel/stats?carId=1`
* **Response:** Returns aggregated statistics for a specific car.

---

## CLI Commands

The CLI communicates with the backend using HTTP. Ensure the backend is running before executing these commands.

### Create Car

```bash
java -cp cli\out cli.Main create-car --brand Toyota --model Corolla --year 2018

```

### Add Fuel Entry

```bash
java -cp cli\out cli.Main add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000

```

### Get Fuel Statistics

```bash
java -cp cli\out cli.Main fuel-stats --carId 1

```

**Expected Output Format:**

```text
Total fuel: 120 L
Total cost: 155.00
Average consumption: 6.4 L/100km

```

---

## How to Run the Project

### Prerequisites

* Java JDK 11 or later
* Apache Tomcat 10.x
* Gson JAR file
* Windows PowerShell or Terminal

### Step 1: Compile the Backend

From the project root directory:

```powershell
javac -cp "C:\Program Files\Apache Software Foundation\Tomcat 10.1\lib\servlet-api.jar;C:\path\to\gson-2.10.1.jar" ^
-d Java-Assignment/WEB-INF/classes ^
src/controller/*.java src/model/*.java src/services/*.java

```

*(Note: Adjust the paths to `servlet-api.jar` and `gson.jar` based on your local installation.)*

### Step 2: Deploy Backend to Tomcat

1. Copy the `Java-Assignment` folder into your Tomcat `webapps` directory:
```text
Tomcat/webapps/Java-Assignment

```


2. Start (or restart) Tomcat.
3. Verify the backend is running by visiting:
`http://localhost:8080/Java-Assignment/api/cars`

### Step 3: Compile the CLI

From the project root directory:

```powershell
javac -d cli\out cli\Main.java

```

### Step 4: Run CLI Commands

Use the commands listed in the **CLI Commands** section above to interact with your application.

---

## Notes

* **Data Persistence:** All data is stored in memory. The data resets whenever Tomcat is restarted.
* **Security:** No authentication is required; this is a development demonstration.
* **Architecture:** The Backend and CLI are separate Java programs but share the same source repository for submission purposes.

## Assignment Compliance

* ✔ Java backend
* ✔ In-memory storage
* ✔ REST-style API
* ✔ Servlet-based implementation
* ✔ Standalone Java CLI
* ✔ HTTP communication
* ✔ No frameworks (Spring, Hibernate)

---

**Author**
Ineza Prince

```