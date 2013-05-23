package ch17;

import javax.swing.*;

public class TableDefaultEditing extends JFrame {

    // название столбцов
    private String[] columns = {
            "Имя", "Любимый Цвет"};
    // данные для таблицы
    private String[][] data = {
            {"Иван", "Зеленый"},
            {"Александр", "Красный"},
            {"Петр", "Синий"}
    };

    public TableDefaultEditing() {
        super("TableDefaultEditing");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTable table = new JTable(data, columns);

        // EDITOR
        JComboBox combo = new JComboBox(new String[] {"Зеленый","Красный","Синий"});
        DefaultCellEditor editor = new DefaultCellEditor(combo);
        table.getColumnModel().getColumn(1).setCellEditor(editor);
        table.setRowHeight(table.getRowHeight() + 5);




        add(new JScrollPane(table));
        setSize(300,300);
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
