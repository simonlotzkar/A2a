public class Game {
    private final World world;

    public Game(int width, int height) {
        world = new World(width, height);
    }

    // Increases turn by times, calls AgeAll(times), then world's moveAll(times), then world's reproduceAll(times)
    public void nextTurn(int times) {
        for (int i = 0; i < times; i++) {
            this.world.reproduceAll();
            this.world.moveAll();
            ageAll();
        }
    }

    // calls the world's getAllCells() then iterates through each cell times times and calls their ageAndValidate()
    // method, if false, calls the cell's clear() method.
    public void ageAll() {
        Cell[] cells = this.world.getCellsArray();
        for (Cell c : cells) {
            if (c.getLifeform() != null) {
                if (!c.getLifeform().ageAndValidate()) {
                    c.clear();
                }
            }
        }
    }

    public World getWorld() {
        return world;
    }
}
