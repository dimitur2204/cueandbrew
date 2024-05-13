package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import via.dk.cueandbrew.shared.Registration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote
{
  void onLogin(String login, String password) throws RemoteException;
  void addPropertyChangeListener(RemotePropertyChangeListener<Registration> listener) throws RemoteException;
}
