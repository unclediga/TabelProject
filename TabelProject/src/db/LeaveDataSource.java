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
                "ID", "EMP", "ДатаНачала", "ДатаОкончания"
        };

    }

    @Override
    public Class[] getColumnClasses() {
        return
                new Class[]{
                        Integer.class, Emp.class, Date.class, Date.class};
    }

    @Override
    public void saveChanges(Map<Object, String> empMap) {

        for(Map.Entry e : empMap.entrySet()){
            String status = (String) e.getValue();
            if(status.equals("I")){
                db.insertLeave((Leave) e.getKey());
            }else if(status.equals("U")){
                db.updateLeave((Leave) e.getKey());
            }else if(status.equals("D")){
                db.deleteLeave((Leave) e.getKey());
            }
        }
    }

    @Override
    public int getNextId() {
        int id;
        id = db.getNextLeaveId();
        return id;
    }

}
