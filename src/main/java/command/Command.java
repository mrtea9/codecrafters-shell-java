package command;

import store.Storage;

import java.util.List;

public interface Command {

    CommandResponse execute(Storage storage, List<String> arguments, List<String> redirects);

}
