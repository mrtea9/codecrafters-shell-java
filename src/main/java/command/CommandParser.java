package command;

import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

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
        if (executable != null && !name.equals("pwd") && !name.equals("cd") && !name.equals("echo")) {
            executeProcess(executable, arguments.subList(1, arguments.size()));
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

    private void executeProcess(String executable, List<String> argumentsRaw) {
        System.out.println(argumentsRaw);
        String[] arguments = argumentsRaw.get(0).split("(?<=')\\s+(?=')");
        System.out.println(Arrays.toString(arguments));

        try {
            Path workingDirectory = Path.of(".").toAbsolutePath().normalize();
            System.out.println(Arrays.toString(arguments));

            final var commandArguments = Stream
                    .concat(
                            Stream.of(executable),
                            Arrays.stream(arguments).skip(1)
                    )
                    .toList();
            Process process = new ProcessBuilder(String.valueOf(commandArguments)).inheritIO().directory(workingDirectory.toFile()).start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
