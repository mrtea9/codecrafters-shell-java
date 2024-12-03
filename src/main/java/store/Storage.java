package store;

import command.Command;
import command.builtin.EchoCommand;
import command.builtin.ExitCommand;
import command.builtin.TypeCommand;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

public class Storage {

    private Map<String, BiFunction<String, List<String>, Command>> parsers;

    public Storage(Map<String, BiFunction<String, List<String>, Command>> parsers) {
        this.parsers = parsers;
    }

    public Map<String, BiFunction<String, List<String>, Command>> getStorage() {
        return parsers;
    }
}
