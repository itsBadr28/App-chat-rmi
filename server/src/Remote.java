import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Remote extends UnicastRemoteObject implements Chat{
    Remote()throws RemoteException{
        super();
    }

    private String  massege;
    @Override
    public String chat(String username ,String message) throws RemoteException {

        System.out.println(username + ": " + message);
        Scanner input = new Scanner(System.in);
            System.out.print("Server: ");
            this.massege = input.nextLine();
            return this.massege;
    }


}