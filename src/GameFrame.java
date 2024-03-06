import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private final Game game;
    private final int width;
    private final int height;
    private final CellPanel[][] panels;
    private JSlider slider;
    private JLabel topLabel;
    private int sliderValue;

    public GameFrame(Game game) {
        this.game = game;
        width = game.getWorld().getWidth();
        height = game.getWorld().getHeight();
        panels = new CellPanel[height][width];

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    game.nextTurn(sliderValue);
                    updateDisplay();
                }
            }
        });
    }

    public void initializeDisplay() {
        this.setTitle("Assignment 2a");
        this.setLayout(new BorderLayout());

        initializeGamePanel();
        initializeSlider();
        initializeTopLabel();
        initializeScreenSize();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeGamePanel() {
        JPanel gamePanel = new JPanel(new GridLayout(height, width));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellPanel cellPanel = new CellPanel();
                panels[i][j] = cellPanel;
                gamePanel.add(cellPanel);
            }
        }
        this.add(gamePanel, BorderLayout.CENTER);
    }

    private void initializeSlider() {
        slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> sliderValue = slider.getValue());
        this.add(slider, BorderLayout.SOUTH);
    }

    private void initializeTopLabel() {
        topLabel = new JLabel("TURN: 0", JLabel.CENTER);
        topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(topLabel, BorderLayout.NORTH);
    }

    private void initializeScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenSizeAdjusted = (int) (screenSize.height * 0.9);
        this.setSize(screenSizeAdjusted,screenSizeAdjusted);
    }

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
