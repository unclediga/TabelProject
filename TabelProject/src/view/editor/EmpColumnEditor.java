package view.editor;

import db.DBSrv;
import model.Emp;
import view.EmpListView;
import view.FormWindow;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.StringTokenizer;

/**
 *
 */
public class EmpColumnEditor extends AbstractCellEditor implements TableCellEditor {

    private int clickCountToStartEdit = 2;
    private ArrayList<Emp> empList;
    private final DefaultCellEditor empEditor;
    private JComboBox comboEmp;


    public EmpColumnEditor(DBSrv db) {

        // работнички
        empList = db.getEmpsFromTxt();
        comboEmp = new JComboBox(empList.toArray());
        comboEmp.setFont(comboEmp.getFont().deriveFont(Font.PLAIN));
        empEditor = new DefaultCellEditor(comboEmp);
        // Чтоб не сразу открувалась, а опосля двух щелчков
        empEditor.setClickCountToStart(2);

    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboEmp.setSelectedItem(value);
        return comboEmp;

    }

    @Override
    public Object getCellEditorValue() {
        return comboEmp.getSelectedItem();
    }

    /**
     Переписал метод, который задерживает начало редактирования
     Аналог взял из <code>AbstractCellEditor</code>
     Такой-же использует <code>com.michaelbaranov.microba.calendar.DataPickerCellEditor</code>
     */
    @Override
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= clickCountToStartEdit;
        }
        return true;
    }

}
