/**
 * A representation of an organism that has an id, moves, eats, dies, and reproduces.
 */
public abstract class Lifeform {
    // Number that creates unique ids and must be incremented afterwards.
    protected static int idNumber = 0;

    // String that holds a unique id made from the idNumber.
    protected String id;

    // The amount of turns that have passed since the organism last ate.
    protected int hunger;

    // The maximum amount of turns an organism can go without eating.
    protected int hungerMax;

    // An array of Moves that dictate where the organism can move itself.
    protected Move[] moves;

    // An array of Moves that dictate where the organism can reproduce to.
    protected Move[] reproduceMoves;

    // The number of other organisms of the same kind an organism needs next to itself to reproduce.
    protected int reproduceMates;

    // The number of empty spaces an organism needs next to itself to reproduce.
    protected int reproduceSpaces;

    /**
     * Increments hunger, then checks if the organism starved or not.
     * @return false if the organism starved, true otherwise.
     */
    public abstract boolean hungerAndValidate();

    /**
     * Checks if the given lifeform (food) can be eaten by the organism.
     * @param food to check if it can be eaten.
     * @return true if food can be eaten; otherwise false.
     */
    public abstract boolean validateEdible(Lifeform food);

    /**
     * Sets hunger to 0 if the given lifeform (food) can be eaten by the organism and is not null.
     * @param food to check if not null and if the organism can eat it.
     */
    public abstract void eat(Lifeform food);

    /**
     * Checks if the organism can reproduce given the number of mates and spaces next to it.
     * @param mates checks against reproduceMates.
     * @param space checks against reproduceSpaces.
     * @return true if the organism can reproduce; false otherwise.
     */
    public abstract boolean validateReproduce(int mates, int space);

    /**
     * From the given cell array (options), chooses a cell to move the organism to.
     * @param options the cell array to choose a move from.
     * @return the cell chosen to move to.
     */
    public abstract Cell chooseMove(Cell[] options);

    /**
     * Checks if the given lifeform is the same subclass of organism.
     * @param lifeform to check if the same subclass of organism.
     * @return true if the given lifeform is the same subclass of organism; false otherwise.
     */
    public abstract boolean isSame(Lifeform lifeform);

    /**
     * Creates a new organism with the same subclass.
     * @return a new organism with the same subclass.
     */
    public abstract Lifeform createCopy();

    /**
     * Returns the organism's moves array.
     * @return the organism's moves array.
     */
    public Move[] getMoves() {
        return this.moves;
    }

    /**
     * Returns the organism's reproduce moves array.
     * @return the organism's reproduce moves array.
     */
    public Move[] getReproduceMoves() {
        return this.reproduceMoves;
    }

    /**
     * Returns the organism's id.
     * @return the organism's id.
     */
    public String getID() {
        return id;
    }
}
