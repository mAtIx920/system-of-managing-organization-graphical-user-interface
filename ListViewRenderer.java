import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class ListViewRenderer extends JCheckBox implements ListCellRenderer<Object> {
    private HashMap<Long, Object> items;
    ListViewRenderer(HashMap<Long, Object> items) {
        this.items = items;
    }
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setSelected(isSelected);
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setText(value.toString());
        return this;
    }
}
