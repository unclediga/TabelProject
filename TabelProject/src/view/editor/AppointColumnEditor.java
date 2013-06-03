package view.editor;

import db.AppointMapper;
import db.DBSrv;
import model.Appoint;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;

/**
 *
 */
public class AppointColumnEditor extends AbstractCellEditor implements TableCellEditor {

    private int clickCountToStartEdit = 2;
    private ArrayList<Appoint> appoints;
    private final DefaultCellEditor defaultCellEditor;
    private JComboBox combo;


    public AppointColumnEditor() {

        appoints = DBSrv.getInstance().getList(Appoint.class);
        combo = new JComboBox(appoints.toArray());
        combo.setFont(combo.getFont().deriveFont(Font.PLAIN));
        defaultCellEditor = new DefaultCellEditor(combo);
        // Чтоб не сразу открувалась, а опосля двух щелчков
        defaultCellEditor.setClickCountToStart(2);

    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        combo.setSelectedItem(value);
        return combo;

    }

    @Override
    public Object getCellEditorValue() {
        return combo.getSelectedItem();
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
