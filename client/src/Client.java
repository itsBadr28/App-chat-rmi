import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 2005);
            Adder obj=(Adder) reg.lookup("add");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the Username: ");
            String username = sc.nextLine();
            while (true) {
                System.out.print(username + ": ");
                String message = sc.nextLine();
                String ServerR =  obj.add(username,message);
                System.out.println("Server: " + ServerR);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
