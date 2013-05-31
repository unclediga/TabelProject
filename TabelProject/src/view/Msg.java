package view;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Msg {
    public static void error(Component c,String s) {
        JOptionPane.showConfirmDialog(c, s, "Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component c, String s) {
        JOptionPane.showConfirmDialog(c, s, "Информация", JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
