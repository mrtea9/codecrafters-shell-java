package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.util.List;

public record ExitCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, List<String> redirects) {
        System.exit(Integer.parseInt(arguments.get(1)));

        return new CommandResponse(arguments.get(1));
    }
}
