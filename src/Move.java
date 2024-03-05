public class Move {
    private final Movement movement;
    private final int magnitude;

    public Move(Movement movement, int magnitude) {
        this.movement = movement;
        this.magnitude = magnitude;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public Movement getMovement() {
        return movement;
    }
}
