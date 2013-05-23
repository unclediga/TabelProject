package ch17;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UsingTableHeader extends JFrame {
    private String[][] data = {
            {"Июнь", "+18 С"},
            {"Июль", "+22 С"},
            {"Август", "+19 С"}
    };
    // названия столбцов
    private String[] columnNames = {
            "Месяц", "Средняя температура"
    };

    public UsingTableHeader() {
        super("UsingTableHeader");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // создаем таблицу
        JTable table = new JTable(data, columnNames);
        // настраиваем заголовок таблицы
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer());
        add(new JScrollPane(table));
        setSize(400, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UsingTableHeader();
            }
        });
    }
}

class HeaderRenderer extends DefaultTableCellRenderer{

    private Border border = BorderFactory.createMatteBorder(16, 16, 16, 16, new ImageIcon("bullet.png"));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setBackground(Color.gray);
        label.setBorder(border);
        return label;
    }
}


