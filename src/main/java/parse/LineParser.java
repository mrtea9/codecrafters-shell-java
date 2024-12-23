package parse;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class LineParser {

    public static final char SPACE = ' ';
    public static final char SINGLE = '\'';
    public static final char DOUBLE = '"';
    public static final char BACKSLASH = '\\';
    public static final char GREATER_THAN = '>';

    private final CharacterIterator iterator;

    private final List<String> arguments = new ArrayList<>();

    public LineParser(String line) {
        this.iterator = new StringCharacterIterator(line);
    }

    public List<String> parse() {
        String argument;

        final var test = iterator.first();

        System.out.println(test);

        return new ArrayList<>();
    }

}
