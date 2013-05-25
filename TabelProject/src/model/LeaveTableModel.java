package model;

import db.DBSrv;
import db.LeaveDataSource;

import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class LeaveTableModel extends ListTableModel {
    public LeaveTableModel(DBSrv dbsrv) {
        this.ds = new LeaveDataSource(dbsrv);
        this.data = ds.getData(true);
        this.changedObjMap = new HashMap<Object,String>(this.data.size());
        this.columnClasses = ds.getColumnClasses();
        this.columnNames = ds.getColumnNames();
    }

    @Override
    public Object createNewObject() {

        int id = ds.getNextId();

        Leave leave = new Leave(
                new Integer(id),
                null,
                Leave.getDefaultType(),
                new Date(),
                new Date()
        );
        return leave;
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Leave)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Leave leave = (Leave) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changedObjMap.containsKey(leave)){
            changedObjMap.put(leave,"U");
        }


        switch (columnIndex) {
            case 0:
                leave.setId((Integer) val);
                break;
            case 1:
                leave.setEmp((Emp) val);
                break;
            case 2:
                leave.setTleave_id((String) val);
                break;
            case 3:
                leave.setDateFrom((Date) val);
                break;
            case 4:
                leave.setDateTo((Date) val);
                break;
            default:
                System.err.println("LeaveTableModel:columnIndex is out of range");
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if(obj == null){
            return null;
        }

        if(!(obj instanceof Leave)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Leave leave = (Leave) obj;

        switch (columnIndex) {
            case 0:
                return leave.getId();
            case 1:
                return leave.getEmp();
            case 2:
                return leave.getTleave_id();
            case 3:
                return leave.getDateFrom();
            case 4:
                return leave.getDateTo();
            default:
                System.err.println("LeaveTableModel:columnIndex is out of range");
        }
        return null;
    }

}
