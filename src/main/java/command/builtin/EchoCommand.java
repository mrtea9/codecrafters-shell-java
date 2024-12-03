package command.builtin;

import command.Command;
import command.CommandResponse;

public record EchoCommand(String message) implements Command {

    @Override
    public CommandResponse execute() {

        return new CommandResponse(message);
    }

}
