package parse;

import io.StandardNamedStream;

import java.nio.file.Path;

public record Redirect(StandardNamedStream namedStream, Path path, boolean append) {

    public boolean isFile() {
        return path != null;
    }

    public void println(String message) {
        final var printStream = namedStream.getPrintStream();

        if (printStream != null) printStream.println(message);
    }
}
