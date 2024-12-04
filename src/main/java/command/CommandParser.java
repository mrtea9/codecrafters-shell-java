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

        String[] argumentsRaw = input.split(" ");
        String name = argumentsRaw[0];

        String[] arguments = Arrays.copyOfRange(argumentsRaw, 1, argumentsRaw.length);
        List<String> argumentsList = Arrays.stream(arguments).toList();

        final var executable = storage.getExecutables().get(name);
        if (executable != null && !name.equals("pwd") && !name.equals("cd") && !name.equals("echo")) {
            executeProcess(executable, arguments);
            return null;
        }

        final var parser = parsers.get(name);
        if (parser == null) {
            System.out.println("%s: command not found".formatted(name));
            return null;
        }

        final var command = parser.apply(name, argumentsList.subList(1, argumentsList.size()));

        return new ParsedCommand(argumentsList, command);
    }

    private void executeProcess(String executable, String[] arguments) {
        try {
            Path workingDirectory = Path.of(".").toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(executable.toString()),
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
