import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    public static ArrayList<Student> readFile(String filepath) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(Paths.get(filepath));
            // skip headline
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty())
                    students.add(prepareStudent(line));
            }
        } catch (IOException e) {
            return null;
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
            String[] splitName = words[0].split(" ");
            String firstName = splitName[1];
            String lastName = splitName[0];
            return new Student(firstName, lastName, words[1], words[2], Integer.parseInt(words[4]));
        } catch (Exception e) {
            // if anything goes wrong there is this dummy student - just says "error" in name
            return new Student();
        }
    }
}

