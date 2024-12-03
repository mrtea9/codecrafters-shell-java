package store;

import command.Command;
import command.builtin.EchoCommand;
import command.builtin.ExitCommand;
import command.builtin.PwdCommand;
import command.builtin.TypeCommand;
import file.FindFile;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Storage {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers = new TreeMap<>();
    private final Map<String, String> executables;

    public Storage() {
        FindFile findFile = new FindFile();
        this.executables = findFile.parseFiles();

        register("pwd", noArgumentCommand(PwdCommand::new));

        register("exit", singleArgumentCommand(ExitCommand::new));
        register("echo", singleArgumentCommand(EchoCommand::new));
        register("type", singleArgumentCommand(TypeCommand::new));
    }

    public Map<String, BiFunction<String, List<String>, Command>> getParsers() {
        return parsers;
    }

    public Map<String, String> getExecutables() {
        return executables;
    }

    public void register(String name, BiFunction<String, List<String>, Command> parser) {
        parsers.put(name, parser);
    }

    private BiFunction<String, List<String>, Command> noArgumentCommand(Supplier<Command> constructor) {
        return (name, arguments) -> {
            return constructor.get();
        };
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
