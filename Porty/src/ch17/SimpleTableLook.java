package ch17;
import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: BorodinovI
 * Date: 19.04.13
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTableLook extends JFrame {
    private Object[][] data = new String[][]{
            {"Мощная", "Синий", "Спортивная"},
            {"Экономичный", "Красный", "Классика"}};

    private Object[] columns = new String[] {
            "Модель", "Цвет", "Дизайн"
    };

    public SimpleTableLook() {
        super("SimpleTableLook");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTable table1 = new JTable(data, columns);
        table1.setRowHeight(40);
        table1.setIntercellSpacing(new Dimension(11,11));
        table1.setGridColor(Color.green);
        table1.setShowVerticalLines(false);
        JTable table2 = new JTable(data,columns);
        table2.setForeground(Color.MAGENTA );
        table2.setSelectionForeground(Color.yellow);
        table2.setSelectionBackground(Color.blue);
        table2.setShowGrid(false);

        setLayout(new GridLayout(1,2,5,5));
        add(new JScrollPane(table1));
        add(new JScrollPane(table2));
        setSize(600,200);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new SimpleTableLook();
                    }
                }
        );
    }
}

