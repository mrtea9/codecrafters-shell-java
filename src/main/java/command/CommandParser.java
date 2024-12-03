package command;

import command.builtin.EchoCommand;
import command.builtin.ExitCommand;
import command.builtin.TypeCommand;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CommandParser {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers = new TreeMap<>();

    public CommandParser() {

        register("exit", singleArgumentCommand(ExitCommand::new));
        register("echo", singleArgumentCommand(EchoCommand::new));
        register("type", singleArgumentCommand(TypeCommand::new));

    }

    public Map<String, BiFunction<String, List<String>, Command>> getParsers() {
        return parsers;
    }

    public void register(String name, BiFunction<String, List<String>, Command> parser) {
        parsers.put(name, parser);
    }

    public ParsedCommand parse(String input) {
        if (input.isEmpty()) throw new IllegalStateException("Input is empty");

        System.out.println(System.getenv("PATH"));

        List<String> arguments = new ArrayList<>(Arrays.asList(input.split(" ", 2)));

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
