package model;

import java.util.Date;

/**
 *
 */
public class Ill {

    private Integer id;
    private Emp emp;
    private Date dateFrom;
    private Date dateTo;

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Ill(Integer id, Emp emp, Date dateFrom, Date dateTo) {
        this.id = id;
        this.emp = emp;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Ill() {

    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
