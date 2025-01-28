package model;

import java.util.ArrayList;
import java.util.Arrays;

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
        Student[] students = this.dataset.toArray(new Student[0]);
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
        return new ArrayList<>(Arrays.asList(students));
    }

    public void setDataset(ArrayList<Student> dataset) {
        this.dataset = dataset;
    }

    public ArrayList<Student> getDataset() {
        return dataset;
    }
}
