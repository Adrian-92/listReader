import java.util.Scanner;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String techfak;
    private int exam;

    public Student(String firstName, String lastName, String techfak, String email, int exam) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.techfak = techfak;
        this.email = email;
        this.exam = exam;
    }

    // dummy for error handling
    public Student() {
        this.firstName = "error";
        this.lastName = "";
        this.techfak = "";
        this.email = "";
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Techfak: " + techfak + "\n" +
                "Exam: " + exam + "\n";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTechfak() {
        return techfak;
    }

    public int getExam() {
        return exam;
    }
}
