package command;

import io.RedirectStream;
import io.RedirectStreams;
import io.StandardNamedStream;
import store.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public record Executable(Path path) implements Command {

    static int times = 0;

    @Override
    public CommandResponse execute(Storage storage, List<String> arguments, RedirectStreams redirects) {
        try {
            Path workingDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();

            final var commandArguments = Stream
                    .concat(
                            Stream.of(path.toString()),
                            arguments.stream().skip(1)
                    )
                    .toList();

            //System.out.println("command arguments = " + commandArguments);
            //System.out.println("redirect = " + redirects.get(0).namedStream());

            final var builder = new ProcessBuilder(commandArguments)
                    .inheritIO()
                    .directory(workingDirectory.toFile());

            applyRedirect(builder, redirects.output(), StandardNamedStream.OUTPUT);
            applyRedirect(builder, redirects.error(), StandardNamedStream.ERROR);

            final var process = builder.start();

            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void applyRedirect(ProcessBuilder builder, RedirectStream stream, StandardNamedStream streamName) {
        final var isStderr = StandardNamedStream.ERROR.equals(streamName);

        switch (stream) {
            case RedirectStream.Standard standard -> {
                if (isStderr && StandardNamedStream.OUTPUT.equals(standard.name())) builder.redirectErrorStream(true);
            }

            case RedirectStream.File file -> {
                file.close();

                if (file.path().equals("/tmp/bar/foo.md")) {
                    System.out.println(streamName);
                    System.out.println(file.append());
                    System.out.println(file.path());
                }

//                System.out.println(streamName);
//                System.out.println(file.append());
//                System.out.println(file.path());

                final var redirect = file.append()
                        ? ProcessBuilder.Redirect.appendTo(file.path().toFile())
                        : ProcessBuilder.Redirect.to(file.path().toFile());

               // System.out.println("append = " + file.append());

                if (isStderr) {
                    builder.redirectError(redirect);
                } else {
                    builder.redirectOutput(redirect);
                }
            }
        }
    }
}
