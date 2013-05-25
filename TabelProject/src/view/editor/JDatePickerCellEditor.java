package view.editor;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Date;

/**
 *
 */
public class JDatePickerCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JDatePickerImpl datePanel;
    private UtilDateModel model;

    public JDatePickerCellEditor() {
        model = new UtilDateModel();
        datePanel = new JDatePickerImpl(new JDatePanelImpl(model));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        model.setValue((Date) value);
        return datePanel;
    }

    @Override
    public Object getCellEditorValue() {
        return model.getValue();
    }
}
