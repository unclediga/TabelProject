package com.porty.swing;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: BorodinovI
 * Date: 24.04.13
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor{

    private JSpinner editor;

    public DateCellEditor() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(),null,null, Calendar.DAY_OF_MONTH);
        editor = new JSpinner(model);


    }

    @Override
    public Object getCellEditorValue() {
        return editor.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setValue(value);
        return editor;
    }
}
