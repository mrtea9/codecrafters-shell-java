package file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindFile {

    private final String[] pathArray = System.getenv("PATH").split(":");

    public void findFile() {
        List<String> result = new ArrayList<>();

        for (String s : pathArray) {
            File file = new File(s);
            File[] list = file.listFiles();
            if (list == null) continue;
            for (var file1 : list) {
                Path path = Paths.get(file1.getAbsolutePath());
                if (Files.isExecutable(path)) result.add(file1.getAbsolutePath());
            }
        }

        System.out.println(result);
    }

}
