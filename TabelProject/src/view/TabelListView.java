package view;

import com.michaelbaranov.microba.calendar.DatePicker;
import model.*;
import view.editor.AppointColumnEditor;
import view.editor.DateColumnEditor;
import view.editor.EmpColumnEditor;
import view.editor.WageRateTypeColumnEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 */
public class TabelListView extends JPanel {
    private GregorianCalendar calendar = new GregorianCalendar();
    private final TabelTableModel model;
    private final JTable table;
    private final DatePicker dp;

    public TabelListView(JInternalFrame frm) {

        setBackground(Color.darkGray);
        setLayout(new BorderLayout(0,0));

        // МЕСЯЦ и ГОД
        JPanel datePanel = new JPanel();
        dp = new DatePicker(new Date(), new SimpleDateFormat("MM.yyyy"));
        dp.setMinimumSize(new Dimension(50,10));
        // Т А Б Л И Ц А
        datePanel.add(dp);
        add(datePanel, BorderLayout.NORTH);




        model = new TabelTableModel();

        table = new JTable(model);
        table.setDefaultEditor(Date.class, new DateColumnEditor());
        table.setDefaultEditor(Emp.class, new EmpColumnEditor());
        table.setDefaultEditor(Appoint.class, new AppointColumnEditor());
        table.setDefaultEditor(WageRateType.class, new WageRateTypeColumnEditor());


        table.setRowHeight(table.getRowHeight() + 5);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Внешний вид таблицы
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(60);

        // КНОПКИ

        JButton btnNew = new JButton("Новый");
        JButton btnEdit = new JButton("Изменить");
        JButton btnDel = new JButton("Удалить");
        JButton btnFill = new JButton("Заполнить");
        JButton btnSave = new JButton("Сохранить");
        JButton btnChanges = new JButton("Изменения");
        JButton btnRefresh = new JButton("Обновить");
        JButton btnExit = new JButton("Выход");
        JPanel panelButton = new JPanel();
        panelButton.add(btnNew);
        panelButton.add(btnEdit);
        panelButton.add(btnDel);
        panelButton.add(btnFill);
        panelButton.add(btnSave);
        panelButton.add(btnChanges);
        panelButton.add(btnRefresh);
        panelButton.add(btnExit);
        add(panelButton, BorderLayout.SOUTH);

        btnExit.addActionListener((ActionListener) frm);

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
                    Msg.info(TabelListView.this, "Операция выполнена успешно.");
                } catch (Exception e1) {
                    Msg.error(TabelListView.this,"Не удалось сохранить данные!");
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.refresh();
            }
        });

        btnFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.fillTabel(dp.getDate());
                model.fireTableDataChanged();
            }
        });
    }
}
