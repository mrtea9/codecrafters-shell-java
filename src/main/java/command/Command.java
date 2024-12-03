package command;

import store.Storage;

public interface Command {

    CommandResponse execute(Storage storage);

}
