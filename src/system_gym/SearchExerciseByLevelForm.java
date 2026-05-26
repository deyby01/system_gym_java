package system_gym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SearchExerciseByLevelForm extends JFrame {

    private JComboBox<String> LevelCombo;
    private JButton SearchButton;
    private JTextField IdField;
    private JButton SearchByIdButton;
    private JLabel ImageLabel;
    private JTable ExerciseTable;
    private DefaultTableModel tableModel;
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);

    public SearchExerciseByLevelForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Search Exercise by Level");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Search Exercise by Level", JLabel.CENTER);
        title.setFont(new Font("Liberation Sans", Font.BOLD, 28));
        title.setForeground(TEXT_COLOR);
        title.setBorder(new EmptyBorder(24, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(0, 32, 28, 32));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        topPanel.setBackground(PANEL_COLOR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240)),
            new EmptyBorder(8, 14, 8, 14)
        ));

        LevelCombo = new JComboBox<>(new String[]{
            "Beginner",
            "Intermediate",
            "Advanced",
            "High Performance"
        });

        SearchButton = new JButton("Search");
        IdField = new JTextField(8);
        SearchByIdButton = new JButton("Search by ID");
        styleButton(SearchButton);
        styleButton(SearchByIdButton);
        LevelCombo.setPreferredSize(new Dimension(170, 32));
        IdField.setPreferredSize(new Dimension(90, 32));

        topPanel.add(new JLabel("Level:"));
        topPanel.add(LevelCombo);
        topPanel.add(SearchButton);
        topPanel.add(new JLabel("ID:"));
        topPanel.add(IdField);
        topPanel.add(SearchByIdButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        ImageLabel = new JLabel("Exercise Image", JLabel.CENTER);
        ImageLabel.setPreferredSize(new Dimension(820, 170));
        ImageLabel.setOpaque(true);
        ImageLabel.setBackground(PANEL_COLOR);
        ImageLabel.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        loadSearchImage();
        mainPanel.add(ImageLabel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Type", "Level", "Estimated Time", "Description", "Last Used"},
            0
        );

        ExerciseTable = new JTable(tableModel);
        ExerciseTable.setRowHeight(28);
        ExerciseTable.setShowGrid(false);
        ExerciseTable.setIntercellSpacing(new Dimension(0, 0));
        ExerciseTable.getTableHeader().setFont(new Font("Liberation Sans", Font.BOLD, 13));
        ExerciseTable.getTableHeader().setBackground(new Color(241, 245, 249));
        ExerciseTable.getTableHeader().setForeground(TEXT_COLOR);
        JScrollPane scrollPane = new JScrollPane(ExerciseTable);
        scrollPane.setPreferredSize(new Dimension(820, 260));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        SearchButton.addActionListener(this::SearchButtonActionPerformed);
        SearchByIdButton.addActionListener(this::SearchByIdButtonActionPerformed);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(115, 32));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
    }

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        tableModel.setRowCount(0);

        String level = LevelCombo.getSelectedItem().toString();

        ExerciseDAO dao = new ExerciseDAO();
        List<Exercise> exercises = dao.searchByLevel(level);

        for (Exercise exercise : exercises) {
            addExerciseToTable(exercise);
        }

        if (exercises.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exercises found for level: " + level);
        }
    }

    private void SearchByIdButtonActionPerformed(java.awt.event.ActionEvent evt) {
        tableModel.setRowCount(0);

        String idText = IdField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an exercise ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            ExerciseDAO dao = new ExerciseDAO();
            Exercise exercise = dao.getExerciseById(id);

            if (exercise == null) {
                JOptionPane.showMessageDialog(this, "No exercise found with ID: " + id);
                return;
            }

            addExerciseToTable(exercise);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void addExerciseToTable(Exercise exercise) {
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

    private void loadSearchImage() {
        java.net.URL imageUrl = getClass().getResource("/img/GYMSEARCH.png");

        if (imageUrl == null) {
            ImageLabel.setText("Image not found");
            return;
        }

        ImageIcon imageIcon = new ImageIcon(imageUrl);
        int maxWidth = 340;
        int maxHeight = 155;
        int originalWidth = imageIcon.getIconWidth();
        int originalHeight = imageIcon.getIconHeight();
        double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        Image scaledImage = imageIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageLabel.setIcon(new ImageIcon(scaledImage));
        ImageLabel.setText("");
    }
}
