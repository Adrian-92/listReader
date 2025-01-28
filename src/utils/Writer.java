package utils;

import model.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Writer {
    private static final String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

    /* writes output files to systems desktop folder
    change path if desired.
    Maybe make it dynamic?
    */
    public static void writeToFile(String filepath, ArrayList<Student> content) throws FileNotFoundException {
        if (!content.isEmpty()) {
            PrintWriter writer = new PrintWriter(desktopPath + File.separator + filepath);
            for (Student student : content) {
                writer.println(student);
            }
            writer.println("Number of students: " + content.size());
            writer.close();
        }
    }


    /* only case 0, 1 and 2 to are relevant for this specific task
    possibly other cases may be added if needed
     */
    public static boolean writeFiles(ArrayList<Student> students) {
        ArrayList<Student> zero = new ArrayList<>();
        ArrayList<Student> one = new ArrayList<>();
        ArrayList<Student> two = new ArrayList<>();
        ArrayList<Student> invalid = new ArrayList<>();
        for (Student student : students) {
            switch (student.getExam()) {
                case 0 -> zero.add(student);
                case 1 -> one.add(student);
                case 2 -> two.add(student);
                default -> {
                    System.err.println("Name: " + student.getFirstName() + " "
                            + student.getLastName() +
                            "\nExam: " + student.getExam()
                            + " is not a valid");
                    invalid.add(student);
                }
            }
        }
        try {
            writeToFile("listZero.txt", zero);
            writeToFile("listOne.txt", one);
            writeToFile("listTwo.txt", two);
            writeToFile("invalidStudents.txt", invalid);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
