import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {
            Chat obj = new Remote();
            Registry reg = LocateRegistry.createRegistry(2005);
            reg.rebind("Chat", obj);
            System.out.println("Server Running....");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}