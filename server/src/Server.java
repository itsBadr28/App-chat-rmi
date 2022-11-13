import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {
            Adder obj = new AdderRemote();
            Registry reg = LocateRegistry.createRegistry(2005);
            reg.rebind("add", obj);
            System.out.println("Server Running...");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}