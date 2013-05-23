package ch17;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class FilterAndSelection extends JFrame {
    public FilterAndSelection() {
        super("FilterAndSelection");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SimpleSorting.SortModel sm = new SimpleSorting.SortModel();
        sm.setColumnCount(4);
        for (int i = 0; i < 100; i++) {
            sm.addRow(new Integer [] {
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5)
            });

        }

        final JTable table = new JTable(sm);
        table.setAutoCreateRowSorter(true);
        TableRowSorter sort = (TableRowSorter) table.getRowSorter();
        sort.setRowFilter(new RowFilter() {
            @Override
            public boolean include(Entry entry) {
                return ((Integer) entry.getValue(0)) % 2 == 0;
            }
        });

        add(new JScrollPane(table));

        final JTextArea out = new JTextArea(4,10);
        add(new JScrollPane(out), BorderLayout.SOUTH);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(table.getSelectedRow() != -1){
                    out.append("Строка:"+table.getSelectedRow()+"\n");
                    out.append("Столбец:"+table.getSelectedColumn()+"\n");
                    out.append("Строка модели:"+
                            table.convertRowIndexToModel(table.getSelectedRow())+"\n");



                }

            }
        });

        setSize(400,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FilterAndSelection();
            }
        });
    }


}
