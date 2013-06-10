package model;

/**
 *
 */
public class TabelDay {
    Integer id;
    Tabel tabel;
    Integer dayNum;
    String dayType;
    double dayHours;

    public TabelDay() {
    }

    public TabelDay(Tabel tabel, Integer dayNum) {
        this.tabel = tabel;
        this.dayNum = dayNum;
        this.dayType = null;
        this.dayHours = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tabel getTabel() {
        return tabel;
    }

    public void setTabel(Tabel tabel) {
        this.tabel = tabel;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public double getDayHours() {
        return dayHours;
    }

    public void setDayHours(double dayHours) {
        this.dayHours = dayHours;
    }

    public String toString() {
        if(dayType == null || dayType.equals("")){
            return ""+dayHours;
        }else {
            return dayType;
        }
    }
}
