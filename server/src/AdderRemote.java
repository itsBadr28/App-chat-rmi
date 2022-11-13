import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class AdderRemote extends UnicastRemoteObject implements Adder{
    AdderRemote()throws RemoteException{
        super();
    }

    private String  Massege;
    @Override
    public String add(String username ,String message) throws RemoteException {

        System.out.println(username + ": " + message);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Server: ");
            Massege = input.nextLine();
//            System.out.println("Server: " + message);
            return Massege;
        }
    }


}