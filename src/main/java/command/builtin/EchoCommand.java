package command.builtin;

import command.Command;
import command.CommandResponse;
import parse.Redirect;
import store.Storage;

import java.util.List;

public record EchoCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, List<Redirect> redirects) {

        return new CommandResponse(arguments.get(1));
    }

}
