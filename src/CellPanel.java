import javax.swing.*;
import java.awt.*;

/**
 * Panel that displays a cell and its contents.
 */
public class CellPanel extends JPanel {

    // Label for displaying a cell's lifeform's id.
    JLabel idLabel = new JLabel();

    /**
     * Creates an empty panel with a border.
     */
    public CellPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(idLabel);
        idLabel.setVisible(false);
    }

    /**
     * Sets the background and id of the panel based on the lifeform in the given cell.
     * @param cell that determines the panel's appearance based on its lifeform.
     */
    public void updatePanel(Cell cell) {
        Lifeform lifeform = cell.getLifeform();
        if (lifeform instanceof Plant) {
            setBackground(Color.GREEN);
        } else if (lifeform instanceof Herbivore) {
            setBackground(Color.YELLOW);
        } else if (lifeform instanceof Omnivore) {
            setBackground(Color.BLUE);
        } else if (lifeform instanceof Carnivore) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.WHITE);
            idLabel.setVisible(false);
            return;
        }
        idLabel.setText(lifeform.getID());
        idLabel.setVisible(true);
    }
}
