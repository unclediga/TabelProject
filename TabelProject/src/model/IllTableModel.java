package model;

import db.DBSrv;
import db.IllDataSource;

import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class IllTableModel extends ListTableModel {
    public IllTableModel(DBSrv dbsrv) {
        this.ds = new IllDataSource(dbsrv);
        this.data = ds.getData(true);
        this.changedObjMap = new HashMap<Object,String>(this.data.size());
        this.columnClasses = ds.getColumnClasses();
        this.columnNames = ds.getColumnNames();
    }

    @Override
    public Object createNewObject() {

        int id = ds.getNextId();

        Ill ill = new Ill(
                new Integer(id),
                null,
                Ill.getDefaultType(),
                new Date(),
                new Date()
        );
        return ill;
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Ill)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Ill ill = (Ill) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changedObjMap.containsKey(ill)){
            changedObjMap.put(ill,"U");
        }


        switch (columnIndex) {
            case 0:
                ill.setId((Integer) val);
                break;
            case 1:
                ill.setEmp((Emp) val);
                break;
            case 2:
                ill.setTill_id((String) val);
                break;
            case 3:
                ill.setDateFrom((Date) val);
                break;
            case 4:
                ill.setDateTo((Date) val);
                break;
            default:
                System.err.println("IllTableModel:columnIndex is out of range");
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if(obj == null){
            return null;
        }

        if(!(obj instanceof Ill)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Ill ill = (Ill) obj;

        switch (columnIndex) {
            case 0:
                return ill.getId();
            case 1:
                return ill.getEmp();
            case 2:
                return ill.getTill_id();
            case 3:
                return ill.getDateFrom();
            case 4:
                return ill.getDateTo();
            default:
                System.err.println("IllTableModel:columnIndex is out of range");
        }
        return null;
    }

}
