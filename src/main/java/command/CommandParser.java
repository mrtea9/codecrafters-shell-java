package command;

import store.Storage;

import java.util.*;
import java.util.function.BiFunction;

public class CommandParser {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers;
    private final Storage storage;

    public CommandParser(Storage storage) {
        this.storage = storage;
        this.parsers = storage.getParsers();
    }

    public ParsedCommand parse(String input) {
        if (input.isEmpty()) throw new IllegalStateException("Input is empty");

        List<String> arguments = new ArrayList<>(Arrays.asList(input.split(" ", 2)));

        String name = arguments.getFirst();
        final var executable = storage.getExecutables().get(name);
        if (executable != null) {
            execute(executable);
            return null;
        }

        final var parser = parsers.get(name);
        if (parser == null) {
            System.out.println("%s: command not found".formatted(name));
            return null;
        }

        final var command = parser.apply(name, arguments.subList(1, arguments.size()));

        return new ParsedCommand(arguments, command);
    }

    private void execute(String executable) {
        System.out.println(executable);
    }
}
