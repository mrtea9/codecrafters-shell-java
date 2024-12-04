package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record EchoCommand(String message) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        String finalMessage = message;

        if (finalMessage.startsWith("'")) finalMessage = singleQuotes();
        System.out.println(finalMessage);

        return new CommandResponse(finalMessage);
    }

    private String singleQuotes() {
        return message.substring(1, message.length() - 1);
    }
}
