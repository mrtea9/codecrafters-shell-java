package command;

import command.builtin.ExitCommand;

import java.util.*;
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

        List<String> arguments = new ArrayList<>(Arrays.asList(input.split(" ")));

        String name = arguments.getFirst();
        final var parser = parsers.get(name);
        if (parser == null) {
            System.out.println("%s: command not found".formatted(name));
            return null;
        }

        final var command = parser.apply(name, arguments.subList(1, arguments.size()));

        return new ParsedCommand(arguments, command);
    }

    private BiFunction<String, List<String>, Command> singleArgumentCommand(Function<String, Command> constructor) {
        return (name, arguments) -> {
            if (arguments.size() != 1) throw new IllegalArgumentException("to many args");

            //System.out.println(name);
            //System.out.println(arguments);

            final var first = arguments.getFirst();
            return constructor.apply(first);
        };
    }


}
