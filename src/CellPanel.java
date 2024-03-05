import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {

    JLabel jLabel = new JLabel();

    public CellPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(jLabel);
        jLabel.setVisible(false);
    }

    public void updatePanel(Cell cell) {
        Lifeform lifeform = cell.getLifeform();
        if (lifeform instanceof Plant) {
            setBackground(Color.GREEN);
        } else if (lifeform instanceof Herbivore) {
            setBackground(Color.YELLOW);
        } else {
            setBackground(Color.WHITE);
            jLabel.setVisible(false);
            return;
        }

        jLabel.setText(lifeform.getID());
        jLabel.setVisible(true);
    }
}
