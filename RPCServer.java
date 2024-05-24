
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

// Interface defining remote methods
interface RemoteService extends Remote {

    int add(int a, int b) throws RemoteException;
}

// Class implementing the remote interface
class RemoteServiceImpl extends UnicastRemoteObject implements RemoteService {

    public RemoteServiceImpl() throws RemoteException {
        super();
    }

    // Implementation of the remote method
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}

// Server class
public class RPCServer {

    public static void main(String args[]) {
        try {
            // Create an instance of the remote implementation
            RemoteService remoteService = new RemoteServiceImpl();

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RemoteService", remoteService);

            System.out.println("Server is ready...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
     