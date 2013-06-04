package ch17;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class SimpleSorting extends JFrame {
    public SimpleSorting() {
        super("SimpleSorting");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 400);
        setVisible(true);

        SortModel sm = new SortModel();
        sm.setColumnCount(4);
        for (int i = 0; i < 100; i++) {
            sm.addRow(new Integer [] {
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5),
                    (int) (Math.random() * 5)
            });

        }

        JTable table = new JTable();
        table.setAutoCreateRowSorter(true);
        TableRowSorter sort = (TableRowSorter) table.getRowSorter();
        sort.setMaxSortKeys(2);
        table.setModel(sm);
        add(new JScrollPane(table));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleSorting();
            }
        });
    }

    public static class SortModel extends DefaultTableModel {

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Integer.class;
        }
    }
}
