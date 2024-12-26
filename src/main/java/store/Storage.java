package store;

import command.Command;
import command.builtin.*;

import java.util.Map;
import java.util.TreeMap;

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
