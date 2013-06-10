package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
public class Tabel {
    Integer id;
    Integer tyear;
    Integer tmonth;
    Integer rowNum;
    Emp emp;
    Trans trans;
    ArrayList<TabelDay> days;

    public Tabel() {
        days = new ArrayList<TabelDay>(31);
        for (int i = 0; i < 31; i++) {
            days.add(new TabelDay(this,i));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTyear() {
        return tyear;
    }

    public void setTyear(Integer tyear) {
        this.tyear = tyear;
    }

    public Integer getTmonth() {
        return tmonth;
    }

    public void setTmonth(Integer tmonth) {
        this.tmonth = tmonth;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Trans getTrans() {
        return trans;
    }

    public void setTrans(Trans trans) {
        this.trans = trans;
    }

    public ArrayList<TabelDay> getDays() {
        return days;
    }

    public void setDays(ArrayList<TabelDay> days) {
        this.days = days;
    }

    public void setDay(int i, TabelDay day) {
        days.set(i, day);
    }

    public TabelDay getDay(int i) {
        return days.get(i);
    }
}
