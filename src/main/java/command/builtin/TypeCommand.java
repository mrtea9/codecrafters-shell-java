package command.builtin;

import command.Command;
import command.CommandParser;
import command.CommandResponse;
import command.Executable;
import io.RedirectStreams;
import store.Storage;

import java.util.List;

public record TypeCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, RedirectStreams redirects) {
        final var command = arguments.get(1);

        final var value = new CommandParser(storage).parse(command);

        if (value instanceof Executable(final var path)) return new CommandResponse("%s is %s".formatted(command, path));

        if (value != null) return new CommandResponse("%s is a shell builtin".formatted(command));

        return new CommandResponse("%s: not found".formatted(command));
    }
}
