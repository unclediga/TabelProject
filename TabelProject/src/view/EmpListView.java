package view;

import db.DBSrv;
import model.EmpTableModel;
import view.editor.DateColumnEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

public class EmpListView extends JPanel{
    private GregorianCalendar calendar = new GregorianCalendar();
    private final EmpTableModel model;
    private final JTable table;

    public EmpListView(FormWindow frm, DBSrv dbsrv) {

        setBackground(Color.darkGray);
        setLayout(new BorderLayout(0,0));


        model = new EmpTableModel(dbsrv);



        table = new JTable(model);
        table.setDefaultEditor(Date.class, new DateColumnEditor());
        add(new JScrollPane(table),BorderLayout.CENTER);

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
                int addedRow = table.convertRowIndexToView(model.getRowCount()-1);
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
                model.saveChanges();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.refresh();
            }
        });

    }

    public static void main(String[] args) {
        final int DEFAULT_HEIGHT = 500;
        final int DEFAULT_WIDTH = 500;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame fr = new JFrame("Список работников");
                fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //fr.add(new EmpListView());
                fr.pack();
                fr.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
                fr.setVisible(true);
                fr.setBackground(Color.YELLOW);
            }
        });

    }
}
