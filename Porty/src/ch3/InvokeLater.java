package ch3;

/**
 *
 */
// InvokeLater.java
// Метод invokeLater() и работа с потоком рассылки событий

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvokeLater extends JFrame {
    private JButton button;

    public InvokeLater() {
        super("InvokeLater");
// при закрытии окна- выход
        setDefaultCloseOperation(EXIT_ON_CLOSE);
// добавим кнопку со слушателем
        button = new JButton("Выполнить сложную работу");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// запустим отдельный поток
                new ComplexJobThread().start();
                button.setText("Подождите...");
            }
        });
// настроим панель содержимого и выведем окно на экран
        setLayout(new FlowLayout());
        add(new JTextField(20));
        add(button);
        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        new InvokeLater();
                    }
                });
    }

    // поток, выполняющий"сложную работу"
    class ComplexJobThread extends Thread {
        public void run() {
            try {
// изобразим задержку
                sleep(10000);
// работа закончена, нужно изменить интерфейс
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        button.setText("Работа завершена");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}