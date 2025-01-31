import data.Student;
import data.StudentDataset;
import utils.Reader;
import utils.Writer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WindowHandler extends JFrame {
    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 512;
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 30;
    private static final int SMALL_BUTTON_WIDTH = BUTTON_WIDTH / 3;
    private static final String OPTIONS_TEXT = """
            use input field to look up students
            
            NOTE:
            generated files contain filtered entries
            """;

    private JFrame frame;

    private StudentDataset studentDataset;

    // IO & interaction
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem readFileMenuBtn;
    private JMenuItem generateMultipleFilesMenuBtn;
    private JMenuItem generateSingleFileMenuBtn;
    private JPanel examButtonPanel;
    private JPanel buttonPanel;

    private TextField inputString;

    private Button search;
    private Button sort;
    private Button invalidStudents;
    private Button refresh;
    private Button exam0;
    private Button exam1;
    private Button exam2;

    private JTextArea textField;


    public static void main(String[] args) {

        WindowHandler windowHandler = new WindowHandler();
        windowHandler.init();
    }

    public WindowHandler() {
    }

    public void initMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menu = new JMenu("Handle Files");
        menuBar.add(menu);
        readFileMenuBtn = new JMenuItem("read new file");
        generateMultipleFilesMenuBtn = new JMenuItem("generate multiple files");
        generateSingleFileMenuBtn = new JMenuItem("generate single file");
        menu.add(readFileMenuBtn);
        menu.addSeparator();
        menu.add(generateMultipleFilesMenuBtn);
        menu.add(generateSingleFileMenuBtn);

        frame.setJMenuBar(menuBar);
    }

    public void init() {
        studentDataset = new StudentDataset();
        setInterface();
        initMenuBar();
        setActionListeners();
        setButtonDimensions();

    }

    // shows a dynamic list of entries
    private void showEntries(ArrayList<Student> students) {
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
    private JList<Student> getStudentJList(DefaultListModel<Student> studentsList) {
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

    public void messageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void openFileReader() throws IOException {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog(WindowHandler.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            studentDataset.setDataset(Reader.readFile(file));
            showEntries(studentDataset.getDataset());
        } else {
            System.out.println("Open command cancelled by user.");
        }

    }

    // add frame and buttons to window
    public void setInterface() {
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
        search = new Button("search by name");
        sort = new Button("sort by name");
        invalidStudents = new Button("show invalid students");
        refresh = new Button("refresh");

        exam0 = new Button("0 exams");
        exam1 = new Button("1 exam");
        exam2 = new Button("2 exams");
        textField = new JTextArea(OPTIONS_TEXT);
        textField.getDocument().putProperty("filterNewlines", Boolean.FALSE);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setText(OPTIONS_TEXT);

        // add buttons to panel
        buttonPanel.add(inputString);
        buttonPanel.add(search);
        buttonPanel.add(sort);
        buttonPanel.add(invalidStudents);
        buttonPanel.add(refresh);

        examButtonPanel.add(exam0);
        examButtonPanel.add(exam1);
        examButtonPanel.add(exam2);
        buttonPanel.add(examButtonPanel);
        buttonPanel.add(textField);

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/images/logo.png").getImage());
        frame.setVisible(true);
    }

    public void setButtonDimensions() {
        int textWidth = textField.getPreferredSize().width;
        int buttonWidth = BUTTON_WIDTH;
        int smallBtnWidth = SMALL_BUTTON_WIDTH;
        if (textWidth > buttonWidth) {
            buttonWidth = textWidth;
            // sometimes it looks quite awful
            smallBtnWidth = buttonWidth / 3;
        }

        buttonPanel.setMaximumSize(new Dimension(buttonWidth + 3, BUTTON_HEIGHT * 5));
        inputString.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        search.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        sort.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        invalidStudents.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        examButtonPanel.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
        refresh.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));

        exam0.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
        exam1.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
        exam2.setPreferredSize(new Dimension(smallBtnWidth, BUTTON_HEIGHT));
    }

    public void setActionListeners() {
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
        invalidStudents.addActionListener(e -> {
            studentDataset.getInvalidStudents();
            showEntries(studentDataset.getSortedStudents());
        });

        refresh.addActionListener(e -> {
            studentDataset.refresh();
            showEntries(studentDataset.getSortedStudents());
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

        readFileMenuBtn.addActionListener(e -> {
            try {
                openFileReader();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
        generateMultipleFilesMenuBtn.addActionListener(e -> {
            if (studentDataset.getSortedStudents().isEmpty()) return;
            if (Writer.writeMultipleFiles(studentDataset.getSortedStudents()))
                messageDialog("files successfully generated");
            else messageDialog("file generation  failed");
        });
        generateSingleFileMenuBtn.addActionListener(e -> {
            if (studentDataset.getSortedStudents().isEmpty()) return;
            if (Writer.writeSingleFile(studentDataset.getSortedStudents()))
                messageDialog("file successfully generated");
            else messageDialog("file generation  failed");
        });

    }
}
