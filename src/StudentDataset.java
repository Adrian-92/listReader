import java.util.ArrayList;

public class StudentDataset {

    private ArrayList<Student> dataset;

    public StudentDataset() {
        this.dataset = new ArrayList<>();
    }

    public ArrayList<Student> searchStudentsByName(String name) {
        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : this.dataset) {
            if (student.getFirstName().contains(name) || student.getLastName().contains(name)) {
                searchedStudents.add(student);
            }
        }
        return searchedStudents;
    }

    public ArrayList<Student> showStudentsByExam(int exams) {
        ArrayList<Student> sortedStudents = new ArrayList<>();
        for (Student student : this.dataset) {
            if (student.getExam() == exams) {
                sortedStudents.add(student);
            }
        }
        return sortedStudents;
    }

    public ArrayList<Student> sortStudentsByName() {
        ArrayList<Student> sortedStudents = new ArrayList<>();
        Student tempStudent = this.dataset.getFirst();
        for(Student student : this.dataset) {
            if(tempStudent.getLastName().compareTo(student.getLastName()) > 0){}
        }
        return sortedStudents;
    }

    public void setDataset(ArrayList<Student> dataset) {
        this.dataset = dataset;
    }

    public ArrayList<Student> getDataset() {
        return dataset;
    }
}
