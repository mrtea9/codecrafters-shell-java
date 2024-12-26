package command;

import parse.Redirect;
import store.Storage;

import java.util.List;

public interface Command {

    CommandResponse execute(Storage storage, List<String> arguments, List<Redirect> redirects);

}
