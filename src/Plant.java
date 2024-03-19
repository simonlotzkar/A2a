/**
 * A lifeform that has a unique ID, reproduces, but does not hunger or move.
 */
public class Plant extends Lifeform implements Herbedible {

    /**
     * Creates a new plant with reproduceMoves, reproduceMates, reproduceSpaces, and a unique id;
     * then increments the idNumber.
     */
    public Plant() {
        reproduceMoves = new Move[]{
                new Move(Movement.ADJACENT, 1),
                new Move(Movement.DIAGONAL, 1),
                new Move(Movement.ADJACENT, 2),
                new Move(Movement.DIAGONAL, 2),
                new Move(Movement.ADJACENT, 3),
                new Move(Movement.DIAGONAL, 3)};
        reproduceMates = 2;
        reproduceSpaces = 3;
        if (idNumber > 999) {
            id = idNumber + "";
        } else {
            id = idNumber + "P";
        }
        idNumber++;
    }

    /**
     * Returns true because plants do not hunger.
     * @return true.
     */
    @Override
    public boolean hungerAndValidate() {
        return true;
    }

    /**
     * Returns false because plants do not eat.
     * @param food does nothing.
     * @return false.
     */
    @Override
    public boolean validateEdible(Lifeform food) {
        return false;
    }

    /**
     * Does nothing because plants do not eat.
     * @param food does nothing.
     */
    @Override
    public void eat(Lifeform food) {}

    /**
     * Checks if the organism can reproduce given the number of mates and spaces next to it.
     * @param mates checks if greater or equal to reproduceMates.
     * @param space checks if greater or equal to reproduceSpaces.
     * @return true if mates and spaces are greater or equal to reproduceMates and reproduceSpaces; false otherwise.
     */
    @Override
    public boolean validateReproduce(int mates, int space) {
        return mates >= reproduceMates && space >= reproduceSpaces;
    }

    /**
     * Return null because plants don't move.
     * @param options does nothing.
     * @return null.
     */
    @Override
    public Cell chooseMove(Cell[] options) {
        return null;
    }

    /**
     * Checks if the given lifeform is a plant.
     * @param lifeform to check if it's a plant.
     * @return true if the given lifeform is a plant; false otherwise.
     */
    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Plant;
    }

    /**
     * Creates a new plant.
     * @return a new plant.
     */
    @Override
    public Lifeform createCopy() {
        return new Plant();
    }
}
