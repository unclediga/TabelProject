package view.editor;

import db.DBSrv;
import view.EmpListView;
import view.FormWindow;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 *
 */
public class EmpColumnEditor extends AbstractCellEditor implements TableCellEditor {

    private FormWindow frm;

    public EmpColumnEditor(FormWindow parentFrm, DBSrv dbsrv) {
        this.frm = new FormWindow("Работнички");
        this.frm.add(new EmpListView(this.frm, dbsrv), BorderLayout.CENTER);
        parentFrm.getDesktopPane().add(frm);
        this.frm.pack();
        this.frm.setVisible(false);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        frm.setVisible(true);
        return frm;

    }

    @Override
    public Object getCellEditorValue() {
        frm.setVisible(false);
        return null;
    }
}
