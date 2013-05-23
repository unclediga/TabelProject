package ch17;

import com.porty.swing.ImageListElement;
import com.porty.swing.ImageTableCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class RegisteringTableRenderer extends JFrame{
    public RegisteringTableRenderer() throws HeadlessException {
        super("RegisteringTableRenderer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTable table = new JTable(new SpecialModel());
        table.setDefaultRenderer(ImageListElement.class,new ImageTableCellRenderer());
        table.setEnabled(true);
        add(new JScrollPane(table));
        setSize(300,400);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisteringTableRenderer();
            }
        });
    }
}
class SpecialModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Компания", "Адрес","Буль"};
    private Icon img1 = new ImageIcon("clip1.gif");
    private Icon img2 = new ImageIcon("clip2.gif");

    private Object[][] data = new Object[][]{
            {new ImageListElement(img1, "MegaWorks"), "<html><font style='color:red'>Паришш",true},
            {new ImageListElement(img2, "Terra"), "<html><b>Лондон",false}

    };


    @Override
    public int getRowCount() {
        return data.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }

}

