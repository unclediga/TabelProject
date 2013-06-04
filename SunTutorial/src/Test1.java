import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class Test1 extends JFrame {
    private final JDesktopPane desktop;
    public Test1() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        JInternalFrame frm = new JInternalFrame("test", true, true, true, true);
        frm.add(new JLabel("Test window"),BorderLayout.CENTER);
        final JButton btn = new JButton("show");
        frm.add(btn,BorderLayout.SOUTH);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane();
                optionPane.setMessage("Hello, World");
                optionPane.setMessageType(
                        JOptionPane.INFORMATION_MESSAGE);
                JInternalFrame modal =
                        optionPane.createInternalFrame(desktop, "Modal");
                modal.setVisible(true);
            }
        });
        frm.pack();
        desktop.add(frm);
        frm.setVisible(true);

//        JPanel glass = new JPanel();
//        glass.setOpaque(false);
//        glass.add(modal);
//        frame.setGlassPane(glass);
//        glass.setVisible(true);
//        modal.setVisible(true);

        setSize(200,200);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test1();
            }
        });
    }
}
