import command.CommandParser;
import command.CommandResponse;
import parse.LineParser;
import store.Storage;

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
        final var parsedLine = new LineParser(line).parse();

        final var arguments = parsedLine.arguments();
        final var command = arguments.getFirst();

        final var parsed = commandParser.parse(command);
        if (parsed == null) return;

        final var redirects = parsedLine.redirects();

        CommandResponse result = parsed.execute(storage, arguments, redirects);
        if (result == null) return;

        System.out.println(result);
    }
}
