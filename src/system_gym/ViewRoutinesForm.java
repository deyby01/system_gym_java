package system_gym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class ViewRoutinesForm extends JFrame {

    private JTable routinesTable;
    private JTable exercisesTable;
    private DefaultTableModel routinesModel;
    private DefaultTableModel exercisesModel;
    private List<Routine> routines;

    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);

    public ViewRoutinesForm() {
        initComponents();
        loadRoutines();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("View Saved Routines");
        setSize(900, 640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Saved Routines", JLabel.CENTER);
        title.setFont(new Font("Liberation Sans", Font.BOLD, 28));
        title.setForeground(TEXT_COLOR);
        title.setBorder(new EmptyBorder(24, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 16));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(0, 32, 12, 32));

        // --- Routines table ---
        routinesModel = new DefaultTableModel(
            new Object[]{"ID", "Level", "Total Time (min)", "Date"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        routinesTable = new JTable(routinesModel);
        styleTable(routinesTable);
        routinesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane routinesScroll = new JScrollPane(routinesTable);
        routinesScroll.setPreferredSize(new Dimension(820, 210));
        routinesScroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // --- Exercises section ---
        JLabel exercisesLabel = new JLabel("Exercises in selected routine:");
        exercisesLabel.setFont(new Font("Liberation Sans", Font.BOLD, 14));
        exercisesLabel.setForeground(TEXT_COLOR);
        exercisesLabel.setBorder(new EmptyBorder(8, 0, 4, 0));

        exercisesModel = new DefaultTableModel(
            new Object[]{"#", "Name", "Type", "Est. Time (min)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        exercisesTable = new JTable(exercisesModel);
        styleTable(exercisesTable);

        JScrollPane exercisesScroll = new JScrollPane(exercisesTable);
        exercisesScroll.setPreferredSize(new Dimension(820, 210));
        exercisesScroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 4));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.add(exercisesLabel, BorderLayout.NORTH);
        bottomPanel.add(exercisesScroll, BorderLayout.CENTER);

        // --- Close button ---
        JButton closeButton = new JButton("Close");
        styleButton(closeButton);
        closeButton.addActionListener(e -> this.dispose());

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(BACKGROUND_COLOR);
        footerPanel.setBorder(new EmptyBorder(8, 0, 16, 0));
        footerPanel.add(closeButton);

        mainPanel.add(routinesScroll, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        routinesTable.getSelectionModel().addListSelectionListener(this::onRoutineSelected);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Liberation Sans", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setForeground(TEXT_COLOR);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(115, 32));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
    }

    private void loadRoutines() {
        ExerciseDAO dao = new ExerciseDAO();
        routines = dao.getAllRoutines();
        routinesModel.setRowCount(0);
        for (Routine r : routines) {
            routinesModel.addRow(new Object[]{
                r.getId(), r.getLevel(), r.getTotalTime(), r.getCreatedAt()
            });
        }
        if (routines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No routines saved yet. Generate one first!");
        }
    }

    private void onRoutineSelected(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        int row = routinesTable.getSelectedRow();
        if (row < 0 || row >= routines.size()) return;

        Routine selected = routines.get(row);
        ExerciseDAO dao = new ExerciseDAO();
        List<Exercise> exercises = dao.getRoutineExercises(selected.getId());

        exercisesModel.setRowCount(0);
        int position = 1;
        for (Exercise ex : exercises) {
            exercisesModel.addRow(new Object[]{
                position++, ex.getName(), ex.getType(), ex.getEstimatedTime()
            });
        }
    }
}
