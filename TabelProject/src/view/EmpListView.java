package view;

import model.Appoint;
import model.EmpTableModel;
import view.editor.AppointColumnEditor;
import view.editor.DateColumnEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.GregorianCalendar;

public class EmpListView extends JPanel {
    private final EmpTableModel model;
    private final JTable table;
    private GregorianCalendar calendar = new GregorianCalendar();

    public EmpListView(final FormWindow frm) {

        setBackground(Color.darkGray);
        setLayout(new BorderLayout(0, 0));


        model = new EmpTableModel();


        table = new JTable(model);
        table.setDefaultEditor(Date.class, new DateColumnEditor());
        table.setDefaultEditor(Appoint.class, new AppointColumnEditor());
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
                int addedRow = table.convertRowIndexToView(model.getRowCount() - 1);
                table.changeSelection(addedRow, 0, false, false);
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
                    Msg.info(EmpListView.this, "Операция выполнена успешно.");

                } catch (Exception e1) {
                    Msg.error(EmpListView.this, "Не удалось сохранить данные!");
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = table.getSelectedRow();
                model.refresh();
                if(model.getRowCount() > 0){
                  if(r < 0) r = 0;
                  else if(r > model.getRowCount()) r = model.getRowCount() - 1;
                  table.setRowSelectionInterval(r, r);
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                FormWindow frmTrans = new FormWindow("Назначения");
//                frmTrans.add(new TransListView(frmTrans), BorderLayout.CENTER);
                JDialog frmTrans = new JDialog((Frame) frm.getTopLevelAncestor());
                frmTrans.add(new JButton("New"));
                frmTrans.add(new JButton("New2"));

                frmTrans.setVisible(true);

                //frm.add(frmTrans);

//                try {
//                   frmTrans.setSelected(true);
//                } catch (PropertyVetoException e1) {
//                    e1.printStackTrace();
//                }
            }
        });
    }
}
