package file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFile {

    private final String[] pathArray = System.getenv("PATH").split(":");

    public Map<String, String> parseFiles() {
        Map<String, String> result = new HashMap<>();

        for (String pathString : pathArray) {
            File directory = new File(pathString);
            File[] listFiles = directory.listFiles();

            if (listFiles == null) continue;

            for (var file : listFiles) {
                Path path = Paths.get(file.getAbsolutePath());
                if (Files.isExecutable(path)) {
                    result.put(file.getName(), file.getAbsolutePath());
                }
            }
        }

        return result;
    }

}
