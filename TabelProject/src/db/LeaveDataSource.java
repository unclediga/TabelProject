package db;

import model.Emp;
import model.Leave;
import model.ModelDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class LeaveDataSource implements ModelDataSource {
    private ArrayList<Leave> al;
    private final DBSrv db;


    public LeaveDataSource(DBSrv dbsrv) {

        db = dbsrv;
        al = new ArrayList(100);

    }


    @Override
    public ArrayList getData(boolean refresh) {
        if(refresh){
            al = db.getLeaves();
        }
        return al;
    }

    @Override
    public String[] getColumnNames() {
        return         new String[]{
                "ID", "EMP", "TLEAVE_ID","ДатаНачала", "ДатаОкончания"
        };

    }

    @Override
    public Class[] getColumnClasses() {
        return
                new Class[]{
                        Integer.class, Emp.class, String.class, Date.class, Date.class};
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
                db.save(e.getKey());
            }
        }
    }

}
