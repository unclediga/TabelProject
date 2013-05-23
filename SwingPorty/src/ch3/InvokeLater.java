package ch3;

/**
 *
 */
// InvokeLater.java
// ����� invokeLater() � ������ � ������� �������� �������

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvokeLater extends JFrame {
    private JButton button;

    public InvokeLater() {
        super("InvokeLater");
// ��� �������� ����- �����
        setDefaultCloseOperation(EXIT_ON_CLOSE);
// ������� ������ �� ����������
        button = new JButton("��������� ������� ������");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// �������� ��������� �����
                new ComplexJobThread().start();
                button.setText("���������...");
            }
        });
// �������� ������ ����������� � ������� ���� �� �����
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

    // �����, �����������"������� ������"
    class ComplexJobThread extends Thread {
        public void run() {
            try {
// ��������� ��������
                sleep(10000);
// ������ ���������, ����� �������� ���������
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        button.setText("������ ���������");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}