package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.io.File;

public record CdCommand(String directoryName) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        String directoryInter = directoryName;

        if (directoryInter.startsWith("./")) directoryInter = nextDirectory();

        System.out.println(directoryInter);

        File directory = new File(directoryInter);

        if (!directory.exists()) return new CommandResponse("cd: %s: No such file or directory".formatted(directoryInter));

        System.setProperty("user.dir", directoryInter);

        return null;
    }

    private String nextDirectory() {
        return "%s/%s".formatted(System.getProperty("user.dir"), directoryName.substring(2));
    }
}
