package store;

import command.Command;
import file.FindFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Storage {

    private final Map<String, BiFunction<String, List<String>, Command>> parsers;
    private final Map<String, String> executables;

    public Storage(Map<String, BiFunction<String, List<String>, Command>> parsers) {
        this.parsers = parsers;

        FindFile findFile = new FindFile();
        this.executables = findFile.parseFiles();
    }

    public Map<String, BiFunction<String, List<String>, Command>> getParsers() {
        return parsers;
    }

    public  Map<String, String> getExecutables() {
        return executables;
    }
}
