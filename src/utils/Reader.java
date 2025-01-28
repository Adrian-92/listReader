package utils;

import model.Student;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    public static ArrayList<Student> readFile(String filepath) throws IOException {
        ArrayList<Student> students = new ArrayList<>();

        Scanner scanner = new Scanner(Paths.get(filepath));
        // skip headline
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                Student student = prepareStudent(line);
                // filter invalid lines
                if (student != null)
                    students.add(student);
            }
        }

        return students;
    }

    /*
    change regex in splitName if other splitter is needed
     */
    private static Student prepareStudent(String input) {
        try {
            String newLine = input.replace("\"", "");
            String[] words = newLine.split(";");
            // NOTE: what about double names??
            String[] splitName = words[0].split(" ");
            String firstName = splitName[1];
            String lastName = splitName[0];
            return new Student(firstName, lastName, words[1], words[2], Float.parseFloat(words[3]), Integer.parseInt(words[4]));
        } catch (Exception e) {
            // if anything goes wrong this line won't be added to list
            return null;
        }
    }
}

