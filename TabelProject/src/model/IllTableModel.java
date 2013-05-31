package model;

import db.DBSrv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class IllTableModel extends ListTableModel<Ill> {

    public IllTableModel() {
        this.columnClasses =
                new Class[]{
                        Integer.class,
                        Emp.class,
                        String.class,
                        Date.class,
                        Date.class};

        this.columnNames = new String[]{
                "ID",
                "EMP",
                "TILL_ID",
                "ДатаНачала",
                "ДатаОкончания"
        };

        this.data = this.getList();
        this.changes = new HashMap<Ill, String>(DBSrv.INIT_CHANGES_COUNT);
    }

    public Ill createNewObject() {
        return  new Ill();
    }

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

        if (!changes.containsKey(ill)){
            changes.put(ill,"U");
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

    @Override
    public ArrayList<Ill> getList() {
        return DBSrv.getInstance().getList(Ill.class);
    }
}
