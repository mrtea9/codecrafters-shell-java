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

            if (i == message.length() - 1) break;

            if (firstChar == '"') {
                i++;
                startDouble = true;
            }

            if (startDouble) sb.append(message.charAt(i));

            if (firstChar == '"' && startDouble) {
                i++;
                startDouble = false;
            }
        }

        System.out.println(sb);

        return sb.toString();
    }
}
