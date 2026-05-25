package system_gym;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SearchExerciseByLevelForm extends JFrame {

    private JComboBox<String> LevelCombo;
    private JButton SearchButton;
    private JTable ExerciseTable;
    private DefaultTableModel tableModel;

    public SearchExerciseByLevelForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Search Exercise by Level");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Search Exercise by Level", JLabel.CENTER);
        title.setFont(new java.awt.Font("Liberation Sans", 1, 30));
        add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout());

        LevelCombo = new JComboBox<>(new String[]{
            "Beginner",
            "Intermediate",
            "Advanced",
            "High Performance"
        });

        SearchButton = new JButton("Search");

        topPanel.add(new JLabel("Level:"));
        topPanel.add(LevelCombo);
        topPanel.add(SearchButton);

        add(topPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Type", "Level", "Estimated Time", "Description", "Last Used"},
            0
        );

        ExerciseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ExerciseTable);

        add(scrollPane, BorderLayout.SOUTH);

        SearchButton.addActionListener(this::SearchButtonActionPerformed);
    }

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        tableModel.setRowCount(0);

        String level = LevelCombo.getSelectedItem().toString();

        ExerciseDAO dao = new ExerciseDAO();
        List<Exercise> exercises = dao.searchByLevel(level);

        for (Exercise exercise : exercises) {
            tableModel.addRow(new Object[]{
                exercise.getId(),
                exercise.getName(),
                exercise.getType(),
                exercise.getIntensityLevel(),
                exercise.getEstimatedTime(),
                exercise.getDescription(),
                exercise.getLastUsed()
            });
        }

        if (exercises.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exercises found for level: " + level);
        }
    }
}