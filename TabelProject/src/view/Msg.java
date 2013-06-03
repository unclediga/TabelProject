package view;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Msg {
    public static void error(Component c,String s) {
        JOptionPane.showMessageDialog(c, s, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component c, String s) {
        JOptionPane.showMessageDialog(c, s, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
}
