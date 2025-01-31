package data;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentDataset {

    private ArrayList<Student> dataset;
    private ArrayList<Student> sortedStudents;

    public StudentDataset() {
        this.dataset = new ArrayList<>();
        this.sortedStudents = new ArrayList<>();
    }

    public void refresh() {
        setSortedStudents(this.dataset);
    }

    public void searchStudentsByName(String name) {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;

        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : data) {
            String fullStudentName = student.getFirstName() + " " + student.getLastName();
            fullStudentName = fullStudentName.toLowerCase();
            if (fullStudentName.contains(name.toLowerCase())) {
                searchedStudents.add(student);
            }
        }
        setSortedStudents(searchedStudents);
    }

    public void showStudentsByExam(int exams) {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;

        ArrayList<Student> sortedStudents = new ArrayList<>();
        for (Student student : data) {
            if (student.getExam() == exams) {
                sortedStudents.add(student);
            }
        }
        setSortedStudents(sortedStudents);
    }

    public void sortStudentsByName() {
        Student[] students;
        if (sortedStudents.isEmpty()) students = this.dataset.toArray(new Student[0]);
        else students = this.sortedStudents.toArray(new Student[0]);

        Student tempStudent;
        for (int i = 0; i < students.length; i++) {
            for (int j = i + 1; j < students.length; j++) {
                if (students[i].getLastName().compareTo(students[j].getLastName()) > 0) {
                    // swapping
                    tempStudent = students[i];
                    students[i] = students[j];
                    students[j] = tempStudent;
                }
            }
        }
        setSortedStudents(new ArrayList<>(Arrays.asList(students)));
    }

    public void getInvalidStudents() {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;
        ArrayList<Student> searchedStudents = new ArrayList<>();

        for (Student student : data) {
            if (student.getExam() < 0 || student.getExam() > 2) {
                searchedStudents.add(student);
            }
        }
        setSortedStudents(searchedStudents);
    }

    public void setDataset(ArrayList<Student> dataset) {
        this.dataset = dataset;
    }

    public ArrayList<Student> getDataset() {
        return dataset;
    }

    public void setSortedStudents(ArrayList<Student> sortedStudents) {
        this.sortedStudents = sortedStudents;
    }

    public ArrayList<Student> getSortedStudents() {
        return sortedStudents;
    }
}
