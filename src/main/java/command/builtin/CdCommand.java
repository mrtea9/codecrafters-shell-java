package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.io.File;
import java.util.Arrays;

public record CdCommand(String directoryName) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        String directoryString = directoryName;

        if (directoryString.startsWith("./")) {
            directoryString = nextDirectory();
            //System.out.println(directoryString);
        }

        if (directoryString.startsWith("../")) {
            directoryString = previousDirectory();
        }

        File directory = new File(directoryString);

        if (!directory.exists()) return new CommandResponse("cd: %s: No such file or directory".formatted(directoryString));

        System.setProperty("user.dir", directoryString);

        return null;
    }

    private String nextDirectory() {
        String currentDirectory = System.getProperty("user.dir");

        //System.out.println("current = " + currentDirectory);
        //System.out.println("directoryName = " + directoryName);

        return "%s/%s".formatted(System.getProperty("user.dir"), directoryName.substring(2));
    }

    private String previousDirectory() {
        String currentDirectory = System.getProperty("user.dir");
        String[] stepsBackArray = directoryName.split("/");
        String[] currentDirectoryArray = currentDirectory.split("/");
        int stepsBack = stepsBackArray.length;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < currentDirectoryArray.length - stepsBack; i++) {
            sb.append(currentDirectoryArray[i]);
            if (i != currentDirectoryArray.length - stepsBack - 1) sb.append("/");
        }

        return sb.toString();
    }
}
