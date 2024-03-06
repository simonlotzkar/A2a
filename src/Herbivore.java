import java.util.ArrayList;

public class Herbivore extends Lifeform {

    public Herbivore() {
        hungerMax = 5;
        hungerRate = 1;
        moves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        if (idNumber > 999) {
            id = idNumber + "";
        } else {
            id = idNumber + "H";
        }
        idNumber++;
    }

    //increments hunger by hungerRate. if hunger >= hungerMax, returns false; otherwise true.
    @Override
    public boolean ageAndValidate() {
        hunger += hungerRate;
        return !(hunger >= hungerMax);
    }

    //returns true if food is of type HerbEdible; otherwise false.
    @Override
    public boolean validateEdible(Lifeform food) {
        return food instanceof Herbedible || food == null;
    }

    @Override
    public void eat(Lifeform food) {
        if (validateEdible(food) && food != null) {
            hunger = 0;
        }
    }

    @Override
    public boolean validateReproduce(int mates, int space) {
        return false;
    }

    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Herbivore;
    }

    @Override
    public Lifeform createCopy() {
        return new Herbivore();
    }

    @Override
    public Cell chooseMove(Cell[] options) {
        ArrayList<Cell> others = new ArrayList<>();
        ArrayList<Cell> herbedibles = new ArrayList<>();
        for (Cell c : options) {
            if (c.getLifeform() instanceof Herbedible) {
                herbedibles.add(c);
            } else {
                others.add(c);
            }
        }
        if (!herbedibles.isEmpty()) {
            return herbedibles.get(RandomGenerator.nextNumber(herbedibles.size()));
        } else {
            return others.get(RandomGenerator.nextNumber(others.size()));
        }
    }
}
