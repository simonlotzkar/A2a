public class Plant extends Lifeform implements Herbedible {

    public Plant() {
        reproduceMoves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        reproduceMates = 4;
        reproduceSpace = 3;
    }

    @Override
    public boolean ageAndValidate(int times) {
        return true; //plants dont age
    }

    @Override
    public boolean validateEdible(Lifeform food) {
        return false; //plants dont eat
    }

    @Override
    public void eat(Lifeform food) {
        //plants dont eat
    }

    @Override
    public boolean validateReproduce(int mates, int space) {
        return mates == reproduceMates && space >= reproduceSpace;
    }

    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Plant;
    }

    @Override
    public Lifeform createCopy() {
        return new Plant();
    }
}
