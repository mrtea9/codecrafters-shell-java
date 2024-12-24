package command;

import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public record Executable(Path path) implements Command {

    static int times = 0;

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments) {
        try {
            Path workingDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(arguments.get(1)),
                            arguments.stream().skip(1)
                    )
                    .toList();

            System.out.println("command arguments = " + commandArguments);

            Process process = new ProcessBuilder(commandArguments)
                    .inheritIO()
                    .directory(workingDirectory.toFile())
                    .redirectErrorStream(true)
                    .start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
