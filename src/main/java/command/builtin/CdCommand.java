package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record CdCommand(String directory) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {

        System.setProperty("user.dir", directory);

        return null;
    }
}
