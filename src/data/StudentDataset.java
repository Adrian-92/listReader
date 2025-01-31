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

    public void searchStudents(String input) {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;
        int inputToInt = -1;
        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : data) {
            String fullStudentName = student.firstName() + " " + student.lastName();

            fullStudentName = fullStudentName.toLowerCase();
            if (input.matches("-?\\d+"))
                inputToInt = Integer.parseInt(input);

            if (fullStudentName.contains(input.toLowerCase())
                    || student.email().toLowerCase().contains(input.toLowerCase())
                    || student.number() == inputToInt) {
                searchedStudents.add(student);
            }
        }
        setSortedStudents(searchedStudents);
    }

    public ArrayList<Student> showStudentsByExam(int exams) {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;

        ArrayList<Student> sortedStudents = new ArrayList<>();
        for (Student student : data) {
            if (student.exam() == exams) {
                sortedStudents.add(student);
            }
        }
        return sortedStudents;
    }

    public void sortStudentsByName() {
        Student[] students;
        if (sortedStudents.isEmpty()) students = this.dataset.toArray(new Student[0]);
        else students = this.sortedStudents.toArray(new Student[0]);

        Student tempStudent;
        for (int i = 0; i < students.length; i++) {
            for (int j = i + 1; j < students.length; j++) {
                if (students[i].lastName().compareTo(students[j].lastName()) > 0) {
                    // swapping
                    tempStudent = students[i];
                    students[i] = students[j];
                    students[j] = tempStudent;
                }
            }
        }
        setSortedStudents(new ArrayList<>(Arrays.asList(students)));
    }

    public ArrayList<Student> getInvalidStudents() {
        ArrayList<Student> data;
        if (sortedStudents.isEmpty()) data = this.dataset;
        else data = this.sortedStudents;
        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : data) {
            if (student.exam() < 0 || student.exam() > 2) {
                searchedStudents.add(student);
            }
        }
        return searchedStudents;
    }

    public void setDataset(ArrayList<Student> dataset) {
        this.dataset = dataset;
        refresh();
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
