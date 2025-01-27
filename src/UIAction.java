import java.util.ArrayList;

public class UIAction {


    public static ArrayList<Student> searchStudentsByName(String name, ArrayList<Student> students) {
        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getFirstName().contains(name) || student.getLastName().contains(name)) {
                searchedStudents.add(student);
            }
        }
        return searchedStudents;
    }

    public static ArrayList<Student> showStudentsByExam(ArrayList<Student> students, int exams) {
        ArrayList<Student> sortedStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getExam() == exams) {
                sortedStudents.add(student);
            }
        }
        return sortedStudents;
    }
}
