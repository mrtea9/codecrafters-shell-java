package command;

import parse.LineParser;
import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        //System.out.println(input);

        List<String> arguments = new LineParser(input).parse();

        String name = arguments.getFirst();

        //System.out.println(name);
        storage.updateExecutables();
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
        //System.out.println();

        String[] arguments = argumentsRaw.getFirst().split("(?<=')\\s+(?=')");
        if (arguments.length == argumentsRaw.size()) {
            arguments = argumentsRaw.getFirst().split("(?<=\")\\s+(?=\")");

            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = arguments[i].replace("\"", "").trim();
            }
        } else {
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = arguments[i].replace("'", "").trim();
            }
        }

        try {
            Path workingDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(executable),
                            Arrays.stream(arguments)
                    )
                    .toList();
            Process process = new ProcessBuilder(commandArguments).inheritIO().directory(workingDirectory.toFile()).start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> splitArguments(String input) {
        List<String> arguments = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "\"(\\\\.|[^\"])*\"|'(\\\\.|[^'])*'|\\S+"
        );
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("\"") && match.endsWith("\"")) {
                // Keep escaped characters and remove surrounding double quotes
                match = match.substring(1, match.length() - 1).replace("\\\"", "\"");
            } else if (match.startsWith("'") && match.endsWith("'")) {
                // Keep escaped characters and remove surrounding single quotes
                match = match.substring(1, match.length() - 1).replace("\\'", "'");
            }
            arguments.add(match);
        }

        return arguments;
    }
}
