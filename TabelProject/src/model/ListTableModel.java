package model;

import db.DBSrv;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class ListTableModel<T> extends AbstractTableModel {
    protected ArrayList<T> data = null;
    protected Map<T,String> changes = null;
    protected Class[] columnClasses = null;
    protected String[] columnNames = null;
    protected Object owner;


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

    public void saveChanges() throws Exception {
        DBSrv.getInstance().putAll((Map<Object, String>) changes);
        DBSrv.getInstance().doCommit();
        changes.clear();
        fireTableDataChanged();
    }

    public void addRow() {

        T newObj = createNewObject();
        data.add(newObj);
        changes.put(newObj, "I");
        fireTableRowsInserted(data.size(), data.size());
    }

    public void removeRow(int i) {
        //если уже есть что-то по указанному объекту
        // просто удаляем все и оставляем одно удаление
        Object changedObj = data.get(i);
        if (changes.containsKey(changedObj)){
            if(changes.get(changedObj).equals("I")){
                changes.remove(changedObj);
            }else {
                changes.put(data.get(i),"D");
            }
        }else{
            changes.put(data.get(i),"D");
        }


        data.remove(i);
        fireTableRowsDeleted(i, i);

    }

    public void printChanges() {
        for(Map.Entry e : changes.entrySet()){
            System.out.println(e.getKey().toString()+":" +e.getValue());
        }

    }

    public void refresh(){
        changes.clear();
        if (owner != null) {
            data = getList(owner);
        }else {
            data = getList();
        }
        fireTableDataChanged();

    }

    public abstract T createNewObject();

    public abstract ArrayList<T> getList();

    public abstract ArrayList<T> getList(Object owner);

    public abstract void setColumnValue(Object obj, int columnIndex, Object val);

    public abstract Object getColumnValue(Object obj, int columnIndex);

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object getOwner() {
        return owner;
    }

    public T getRowObject(int rowIndex){
        // какие-либо нескладушки с получением из спсика
        // 1) Уже нет такого индекса
        // 2) пустой data
        try {
            return data.get(rowIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
