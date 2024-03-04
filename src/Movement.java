public enum Movement {
    ADJACENT(new int[]{1}),
    DIAGONAL(new int[]{1,1});

    private final int[] steps;

    Movement(int[] steps) {
        this.steps = steps;
    }

    public int[] getSteps() {
        return this.steps;
    }
}
