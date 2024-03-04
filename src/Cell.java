public class Cell {
    private Lifeform lifeform;

    public Cell() {
        this.lifeform = null;
    }

    public void clear() {
        this.lifeform = null;
    }

    public void fill(Lifeform lifeform) {
        this.lifeform = lifeform;
    }

    public Lifeform getLifeform() {
        return this.lifeform;
    }
}
