package via.dk.cueandbrew.client;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public interface CallbackClient
{
  void onLogin(String login, String password) throws RemoteException;
  void addPropertyChange(PropertyChangeListener listener);
}
