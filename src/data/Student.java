package data;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private int number;
    private int exam;
    private float percent;

    // Name;email;Matrnr;prozPunkte;Praesentationen
    public Student(String firstName, String lastName, String email, int number,float percent, int exam) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.percent = percent;
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
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

    public int getNumber(){
        return number;
    }

    public int getExam() {
        return exam;
    }

    public float getPercent() {
        return percent;
    }
}
