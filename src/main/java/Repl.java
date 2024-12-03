import command.CommandParser;

import java.util.Scanner;

public class Repl {

    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser commandParser = new CommandParser();

    public void start() {

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            final var parsed = commandParser.parse(input);

            if (parsed == null) continue;

            System.out.println(parsed.command().execute());
        }

    }
}
