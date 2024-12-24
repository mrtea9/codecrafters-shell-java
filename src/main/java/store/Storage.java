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
    private Map<String, String> executables;

    public Storage() {
        FindFile findFile = new FindFile();
        this.executables = findFile.parseFiles();

        register("pwd", new PwdCommand());
        register("exit", new ExitCommand());
        register("echo", new EchoCommand());
        register("type", new TypeCommand());
        register("cd", new CdCommand());
    }

    public void updateExecutables() {
        FindFile findFile = new FindFile();
        this.executables = findFile.parseFiles();
    }

    public Map<String, Command> getParsers() {
        return parsers;
    }

    public Map<String, String> getExecutables() {
        return executables;
    }

    public void register(String name, Command parser) {
        parsers.put(name, parser);
    }
}
