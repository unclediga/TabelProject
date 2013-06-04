import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JLayerTest1 {

        public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createUI();
                }
            });
        }

        public static void createUI() {
            JFrame f = new JFrame ("Test1");

            LayerUI<JPanel> layerUI = new Test1LayerUI();
            JPanel panel = createPanel();
            JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);

            f.add (jlayer);

            f.setSize(300, 200);
            f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            f.setLocationRelativeTo (null);
            f.setVisible (true);
            f.setGlassPane(new InterceptPane());
        }

        private static JPanel createPanel() {
            final JPanel p = new JPanel();

            ButtonGroup entreeGroup = new ButtonGroup();
            JRadioButton radioButton;
            p.add(radioButton = new JRadioButton("Beef", true));
            entreeGroup.add(radioButton);
            p.add(radioButton = new JRadioButton("Chicken"));
            entreeGroup.add(radioButton);
            p.add(radioButton = new JRadioButton("Vegetable"));
            entreeGroup.add(radioButton);

            p.add(new JCheckBox("Ketchup"));
            p.add(new JCheckBox("Mustard"));
            p.add(new JCheckBox("Pickles"));

            p.add(new JLabel("Special requests:"));
            p.add(new JTextField(20));

            JButton orderButton = new JButton("Place Order");
            p.add(orderButton);
            orderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((JFrame)p.getTopLevelAncestor()).getGlassPane().setVisible(true);
                }
            });

            return p;
        }
    }

    class Test1LayerUI extends LayerUI<JPanel> {
        private boolean mActive;
        private int mX, mY;

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(
                    AWTEvent.MOUSE_EVENT_MASK |
                            AWTEvent.MOUSE_MOTION_EVENT_MASK
            );
        }

        @Override
        public void uninstallUI(JComponent c) {
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(0);
            super.uninstallUI(c);
        }

        @Override
        public void paint (Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D)g.create();

            // Paint the view.
            super.paint (g2, c);

            JPanel glass = ((JLayer)c).getGlassPane();

            if (mActive) {
                glass.setOpaque(false);


                // Create a radial gradient, transparent in the middle.
                java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(mX, mY);
                float radius = 72;
                float[] dist = {0.0f, 1.0f};
                Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
                //c.setVisible(true);
                RadialGradientPaint p =
                        new RadialGradientPaint(center, radius, dist, colors);
                g2.setPaint(p);
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, .6f));
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            }else{
                //System.out.println("ТЩ");
            }


            g2.dispose();
        }

        @Override
        protected void processMouseEvent(MouseEvent e, JLayer l) {
            if (e.getID() == MouseEvent.MOUSE_ENTERED) mActive = true;
            if (e.getID() == MouseEvent.MOUSE_EXITED) mActive = false;
            l.repaint();
        }

        @Override
        protected void processMouseMotionEvent(MouseEvent e, JLayer l) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l);
            mX = p.x;
            mY = p.y;
            l.repaint();
        }


    }

class InterceptPane extends JComponent {
    int click;
    InterceptPane() {

        click = 0;
        // надо включить события от мыши
        enableEvents(MouseEvent.MOUSE_EVENT_MASK);
        enableEvents(KeyEvent.KEY_EVENT_MASK);
        // по умолчанию невидим и прозрачен
        setVisible(false);
        setOpaque(false);
    }
    // перехватываем события от мыши
    public void processMouseEvent(MouseEvent e) {
        // отслеживаем нажатия мыши
        if ( e.getID() == MouseEvent.MOUSE_PRESSED) {

            if(++click > 3){

            // определяем, какой компонент был выбран
            Component contentPane = ((JFrame)getTopLevelAncestor()).getContentPane();
            MouseEvent ne =
                    SwingUtilities.convertMouseEvent(
                            this, e, contentPane);
            // видимый компонент в указанных координатах
            Component visibleComp =
                    SwingUtilities.getDeepestComponentAt(
                            contentPane, ne.getX(), ne.getY());
            // показываем справочную информацию
            JOptionPane.showMessageDialog(
                    null, "Message for you!");
            // отключаемся
            setVisible(false);
            click = 0;
            }
        }
    }
}

