package model;

import java.util.Date;

/**
 *
 */
public class Ill {

    private Integer id;
    private Emp emp;
    private String till_id;
    private Date dateFrom;
    private Date dateTo;
    private static String[] types = new String[] { "БЛН" , "РЕБ" };

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Ill(Integer id, Emp emp, String tleave_id, Date dateFrom, Date dateTo) {
        this.id = id;
        this.emp = emp;
        this.till_id = tleave_id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Ill() {
        this.id = null;
        this.emp = null;
        this.till_id = Ill.getDefaultType();
        this.dateFrom = new Date();
        this.dateTo = new Date();
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

    public String getTill_id() {
        return till_id;
    }

    public void setTill_id(String till_id) {
        this.till_id = till_id;
    }

    public static String getDefaultType(){
        return types[0];
    }

    public static String[] getTypes() {
        return types;
    }

}
