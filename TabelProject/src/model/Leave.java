package model;

import java.util.Date;

/**
 *
 */
public class Leave {

    private Integer id;
    private Emp emp;
    private String tleave_id;
    private Date dateFrom;
    private Date dateTo;
    private static String[] types  = new String[] { "ОТП" , "АДМ" };

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Leave(Integer id, Emp emp, String tleave_id, Date dateFrom, Date dateTo) {
        this.id = id;
        this.emp = emp;
        this.tleave_id = tleave_id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Leave() {

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

    public String getTleave_id() {
        return tleave_id;
    }

    public void setTleave_id(String tleave_id) {
        this.tleave_id = tleave_id;
    }

    public static String getDefaultType() {
        return types[0];
    }

    public static String[] getTypes() {
        return types;
    }

}
