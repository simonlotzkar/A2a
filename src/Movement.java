/**
 * A movement that has a descriptive name of a series of steps with their own magnitudes that
 * represent a pattern of movement where each subsequent step is in a new dimension.
 */
public enum Movement {
    
    ADJACENT(new int[]{1}),    // Moves once in one dimension
    DIAGONAL(new int[]{1,1});  // Moves once in one dimension, then once in a different dimension

    // Array of steps in a Movement.
    private final int[] steps;

    /**
     * Creates a movement with the given array of steps.
     * @param steps to set the new movement to.
     */
    Movement(int[] steps) {
        this.steps = steps;
    }

    /**
     * Returns the array of steps for the movement.
     * @return the array of steps for the movement.
     */
    public int[] getSteps() {
        return this.steps;
    }
}
