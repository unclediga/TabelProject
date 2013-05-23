import org.h2.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EmpTable extends JFrame {

    // ������ ����������
    private ArrayList<EmpDev> empList;

    // �������� ��������
    private String[] columns = {
            "���", "������� ����","��������"};
    // ������ ��� �������
    private Object[][] data = {
            {"����", "�������",
                    new EmpDev(1, "������", "����", "��������")},
            {"���������", "�������",
                    new EmpDev(2, "������", "����", "��������")},
            {"����", "�����",new EmpDev(3, "�������", "�����", "���������") }

    };

    KeyListener editorKeyListener;
    //        al.add(new Emp(1, "������", "����", "��������", new GregorianCalendar(2000, 01, 20).getTime(), null));
//        al.add(new Emp(2, "������", "����", "��������", new GregorianCalendar(2002, 02, 20).getTime(), null));
//        al.add(new Emp(3, "�������", "�����", "���������", new GregorianCalendar(2004, 03, 20).getTime(), null));
//        al.add(new Emp(4, "��������", "�������", "����������", new GregorianCalendar(2008, 10, 20).getTime(), null));

    public EmpTable() {
        super("TableDefaultEditing");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTable table = new JTable(data, columns);


        // ����������
        empList = new ArrayList<EmpDev>(100);
        empList.add(new EmpDev(1, "������", "����", "��������")); //, new GregorianCalendar(2000, 01, 20).getTime(), null));
        empList.add(new EmpDev(2, "������", "����", "��������")); //, new GregorianCalendar(2002, 02, 20).getTime(), null));
        empList.add(new EmpDev(3, "�������", "�����", "���������"));//, new GregorianCalendar(2004, 03, 20).getTime(), null));
        empList.add(new EmpDev(4, "��������", "�������", "����������"));//, new GregorianCalendar(2008, 10, 20).getTime(), null));

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("emplist.txt")));
            String str = null;
            int i = empList.size();
            while((str = r.readLine()) != null){
                StringTokenizer t = new StringTokenizer(str,":");

                while (t.hasMoreTokens()){
                    empList.add(new EmpDev(i, t.nextToken(), t.nextToken(),t.nextToken()));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }




        // EDITOR
        JComboBox combo = new JComboBox(new String[] {"�������","�������","�����"});
        final DefaultCellEditor editor = new DefaultCellEditor(combo);
        table.getColumnModel().getColumn(1).setCellEditor(editor);
        final JComboBox comboEmp = new JComboBox(empList.toArray());
        comboEmp.setFont(comboEmp.getFont().deriveFont(Font.PLAIN));
        final DefaultCellEditor empEditor = new DefaultCellEditor(comboEmp);
        table.getColumnModel().getColumn(2).setCellEditor(empEditor);

        table.setRowHeight(table.getRowHeight() + 5);


        add(new JScrollPane(table));
        setSize(500,300);
        setVisible(true);

    editorKeyListener = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            if (comboEmp.isDisplayable()) comboEmp.setPopupVisible(true);
            boolean hitBackspace=false;
            switch (e.getKeyCode()) {
                // determine if the pressed key is backspace (needed by the remove method)
                case KeyEvent.VK_BACK_SPACE : hitBackspace=true;
                    //hitBackspaceOnSelection=empEditor.getSelectionStart()!=empEditor.getSelectionEnd();
                    break;
          /*
           *  modification F. Degrelle
           *  transmit other keys back to Forms
           *  to keep a standard behaviour
           *  (F4, Tab, Enter, etc.)
           */
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_TAB:
                case KeyEvent.VK_CANCEL:
                case KeyEvent.VK_CLEAR:
                case KeyEvent.VK_SHIFT:
                case KeyEvent.VK_CONTROL:
                case KeyEvent.VK_ALT:
                case KeyEvent.VK_PAUSE:
                case KeyEvent.VK_CAPS_LOCK:
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_PAGE_UP:
                case KeyEvent.VK_PAGE_DOWN:
                    //case KeyEvent.VK_UP:
                    //case KeyEvent.VK_DOWN:
                case KeyEvent.VK_F1:
                case KeyEvent.VK_F2:
                case KeyEvent.VK_F3:
                case KeyEvent.VK_F4:
                case KeyEvent.VK_F5:
                case KeyEvent.VK_F6:
                case KeyEvent.VK_F7:
                case KeyEvent.VK_F8:
                case KeyEvent.VK_F9:
                case KeyEvent.VK_F10:
                case KeyEvent.VK_F11:
                case KeyEvent.VK_F12:
                case KeyEvent.VK_F13:
                case KeyEvent.VK_F14:
                case KeyEvent.VK_F15:
                case KeyEvent.VK_F16:
                case KeyEvent.VK_F17:
                case KeyEvent.VK_F18:
                case KeyEvent.VK_F19:
                case KeyEvent.VK_F20:
                case KeyEvent.VK_F21:
                case KeyEvent.VK_F22:
                case KeyEvent.VK_F23:
                case KeyEvent.VK_F24:
                    return;
                default:
                    System.out.println(""+e.getKeyChar());
            }
        }
    };


        comboEmp.addKeyListener(editorKeyListener);
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmpTable();
            }
        });
    }
}
