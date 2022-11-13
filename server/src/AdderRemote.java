import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AdderRemote extends UnicastRemoteObject implements Adder{
    AdderRemote()throws RemoteException{
        super();
    }

    @Override
    public String add(String username ,String message) throws RemoteException {
        System.out.println(username + ": " + message);
        return username;
    }


}