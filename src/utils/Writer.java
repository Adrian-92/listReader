package utils;

import data.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class Writer {
    private static final String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

    /* writes output files to systems desktop folder
    change path if desired.
    */
    public static void writeToFile(String fileName, ArrayList<Student> content) throws FileNotFoundException {
        if (!content.isEmpty()) {
            LocalDate date = LocalDate.now();
            String filePath = desktopPath + File.separator + fileName + "_" + date + ".txt";
            PrintWriter writer = new PrintWriter(filePath);
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
    public static boolean writeMultipleFiles(ArrayList<Student> students) {
        ArrayList<Student> zero = new ArrayList<>();
        ArrayList<Student> one = new ArrayList<>();
        ArrayList<Student> two = new ArrayList<>();
        ArrayList<Student> invalid = new ArrayList<>();
        for (Student student : students) {
            switch (student.exam()) {
                case 0 -> zero.add(student);
                case 1 -> one.add(student);
                case 2 -> two.add(student);
                default -> {
                    System.err.println("Name: " + student.firstName() + " "
                            + student.lastName() +
                            "\nExam: " + student.exam()
                            + " is not a valid");
                    invalid.add(student);
                }
            }
        }
        try {
            writeToFile("zeroExams", zero);
            writeToFile("oneExam", one);
            writeToFile("twoExams", two);
            writeToFile("invalidExams", invalid);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean writeSingleFile(ArrayList<Student> students) {
        try {
            writeToFile("list", students);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
