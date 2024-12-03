package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record PwdCommand() implements Command {

    public CommandResponse execute(Storage storage) {

        return new CommandResponse(System.getProperty("user.dir"));
    }

}
