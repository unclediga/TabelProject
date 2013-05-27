package view;

import db.DBSrv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 *
 */


public class MainWindow extends JFrame {


    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 800;
    private final JDesktopPane desktop;
    private DBSrv dbsrv = null;


    private MainWindow() throws HeadlessException {


        setTitle("Главное окно // ТАБЕЛЬ-УЧЁТ");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        // Создаём Менюшки для главного окна
        createMenus();


        dbsrv = new DBSrv();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();

            }
        });


    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                //Чтобы окно было в стиле МЕТАЛ, иначе будет в Видовом L&F
                JFrame.setDefaultLookAndFeelDecorated(true);
                JFrame frame = new MainWindow();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

    /**
     * Создаёт менюшки для главного окна
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });
        fileMenu.add(exitItem);

        JMenu srvMenu = new JMenu("Сервис");
        menuBar.add(srvMenu);
        JMenuItem srvInsEmpItem = new JMenuItem("Заполнить работников");
        srvInsEmpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        dbsrv.srvInsertEmpList();
                    }
                });
            }
        });
        srvMenu.add(srvInsEmpItem);

        JMenuItem empListViewItem = new JMenuItem("Работники");
//        empListViewItem.setPreferredSize(new Dimension(-1,20));

        empListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.EMP);
            }
        }
        );
        menuBar.add(empListViewItem);

        JMenuItem leaveListViewItem = new JMenuItem("Отпуска");

        leaveListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.LEAVE);
            }
        }
        );
        menuBar.add(leaveListViewItem);

        JMenuItem illListViewItem = new JMenuItem("Больничные");

        illListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.ILL);
            }
        }
        );
        menuBar.add(illListViewItem);

        JMenuItem tblListViewItem = new JMenuItem("Табель");
        tblListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.TBL);
            }
        }
        );
        menuBar.add(tblListViewItem);

        JMenuItem scheduleListViewItem = new JMenuItem("Расписание");
        scheduleListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.SCH);
            }
        }
        );
        menuBar.add(scheduleListViewItem);


    }

    /**
     * Делаем Internal формочку
     *
     * @param frm типа enum FORMS
     */
    private void createForm(FORMS frm) {
        switch (frm) {
            case EMP:
                FormWindow frmEmps = new FormWindow("Работнички");
                frmEmps.add(new EmpListView(frmEmps, dbsrv), BorderLayout.CENTER);
                desktop.add(frmEmps);
                frmEmps.pack();
                frmEmps.setVisible(true);
                break;
            case LEAVE:
                FormWindow frmLeaves = new FormWindow("Отпуска");
                frmLeaves.add(new LeaveListView(frmLeaves, dbsrv), BorderLayout.CENTER);
                desktop.add(frmLeaves);
                frmLeaves.pack();
                frmLeaves.setVisible(true);
                break;
            case ILL:
                FormWindow frmIlls = new FormWindow("Больничные");
                frmIlls.add(new IllListView(frmIlls, dbsrv), BorderLayout.CENTER);
                desktop.add(frmIlls);
                frmIlls.pack();
                frmIlls.setVisible(true);
                break;
            case TBL:
                FormWindow frmTbl = new FormWindow("Табель");
                frmTbl.add(new EmpListView(frmTbl, dbsrv), BorderLayout.CENTER);
                desktop.add(frmTbl);
                frmTbl.pack();
                frmTbl.setVisible(true);
                break;
            case SCH:
                FormWindow frmSch = new FormWindow("Расписание");
                frmSch.add(new ScheduleListView(frmSch, dbsrv), BorderLayout.CENTER);
                desktop.add(frmSch);
                frmSch.pack();
                frmSch.setVisible(true);
                break;
            default:
                System.out.println("Form not found!");

        }
    }

    private void exitApplication(){
        dbsrv.closeConnection();
        System.exit(0);
    }
}


