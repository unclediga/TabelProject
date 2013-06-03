package model;

import db.DBSrv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 */
public class TransTableModel extends ListTableModel<Trans>{



    public TransTableModel() {
        this.columnNames =         new String[]{
                "ID", "EMP", "ДатаНачала", "Должность", "Ставка"
        };
        this.columnClasses =                 new Class[]{
                Integer.class, Emp.class, Date.class, Appoint.class, Double.class};


        this.data = this.getList();
        this.changes = new HashMap<Trans, String>(DBSrv.INIT_CHANGES_COUNT);

    }

    @Override
    public Trans createNewObject() {
        return new Trans();
    }

    @Override
    public ArrayList<Trans> getList() {
        return DBSrv.getInstance().getList(Trans.class);
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Trans)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Trans trans = (Trans) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changes.containsKey(trans)){
            changes.put(trans,"U");
        }


        switch (columnIndex) {
            case 0:
                trans.setId((Integer) val);
                break;
            case 1:
                trans.setEmp((Emp) val);
                break;
            case 2:
                trans.setDateFrom((Date) val);
                break;
            case 3:
                trans.setAppoint((Appoint) val);
                break;
            case 4:
                trans.setWageRate((Double) val);
                break;
            default:
                System.err.println("TransTableModel:columnIndex is out of range");
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if(obj == null){
            return null;
        }

        if(!(obj instanceof Trans)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Trans trans = (Trans) obj;

        switch (columnIndex) {
            case 0:
                return trans.getId();
            case 1:
                return trans.getEmp();
            case 2:
                return trans.getDateFrom();
            case 3:
                return trans.getAppoint();
            case 4:
                return trans.getWageRate();
            default:
                System.err.println("LeaveTableModel:columnIndex is out of range");
        }
        return null;
    }
}
