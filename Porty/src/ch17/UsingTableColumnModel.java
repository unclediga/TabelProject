package ch17;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class UsingTableColumnModel extends JFrame{
    private TableColumnModel columnModel;
    private String[] columnNames = {
            "Имя", "Любимый цвет", "Напиток"
    };
    // данные для таблицы
    private String[][] data = {
            { "Иван", "Зеленый", "Апельсиновый сок"},
            { "Александр", "Бежевый", "Зеленый чай"}
    };

    public UsingTableColumnModel() throws HeadlessException {
        super("UsingTableColumnModel");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTable table = new JTable(data, columnNames);
        columnModel = table.getColumnModel();
        Enumeration e = columnModel.getColumns();
        while (e.hasMoreElements()) {
            TableColumn col = (TableColumn) e.nextElement();
            col.setMinWidth(30);
            col.setMaxWidth(90);
        }

        JPanel buttons = new JPanel();
        JButton move = new JButton("Move");
        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                columnModel.moveColumn(0, 1);
            }
        });

        JButton add = new JButton("add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumn column = new TableColumn(1, 100);
                columnModel.addColumn(column);
                column.setHeaderValue("NEW");
            }
        });

        buttons.add(add);
        buttons.add(move);

        add(new JScrollPane(table));
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
        setSize(400,300);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UsingTableColumnModel();
            }
        });
    }
}
