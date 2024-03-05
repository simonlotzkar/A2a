import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private final World world;
    private final int width;
    private final int height;
    private final CellPanel[][] panels;

    public GameFrame(Game game) {
        world = game.getWorld();
        width = world.getWidth();
        height = world.getHeight();
        panels = new CellPanel[height][width];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    game.nextTurn(1);
                }
                else if (SwingUtilities.isRightMouseButton(e)) {
                    game.nextTurn(2);
                }
                updateDisplay();
            }
        });
    }

    public void initializeDisplay() {
        this.setTitle("Assignment 2a");
        this.setLayout(new GridLayout(height, width));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellPanel cellPanel = new CellPanel();
                panels[i][j] = cellPanel;
                this.add(cellPanel);
            }
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.height / 2,screenSize.height / 2);
        this.setVisible(true);
    }

    public void updateDisplay() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                panels[i][j].updatePanel(world.getCells()[i][j]);
//                panels[i][j].addLabel("" + (i * height + j));
            }
        }
        repaint();
    }
}
