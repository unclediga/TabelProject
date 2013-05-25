package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public abstract class ListTableModel extends AbstractTableModel {
    protected ArrayList<Object> data = null;
    protected Map<Object,String> changedObjMap = null;
    protected Class[] columnClasses = null;
    protected String[] columnNames = null;
    protected ModelDataSource ds;


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumnValue(data.get(rowIndex),columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        setColumnValue(data.get(rowIndex),columnIndex,aValue);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void saveChanges() {
        ds.saveChanges(changedObjMap);
    }

    public void addRow() {

        Object newObj = createNewObject();
        data.add(newObj);
        changedObjMap.put(newObj, "I");
        fireTableRowsInserted(data.size(), data.size());
    }

    public abstract Object createNewObject();

    public void removeRow(int i) {
        //если уже есть что-то по указанному объекту
        // просто удаляем все и оставляем одно удаление
        Object changedObj = data.get(i);
        if (changedObjMap.containsKey(changedObj)){
            if(changedObjMap.get(changedObj).equals("I")){
                changedObjMap.remove(changedObj);
            }else {
                changedObjMap.put(data.get(i),"D");
            }
        }else{
            changedObjMap.put(data.get(i),"D");
        }


        data.remove(i);
        fireTableRowsDeleted(i, i);

    }

    public abstract void setColumnValue(Object obj, int columnIndex, Object val);

    public abstract Object getColumnValue(Object obj, int columnIndex);

    public void printChanges() {
        for(Map.Entry e : changedObjMap.entrySet()){
            System.out.println(e.getKey().toString()+":" +e.getValue());
        }

    }

    public void refresh(){
        changedObjMap.clear();
        data = ds.getData(true);
        fireTableDataChanged();

    }
}
