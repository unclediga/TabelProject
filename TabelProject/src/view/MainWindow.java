package view;

import db.DBSrv;
import test.TestUtils;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

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
        try {
            dbsrv.getConnection();
        } catch (Exception e) {
            System.err.println("Application will be stopped.");
            exitApplication();
        }

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
//                try {
//                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
//                } catch (UnsupportedLookAndFeelException e) {
//                    e.printStackTrace();
//                }
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


        // ВЫХОД
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });


        // СЕРВИС
        JMenuItem srvInsEmpItem = new JMenuItem("Заполнить работников");
        srvInsEmpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        TestUtils.insertEmpList();
                    }
                });
            }
        });

        JMenuItem srvTestWinItem = new JMenuItem("тестовое окно");
        srvTestWinItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestUtils.createTestWindow(desktop);
            }
        });



        // РАБОТНИКИ
        JMenuItem empListViewItem = new JMenuItem("Работники");
//        empListViewItem.setPreferredSize(new Dimension(-1,20));
        empListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.EMP);
            }
        });

        // РАСПИСАНИЕ
        JMenuItem scheduleListViewItem = new JMenuItem("Расписание");
        scheduleListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.SCH);
            }
        });

        //НАЗНАЧЕНИЯ
        JMenuItem transListViewItem = new JMenuItem("Назначения");
        transListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.TRANS);
            }
        });

        //ТАБЕЛЬ
        JMenuItem tblListViewItem = new JMenuItem("Табель");
        tblListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.TBL);
            }
        });

        // НЕЯВКИ
        JMenuItem leaveListViewItem = new JMenuItem("Отпуска");
        leaveListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.LEAVE);
            }
        });

        JMenuItem illListViewItem = new JMenuItem("Больничные");
        illListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.ILL);
            }
        });

        // СПРАВОЧНИКИ
        JMenuItem appointListViewItem = new JMenuItem("Должности");
        appointListViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createForm(FORMS.APP);
            }
        });


        // ОБЩЕЕ МЕНЮ
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.setLayout(new GridLayout(1,10));
        // ФАЙЛ
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setPreferredSize(new Dimension(10,10));
        menuBar.add(fileMenu);
        fileMenu.add(exitItem);
        // работники
        menuBar.add(empListViewItem);
        // расписание
        menuBar.add(scheduleListViewItem);
        //табель
        menuBar.add(transListViewItem);

        //табель
        menuBar.add(tblListViewItem);

        //НЕЯВКИ
        JMenu disMenu = new JMenu("Неявки");
        menuBar.add(disMenu);
        disMenu.add(leaveListViewItem);
        disMenu.add(illListViewItem);

        // Справочники
        JMenu dicMenu = new JMenu("Справочники");
        menuBar.add(dicMenu);
        dicMenu.add(appointListViewItem);

        // Вставлю распорку для красоты
        // как советуют в java tutorial

        menuBar.add(Box.createHorizontalStrut(100));
        menuBar.add(Box.createHorizontalBox());
        menuBar.add(Box.createHorizontalGlue());

        //Сервис
        JMenu srvMenu = new JMenu("Сервис");
        menuBar.add(srvMenu);
        srvMenu.add(srvInsEmpItem);
        srvMenu.add(srvTestWinItem);


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
                frmEmps.add(new EmpListView(frmEmps), BorderLayout.CENTER);
                desktop.add(frmEmps);
                frmEmps.pack();
                frmEmps.setVisible(true);
                break;
            case LEAVE:
                FormWindow frmLeaves = new FormWindow("Отпуска");
                frmLeaves.add(new LeaveListView(frmLeaves), BorderLayout.CENTER);
                desktop.add(frmLeaves);
                frmLeaves.pack();
                frmLeaves.setVisible(true);
                break;
            case ILL:
                FormWindow frmIlls = new FormWindow("Больничные");
                frmIlls.add(new IllListView(frmIlls), BorderLayout.CENTER);
                desktop.add(frmIlls);
                frmIlls.pack();
                frmIlls.setVisible(true);
                break;
            case TBL:
                FormWindow frmTbl = new FormWindow("Табель");
                frmTbl.add(new TabelListView(frmTbl), BorderLayout.CENTER);
                desktop.add(frmTbl);
                try {
                    frmTbl.setMaximum(true);
                } catch (PropertyVetoException e) {
                    System.err.println("Кто-то дал veto на maximize окошка!");
                }
                frmTbl.pack();
                frmTbl.setVisible(true);
                break;
            case SCH:
                FormWindow frmSch = new FormWindow("Расписание");
                frmSch.add(new ScheduleListView(frmSch), BorderLayout.CENTER);
                desktop.add(frmSch);
                frmSch.pack();
                frmSch.setVisible(true);
                break;
            case APP:
                FormWindow frmApp = new FormWindow("Должности");
                frmApp.add(new AppointListView(frmApp, dbsrv), BorderLayout.CENTER);
                desktop.add(frmApp);
                frmApp.pack();
                frmApp.setVisible(true);
                break;
            case TRANS:
                FormWindow frmTrans = new FormWindow("Назначения");
                frmTrans.add(new TransListView(frmTrans,null), BorderLayout.CENTER);
                desktop.add(frmTrans);
                frmTrans.pack();
                frmTrans.setVisible(true);
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


