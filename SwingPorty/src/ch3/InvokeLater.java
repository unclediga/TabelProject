package ch3;

/**
 *
 */
// InvokeLater.java
// ћетод invokeLater() и работа с потоком рассылки событий

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
        button = new JButton("¬ыполнить сложную работу");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// запустим отдельный поток
                new ComplexJobThread().start();
                button.setText("ѕодождите...");
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

    // поток, выполн€ющий"сложную работу"
    class ComplexJobThread extends Thread {
        public void run() {
            try {
// изобразим задержку
                sleep(10000);
// работа закончена, нужно изменить интерфейс
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        button.setText("–абота завершена");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}