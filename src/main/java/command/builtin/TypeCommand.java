package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record TypeCommand(String command) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        final var value = storage.getParsers().get(command);
        final var executable = storage.getExecutables().get(command);

        if (value != null && executable == null) return new CommandResponse("%s is a shell builtin".formatted(command));

        if (executable != null) return new CommandResponse("%s is %s".formatted(command, executable));

        return new CommandResponse("%s is a shell builtin".formatted(command));
    }

}
