import command.CommandParser;
import file.FindFile;
import store.Storage;

import java.util.Arrays;
import java.util.Scanner;

public class Repl {

    private final Scanner scanner = new Scanner(System.in);
    private final CommandParser commandParser = new CommandParser();
    private final Storage storage = new Storage(commandParser.getParsers());

    public void start() {

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            final var parsed = commandParser.parse(input);

            if (parsed == null) continue;

            System.out.println(parsed.command().execute(storage));
        }

    }
}
