package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.kordamp.ikonli.swing.FontIcon;

public class ButtonRenderer extends JButton implements TableCellRenderer {
  private FontIcon icon;

  public ButtonRenderer(FontIcon icon) {
    setOpaque(true);
    this.icon = icon;

  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    setText((value == null) ? "" : value.toString());
    setIcon(icon);
    setBackground(Color.WHITE);

    return this;
  }

}
