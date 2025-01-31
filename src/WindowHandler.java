import data.Student;
import data.StudentDataset;
import utils.Reader;
import utils.Writer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public class WindowHandler extends JFrame {
    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 512;
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 30;
    private static final int SMALL_BUTTON_WIDTH = BUTTON_WIDTH / 3;
    private static final String OPTIONS_TEXT = """
            use input field to look up students
            
            generated files contain filtered entries""";

    private static JFrame frame;
    private static String input;
    private static StudentDataset studentDataset;

    private static JPanel examButtonPanel;
    private static JPanel buttonPanel;


    private static TextField inputString;
    private static Button readFile;
    private static Button saveFile;
    private static Button search;
    private static Button sort;

    private static Button exam0;
    private static Button exam1;
    private static Button exam2;
    private static JTextArea textField;


    public static void main(String[] args) {

        studentDataset = new StudentDataset();
        // set window
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PdP-Exams");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        // set  panels and layout on side
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        examButtonPanel = new JPanel();
        examButtonPanel.setLayout(new BoxLayout(examButtonPanel, BoxLayout.X_AXIS));

        // buttons and text input
        inputString = new TextField(20);
        readFile = new Button("read file");
        saveFile = new Button("generate files");
        search = new Button("search by name");
        sort = new Button("sort by name");

        exam0 = new Button("0 exams");
        exam1 = new Button("1 exam");
        exam2 = new Button("2 exams");
        textField = new JTextArea(OPTIONS_TEXT);
        textField.getDocument().putProperty("filterNewlines", Boolean.FALSE);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setText("enter file path");


        // set dimensions
        setButtonDimensions();


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

        buttonPanel.add(textField);

        // set action listeners
        readFile.addActionListener(e -> {
                    input = inputString.getText();
                    try {
                        studentDataset.setDataset(Reader.readFile(input));
                        if (studentDataset.getDataset() != null || !input.isEmpty()) {
                            showEntries(studentDataset.getDataset());
                            inputString.setText("");
                            textField.setText(OPTIONS_TEXT);
                            setButtonDimensions();
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
                inputString.setText("");
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
                            "Email: " + student.getEmail() + "<br>" +
                            "Matrikel-Nr: " + student.getNumber() + "<br>" +
                            "Percentage: " + student.getPercent() + "% <br>" +
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


    public static void setButtonDimensions() {
        int textWidth = textField.getPreferredSize().width;
        int buttonWidth = BUTTON_WIDTH;
        int smallBtnWidth = SMALL_BUTTON_WIDTH;
        if (textWidth > buttonWidth) {
            buttonWidth = textWidth;
            // sometimes it looks quite awful
            smallBtnWidth = buttonWidth / 3;
        }

        inputString.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        readFile.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        saveFile.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        search.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        sort.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        examButtonPanel.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));

        exam0.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
        exam1.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
        exam2.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
    }
}

