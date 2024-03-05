public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program...");
        Game game = new Game(25, 25);
        GameFrame gameFrame = new GameFrame(game);
        gameFrame.initializeDisplay();
        gameFrame.updateDisplay();
    }
}