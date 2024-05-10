package via.dk.cueandbrew.model;

import java.util.Date;

public class Manager {
    private String cpr;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private Date hiredate;
    private Date firedate;

    public Manager(String cpr, String firstname, String lastname, Date birthdate, Date hiredate) {
        this.cpr = cpr;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.hiredate = hiredate;
        this.firedate = null;
    }

}
