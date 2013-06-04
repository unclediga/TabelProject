package ch17;

import com.porty.swing.DateCellEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class CustomTableEditor extends JFrame {

    // заголовок столбцов таблицы
    private String[] columns = {
            "Операция", "Дата" };
    // данные таблицы
    private Object[][] data = {
            { "Покупка", new Date() },
            { "Продажа", new Date() }
    };

    public CustomTableEditor() {
        super("CustomTableEditor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(data, columns){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return data[0][columnIndex].getClass();
            }
        };
        JTable table = new JTable(model);

        // EDITOR
        //table.getColumnModel().getColumn(1).setCellEditor(new DateCellEditor());
        table.setDefaultEditor(Date.class,new DateCellEditor());



        table.setRowHeight(20);
        add(new JScrollPane(table));
//        add(table);
        setSize(300,600);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomTableEditor();
            }
        });
    }
}
