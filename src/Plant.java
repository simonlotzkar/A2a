public class Plant extends Lifeform implements Herbedible {

    public Plant() {
        reproduceMoves = new Move[]{new Move(Movement.ADJACENT, 1), new Move(Movement.DIAGONAL, 1)};
        reproduceMates = 3;
        reproduceSpace = 3;
        if (idNumber > 999) {
            id = idNumber + "";
        } else {
            id = idNumber + "P";
        }
        idNumber++;
    }

    @Override
    public boolean ageAndValidate() {
        return true;
    }

    @Override
    public boolean validateEdible(Lifeform food) {
        return false;
    }

    @Override
    public void eat(Lifeform food) {

    }

    @Override
    public boolean validateReproduce(int mates, int space) {
        return mates >= reproduceMates && space >= reproduceSpace;
    }

    @Override
    public boolean isSame(Lifeform lifeform) {
        return lifeform instanceof Plant;
    }

    @Override
    public Lifeform createCopy() {
        return new Plant();
    }

    @Override
    public Cell chooseMove(Cell[] options) {
        return null;
    }
}
