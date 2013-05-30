package model;

import java.util.Date;

/**
 *  Расписание работы работника
 */
public class Schedule {
    private Integer id;

    private Emp emp;

    private Date dateFrom;
    private int[] days = new int[8];

    public Schedule(){
        for(int i=1;i<8;i++)
            days[i] = 0;
        this.dateFrom = new Date();

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

    public int getHours(int dayOfWeek){
        return days[dayOfWeek];
    }

    public void setHours(int dayOfWeek,int hours){
        days[dayOfWeek] = hours;
    }

    public int getSumHours() {
        int sum = 0;
        for (int i = 1; i < 8; i++) {
            sum += days[i];
        }
        return sum;
    }
}
