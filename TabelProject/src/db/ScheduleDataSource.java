package db;

import model.Emp;
import model.Leave;
import model.ModelDataSource;
import model.Schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class ScheduleDataSource implements ModelDataSource {
    private ArrayList<Schedule> al;
    private final DBSrv db;


    public ScheduleDataSource(DBSrv dbsrv) {

        db = dbsrv;
        al = new ArrayList(100);

    }


    @Override
    public ArrayList getData(boolean refresh) {
        if(refresh){
            al = db.getSchedules();
        }
        return al;
    }

    @Override
    public String[] getColumnNames() {
        return         new String[]{
                "ID", "EMP", "ДатаНачала", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вск", "Всего"
        };

    }

    @Override
    public Class[] getColumnClasses() {
        return
                new Class[]{
                        Integer.class,
                        Emp.class,
                        Date.class,
                        Integer.class,
                        Integer.class,
                        Integer.class,
                        Integer.class,
                        Integer.class,
                        Integer.class,
                        Integer.class,
                        Integer.class};
    }

    @Override
    public void saveChanges(Map<Object, String> empMap) {

        for(Map.Entry e : empMap.entrySet()){
            String status = (String) e.getValue();
            if(status.equals("I")){
                db.save(e.getKey());
            }else if(status.equals("U")){
                db.save(e.getKey());
            }else if(status.equals("D")){
                db.delete(e.getKey());
            }
        }
    }

}
