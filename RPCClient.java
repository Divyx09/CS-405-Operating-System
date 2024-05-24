
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

interface RemoteService extends Remote {

    int add(int a, int b) throws RemoteException;
}

public class RPCClient {

    public static void main(String[] args) {
        try {
            // Get the RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote service
            RemoteService stub = (RemoteService) registry.lookup("RemoteService");

            // Call the remote method
            int result = stub.add(5, 3);
            System.out.println("Result of 5 + 3: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
