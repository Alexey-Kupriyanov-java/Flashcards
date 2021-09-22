package flashcards;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOSystem {
    private final StringBuilder log;

    public IOSystem() {
        this.log = new StringBuilder();
    }

    public void saveLog() {
        output("File name:");
        String fileName = this.input();
        output("The log has been saved.");
        try (PrintWriter printer = new PrintWriter(fileName)) {
            printer.println(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void output(String text) {
        System.out.println(text);
        log.append(text);
        log.append("\n");
    }
    public String input() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        log.append(input);
        log.append("\n");

        scanner.close();
        return input;
    }
}
