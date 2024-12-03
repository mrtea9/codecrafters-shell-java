package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record ExitCommand(String status) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        System.exit(Integer.parseInt(status));

        return new CommandResponse(status);
    }
}
