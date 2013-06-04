package com.porty.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageTableCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageListElement) {
            ImageListElement imageListElement = (ImageListElement) value;
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setText(imageListElement.getText());
            label.setIcon(imageListElement.getIcon());
            label.setToolTipText(imageListElement.getText());
            return label;


        } else {

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
