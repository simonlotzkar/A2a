/**
 * Contains a mutable lifeform that can be set, reset (set to null), and retrieved.
 */
public class Cell {
    // Contents of the cell
    private Lifeform lifeform;

    /**
     * Creates a cell with a null lifeform.
     */
    public Cell() {
        this.lifeform = null;
    }

    /**
     * Sets the cell's lifeform to null.
     */
    public void clear() {
        this.lifeform = null;
    }

    /**
     * Sets the cell's lifeform to the given lifeform.
     * @param lifeform to set the cell's lifeform to.
     */
    public void fill(Lifeform lifeform) {
        this.lifeform = lifeform;
    }

    /**
     * Returns the cell's lifeform.
     * @return the cell's lifeform.
     */
    public Lifeform getLifeform() {
        return this.lifeform;
    }
}
