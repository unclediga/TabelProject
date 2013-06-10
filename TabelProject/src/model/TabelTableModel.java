package model;

import db.DBSrv;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 */
public class TabelTableModel extends ListTableModel<Tabel>{

    private final int columnCount;

    public TabelTableModel() {

        columnCount = 4 + 31;
        columnNames = new String[columnCount];   columnClasses = new Class[columnCount];
        columnNames[0] = "ID";          columnClasses[0] = Integer.class;
        columnNames[1] = "NUM";         columnClasses[1] = Integer.class;
        columnNames[2] = "EMP";         columnClasses[2] = Emp.class;
        columnNames[3] = "Назначение";  columnClasses[3] = Trans.class;

        for (int i = 4; i < columnCount; i++) {
            columnNames[i] = ""+(i-3);
            columnClasses[i] = TabelDay.class;
        }
        this.data = this.getList();
        this.changes = new HashMap<Tabel, String>(DBSrv.INIT_CHANGES_COUNT);

    }
//    public TabelTableModel(Object owner) {
//
//        if (owner != null) {
//            this.owner = owner;
//            this.data = this.getList(owner);
//        }else{
//            this.data = this.getList();
//        }
//        this.changes = new HashMap<Trans, String>(DBSrv.INIT_CHANGES_COUNT);
//    }

    @Override
    public Tabel createNewObject() {
        Tabel tabel = new Tabel();
        Emp owner = (Emp) this.getOwner();

        if (owner != null) {
            tabel.setEmp(owner);
        }
        return tabel;
    }

    @Override
    public ArrayList<Tabel> getList() {
        return DBSrv.getInstance().getList(Tabel.class);
    }

    @Override
    public ArrayList<Tabel> getList(Object owner) {
        return DBSrv.getInstance().getList(Tabel.class,owner);
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Tabel)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Tabel tabel = (Tabel) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changes.containsKey(tabel)){
            changes.put(tabel,"U");
        }

        if(columnIndex < 0  || columnIndex > columnCount){
            System.err.println("Column index:"+columnIndex+" higher column count:"+columnCount);
            return;
        }


        switch (columnIndex) {
            case 0:
                tabel.setId((Integer) val);
                break;
            case 1:
                tabel.setRowNum((Integer) val);
                break;
            case 2:
                tabel.setEmp((Emp) val);
                break;
            case 3:
                tabel.setTrans((Trans) val);
                break;
            default:
                tabel.setDay(columnIndex - 4,(TabelDay) val);
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if(obj == null){
            return null;
        }

        if(!(obj instanceof Tabel)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Tabel tabel = (Tabel) obj;

        switch (columnIndex) {
            case 0:
                return tabel.getId();
            case 1:
                return tabel.getRowNum();
            case 2:
                return tabel.getEmp();
            case 3:
                return tabel.getTrans();
        }
        if(3 < columnIndex  && columnIndex < columnCount){
            return tabel.getDay(columnIndex - 4);
        }
        System.err.println("Column index:"+columnIndex+" higher column count:"+columnCount);
        return null;
    }
}
