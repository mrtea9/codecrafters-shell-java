package command;

import command.builtin.ExitCommand;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CommandParser {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers = new TreeMap<>();

    public CommandParser() {
        register("exit", singleArgumentCommand(ExitCommand::new));
    }

    public void register(String name, BiFunction<String, List<String>, Command> parser) {
        parsers.put(name, parser);
    }

    public ParsedCommand parse(String input) {
        if (input.isEmpty()) throw new IllegalStateException("Input is empty");

        String[] arguments = input.split(" ");

        String name = arguments[0];
        final var parser = parsers.get(name);
        if (parser == null) System.out.println("%s: command not found".formatted(name));

        final var command = parser.apply(name, List.of("1"));

        System.out.println(name);

        return null;
    }

    private BiFunction<String, List<String>, Command> singleArgumentCommand(Function<String, Command> constructor) {
        return (name, arguments) -> {
            if (arguments.size() != 1) throw new IllegalArgumentException("to many args");

            System.out.println(arguments);

            final var first = arguments.getFirst();
            return constructor.apply(first);
        };
    }
}
