package model;

import db.DBSrv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class AppointTableModel extends ListTableModel<Appoint> {

    public AppointTableModel() {
        this.columnNames = new String[]{
                "ID", "NAME", "SOCR"
        };
        this.columnClasses = new Class[]{
                Integer.class, String.class, String.class
        };


        this.data = this.getList();
        this.changes = new HashMap<Appoint, String>(DBSrv.INIT_CHANGES_COUNT);

    }

    @Override
    public Appoint createNewObject() {
        return new Appoint();
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Appoint)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Appoint appoint = (Appoint) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changes.containsKey(appoint)){
            changes.put(appoint,"U");
        }

        switch (columnIndex) {
            case 0:
                appoint.setId((Integer) val);
                break;
            case 1:
                appoint.setName((String) val);
                break;
            case 2:
                appoint.setSocr((String) val);
                break;
            default:
                System.err.println("AppointTableModel:columnIndex is out of range");
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if(obj == null){
            return null;
        }

        if(!(obj instanceof Appoint)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Appoint appoint = (Appoint) obj;

        switch (columnIndex) {
            case 0:
                return appoint.getId();
            case 1:
                return appoint.getName();
            case 2:
                return appoint.getSocr();
            default:
                System.err.println("AppointTableModel:columnIndex is out of range");
        }
        return null;
    }

    @Override
    public ArrayList<Appoint> getList() {
        return DBSrv.getInstance().getList(Appoint.class);
    }

    @Override
    public ArrayList<Appoint> getList(Object owner) {
        return DBSrv.getInstance().getList(Appoint.class,owner);
    }

}
