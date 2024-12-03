package command.builtin;

import command.Command;
import command.CommandResponse;

public record ExitCommand(String status) implements Command {

    @Override
    public CommandResponse execute() {
        System.out.println("sadadadsad");

        return new CommandResponse(status);
    }
}
