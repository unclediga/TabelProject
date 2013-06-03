package view;

import db.DBSrv;
import model.Emp;
import model.Leave;
import model.LeaveTableModel;
import view.editor.DateColumnEditor;
import view.editor.EmpColumnEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 */
public class LeaveListView extends JPanel {
    private GregorianCalendar calendar = new GregorianCalendar();
    private final LeaveTableModel model;
    private final JTable table;

    public LeaveListView(FormWindow frm, DBSrv dbsrv) {

        setBackground(Color.darkGray);
        setLayout(new BorderLayout(0,0));


        model = new LeaveTableModel();



        table = new JTable(model);
        table.setDefaultEditor(Date.class, new DateColumnEditor());
        table.setDefaultEditor(Emp.class, new EmpColumnEditor(dbsrv));
        table.getColumnModel().getColumn(2).setCellEditor(
                new DefaultCellEditor(
                        new JComboBox(Leave.getTypes())));
        table.setRowHeight(table.getRowHeight() + 5);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // КНОПКИ

        JButton btnNew = new JButton("Новый");
        JButton btnEdit = new JButton("Изменить");
        JButton btnDel = new JButton("Удалить");
        JButton btnSave = new JButton("Сохранить");
        JButton btnChanges = new JButton("Изменения");
        JButton btnRefresh = new JButton("Обновить");
        JButton btnExit = new JButton("Выход");
        JPanel panelButton = new JPanel();
        panelButton.add(btnNew);
        panelButton.add(btnEdit);
        panelButton.add(btnDel);
        panelButton.add(btnSave);
        panelButton.add(btnChanges);
        panelButton.add(btnRefresh);
        panelButton.add(btnExit);
        add(panelButton, BorderLayout.SOUTH);

        btnExit.addActionListener(frm);

        btnChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.printChanges();

            }
        });

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow();
            }
        });
        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.saveChanges();
                    Msg.info(LeaveListView.this, "Операция выполнена успешно.");
                } catch (Exception e1) {
                    Msg.error(LeaveListView.this,"Не удалось сохранить данные!");
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.refresh();
            }
        });


    }
}
