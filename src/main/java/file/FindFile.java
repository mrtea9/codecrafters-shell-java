package file;

import java.io.File;
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
                result.add(file1.getName());
            }
        }

        System.out.println(result);
    }

}
