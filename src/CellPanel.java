import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {

    public CellPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void updatePanel(Cell cell) {
        Lifeform lifeform = cell.getLifeform();
        if (lifeform instanceof Plant) {
            setBackground(Color.GREEN);
        } else if (lifeform instanceof Herbivore) {
            setBackground(Color.YELLOW);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public void addLabel(String string) {
        JLabel jLabel = new JLabel(string);
        this.add(jLabel);
    }
}
