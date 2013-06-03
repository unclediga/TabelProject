package model;

import java.util.Date;

/**
 *  Класс РАБОТНИК
 */
public class Emp {

    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String appoint;


    private Date hireDate;
    private Date fireDate;
    private Appoint appoint;

    public Appoint getAppoint() {
        return appoint;
    }

    public void setAppoint(Appoint appoint) {
        this.appoint = appoint;
    }


    public Emp(Integer id, String lastName, String firstName, String middleName, String appoint, Date hireDate, Date fireDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.appoint    = appoint;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
    }

    public Emp() {
        this.id = null;
        this.lastName = "Некто";
        this.firstName = "Некто";
        this.middleName = "Некто";
        this.hireDate = new Date();
        this.fireDate = new Date();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAppoint() {
        return appoint;
    }

    public void setAppoint(String appoint) {
        this.appoint = appoint;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getFireDate() {
        return fireDate;
    }

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString(){
        return lastName+" "+firstName+" "+middleName;
    }



}
