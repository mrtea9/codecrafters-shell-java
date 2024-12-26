package io;

import parse.Redirect;

import java.io.FileNotFoundException;
import java.util.List;

public record RedirectStreams(RedirectStream output, RedirectStream error) implements AutoCloseable {

    @Override
    public void close() {
        try (output; error) {}
    }

    public static RedirectStreams from(List<Redirect> redirects) throws FileNotFoundException {
        RedirectStream output = new RedirectStream.Standard(StandardNamedStream.OUTPUT, System.out);
        RedirectStream error = new RedirectStream.Standard(StandardNamedStream.ERROR, System.err);

        for (final var redirect : redirects) {
            @SuppressWarnings("resource")
            final var stream = new RedirectStream.File(redirect.path(), redirect.append());

            switch (redirect.namedStream()) {
                case OUTPUT -> {
                    output.close();
                    output = stream;
                }
                case ERROR -> {
                    error.close();
                    error = stream;
                }
                case UNKNOWN -> {
                    stream.close();
                }
            }
        }

        return new RedirectStreams(output, error);
    }
}
