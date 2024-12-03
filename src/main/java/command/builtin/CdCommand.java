package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record CdCommand(String directory) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {

        boolean result = false;

        result = System.setProperty("user.dir", directory) != null;

        return result ? null : new CommandResponse("cd: <%s>: No such file or directory".formatted(directory));
    }
}
