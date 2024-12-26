package io;

import java.io.PrintStream;

public enum StandardNamedStream {

    OUTPUT(System.out),
    ERROR(System.err),
    UNKNOWN(null);

    private final PrintStream printStream;

    StandardNamedStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public static StandardNamedStream fromFileDescriptor(int fileDescriptor) {
        return switch (fileDescriptor) {
            case 1 -> OUTPUT;
            case 2 -> ERROR;
            default -> UNKNOWN;
        };
    }

}
