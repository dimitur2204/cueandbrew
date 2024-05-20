package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.util.UUID;

/**
 * A class that is responsible for the Registration
 * @Author Marius Marcoci
 */
public class Registration implements Serializable
{
    private static Registration instance;
    private int manager_id;
    private String login;
    private UUID id;

    /**
     * A private constructor that initializes the Registration with the specified values
     */
    private Registration() {
        this.manager_id = -1;
        this.login = null;
        this.id = UUID.randomUUID();
    }

    /** A method that returns the instance of the registration
     * @return The instance of the registration
     */
    public static synchronized Registration getInstance() {
        if (instance == null) {
            instance = new Registration();
        }
        return instance;
    }

    /** A method that creates an empty registration
     * @return An empty registration
     */
    public static synchronized Registration createAnEmptyRegistration() {
        return new Registration();
    }

    /**
     * A method that sets the manager id
     * @param manager_id The id of the manager
     */
    public void setManager_id(int manager_id)
    {
        this.manager_id = manager_id;
    }

    /**
     * A method that sets the login
     * @param login The login of the manager
     */
    public void setLogin(String login)
    {
        this.login = login;
    }

    /**
     * A method that returns the manager id
     * @return The id of the manager
     */
    public int getManager_id()
    {
        return manager_id;
    }

    /**
     * A method that returns the login
     * @return The login of the manager
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * A method that returns the id
     * @return The id of the registration
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * A method that sets the id
     * @param id The id of the registration
     */
    public void setId(UUID id)
    {
        this.id = id;
    }
}
