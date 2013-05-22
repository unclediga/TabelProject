package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

/**
 *
 */
public class FormWindow extends JInternalFrame implements ActionListener {
    public FormWindow(String title) {
        super(title, true, true, true, true);
        //setPreferredSize(new Dimension(300,300));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FormWindow.this.setClosed(true);
        } catch (PropertyVetoException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
