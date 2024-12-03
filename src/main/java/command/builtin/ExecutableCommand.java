package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.util.List;

public record ExecutableCommand(String name, String arguments) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {

        System.out.println("executable = %s".formatted(name));
        System.out.println("executable = %s".formatted(arguments));

        return new CommandResponse("ok");
    }
}
