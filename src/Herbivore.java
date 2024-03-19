/**
 * A lifeform that has a unique ID, moves, hungers, but does not reproduce.
 */
public class Herbivore extends Lifeform {

    /**
     * Creates a new herbivore with hungerMax, hungerRate, moves, and a unique id; then increments the idNumber.
     */
    public Herbivore() {
        hungerMax = 5;
        moves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};

        reproduceMoves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        reproduceMates = 1;
        reproduceSpaces = 4;

        if (idNumber < 1000) {
            id = idNumber + "H";
        } else {
            id = idNumber + "";
        }
        idNumber++;
    }

    /**
     * Increments hunger by one, then checks if the herbivore starved or not.
     * @return false if the herbivore starved, true otherwise.
     */
    @Override
    public boolean hungerAndValidate() {
        hunger ++;
        return !(hunger >= hungerMax);
    }

    /**
     * Checks if the given lifeform (food) can be eaten by the herbivore.
     * @param food to check if it can be eaten.
     * @return true if food is of type HerbEdible; otherwise false.
     */
    @Override
    public boolean validateEdible(Lifeform food) {
        return food instanceof Herbedible || food == null;
    }

    /**
     * Sets hunger to 0 if the given lifeform (food) can be eaten by the herbivore and is not null.
     * @param food to check if null and the herbivore can eat it.
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
//        ArrayList<Cell> others = new ArrayList<>();
//        ArrayList<Cell> herbEdibles = new ArrayList<>();
//        for (Cell c : options) {
//            if (c.getLifeform() instanceof Herbedible) {
//                herbEdibles.add(c);
//            } else {
//                others.add(c);
//            }
//        }
//        if (!herbEdibles.isEmpty()) {
//            return herbEdibles.get(RandomGenerator.nextNumber(herbEdibles.size()));
//        } else {
//            return others.get(RandomGenerator.nextNumber(others.size()));
//        }
        return options[RandomGenerator.nextNumber(options.length)];
    }

    /**
     * Checks if the given lifeform is a herbivore.
     * @param lifeform to check if a herbivore.
     * @return true if the given lifeform is a herbivore; false otherwise.
     */
    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Herbivore;
    }

    /**
     * Increases this herbivore's hunger by a quarter of their hungerMax, then returns a new herbivore that is 25% full.
     * @return a new herbivore with hunger equal to 75% of their hungerMax.
     */
    @Override
    public Lifeform createCopy() {
        hunger += (hungerMax / 4);
        Herbivore baby = new Herbivore();
        baby.hunger = hungerMax - (hungerMax / 4);
        return baby;
    }
}
