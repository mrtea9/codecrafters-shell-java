package command;

import parse.LineParser;
import store.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class CommandParser {

    public static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

    private final Map<String, Command> parsers;
    private final Storage storage;

    public CommandParser(Storage storage) {
        this.storage = storage;
        this.parsers = storage.getParsers();
    }

    public ParsedCommand parse(String name, List<String> arguments) {

        final var builtin = parsers.get(name);
        if (builtin != null) return new ParsedCommand(arguments, builtin);

        final var separator = IS_WINDOWS ? ";" : ":";
        final var paths = System.getenv("PATH").split(separator);

        for (final var directory : paths) {
            final var path = Paths.get(directory, name).normalize().toAbsolutePath();

            if (Files.exists(path)) {
                executeProcess(name, arguments.subList(1, arguments.size()));
                return null;
            }
        }

        return null;
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
