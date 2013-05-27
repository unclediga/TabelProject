package model;

import db.DBSrv;
import db.ScheduleDataSource;

import java.util.Date;
import java.util.HashMap;


/**
 *
 */
public class ScheduleTableModel extends ListTableModel {
    public ScheduleTableModel(DBSrv dbsrv) {
        this.ds = new ScheduleDataSource(dbsrv);
        this.data = ds.getData(true);
        this.changedObjMap = new HashMap<Object, String>(this.data.size());
        this.columnClasses = ds.getColumnClasses();
        this.columnNames = ds.getColumnNames();
    }

    /**
     * Поле <b>ВСЕГО</b> не должно быть редактируемым.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(getColumnName(columnIndex).equals("Всего")){
            return false;
        }

        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public Object createNewObject() {

        Schedule schedule = new Schedule();
        schedule.setDateFrom(new Date());
        return schedule;
    }

    /**
     * Переопределяю из-за столбца <b>ВСЕГО</b>.<br>
     * При изменении какой-либо ячейки с часами, должна измениться итоговая сумма.<br/>
     * fireTableChanged() сработает, но сбрасывает текущую строку и её выделение.<br/>
     * Приходится прицельно обновлять ячейку в строке.<br>
     * Параметры взяты наследованием.
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        if(columnIndex > 1 && columnIndex != 10){
            fireTableCellUpdated(rowIndex,10);
        }
    }

    @Override
    public void setColumnValue(Object obj, int columnIndex, Object val) {

        if (obj == null) {
            return;
        }

        if (!(obj instanceof Schedule)) {
            System.err.println("Чё-то не то передал");
            return;
        }

        Schedule schedule = (Schedule) obj;

        // если INSERT или DELETE (что неверно) уже были
        // нечего сбрасывать в UPDATE. Потом пойдут ошибки при обновлении

        if (!changedObjMap.containsKey(schedule)) {
            changedObjMap.put(schedule, "U");
        }


        switch (columnIndex) {
            case 0:
                schedule.setId((Integer) val);
                break;
            case 1:
                schedule.setEmp((Emp) val);
                break;
            case 2:
                schedule.setDateFrom((Date) val);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                schedule.setHours(columnIndex - 2, (Integer) val);
                break;
            default:
                System.err.println("ScheduleTableModel:columnIndex is out of range");
        }

    }

    @Override
    public Object getColumnValue(Object obj, int columnIndex) {
        if (obj == null) {
            return null;
        }

        if (!(obj instanceof Schedule)) {
            System.err.println("Чё-то не то передал");
            return null;
        }

        Schedule schedule = (Schedule) obj;

        switch (columnIndex) {
            case 0:
                return schedule.getId();
            case 1:
                return schedule.getEmp();
            case 2:
                return schedule.getDateFrom();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return schedule.getHours(columnIndex - 2);
            case 10:
                return schedule.getSumHours();
            default:
                System.err.println("LeaveTableModel:columnIndex is out of range");
        }
        return null;
    }

}
