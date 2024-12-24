package store;

import command.Command;
import command.builtin.*;
import file.FindFile;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Storage {

    private final Map<String, Command> parsers = new TreeMap<>();

    public Storage() {
        register("pwd", new PwdCommand());
        register("exit", new ExitCommand());
        register("echo", new EchoCommand());
        register("type", new TypeCommand());
        register("cd", new CdCommand());
    }

    public Map<String, Command> getParsers() {
        return parsers;
    }

    public void register(String name, Command parser) {
        parsers.put(name, parser);
    }
}
