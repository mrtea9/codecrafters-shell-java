package command;

import java.util.List;

public record ParsedCommand(List<String> raw, Command command) {}
