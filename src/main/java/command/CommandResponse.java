package command;

public record CommandResponse(String value) {

    public String toString() {
        return "%s".formatted(value);
    }
}
