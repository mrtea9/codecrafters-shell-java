package command;

import io.RedirectStreams;
import store.Storage;

import java.util.List;

public interface Command {

    CommandResponse execute(Storage storage, List<String> arguments, RedirectStreams redirects);

}
