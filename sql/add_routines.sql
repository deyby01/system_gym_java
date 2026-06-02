-- Migration: add routine persistence tables
-- Run after the initial setup (fixtures.sql is separate)
--
-- Linux / macOS (terminal):
--   mysql -u gym_user -p system_gym < sql/add_routines.sql
--
-- Windows (cmd or PowerShell, from the project root):
--   mysql -u gym_user -p system_gym < sql\add_routines.sql
--
-- Alternative on any OS (from MySQL Workbench or a MySQL session):
--   source C:/path/to/project/sql/add_routines.sql

CREATE TABLE IF NOT EXISTS routines (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    level      VARCHAR(20)    NOT NULL,
    total_time DECIMAL(6,2)   NOT NULL,
    created_at TIMESTAMP      DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS routine_exercises (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    routine_id INT NOT NULL,
    exercise_id INT NOT NULL,
    position   INT NOT NULL,
    FOREIGN KEY (routine_id) REFERENCES routines(id) ON DELETE CASCADE
);

SELECT 'Routines tables created successfully.' AS '';
