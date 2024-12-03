package file;

import java.io.File;
import java.util.Arrays;

public class FindFile {

    private final String[] pathArray = System.getenv("PATH").split(":");

    public void findFile() {
        File file = new File(pathArray[0]);
        File[] list = file.listFiles();
        for (var file1 : list) {
            System.out.println(file1.getName());
        }
    }

}
