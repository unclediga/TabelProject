/**
 *
 */
import components.MyInternalFrame;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class NewJFrame extends javax.swing.JFrame
{
    public NewJFrame()
    {
        JDesktopPane pane = new JDesktopPane();
        add(pane);

        JInternalFrame frame = new JInternalFrame();
        frame.setSize(200,200);
        frame.setTitle("modal");

        //frame.setModal(true);
        frame.setVisible(true);
        pane.add(frame);

        JInternalFrame f2 = new JInternalFrame();
        f2.setTitle("nonmodal");
        f2.setBounds(200,200,100,100);
        f2.setVisible(true);
        pane.add(f2);

        initComponents();
        setSize(600,600);
        setVisible(true);
    }

    private void initComponents()
    {
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jMenu1.setText("Menu");
        jMenuItem1.setText("Item");
        jMenu1.add(jMenuItem1);
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);
    }

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new NewJFrame();
            }
        });
    }

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
}