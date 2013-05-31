package model;

import db.DBSrv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class LeaveTableModel extends ListTableModel<Leave> {

    public LeaveTableModel() {
        this.columnNames =         new String[]{
                "ID", "EMP", "TLEAVE_ID","ДатаНачала", "ДатаОкончания"
        };
        this.columnClasses =                 new Class[]{
                Integer.class, Emp.class, String.class, Date.class, Date.class};


        this.data = this.getList();
        this.changes = new HashMap<Leave, String>(DBSrv.INIT_CHANGES_COUNT);

    }

    @Override
    public Leave createNewObject() {
        return new Leave();
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

        if (!changes.containsKey(leave)){
            changes.put(leave,"U");
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

    @Override
    public ArrayList<Leave> getList() {
        return DBSrv.getInstance().getList(Leave.class);
    }

}
