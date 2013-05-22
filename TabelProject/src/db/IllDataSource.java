package db;

import model.Emp;
import model.Ill;
import model.Leave;
import model.ModelDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class IllDataSource implements ModelDataSource {
    private ArrayList<Ill> al;
    private final DBSrv db;


    public IllDataSource(DBSrv dbsrv) {

        db = dbsrv;
        al = new ArrayList(100);

    }


    @Override
    public ArrayList getData(boolean refresh) {
        if(refresh){
            al = db.getIlls();
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
        id = db.getNextIllId();
        return id;
    }

}
