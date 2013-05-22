package view.editor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 *  редактор дат для столбца таблицы
 */
public class SpinnerDateColumnEditor extends AbstractCellEditor implements TableCellEditor{

    private JSpinner editor = null;

    public SpinnerDateColumnEditor() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(),null,null, Calendar.DAY_OF_MONTH);
        editor = new JSpinner(model);

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setValue(value);
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getValue();
    }
}
