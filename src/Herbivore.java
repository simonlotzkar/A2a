public class Herbivore extends Lifeform {

    public Herbivore() {
        hungerMax = 5;
        hungerRate = 1;
        moves = new Move[]{new Move(Movement.ADJACENT, 1)};
    }

    //increments hunger by hungerRate. if hunger >= hungerMax, returns false; otherwise true.
    @Override
    public boolean ageAndValidate(int times) {
        hunger += hungerRate * times;
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
        return false; //herbivores dont reproduce
    }

    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Herbivore;
    }

    @Override
    public Lifeform createCopy() {
        return new Herbivore();
    }
}
