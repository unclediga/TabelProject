package model;

import java.util.Date;

/**
 *
 */
public class Trans {
    Integer id;
    Emp emp;
    Date dateFrom;
    Appoint appoint;
    WageRateType wageRateType;
    double wageRate;

    public Trans(Integer id, Emp emp, Date dateFrom, Appoint appoint, Float wageRate) {
        this.id = id;
        this.emp = emp;
        this.dateFrom = dateFrom;
        this.appoint = appoint;
        this.wageRate = wageRate;
    }

    public Trans() {
        this.id = null;
        this.emp = null;
        this.dateFrom = new Date();
        this.appoint = null;
        this.wageRate = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Appoint getAppoint() {
        return appoint;
    }

    public void setAppoint(Appoint appoint) {
        this.appoint = appoint;
    }

    public WageRateType getWageRateType() {
        return wageRateType;
    }
    public void setWageRateType(WageRateType wageRateType) {
        this.wageRateType = wageRateType;
    }

    public double getWageRate() {
        return wageRate;
    }

    public void setWageRate(double wageRate) {
        this.wageRate = wageRate;
    }

    @Override
    public String toString() {
        return ""+wageRate+" "+appoint;
    }
}
