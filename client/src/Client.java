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
                System.out.print("Enter the  message: ");
                String message = sc.nextLine();
                obj.add(username,message);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
