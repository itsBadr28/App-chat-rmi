import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String username;
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 2005);
            Chat obj = (Chat) reg.lookup("Chat");
            Scanner sc = new Scanner(System.in);
            do {
                System.out.print("Enter the Username: ");
                username = sc.nextLine();
            } while (username.equals("") || username.equals("  ") );
            while (true) {
                System.out.print(username + ": ");
                String message = sc.nextLine();
                if (message.equals("exit")) {
                    System.out.println("Client Exit");
                    obj.chat(username, "Client Exit");
                    break;
                }else {
                    String ServerR = obj.chat(username, message);
                    System.out.println("Server: " + ServerR);
                }

            }


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
