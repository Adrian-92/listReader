package data;

public record Student(String firstName, String lastName, String email, int number, float percent, int exam) {
    // name, mail, matrikelnummer, percent, num Exams

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Exam: " + exam + "\n";
    }
}
