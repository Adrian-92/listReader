package utils;

import data.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    public static ArrayList<Student> readFile(File file) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        String filePath = file.getAbsolutePath();
        Scanner scanner = new Scanner(Paths.get(filePath));
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
            /*
            NOTE: what about double names and double last names??
               provided data is not helpful here
             */
            String[] splitName = words[0].split(" ");
            String firstName;
            String lastName;
            if (splitName.length > 2) {
                firstName = splitName[splitName.length - 2] + " " + splitName[splitName.length - 1];
            } else {
                firstName = splitName[1];
            }
            lastName = splitName[0];


            // name, mail, matrikelnummer, percent, num Exams
            return new Student(firstName, lastName, words[1], Integer.parseInt(words[2]), Float.parseFloat(words[3]), Integer.parseInt(words[4]));
        } catch (Exception e) {
            // if anything goes wrong this line won't be added to list
            return null;
        }
    }
}

