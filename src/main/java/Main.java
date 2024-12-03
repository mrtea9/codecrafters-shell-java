import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(System.getenv("PATH").split(":")));

        Repl repl = new Repl();

        repl.start();

    }
}
