import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{
    public String chat(String username ,String message)throws RemoteException;
}
