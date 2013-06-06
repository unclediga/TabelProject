package model;

import db.DBSrv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Общая модель для всех форм-списков
 */
public class EmpTableModel extends ListTableModel<Emp> {


    public EmpTableModel() {

        this.columnNames = new String[]{
                "ID", "Фамилия", "Имя", "Отчество", "ДатаПриема","ДатаУвольнения"
        };
        this.columnClasses =   new Class[]{
                Integer.class, String.class, String.class, String.class, Date.class, Date.class};

        this.data = this.getList();
        this.changes = new HashMap<Emp, String>(DBSrv.INIT_CHANGES_COUNT);
    }

    @Override
    public Emp createNewObject() {
        return new Emp();
    }

    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if(obj == null){
            return;
        }

        if(!(obj instanceof Emp)){
            System.err.println("Чё-то не то передал");
            return;
        }

        Emp emp = (Emp) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changes.containsKey(emp)){
            changes.put(emp,"U");
        }


            switch (columnIndex) {
                case 0:
                    emp.setId((Integer) val);
                    break;
                case 1:
                    emp.setLastName((String) val);
                    break;
                case 2:
                    emp.setFirstName((String) val);
                    break;
                case 3:
                    emp.setMiddleName((String) val);
                    break;
                case 4:
                    emp.setHireDate((Date) val);
                    break;
                case 5:
                    emp.setFireDate((Date) val);
                    break;
                default:
                    System.err.println("tabel:columnIndex is out of range");
            }

    }

    public Object getColumnValue(Object obj, int columnIndex) {

        if(obj == null){
            return null;
        }

        if(!(obj instanceof Emp)){
            System.err.println("Чё-то не то передал");
            return null;
        }

        Emp emp = (Emp) obj;

            switch (columnIndex) {
                case 0:
                    return emp.getId();
                case 1:
                    return emp.getLastName();
                case 2:
                    return emp.getFirstName();
                case 3:
                    return emp.getMiddleName();
                case 4:
                    return emp.getHireDate();
                case 5:
                    return emp.getFireDate();
                default:
                    return "DATA_NO_FOUND";
            }

    }

    @Override
    public ArrayList<Emp> getList() {
        return DBSrv.getInstance().getList(Emp.class);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnClasses[columnIndex] == Appoint.class){
            return false;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }
}
