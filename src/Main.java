/**
 * Drives the program.
 * @author SimonLotzkar
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game(new World(25,25));
        GameFrame gameFrame = new GameFrame(game);
        gameFrame.initializeDisplay();
    }
}