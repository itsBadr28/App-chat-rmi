import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Adder extends Remote{
    public String add(String username ,String message)throws RemoteException;
}
