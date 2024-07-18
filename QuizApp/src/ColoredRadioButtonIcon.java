import javax.swing.*;
import java.awt.*;

public class ColoredRadioButtonIcon implements Icon {
    private final Color color;

    public ColoredRadioButtonIcon(Color color) {
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, getIconWidth(), getIconHeight());
    }

    @Override
    public int getIconWidth() {
        return 15;
    }

    @Override
    public int getIconHeight() {
        return 15;
    }
}
