# Architecture Report — Create & Update Exercise
### System Gym (Java) — Paradigms Course, UNAB

---

## 1. Object Model: `Exercise`, `Strength` and `Cardio`

The foundation of the system is the base class `Exercise`, which represents any gym
exercise regardless of its type. It contains the attributes common to all exercises:

```
id, name, type, intensityLevel, estimatedTime, description, lastUsed
```

Each attribute has its corresponding getter and setter, following the encapsulation
principle. The `type` field is key: it indicates whether the exercise is `"Strength"`
or `"Cardio"`.

The class has **two constructors via overloading**:
- One with `id` — used when the exercise already exists in the database and is retrieved from it.
- One without `id` — used when creating a new exercise, because the `id` is assigned
  automatically by MySQL via `AUTO_INCREMENT`.

```java
// With id → for data coming from the database
public Exercise(int id, String name, String type, String intensityLevel,
                double estimatedTime, String description, int lastUsed)

// Without id → for new exercises that don't have an id yet
public Exercise(String name, String type, String intensityLevel,
                double estimatedTime, String description, int lastUsed)
```

The classes `Strength` and `Cardio` **extend** `Exercise`. Their only responsibility
is to fix the value of the `type` field automatically via `super()`, so that no
outside code has to write it manually:

```java
public class Strength extends Exercise {
    public Strength(String name, String level, double time, String desc, int lastUsed) {
        super(name, "Strength", level, time, desc, lastUsed); // type fixed
    }
}

public class Cardio extends Exercise {
    public Cardio(String name, String level, double time, String desc, int lastUsed) {
        super(name, "Cardio", level, time, desc, lastUsed); // type fixed
    }
}
```

This enables **polymorphism**: throughout the entire codebase, the reference type is
`Exercise`, but the actual object is either `Strength` or `Cardio` depending on what
the user selected. For example:

```java
Exercise exercise;
if (selectedType.equals("Strength")) {
    exercise = new Strength(name, level, time, desc, 0);
} else {
    exercise = new Cardio(name, level, time, desc, 0);
}
```

The variable `exercise` is of type `Exercise`, but holds a more specific object. When
the DAO calls `exercise.getType()`, it correctly receives `"Strength"` or `"Cardio"`
without needing to ask which one it is.

---

## 2. The DAO Pattern — `ExerciseDAO`

The DAO (Data Access Object) pattern centralizes **all database access in a single
place**. No JFrame writes SQL directly — that is the exclusive responsibility of
`ExerciseDAO`. This follows the Single Responsibility Principle (SRP): forms display
data, the DAO handles it.

The analogy with Django's MVT: `ExerciseDAO` plays the role of the model/repository —
the view (JFrame) requests data without knowing anything about SQL.

The class has three fields:

```java
Conection con_db;   // MySQL connection wrapper
Connection conect;  // active JDBC connection
Statement st;       // object for executing SQL
```

The methods relevant to Create and Update are:

### `createExercise(Exercise e)`
Inserts a new exercise. Builds the SQL string from the object's data, executes
`executeUpdate()`, and returns `true` if at least one row was inserted:

```java
public boolean createExercise(Exercise e) {
    String sql = "INSERT INTO exercises(name, type, intensity_level, estimated_time, description, last_used)"
               + " VALUES('" + e.getName() + "', '" + e.getType() + "', '"
               + e.getIntensityLevel() + "', " + e.getEstimatedTime()
               + ", '" + e.getDescription() + "', " + e.getLastUsed() + ")";
    try {
        conect = con_db.connect();
        st = conect.createStatement();
        int rows = st.executeUpdate(sql);
        con_db.disconnect();
        return rows > 0;
    } catch (Exception err) {
        System.out.println("Error in create Exercise: " + err);
        return false;
    }
}
```

### `getExerciseById(int id)`
Retrieves an exercise by its ID to pre-fill the edit form. Executes a `SELECT`,
reads each column from the `ResultSet`, and returns an `Exercise` object, or `null`
if it does not exist:

```java
public Exercise getExerciseById(int id) {
    String sql = "SELECT * FROM exercises WHERE id=" + id;
    try {
        conect = con_db.connect();
        st = conect.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            // reads each column and builds the object
            return new Exercise(id, name, type, intensityLevel,
                                estimatedTime, description, lastUsed);
        }
        con_db.disconnect();
    } catch (Exception err) {
        System.out.println("Error fetching exercise by id: " + err);
    }
    return null;
}
```

### `updateExercise(Exercise e)`
Updates an existing exercise identified by its `id`. The object already has the `id`
set before the method is called:

```java
public boolean updateExercise(Exercise e) {
    String sql = "UPDATE exercises SET name='" + e.getName()
               + "', type='" + e.getType()
               + "', intensity_level='" + e.getIntensityLevel()
               + "', estimated_time=" + e.getEstimatedTime()
               + ", description='" + e.getDescription()
               + "', last_used=" + e.getLastUsed()
               + " WHERE id=" + e.getId();
    try {
        conect = con_db.connect();
        st = conect.createStatement();
        int rows = st.executeUpdate(sql);
        con_db.disconnect();
        return rows > 0;
    } catch (Exception err) {
        System.out.println("Error updating Exercise: " + err);
        return false;
    }
}
```

---

## 3. Main Menu — `Menu.java`

`Menu` extends `JFrame` and acts as the visual entry point of the system. On open,
it displays 6 buttons representing the system options. Each button has an
`ActionListener` bound to a method that opens the corresponding form.

For **Create Exercise**:
```java
private void CreateButtonActionPerformed(ActionEvent evt) {
    CreateExerciseForm form = new CreateExerciseForm(); // empty constructor = create mode
    form.setVisible(true);
}
```

For **Update Exercise**, the menu first asks for the ID via `JOptionPane`, queries the
DAO, and if the exercise exists opens the pre-filled form:

```java
private void UpdateButtonActionPerformed(ActionEvent evt) {
    String input = JOptionPane.showInputDialog(null, "Enter the ID of the exercise to update:");
    if (input == null || input.trim().isEmpty()) return;

    try {
        int id = Integer.parseInt(input.trim());
        ExerciseDAO dao = new ExerciseDAO();
        Exercise exercise = dao.getExerciseById(id);

        if (exercise == null) {
            JOptionPane.showMessageDialog(null, "Exercise not found.");
            return;
        }

        CreateExerciseForm form = new CreateExerciseForm(exercise); // edit mode
        form.setVisible(true);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID must be a number.");
    }
}
```

---

## 4. `CreateExerciseForm` — One Form for Both Create and Update

The most important design decision here was **reusing the same form** for both
operations. This is achieved through **constructor overloading** and a control variable:

```java
private Exercise exerciseToEdit = null; // null = create mode, object = edit mode
```

### Default constructor (create mode)
Opens the form blank, with no fields pre-filled.

### Constructor with `Exercise e` (edit mode)
Receives the exercise from the DAO and pre-fills every field:

```java
public CreateExerciseForm(Exercise e) {
    initComponents();
    setLocationRelativeTo(null);
    exerciseToEdit = e;

    jLabel1.setText("Update Exercise");
    SaveButton.setText("Update");
    NameField.setText(e.getName());
    EstimatedField.setText(String.valueOf(e.getEstimatedTime()));
    DescriptionField.setText(e.getDescription());

    if (e.getType().equals("Strength")) StrengthOption.setSelected(true);
    else CardioOption.setSelected(true);

    switch (e.getIntensityLevel()) {
        case "Beginner"          -> BeginnerOption.setSelected(true);
        case "Intermediate"      -> IntermediateOption.setSelected(true);
        case "Advanced"          -> AdvancedOption.setSelected(true);
        case "High Performance"  -> HighOption.setSelected(true);
    }
}
```

The radio buttons are organized into two `ButtonGroup` instances — one for type
(`Strength` / `Cardio`) and one for level (`Beginner`, `Intermediate`, `Advanced`,
`High Performance`) — which guarantees that only one option per group can be selected
at a time.

### `SaveButtonActionPerformed` — the complete flow

When the user presses Save or Update, the method executes four steps:

**Step 1 — Validation**
```java
boolean noTypeSelected  = !StrengthOption.isSelected() && !CardioOption.isSelected();
boolean noLevelSelected = !BeginnerOption.isSelected() && !IntermediateOption.isSelected()
                       && !AdvancedOption.isSelected()  && !HighOption.isSelected();

if (noTypeSelected || noLevelSelected) {
    JOptionPane.showMessageDialog(null, "Please select a type and a level.");
    return;
}
if (name.isEmpty()) {
    JOptionPane.showMessageDialog(null, "Name is required.");
    return;
}
// parseInt in try/catch to catch non-numeric input
```

**Step 2 — Object construction (polymorphism in action)**
```java
Exercise exercise;
if (selectedType.equals("Strength")) {
    exercise = new Strength(name, selectedLevel, estimatedTime, description, 0);
} else {
    exercise = new Cardio(name, selectedLevel, estimatedTime, description, 0);
}
```

**Step 3 — Create vs. Update decision**
```java
ExerciseDAO dao = new ExerciseDAO();
boolean success;

if (exerciseToEdit == null) {
    success = dao.createExercise(exercise);       // INSERT
} else {
    exercise.setId(exerciseToEdit.getId());
    success = dao.updateExercise(exercise);        // UPDATE
}
```

**Step 4 — User feedback**
```java
if (success) {
    String msg = (exerciseToEdit == null) ? "Exercise created successfully!"
                                          : "Exercise updated successfully!";
    JOptionPane.showMessageDialog(null, msg);
    this.dispose();
} else {
    JOptionPane.showMessageDialog(null, "Error: could not save the exercise.");
}
```

---

## 5. Connection Between Components

```
Menu.java
  │
  ├── CreateButtonActionPerformed
  │     └── new CreateExerciseForm()              ← create mode
  │           └── SaveButton → ExerciseDAO.createExercise(exercise)
  │                               └── INSERT INTO exercises (MySQL)
  │
  └── UpdateButtonActionPerformed
        ├── JOptionPane → asks for ID
        ├── ExerciseDAO.getExerciseById(id)        ← SELECT from MySQL
        └── new CreateExerciseForm(exercise)       ← edit mode, fields pre-filled
              └── SaveButton → ExerciseDAO.updateExercise(exercise)
                                  └── UPDATE exercises WHERE id=X (MySQL)

ExerciseDAO ←→ Conection.java (JDBC) ←→ MySQL (table: exercises)

Exercise  (base class — polymorphic reference)
  ├── Strength  (type fixed to "Strength" via super())
  └── Cardio    (type fixed to "Cardio"   via super())
```

The flow always goes in the same direction:
**Form collects user input → builds an `Exercise` object → passes it to the DAO →
DAO executes SQL → returns a boolean → form shows the result.**

---

*System Gym — Group project, Paradigms course, UNAB.*
