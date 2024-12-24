package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.util.List;

public record EchoCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments) {

        return new CommandResponse(arguments.get(1));
    }

}
