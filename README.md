# TaskManager_backend_Task1

# TaskManager Backend - REST API (Task 1)

## Overview

TaskManager is a Java Spring Boot backend application providing a REST API for managing and executing tasks. Each task represents a shell command that can be executed. Task executions are stored with start/end time and output.

## Tech Stack

* Java 17+
* Spring Boot
* MongoDB
* Gradle
* PowerShell / Curl for testing API

## Project Structure

```plaintext
TaskManager/
├─ src/main/java/com/task1/TaskManager/
│  ├─ controller/TaskController.java
│  ├─ model/Task.java
│  ├─ model/TaskExecution.java
│  └─ service/TaskService.java
├─ build.gradle
├─ settings.gradle
└─ application.properties

```



## Setup Instructions

1. Clone repository:

powershell
git clone https://github.com/mahi-88/TaskManager_backend_Task1.git
cd TaskManager


2. Start MongoDB (make sure MongoDB service is running on localhost:27017):


3. Build and run Spring Boot application:
   
./gradlew bootRun


The application will start at http://localhost:8080.

## REST API Endpoints

| Method | Endpoint                  | Description                         |
| ------ | ------------------------- | ----------------------------------- |
| GET    | /tasks                    | Get all tasks                       |
| GET    | /tasks?id={id}            | Get task by ID                      |
| PUT    | /tasks                    | Create or update a task (JSON body) |
| DELETE | /tasks/{id}               | Delete task by ID                   |
| GET    | /tasks/search?name={name} | Find tasks by name                  |
| PUT    | /tasks/{id}/executions    | Execute task command by ID          |

### Example Task JSON

```json
[
    {
        "id": "68f638032ec0bba5016eb71e",
        "name": "Print Hello",
        "owner": "Mahivardhan",
        "command": "echo Print Hello",
        "taskExecutions": [
            {
                "startTime": "2025-10-20T13:24:31.497+00:00",
                "endTime": "2025-10-20T13:24:31.561+00:00",
                "output": "Print Hello\n"
            }
        ]
    }
]
```


## Example Commands (Ubuntu and others) and Screenshots (commands work on Windows)

1. *Get all tasks:*


curl -X GET http://localhost:8080/tasks

![WhatsApp Image 2025-10-20 at 21 05 48_4b59f21a](https://github.com/user-attachments/assets/a76fc15b-5100-44b6-be66-335b80293da8)

2. *Get task by ID:*


curl -X GET http://localhost:8080/tasks?id=124

![WhatsApp Image 2025-10-20 at 21 09 39_73dc127c](https://github.com/user-attachments/assets/d41a7e8a-9150-4a90-81cd-2a81c09d674c)

3. *Create task:*

curl -X PUT http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"id":"123","name":"Print Hello","owner":"John Smith","command":"echo Hello World!"}'

![WhatsApp Image 2025-10-20 at 21 08 26_f083a19a](https://github.com/user-attachments/assets/ce2df3bd-eb08-476a-9bfc-a5b0d87d5ee9)


4. *Delete task:*

curl -X DELETE http://localhost:8080/tasks/124

![WhatsApp Image 2025-10-20 at 21 13 06_859eccd3](https://github.com/user-attachments/assets/21b4a976-faac-4473-9789-bc8b4952eeef)

5. *Search tasks by name:*

curl -X GET http://localhost:8080/tasks/search?name=print%20Date

![WhatsApp Image 2025-10-20 at 21 10 33_560911de](https://github.com/user-attachments/assets/984a5149-fbc6-4ec5-a11f-907e53d35b21)

6. *Run task execution:*


curl -X PUT http://localhost:8080/tasks/123/executions




---


