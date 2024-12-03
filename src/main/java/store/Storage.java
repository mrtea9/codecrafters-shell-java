package store;

import command.Command;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Storage {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers;

    public Storage(Map<String, BiFunction<String, List<String>, Command>> parsers) {
        this.parsers = parsers;
    }

    public Map<String, BiFunction<String, List<String>, Command>> getStorage() {
        return parsers;
    }
}
