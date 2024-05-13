package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Registration implements Serializable
{
    private int manager_id;
    private String login;

    public Registration() {
        this.manager_id = -1;
        this.login = null;
    }

    public Registration(int id, String login) {
        this.manager_id = id;
        this.login = login;
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

    @Override public String toString()
    {
        return "Registration{" + "manager_id=" + manager_id + ", login='"
            + login + '\'' + '}';
    }
}
