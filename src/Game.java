/**
 * Represents a game with a world that contains cells.
 */
public class Game {
    // World that contains the cells.
    private final World world;

    // Current turn the game is on.
    private int turn;

    /**
     * Creates a game with the given world.
     * @param world the world to set the new game to.
     */
    public Game(World world) {
        this.world = world;
    }

    //

    /**
     * Calls the ageAll, the world's reproduceAll and moveAll, and increases the turn times times.
     * @param times amount of turns to advance the game by.
     */
    public void nextTurn(int times) {
        for (int i = 0; i < times; i++) {
            world.reproduceAll();
            world.moveAll();
            ageAll();
            turn++;
        }
    }

    /**
     * Gets each cell in the world and calls their ageAndValidate methods, clearing them if they return false.
     */
    public void ageAll() {
        Cell[] cells = world.getCellsArray();
        for (Cell c : cells) {
            if (c.getLifeform() != null) {
                if (!c.getLifeform().hungerAndValidate()) {
                    c.clear();
                }
            }
        }
    }

    /**
     * Returns the game's world.
     * @return the game's world.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Returns the game's turn number.
     * @return the game's turn number.
     */
    public int getTurn() {
        return turn;
    }
}
