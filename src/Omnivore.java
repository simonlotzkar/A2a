import java.util.ArrayList;

/**
 * A lifeform that has a unique ID, moves, hungers, and reproduce. Eats lifeforms that are omniedible.
 */
public class Omnivore extends Lifeform implements CarnEdible {

    /**
     * Creates a new omnivore with hungerMax, hungerRate, moves, reproduceMates, reproduceSpaces, reproduceMoves,
     * and a unique id; then increments the idNumber.
     */
    public Omnivore() {
        hungerMax = 12;
        moves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};

        reproduceMoves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        reproduceMates = 1;
        reproduceSpaces = 1;

        if (idNumber < 1000) {
            id = idNumber + "O";
        } else {
            id = idNumber + "";
        }
        idNumber++;
    }

    /**
     * Increments hunger by one, then checks if the omnivore starved or not.
     * @return false if the omnivore starved, true otherwise.
     */
    @Override
    public boolean hungerAndValidate() {
        hunger ++;
        return !(hunger >= hungerMax);
    }

    /**
     * Checks if the given lifeform (food) is not a Omnivore.
     * @param food to check if it is not a Omnivore.
     * @return true if food is not a Omnivore; otherwise false.
     */
    @Override
    public boolean validateEdible(Lifeform food) {
        return !(food instanceof Omnivore);
    }

    /**
     * Sets hunger to 0 if the given lifeform (food) is Omninedible.
     * @param food to check if Omniedible.
     */
    @Override
    public void eat(Lifeform food) {
        if (food instanceof OmniEdible) {
            hunger = 0;
        }
    }

    /**
     * Checks if the organism can reproduce given the number of mates and spaces next to it.
     * @param mates checks if equal to reproduceMates.
     * @param space checks if greater or equal to reproduceSpaces.
     * @return true if mates and spaces are greater or equal to reproduceMates and reproduceSpaces; false otherwise.
     */
    @Override
    public boolean validateReproduce(int mates, int space) {
        return mates >= reproduceMates && space >= reproduceSpaces;
    }

    /**
     * From the given cell array (options), randomly chooses a cell.
     * @param options the cell array to choose a move from.
     * @return the cell chosen to move to.
     */
    @Override
    public Cell chooseMove(Cell[] options) {
        ArrayList<Cell> others = new ArrayList<>();
        ArrayList<Cell> herbEdibles = new ArrayList<>();
        for (Cell c : options) {
            if (c.getLifeform() instanceof OmniEdible) {
                herbEdibles.add(c);
            } else {
                others.add(c);
            }
        }
        if (!herbEdibles.isEmpty()) {
            return herbEdibles.get(RandomGenerator.nextNumber(herbEdibles.size()));
        } else {
            return others.get(RandomGenerator.nextNumber(others.size()));
        }
    }

    /**
     * Checks if the given lifeform is a omnivore.
     * @param lifeform to check if a omnivore.
     * @return true if the given lifeform is a omnivore; false otherwise.
     */
    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Omnivore;
    }

    /**
     * Returns a new omnivore that is 25% full.
     * @return a new omnivore with hunger equal to 75% of their hungerMax.
     */
    @Override
    public Lifeform createCopy() {
        Herbivore baby = new Herbivore();
        baby.hunger = hungerMax - (hungerMax / 4);
        return baby;
    }
}
