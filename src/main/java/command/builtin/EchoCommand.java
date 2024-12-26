package command.builtin;

import command.Command;
import command.CommandResponse;
import io.RedirectStreams;
import store.Storage;

import java.util.List;
import java.util.stream.Collectors;

public record EchoCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, RedirectStreams redirects) {

        final var line = arguments.stream().skip(1).collect(Collectors.joining(" "));

        redirects.output().println(line);

        return null;
    }

}
