package command.builtin;

import command.Command;
import command.CommandResponse;
import io.RedirectStreams;
import store.Storage;

import java.util.List;

public record PwdCommand() implements Command {

    public CommandResponse execute(Storage storage, List<String> arguments, RedirectStreams redirects) {

        return new CommandResponse(System.getProperty("user.dir"));
    }

}
