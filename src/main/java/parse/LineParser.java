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

        iterator.first();
        while ((argument = nextArgument()) != null) {
            arguments.add(argument);
        }

        System.out.println("arguments = " + arguments);

        return arguments;
    }

    private String nextArgument() {
        final var stringBuilder = new StringBuilder();

        for (var character = iterator.current(); character != CharacterIterator.DONE; character = iterator.next()) {
            System.out.println("char = " + character);
            switch (character) {
                case SPACE -> {
                    if (!stringBuilder.isEmpty()) return stringBuilder.toString();
                }
                default -> {
                    stringBuilder.append(character);
                }
            }
        }

        if (!stringBuilder.isEmpty()) return stringBuilder.toString();

        return null;
    }
}
