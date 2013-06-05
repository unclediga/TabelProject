package test;

import db.DBSrv;
import model.Emp;
import view.FormWindow;
import view.ModalWindow;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 */
public class TestUtils {

    /**
     * Читаем работников из текстового файла
     * @return
     */
    private static ArrayList<Emp> getEmpsFromTxt() {

        ArrayList<Emp> emps = new ArrayList<Emp>(100);
        Random rnd = new Random();

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("emplist.txt")));
            String str;
            int i = emps.size();
            while ((str = r.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(str, ":");

                while (t.hasMoreTokens()) {

                    Calendar c = new GregorianCalendar(2000, 1, 1);
                    c.set(2000 + rnd.nextInt(13), 1 + rnd.nextInt(12), 1 + rnd.nextInt(29));
                    emps.add(new Emp(i, t.nextToken(), t.nextToken(), t.nextToken(), null,c.getTime(), new java.util.Date()));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emps;
    }

    /**
     * Обработать emplist.txt и вставить из
     * него работников в таблицу DDT_EMP
     * Таблицу очищаем перед этим
     */
    public static void insertEmpList() {

        ArrayList<Emp> emps = getEmpsFromTxt();
        if (emps.size() == 0) {
            System.err.println("No employers for INSERT!!");
            return;
        }

        System.out.println("we will insert " + emps.size() + " employers");


        DBSrv.getInstance().clearBase();

        for (Emp emp : emps) {
            System.out.println("Prepare for inserting " + emp);
            DBSrv.getInstance().put(emp);
        }
    }

    public static void createTestWindow(final JDesktopPane desktop){
        final JInternalFrame ifmain = new FormWindow("Тестовое");

        final JLabel lb = new JLabel("T E S T");
        final JButton btn1 = new JButton("test1");
        final JButton btn2 = new JButton("test2");
        final JPanel panel = new JPanel();
        panel.add(btn1);
        panel.add(btn2);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createModalTestWin2(ifmain);
            }
        });
        ifmain.add(lb, BorderLayout.CENTER);
        ifmain.add(panel, BorderLayout.SOUTH);
        desktop.add(ifmain);
        ifmain.pack();
        ifmain.setVisible(true);
        Dimension d = desktop.getSize();
        ifmain.setLocation(new Point(d.width / 2, d.height / 2));

    }
    private static void createModalTestWin2(final JInternalFrame parent){
        final JInternalFrame ifmodal = new ModalWindow("Модальное",parent);
        final JLabel lb = new JLabel("M O D A L");
        final JButton btn1 = new JButton("close");
        btn1.addActionListener((ActionListener) ifmodal);
        ifmodal.add(lb, BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.add(btn1);
        ifmodal.add(p, BorderLayout.SOUTH);
        parent.getDesktopPane().add(ifmodal);
        Point point = parent.getLocation();
        ifmodal.setLocation(new Point(point.x + 10, point.y + 10));
        ifmodal.pack();
        ifmodal.setVisible(true);

    }

    private static void createModalTestWindow(final JDesktopPane desktop, final JInternalFrame parent) {

        final JInternalFrame ifmodal = new FormWindow("Модальное");
        final JLabel lb = new JLabel("M O D A L");
        final JButton btn1 = new JButton("add");
        final JButton btn2 = new JButton("rem");
        final int i = 0;
        final MouseInputAdapter lsr = new MouseInputAdapter() {
            int i = 0;
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Click" + i++);
                super.mouseClicked(e);
            }
        };

        final VetoableChangeListener veto = new VetoableChangeListener() {
            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                final String propertyName = evt.getPropertyName();
                if(propertyName.equals("closed")
                        || propertyName.equals("selected")
                        || propertyName.equals("maximum")
                        || propertyName.equals("focused")
                        || propertyName.equals("icon")) {
                    System.out.println("!! throw veto lsr:" + propertyName);
                    throw new PropertyVetoException("Модаль",evt);
                }else{
                    System.out.println("veto lsr:" + propertyName);
                }
            }
        };



        parent.getGlassPane().addMouseListener(lsr);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getGlassPane().setVisible(true);
//                parent.setClosable(false);

                parent.addVetoableChangeListener(veto);
            }

        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getGlassPane().setVisible(false);
//                parent.setClosable(true);
                parent.removeVetoableChangeListener(veto);
            }
        });


        ifmodal.add(lb, BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.add(btn1);
        p.add(btn2);
        ifmodal.add(p, BorderLayout.SOUTH);
        desktop.add(ifmodal);
        ifmodal.pack();
        ifmodal.setVisible(true);
        Dimension d = desktop.getSize();
        ifmodal.setLocation(new Point(d.width / 2 + 10, d.height / 2 + 10));



    }
}
