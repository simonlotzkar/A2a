/**
 * A move that has a movement (move pattern) and magnitude (move pattern multiplier).
 */
public class Move {
    // The movement that represents the pattern of the move.
    private final Movement movement;

    // The magnitude of the move pattern.
    private final int magnitude;

    /**
     * Creates a move with the given movement and magnitude.
     * @param movement to set the Move to.
     * @param magnitude to set the Move to.
     */
    public Move(Movement movement, int magnitude) {
        this.movement = movement;
        this.magnitude = magnitude;
    }

    /**
     * Returns the Move's magnitude.
     * @return the Move's magnitude.
     */
    public int getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the Move's movement.
     * @return the Move's movement.
     */
    public Movement getMovement() {
        return movement;
    }
}
