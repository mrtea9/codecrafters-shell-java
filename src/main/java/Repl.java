import java.util.Scanner;

public class Repl {

    private Scanner scanner = new Scanner(System.in);

    public void startLoop() {

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            System.out.println(input + ": command not found");
        }

    }
}
