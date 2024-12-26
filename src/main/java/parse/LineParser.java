package parse;

import io.StandardNamedStream;

import java.nio.file.Path;
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
    private final List<Redirect> redirects = new ArrayList<>();

    public LineParser(String line) {
        this.iterator = new StringCharacterIterator(line);
    }

    public ParsedLine parse() {
        String argument;

        iterator.first();
        while ((argument = nextArgument()) != null) {
            arguments.add(argument);
        }

        return new ParsedLine(arguments, redirects);
    }

    private String nextArgument() {
        final var stringBuilder = new StringBuilder();

        for (var character = iterator.current(); character != CharacterIterator.DONE; character = iterator.next()) {
            switch (character) {
                case SPACE -> {
                    if (!stringBuilder.isEmpty()) return stringBuilder.toString();
                }
                case SINGLE -> singleQuote(stringBuilder);
                case DOUBLE -> doubleQuote(stringBuilder);
                case BACKSLASH -> backslash(stringBuilder, false);
                case GREATER_THAN -> redirect(StandardNamedStream.OUTPUT);
                default -> {
                    if (Character.isDigit(character) && peek() == GREATER_THAN) {
                        iterator.next();
                        redirect(StandardNamedStream.fromFileDescriptor(Character.digit(character, 10)));
                        continue;
                    }

                    stringBuilder.append(character);
                }
            }
        }

        if (!stringBuilder.isEmpty()) return stringBuilder.toString();

        return null;
    }

    private void singleQuote(StringBuilder stringBuilder) {
        char character;
        while ((character = iterator.next()) != CharacterIterator.DONE && character != SINGLE) {
            stringBuilder.append(character);
        }
    }

    private void doubleQuote(StringBuilder stringBuilder) {
        char character;
        while ((character = iterator.next()) != CharacterIterator.DONE && character != DOUBLE) {
            if (character == BACKSLASH) {
                backslash(stringBuilder, true);
            } else {
                stringBuilder.append(character);
            }
        }
    }

    private void backslash(StringBuilder stringBuilder, boolean inQuote) {
        var character = iterator.next();

        if (character == CharacterIterator.DONE) return;

        if (inQuote) {
            final var mappedCharacter = mapBackslashCharacter(character);

            if (mappedCharacter != CharacterIterator.DONE) {
                character = mappedCharacter;
            } else {
                stringBuilder.append(BACKSLASH);
            }
        }

        stringBuilder.append(character);
    }

    private char mapBackslashCharacter(char character) {
        return switch (character) {
            case DOUBLE -> DOUBLE;
            case BACKSLASH -> BACKSLASH;
            default -> CharacterIterator.DONE;
        };
    }

    private char peek() {
        final var character = iterator.next();
        if (character != CharacterIterator.DONE) iterator.previous();

        return character;
    }

    private void redirect(StandardNamedStream standardStream) {
        var character = iterator.next();
        if (character == CharacterIterator.DONE) return;

        final var append = character == GREATER_THAN;
        if (append) iterator.next();

        final var path = nextArgument();
        //System.out.println("path = " + path);

        redirects.add(new Redirect(standardStream, Path.of(path), append));
    }
}
