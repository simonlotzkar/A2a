public class Game {
    private int turn;
    private final World world;

    public Game(int width, int height) {
        turn = 0;
        world = new World(width, height);
    }

    // Increases turn by times, calls AgeAll(times), then world's moveAll(times), then world's reproduceAll(times)
    public void nextTurn(int times) {
        turn += times;
        ageAll(times);
        this.world.moveAll(times);
        this.world.reproduceAll(times);
//        System.out.println("Turn increased " + times + " times. Turn = " + turn);
    }

    // calls the world's getAllCells() then iterates through each cell times times and calls their ageAndValidate()
    // method, if false, calls the cell's clear() method.
    public void ageAll(int times) {
        Cell[] cells = this.world.getCellsArray();
        for (Cell c : cells) {
            if (c.getLifeform() != null) {
                if (!c.getLifeform().ageAndValidate(times)) {
                    c.clear();
                }
            }
        }
    }

    public World getWorld() {
        return world;
    }
}
