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

    public Command parse(String name) {

        final var builtin = parsers.get(name);
        if (builtin != null) return builtin;

        final var separator = IS_WINDOWS ? ";" : ":";
        final var paths = System.getenv("PATH").split(separator);

        for (final var directory : paths) {
            final var path = Paths.get(directory, name).normalize().toAbsolutePath();

            if (Files.exists(path)) return new Executable(path);
        }

        System.out.println("%s: command not found".formatted(name));

        return null;
    }
}
