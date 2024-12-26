package parse;

import java.util.List;

public record ParsedLine(List<String> arguments, List<Redirect> redirects) {
}
