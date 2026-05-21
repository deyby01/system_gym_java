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
- JDBC (MySQL Connector/J — included in `lib/`)
- NetBeans + Ant (project build)

---

## Getting started (for collaborators)

Follow these steps to clone the repository and run it on your own machine so you
can contribute.

### 1. Prerequisites

Make sure you have installed:

- **JDK 21**
- **NetBeans** (with Java support)
- **MySQL Server** running locally

> The MySQL Connector/J driver is already included in the `lib/` folder, so you
> do **not** need to download it separately.

### 2. Clone the repository

```bash
git clone https://github.com/deyby01/<repo-name>.git
cd <repo-name>
```

### 3. Create the database and table

Open a MySQL session and run:

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

### 4. Create the database user

The application connects with a dedicated MySQL user (not `root`). Create the
same user the project expects so the connection works out of the box:

```sql
CREATE USER 'gym_user'@'localhost' IDENTIFIED BY 'Gym_2024!';
GRANT ALL PRIVILEGES ON system_gym.* TO 'gym_user'@'localhost';
FLUSH PRIVILEGES;
```

> If your MySQL uses a different host, port, user or password, update the values
> in `src/system_gym/Conection.java` accordingly:
>
> ```java
> String bd = "system_gym";
> String url = "jdbc:mysql://localhost:3306/";
> String user = "gym_user";
> String password = "Gym_2024!";
> ```

### 5. Open and run in NetBeans

1. In NetBeans: **File > Open Project** and select the cloned folder.
2. Make sure the **MySQL server is running**.
3. Run the project — the main class is `system_gym.System_gym`.
4. On startup it tests the database connection. If everything is set up correctly
   you should see in the Output window:
   ```
   Se conecto a la bd: system_gym
   ```

If you see `Access denied` or `Communications link failure`, double-check that
MySQL is running and that the user/password from step 4 match
`Conection.java`.

---

## Project structure

```
system_gym/
├── lib/                         # MySQL Connector/J driver (.jar)
├── src/system_gym/
│   ├── System_gym.java          # Main class (entry point)
│   ├── Conection.java           # MySQL connection (JDBC)
│   ├── Menu.java / Menu.form    # Swing panel (GUI)
│   └── ...                      # Model and DAO classes (in progress)
└── nbproject/                   # NetBeans/Ant project configuration
```

## How to contribute

1. Make sure you can run the project locally (steps above).
2. Create a branch for your changes:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your work and push the branch:
   ```bash
   git push -u origin feature/your-feature-name
   ```
4. Open a Pull Request on GitHub for review.

## Authors

Group project — Paradigms course, UNAB.
