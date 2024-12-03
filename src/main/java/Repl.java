import command.CommandParser;

import java.util.Scanner;

public class Repl {

    private Scanner scanner = new Scanner(System.in);
    private final CommandParser commandParser = new CommandParser();

    public void start() {

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            commandParser.parse(input);

            System.out.println(input + ": command not found");
        }

    }
}
