package command.builtin;

import command.Command;
import command.CommandResponse;

public record TypeCommand(String command) implements Command {

    @Override
    public CommandResponse execute() {
        return new CommandResponse("Ok");
    }

}
