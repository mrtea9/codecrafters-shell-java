import command.CommandParser;
import command.CommandResponse;
import file.FindFile;
import store.Storage;

import java.util.Arrays;
import java.util.Scanner;

public class Repl {

    private final Scanner scanner = new Scanner(System.in);
    private final Storage storage = new Storage();
    private final CommandParser commandParser = new CommandParser(storage);

    public void start() {
        while (true) {
            final var line = read();
            eval(storage, line);
        }
    }

    private String read() {
        while (true) {
            System.out.print("$ ");
            String line = this.scanner.nextLine();

            if (!line.isEmpty()) return line;
        }
    }

    private void eval(Storage storage, String line) {
        final var parsed = commandParser.parse(line);

        if (parsed == null) return;

        CommandResponse result = parsed.command().execute(storage);
        if (result == null) return;

        System.out.println(result);
    }
}
