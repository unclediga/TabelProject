/**
 *
 */
import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.*;

public class InternalDialogTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createLayer());
        frame.setSize(256, 256);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static JLayer<JLayeredPane> createLayer() {
        final ModalLayerUI layerUI = new ModalLayerUI();
        final JLayeredPane layeredPane = new JLayeredPane();
        JLayer<JLayeredPane> layer = new JLayer(layeredPane, layerUI);

        JInternalFrame internal = new JInternalFrame("Internal Frame");
        internal.setResizable(true);

        JButton button = new JButton("Make dialog");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JInternalFrame cd = new JInternalFrame("Custom Dialog");
                cd.add(new JLabel("Internal Custom Dialog"));
                cd.setClosable(true);
                cd.pack();
                cd.setLocation((layeredPane.getWidth()-cd.getWidth())/2,
                        (layeredPane.getHeight()-cd.getHeight())/2);

                System.out.println("Showing Dialog");
                layerUI.showModalDialog(layeredPane, cd);
                System.out.println("Done showing Dialog");
            }
        });

        internal.add(button);
        internal.pack();
        layeredPane.add(internal);
        internal.setVisible(true);

        return layer;
    }
    public static class ModalLayerUI extends LayerUI<JLayeredPane>
            implements Runnable {

        JInternalFrame dialog;
        SecondaryLoop blockingLoop;

        /*The JLayeredPane's parent is assumed to be the JLayer containing this
         *this LayerUI.  The dialog needs to be packed, layed out, and ready
         *to be made visible prior to calling this method.  This method blocks,
         *until the dialog has been closed.
         */
        public void showModalDialog(JLayeredPane pane, JInternalFrame dialog) {
            if(this.dialog != null) {
                throw new IllegalStateException("Modal dialog already present!");
            }

            this.dialog = dialog;

            Component prevFocusOwner = FocusManager.getCurrentManager().getFocusOwner();

            pane.add(dialog,JLayeredPane.MODAL_LAYER);
            dialog.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.requestFocusInWindow();

            EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
            blockingLoop = queue.createSecondaryLoop();

            ((JLayer) pane.getParent()).setLayerEventMask(-1);
            if(!blockingLoop.enter()) {
                throw new IllegalStateException("SecondaryLoop already in use");
            }
            ((JLayer) pane.getParent()).setLayerEventMask(0);
            prevFocusOwner.requestFocusInWindow();
        }
        public void eventDispatched(AWTEvent e, JLayer<? extends JLayeredPane> l) {
            if(dialog == null)
                return;

            Object source = e.getSource();

            if(source instanceof Component &&
                    SwingUtilities.isDescendingFrom((Component) source, dialog)){
                SwingUtilities.invokeLater(this);
            }else {
                if(e instanceof InputEvent)
                    ((InputEvent) e).consume();
                else if(e instanceof FocusEvent)
                    dialog.requestFocusInWindow(); //get the focus back
            }
        }
        public void run() {
            if(dialog != null && !dialog.isVisible()) {
                dialog = null;
                blockingLoop.exit();
                blockingLoop = null;
            }
        }
    }
}