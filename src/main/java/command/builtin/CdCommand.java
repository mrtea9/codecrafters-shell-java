package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.io.File;

public record CdCommand(String directoryName) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        File directory = new File(directoryName);


        if (!directory.exists()) return new CommandResponse("cd: %s: No such file or directory".formatted(directoryName));

        System.setProperty("user.dir", directoryName);

        return null;
    }
}
