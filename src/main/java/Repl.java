import command.CommandParser;

import java.util.Scanner;

public class Repl {

    private Scanner scanner = new Scanner(System.in);
    private final CommandParser commandParser = new CommandParser();

    public void start() {

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            final var parsed = commandParser.parse(input);
            if (parsed == null) continue;
            parsed.command().execute();
        }

    }
}
