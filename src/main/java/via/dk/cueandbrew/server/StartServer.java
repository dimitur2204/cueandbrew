package via.dk.cueandbrew.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class StartServer
{
  public static void main(String[] args)
      throws RemoteException, AlreadyBoundException
  {
    Registry registry = LocateRegistry.createRegistry(1099);
    ServerImplementation server = new ServerImplementation();
    registry.bind("cueandbrew", UnicastRemoteObject.exportObject(server, 0));
    System.out.println("Server started");
  }
}
