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
            finalMessage = message.replaceAll("\"", "");
        } else {
            finalMessage = message.replaceAll("\\s+", " ");
        }

        return new CommandResponse(finalMessage);
    }

    private String singleQuotes() {
        return message.substring(1, message.length() - 1);
    }
}
