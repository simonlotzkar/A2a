public abstract class Lifeform {
    protected int hunger;
    protected int hungerMax;
    protected int hungerRate;
    protected Move[] moves;
    protected Move[] reproduceMoves;
    protected int reproduceMates;
    protected int reproduceSpace;

    public abstract boolean ageAndValidate(int times);
    public abstract boolean validateEdible(Lifeform food);
    public abstract void eat(Lifeform food);
    public abstract boolean validateReproduce(int mates, int space);

    public Move[] getMoves() {
        return this.moves;
    }

    public Move[] getReproduceMoves() {
        return this.reproduceMoves;
    }

    public abstract boolean isSame(Lifeform lifeform);

    public abstract Lifeform createCopy();
}
