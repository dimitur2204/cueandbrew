package via.dk.cueandbrew.model;

import java.beans.PropertyChangeListener;

public interface Model
{
  void onLogin(String login, String password);
  void addPropertyChangeListener(PropertyChangeListener listener);
}
