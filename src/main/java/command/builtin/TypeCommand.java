package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

import java.util.List;

public record TypeCommand() implements Command {

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments) {
        final var command = arguments.get(1);

        final var value = storage.getParsers().get(command);

        if (value != null) return new CommandResponse("%s is a shell builtin".formatted(command));

        return new CommandResponse("%s: not found".formatted(command));
    }
}
