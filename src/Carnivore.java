/**
 * A lifeform that has a unique ID, moves, hungers, and reproduce. Eats lifeforms that are carnedible.
 */
public class Carnivore extends Lifeform implements Omniedible {

    /**
     * Creates a new carnivore with hungerMax, hungerRate, moves, reproduceMates, reproduceSpaces, reproduceMoves,
     * and a unique id; then increments the idNumber.
     */
    public Carnivore() {
        hungerMax = 16;
        moves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};

        reproduceMoves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        reproduceMates = 1;
        reproduceSpaces = 3;

        if (idNumber < 1000) {
            id = idNumber + "C";
        } else {
            id = idNumber + "";
        }
        idNumber++;
    }

    /**
     * Increments hunger by one, then checks if the carnivore starved or not.
     * @return false if the carnivore starved, true otherwise.
     */
    @Override
    public boolean hungerAndValidate() {
        hunger ++;
        return !(hunger >= hungerMax);
    }

    /**
     * Checks if the given lifeform (food) can be eaten by the carnivore.
     * @param food to check if it can be eaten.
     * @return true if food is of type Carnedible; otherwise false.
     */
    @Override
    public boolean validateEdible(Lifeform food) {
        return food instanceof Carnedible || food == null;
    }

    /**
     * Sets hunger to 0 if the given lifeform (food) can be eaten by the carnivore and is not null.
     * @param food to check if null and the carnivore can eat it.
     */
    @Override
    public void eat(Lifeform food) {
        if (validateEdible(food) && food != null) {
            hunger = 0;
        }
    }

    /**
     * Checks if the organism can reproduce given the number of mates and spaces next to it, and whether it's half full
     * or more.
     * @param mates checks if equal to reproduceMates.
     * @param space checks if greater or equal to reproduceSpaces.
     * @return true if mates and spaces are greater or equal to reproduceMates and reproduceSpaces, and hunger is half
     * or less than the hunger max; false otherwise.
     */
    @Override
    public boolean validateReproduce(int mates, int space) {
        return mates == reproduceMates && space >= reproduceSpaces && hunger <= (hungerMax / 2);
    }

    /**
     * From the given cell array (options), randomly chooses a cell.
     * @param options the cell array to choose a move from.
     * @return the cell chosen to move to.
     */
    @Override
    public Cell chooseMove(Cell[] options) {
        return options[RandomGenerator.nextNumber(options.length)];
    }

    /**
     * Checks if the given lifeform is a carnivore.
     * @param lifeform to check if a carnivore.
     * @return true if the given lifeform is a carnivore; false otherwise.
     */
    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Carnivore;
    }

    /**
     * Increases this carnivore's hunger by a quarter of their hungerMax, then returns a new carnivore that is 25% full.
     * @return a new carnivore with hunger equal to 75% of their hungerMax.
     */
    @Override
    public Lifeform createCopy() {
        hunger += (hungerMax / 4);
        Herbivore baby = new Herbivore();
        baby.hunger = hungerMax - (hungerMax / 4);
        return baby;
    }
}
