package command;

import java.util.List;

public class CommandParser {

    public ParsedCommand parse(String input) {
        if (input.isEmpty()) throw new IllegalStateException("Input is empty");

        String[] arguments = input.split(" ");

        String command = arguments[0];

        System.out.println(command);

        return null;
    }
}
