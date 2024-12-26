package command;

import parse.Redirect;
import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public record Executable(Path path) implements Command {

    static int times = 0;

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, List<Redirect> redirects) {
        try {
            Path workingDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(path.toString()),
                            arguments.stream().skip(1)
                    )
                    .toList();

            //System.out.println("command arguments = " + commandArguments);
            //System.out.println("redirect = " + redirects.get(0));

            final var builder = new ProcessBuilder(commandArguments)
                    .inheritIO()
                    .directory(workingDirectory.toFile());


            if (!redirects.isEmpty()) {
                final var redirect = ProcessBuilder.Redirect.to(redirects.get(0).path().toFile());

                builder.redirectOutput(redirect);

                builder.redirectErrorStream(true);
                builder.redirectError(redirect);
            }

            final var process = builder.start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
