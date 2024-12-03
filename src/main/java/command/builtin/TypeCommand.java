package command.builtin;

import command.Command;
import command.CommandResponse;
import store.Storage;

public record TypeCommand(String command) implements Command {

    @Override
    public CommandResponse execute(Storage storage) {
        final var value = storage.getStorage().get(command);

        return new CommandResponse(
                switch (value) {
                    case null -> "%s: not found".formatted(command);
                    default -> "%s is a shell builtin".formatted(command);
                }
        );
    }

}
