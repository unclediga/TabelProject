import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class Test1 extends JFrame {
    private final JDesktopPane desktop;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public Test1() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        JInternalFrame frm = new JInternalFrame("test", true, true, true, true);
        //text = "Test window";
        frm.add(new JLabel(text),BorderLayout.CENTER);
        final JButton btn = new JButton("show");
        frm.add(btn,BorderLayout.SOUTH);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane();
                String msg = "Hello, World";
                msg = msg +", hello too";
                optionPane.setMessage(msg);
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
                Test1 t1 = new Test1();
                t1.setText("Test!");
            }
        });
    }
}
