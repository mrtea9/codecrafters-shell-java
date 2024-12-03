package file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindFile {

    private final String[] pathArray = System.getenv("PATH").split(":");

    public void findFile() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < pathArray.length; i++) {
            System.out.println(pathArray[i]);
            File file = new File(pathArray[i]);
            File[] list = file.listFiles();
            for (var file1 : list) {
                System.out.println(file1.getName());
            }
        }

        System.out.println(result);
    }

}
