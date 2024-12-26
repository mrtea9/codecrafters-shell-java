package io;

import java.io.*;
import java.nio.file.Path;

public sealed interface RedirectStream extends AutoCloseable {

    void write(byte[] buffer, int offset, int length) throws IOException;

    void println(String message);

    @Override
    void close();

    public record Standard(StandardNamedStream name, PrintStream print) implements RedirectStream {

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            print.write(buffer, offset, length);
        }

        @Override
        public void println(String message) {
            print.println(message);
        }

        @Override
        public void close() {}
    }

    public final class File implements RedirectStream {

        private final Path path;
        private final boolean append;
        private final FileOutputStream outputStream;
        private final PrintWriter writer;

        public File(Path path, boolean append) throws FileNotFoundException {
            this.path = path;
            this.append = append;
            this.outputStream = new FileOutputStream(path.toFile(), append);
            this.writer = new PrintWriter(outputStream, true);
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            writer.flush();
            outputStream.write(buffer, offset, length);
        }

        @Override
        public void println(String message) {
            writer.println(message);
        }

        @Override
        public void close() {
            try (outputStream; writer) {} catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Path path() {
            return path;
        }

        public boolean append() {
            return append;
        }
    }
}
