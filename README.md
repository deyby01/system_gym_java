# System Gym

A workout routine management system. This is a **group project** that re-implements
in **Java** an application originally built in **C++**, now featuring a graphical user
interface and a database for persistent storage.

## About the project

The original C++ version managed gym exercises (Strength and Cardio) through a
console menu, keeping the data in memory. This Java version keeps the same core
idea and object model but modernizes it with:

- **Java** as the main language, preserving the object-oriented design
  (an `Exercise` base class with `Strength` and `Cardio` subclasses).
- **Swing (JPanel)** for the graphical interface, replacing the old console menu
  with visual panels and user interaction.
- **MySQL** as a local database, replacing the in-memory list so the data is
  stored permanently.
- **JDBC** to connect the Java application to the MySQL database.

## Features

- Create, update, delete and view exercises.
- Search exercises by intensity level.
- Generate a workout routine based on level and exercise availability.

## Tech stack

- Java 21
- Swing (JPanel) for the GUI
- MySQL (local database)
- JDBC (MySQL Connector/J)
- NetBeans + Ant (project build)

## Requirements

- JDK 21
- MySQL Server running locally
- A database named `system_gym` with the `exercises` table

## Database setup

```sql
CREATE DATABASE system_gym;
USE system_gym;

CREATE TABLE exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(20),
    intensity_level VARCHAR(20),
    estimated_time DECIMAL(5,2),
    description TEXT,
    last_used INT
);
```

Update the connection credentials in `src/system_gym/Conection.java` to match
your local MySQL user and password.

## How to run

1. Open the project in NetBeans.
2. Make sure the MySQL server is running.
3. Run the project (`System_gym.java` is the main class).

## Authors

Group project — Paradigms course, UNAB.
