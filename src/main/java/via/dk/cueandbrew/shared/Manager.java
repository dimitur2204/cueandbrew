package via.dk.cueandbrew.shared;

import java.util.Date;

/**
 * A class that is responsible for the Manager
 * @Author Dimitar Nizamov
 */
public class Manager {
    private String cpr;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private Date hiredate;
    private Date firedate;

    /**
     * A constructor that initializes the Manager with the specified values
     * @param cpr The cpr of the manager
     * @param firstname The first name of the manager
     * @param lastname The last name of the manager
     * @param birthdate The birth date of the manager
     * @param hiredate The hire date of the manager
     * @param firedate The fire date of the manager
     */
    public Manager(String cpr, String firstname, String lastname, Date birthdate, Date hiredate) {
        this.cpr = cpr;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.hiredate = hiredate;
        this.firedate = null;
    }

}
