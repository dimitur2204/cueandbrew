package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Registration implements Serializable
{
    private static Registration instance;
    private int manager_id;
    private String login;

    private Registration() {
        this.manager_id = -1;
        this.login = null;
    }

    public static synchronized Registration getInstance() {
        if (instance == null) {
            instance = new Registration();
        }
        return instance;
    }

    public static synchronized Registration createAnEmptyRegistration() {
        return new Registration();
    }

    public void setManager_id(int manager_id)
    {
        this.manager_id = manager_id;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public int getManager_id()
    {
        return manager_id;
    }

    public String getLogin()
    {
        return login;
    }
}
