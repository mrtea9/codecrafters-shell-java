package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record EchoCommand(String message) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        String finalMessage = message;

        if (finalMessage.startsWith("'")) {
            finalMessage = singleQuotes();
        } else if (finalMessage.startsWith("\"")) {
            finalMessage = doubleQuotes();
        } else if (finalMessage.contains("\\")) {
            finalMessage = blackSlash();
        } else {
            finalMessage = message.replaceAll("\\s+", " ");
        }


        return new CommandResponse(finalMessage);
    }

    private String singleQuotes() {
        return message.substring(1, message.length() - 1);
    }

    private String doubleQuotes() {
        StringBuilder sb = new StringBuilder();
        boolean startDouble = false;

        for (int i = 0; i < message.length(); i++) {
            final var firstChar = message.charAt(i);

            if (!startDouble && firstChar == ' ' && message.charAt(i + 1) == ' ') {
                continue;
            }

            if (startDouble && firstChar == '"') {
                startDouble = false;
                continue;
            }

            if (firstChar == '"') {
                startDouble = true;
                continue;
            }

            sb.append(firstChar);
        }

        return sb.toString();
    }

    private String blackSlash() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            final var firstChar = message.charAt(i);

            if (firstChar == '\\') {
                sb.append(message.charAt(i++));
                continue;
            }

            sb.append(firstChar);
        }

        return sb.toString();
    }
}
