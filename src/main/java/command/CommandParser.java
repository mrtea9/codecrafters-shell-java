package command;

import parse.LineParser;
import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class CommandParser {

    private final Map<String, Command> parsers;
    private final Storage storage;

    public CommandParser(Storage storage) {
        this.storage = storage;
        this.parsers = storage.getParsers();
    }

    public ParsedCommand parse(String name, List<String> arguments) {

        storage.updateExecutables();
        final var executable = storage.getExecutables().get(name);
        if (executable != null && !name.equals("pwd") && !name.equals("cd") && !name.equals("echo")) {
            executeProcess(executable, arguments.subList(1, arguments.size()));
            return null;
        }

        final var command = parsers.get(name);
        if (command == null) {
            System.out.println("%s: command not found".formatted(name));
            return null;
        }

        return new ParsedCommand(arguments, command);
    }

    private void executeProcess(String executable, List<String> arguments) {
        //System.out.println(arguments);

        try {
            Path workingDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(executable),
                            arguments.stream()
                    )
                    .toList();

            System.out.println("command arguments = " + commandArguments);

            Process process = new ProcessBuilder(commandArguments)
                    .inheritIO()
                    .directory(workingDirectory.toFile())
                    .redirectErrorStream(true)
                    .start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
