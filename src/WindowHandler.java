import model.Student;
import model.StudentDataset;
import utils.Reader;
import utils.Writer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public class WindowHandler extends JFrame {
    private static final int width = 1024;
    private static final int height = 512;
    private static final int buttonWidth = 150;
    private static final int buttonHeight = 20;
    private static final int smallButtonWidth = buttonWidth / 3;

    private static JFrame frame;
    private static String input;
    private static StudentDataset studentDataset;

    public static void main(String[] args) {

        studentDataset = new StudentDataset();
        // set window
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PdP-Exams");
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        // set  panels and layout on side
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel examButtonPanel = new JPanel();
        examButtonPanel.setLayout(new BoxLayout(examButtonPanel, BoxLayout.X_AXIS));

        // buttons and text input
        TextField inputString = new TextField(20);
        Button readFile = new Button("read file");
        Button saveFile = new Button("generate files");
        Button search = new Button("search by name");
        Button sort = new Button("sort by name");

        Button exam0 = new Button("0 exams");
        Button exam1 = new Button("1 exam");
        Button exam2 = new Button("2 exams");

        // set dimensions
        inputString.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        readFile.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        saveFile.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        search.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        sort.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        examButtonPanel.setMaximumSize(new Dimension(buttonWidth, buttonHeight));

        exam0.setPreferredSize(new Dimension(smallButtonWidth, buttonHeight));
        exam1.setPreferredSize(new Dimension(smallButtonWidth, buttonHeight));
        exam2.setPreferredSize(new Dimension(smallButtonWidth, buttonHeight));

        // add buttons to panel
        buttonPanel.add(inputString);
        buttonPanel.add(readFile);
        buttonPanel.add(saveFile);
        buttonPanel.add(search);
        buttonPanel.add(sort);

        examButtonPanel.add(exam0);
        examButtonPanel.add(exam1);
        examButtonPanel.add(exam2);
        buttonPanel.add(examButtonPanel);

        // set action listeners
        readFile.addActionListener(e -> {
                    input = inputString.getText();
                    try {
                        studentDataset.setDataset(Reader.readFile(input));
                        if (studentDataset.getDataset() != null || !input.isEmpty()) {
                            showEntries(studentDataset.getDataset());
                            inputString.setText("");
                        }
                    } catch (IOException | InvalidPathException ex) {
                        System.err.println(ex.getMessage());
                        messageDialog("enter valid filepath");
                    }
                }
        );

        saveFile.addActionListener(e -> {
            input = inputString.getText();
            if (studentDataset.getDataset() == null && !input.isEmpty()) {
                try {
                    studentDataset.setDataset(Reader.readFile(input));
                } catch (IOException ex) {
                    messageDialog("enter valid filepath");
                }
            }
            if (studentDataset.getSortedStudents() != null && !studentDataset.getSortedStudents().isEmpty()) {
                if (Writer.writeFiles(studentDataset.getSortedStudents())) {
                    messageDialog("files generated");
                }
            } else if (studentDataset.getDataset() != null && !studentDataset.getDataset().isEmpty()) {
                if (Writer.writeFiles(studentDataset.getDataset())) {
                    messageDialog("files generated");
                }
            } else messageDialog("no students found");

        });

        search.addActionListener(e -> {
            String searchInput = inputString.getText();
            if (studentDataset.getDataset() != null) {
                studentDataset.searchStudentsByName(searchInput);
                showEntries(studentDataset.getSortedStudents());
            }
        });

        sort.addActionListener(e -> {
            if (studentDataset.getDataset() != null) {
                studentDataset.sortStudentsByName();
                showEntries(studentDataset.getSortedStudents());
            }
        });
        exam0.addActionListener(e -> {
            studentDataset.showStudentsByExam(0);
            showEntries(studentDataset.getSortedStudents());
        });

        exam1.addActionListener(e -> {
            studentDataset.showStudentsByExam(1);
            showEntries(studentDataset.getSortedStudents());
        });

        exam2.addActionListener(e -> {
            studentDataset.showStudentsByExam(2);
            showEntries(studentDataset.getSortedStudents());
        });

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    // shows a dynamic list of entries
    private static void showEntries(ArrayList<Student> students) {
        if (students == null) return;
        if (students.isEmpty()) {
            messageDialog("No students found");
            return;
        }
        // dynamic list
        DefaultListModel<Student> studentsList = new DefaultListModel<>();

        for (Student student : students) {
            studentsList.addElement(student);
        }
        JList<Student> list = getStudentJList(studentsList);
        JScrollPane scrollPane = new JScrollPane(list);

        // remove previous list if exists
        if (frame.getContentPane().getComponentCount() > 1) {
            frame.getContentPane().remove(1);
        }
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // better visualisation set text as you wish
    private static JList<Student> getStudentJList(DefaultListModel<Student> studentsList) {
        JList<Student> list = new JList<>(studentsList);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Student student) {
                    // set text here
                    String text = "<html>" +
                            "Name: " + student.getFirstName() + " " + student.getLastName() + "<br>" +
                            "Techfak: " + student.getTechfak() + "<br>" +
                            "Email: " + student.getEmail() + "<br>" +
                            "Percentage: " + student.getPercent() + "<br>" +
                            "Exams: " + student.getExam() +
                            "</html>";
                    label.setText(text);
                }
                return label;
            }
        });
        return list;
    }

    public static void messageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}

