package codealpha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class StudentGradeTracker extends JFrame {
    private JTextField gradeInput;
    private JTextArea resultArea;
    private ArrayList<Integer> grades;

    public StudentGradeTracker() {
    
        setTitle("Student Grade Tracker");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        grades = new ArrayList<>();

        // Components
        JLabel titleLabel = new JLabel(" Student Grade Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        JLabel enterLabel = new JLabel("Enter Grade (0-100):");
        add(enterLabel);

        gradeInput = new JTextField(10);
        add(gradeInput);

        JButton addButton = new JButton("Add Grade");
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");

        add(addButton);
        add(calculateButton);
        add(clearButton);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        // Add Grade Button Action
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int grade = Integer.parseInt(gradeInput.getText());
                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(null, " Grade must be between 0 and 100!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        grades.add(grade);
                        resultArea.append("Added: " + grade + "\n");
                        gradeInput.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, " Invalid input! Enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Calculate Button Action
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (grades.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "⚠ No grades entered!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    int highest = Collections.max(grades);
                    int lowest = Collections.min(grades);
                    double average = grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);

                    resultArea.append("\n Student Grades Summary \n");
                    resultArea.append("Total Students: " + grades.size() + "\n");
                    resultArea.append("Average Grade: " + String.format("%.2f", average) + "\n");
                    resultArea.append("Highest Grade: " + highest + "\n");
                    resultArea.append("Lowest Grade: " + lowest + "\n");
                }
            }
        });

        // Clear Button Action
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grades.clear();
                resultArea.setText("");
                gradeInput.setText("");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentGradeTracker();
    }
}
