package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record EchoCommand(String message) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {

        return new CommandResponse(message);
    }

}
