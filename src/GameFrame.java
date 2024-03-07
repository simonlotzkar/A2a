import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Window where a game and its world is displayed and interacted with. Allows turn advancement by clicking and
 * a slider adjusts how much each click advances the turns.
 */
public class GameFrame extends JFrame {
    // Game to display and interact with
    private final Game game;

    // Width of the game's world
    private final int width;

    // Height of the game's world
    private final int height;

    // CellPanel 2D array of the game's world
    private final CellPanel[][] panels;

    // Label for turn tracker
    private JLabel topLabel;

    // Slider for turn advancement
    private JSlider slider;

    // Amount of turns to advance on each left click
    private int turnAdvancementScale = 1;

    /**
     * Creates a game window with the given game and sets a mouselistener to track clicks.
     * @param game the game the window will display and interact with.
     */
    public GameFrame(Game game) {
        this.game = game;
        width = game.getWorld().getWidth();
        height = game.getWorld().getHeight();
        panels = new CellPanel[height][width];
        initializeMouseListener();
    }

    /**
     * Adds a left click listener to the window to increase the turn by the slider value and update the display.
     */
    private void initializeMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    game.nextTurn(turnAdvancementScale);
                    updateDisplay();
                }
            }
        });
    }

    /**
     * Initializes the window's subcomponents and populates the game grid with the game's world's cells.
     */
    public void initializeDisplay() {
        this.setTitle("Assignment 2a");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGameGridPanel();
        initializeSlider();
        initializeTopLabel();
        initializeScreenSettings();
        updateDisplay();
    }

    /**
     * Creates the game grid with cell panels for each cell in the game's world.
     */
    private void initializeGameGridPanel() {
        JPanel gameGridPanel = new JPanel(new GridLayout(height, width));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellPanel cellPanel = new CellPanel();
                panels[i][j] = cellPanel;
                gameGridPanel.add(cellPanel);
            }
        }
        this.add(gameGridPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the slider and listener for changing the turn advancement scale; goes from 0-20 and starts at 1.
     */
    private void initializeSlider() {
        slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 1);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> turnAdvancementScale = slider.getValue());
        this.add(slider, BorderLayout.SOUTH);
    }

    /**
     * Creates the label that tracks and displays the current turn number, starts at 0.
     */
    private void initializeTopLabel() {
        topLabel = new JLabel("TURN: 0", JLabel.CENTER);
        topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(topLabel, BorderLayout.NORTH);
    }

    /**
     * Sets the size of the screen to a square 90% of the screen's height, centered.
     */
    private void initializeScreenSettings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenSizeAdjusted = (int) (screenSize.height * 0.9);
        this.setSize(screenSizeAdjusted,screenSizeAdjusted);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Populates each cellpanel with the game's world's current configuration, updates the turn tracker, and repaints.
     */
    public void updateDisplay() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                panels[i][j].updatePanel(game.getWorld().getCells()[i][j]);
            }
        }
        topLabel.setText("TURN: " + game.getTurn());
        repaint();
    }
}
