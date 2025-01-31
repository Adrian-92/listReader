import data.Student;
import data.StudentDataset;
import utils.Reader;
import utils.Writer;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
            NOTE:
            use handle files menu to import dataset
            and generate output files
            
            use input field to look up students
            
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
    private JPanel buttonPanel;
    private JComboBox<String> examSelection;

    private TextField inputString;
    private Button search;
    private Button sort;
    private Button refresh;


    private JTextArea textField;


    public static void main(String[] args) {

        WindowHandler windowHandler = new WindowHandler();
        windowHandler.init();
    }

    public WindowHandler() {
    }

    public void initMenuBar() {
        menuBar = new JMenuBar();
        menu = new JMenu("Handle Files");
        menu.setBorder(new LineBorder(Color.GRAY, 1));
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
            textField.setText("no students found");
            return;
        }
        textField.setText(OPTIONS_TEXT);
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
                            "Name: " + student.firstName() + " " + student.lastName() + "<br>" +
                            "Email: " + student.email() + "<br>" +
                            "Matrikel-Nr: " + student.number() + "<br>" +
                            "Percentage: " + student.percent() + "% <br>" +
                            "Exams: " + student.exam() +
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

        // buttons and text input
        inputString = new TextField(20);
        search = new Button("search by name");
        sort = new Button("sort by name");
        refresh = new Button("show full list");
        textField = new JTextArea(OPTIONS_TEXT);
        textField.getDocument().putProperty("filterNewlines", Boolean.FALSE);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setText(OPTIONS_TEXT);

        // add buttons to panel
        buttonPanel.add(inputString);
        buttonPanel.add(search);
        buttonPanel.add(sort);
        buttonPanel.add(refresh);

        examSelection = new JComboBox<>(new String[]{"0 exams", "1 exam", "2 exams", "invalid students"});
        buttonPanel.add(examSelection);
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

        refresh.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
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
        refresh.addActionListener(e -> {
            studentDataset.refresh();
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
        examSelection.addActionListener(e -> {
            int selectedIndex = examSelection.getSelectedIndex();
            if (selectedIndex > 2 || selectedIndex < 0) showEntries(studentDataset.getInvalidStudents());
            else showEntries(studentDataset.showStudentsByExam(selectedIndex));
        });

    }
}
