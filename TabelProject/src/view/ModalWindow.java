package view;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

/**
 *
 */
public class ModalWindow extends JInternalFrame
        implements ActionListener,VetoableChangeListener,
        MouseListener,MouseWheelListener,MouseInputListener,MouseMotionListener,InternalFrameListener{

    JInternalFrame parent;

    public ModalWindow(String title,JInternalFrame parent) {
        super(title, true, true, true, true);
        this.parent = parent;
        parent.getGlassPane().setVisible(true);
        parent.getGlassPane().addMouseListener(this);
        parent.getGlassPane().addMouseMotionListener(this);
        parent.addVetoableChangeListener(this);
        addInternalFrameListener(this);

    }

    private void removeAllListeners() throws PropertyVetoException {
            parent.removeVetoableChangeListener(this);
            parent.getGlassPane().removeMouseListener(this);
            parent.getGlassPane().removeMouseMotionListener(this);
            parent.getGlassPane().setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            removeAllListeners();
            this.setClosed(true);
        } catch (PropertyVetoException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        final String propertyName = evt.getPropertyName();
        if (propertyName.equals("closed")
                || propertyName.equals("maximum")
                || propertyName.equals("selected")
                || propertyName.equals("icon")) {
//            System.out.println("!! throw veto lsr:" + propertyName+" src"+evt.getSource());
            throw new PropertyVetoException("Модаль", evt);
        }

    }



    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        try {
            removeAllListeners();
        } catch (PropertyVetoException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }
}
