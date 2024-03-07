import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A two-dimensional game world comprised of cells with lifeforms.
 */
public class World {
    // 2D Array of cells that makes the world.
    private final Cell[][] cells;

    // The horizontal size of the world, or the column length.
    private final int width;

    // The vertical size of the world, or the row length.
    private final int height;

    /**
     * Creates the world's cells array of the given dimensions then iterates through it and initializes each cell.
     * @param width to set the world's width to.
     * @param height to set the world's height to.
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = generate();
            }
        }
    }

    /**
     * Creates a new cell using RandomGenerator to pick a number from 1-100 for its contents:
     *     100-85 (15%) results in a herbivore cell,
     *     84-65  (20%) results in a plant cell,
     *     64-1   (65%) results in an empty cell.
     * @return the cell created and filled.
     */
    private Cell generate() {
        Cell cell = new Cell();
        int number = RandomGenerator.nextNumber(100);
        if (number >= 85) {
            cell.fill(new Herbivore());
        } else if (number >= 65) {
            cell.fill(new Plant());
        }
        return cell;
    }

    /**
     * Returns a 1D array of all cells in the world.
     * @return a 1D array of all cells in the world.
     */
    public Cell[] getCellsArray() {
        Cell[] cells1D = new Cell[width * height];
        int index = 0;
        for (int i = 0; i < height; i++) {
            System.arraycopy(cells[i], 0, cells1D, index, width);
            index += width;
        }
        return cells1D;
    }

    /**
     * Returns a set of cells that are reached using the given moves from the given cell (origin).
     * @param origin the cell to generate the destinations from.
     * @param moves the moves to generate the destinations with.
     * @return a set of cells that are reached using the given moves from the given cell (origin).
     */
    private Set<Cell> findValidMoves(Cell origin, Move[] moves) {
        int row = 0;
        int col = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j] == origin) {
                    row = i;
                    col = j;
                }
            }
        }
        return findValidDestinations(moves, row, col);
    }

    /**
     * Returns a set of valid destinations that can be reached from the given location using the given moves.
     * @param moves to find valid destinations with.
     * @param row vertical location for finding valid destinations.
     * @param col horizontal location for finding valid destinations.
     * @return a set of valid destinations that can be reached from the given location using the given moves.
     */
    private Set<Cell> findValidDestinations(Move[] moves, int row, int col) {
        Set<Cell> validDestinations = new LinkedHashSet<>();
        for (Move m : moves) {
            int stepLength = m.getMovement().getSteps().length;
            if (stepLength == 1) {
                addValidDestinations1D(validDestinations, m, row, col);
            } else if (stepLength == 2) {
                addValidDestinations2D(validDestinations, m, row, col);
            }
        }
        return validDestinations;
    }

    /**
     * Adds all 1-dimensional moves to the set given a move, row, and column.
     * @param validDestinations set to add valid destinations to.
     * @param m move to find valid destinations with.
     * @param row vertical location for finding valid destinations.
     * @param col horizontal location for finding valid destinations.
     */
    private void addValidDestinations1D(Set<Cell> validDestinations, Move m, int row, int col) {
        int step = m.getMovement().getSteps()[0] * m.getMagnitude();
        for (int i = -1; i < 2; i++) {
            if (i != 0) {
                if (isWithinWorld(row + step * i, col)) {
                    validDestinations.add(cells[row + step * i][col]);
                }
                if (isWithinWorld(row, col + step * i)) {
                    validDestinations.add(cells[row][col + step * i]);
                }
            }
        }
    }

    /**
     * Adds all 2-dimensional moves to the set given a move, row, and column.
     * @param validDestinations set to add valid destinations to.
     * @param m move to find valid destinations with.
     * @param row vertical location for finding valid destinations.
     * @param col horizontal location for finding valid destinations.
     */
    private void addValidDestinations2D(Set<Cell> validDestinations, Move m, int row, int col) {
        int step0 = m.getMovement().getSteps()[0] * m.getMagnitude();
        int step1 = m.getMovement().getSteps()[1] * m.getMagnitude();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    if (isWithinWorld(row + step0 * i, col + step1 * j)) {
                        validDestinations.add(cells[row + step0 * i][col + step1 * j]);
                    }
                    if (isWithinWorld(row + step1 * j, col + step0 * i)) {
                        validDestinations.add(cells[row + step1 * j][col + step0 * i]);
                    }
                }
            }
        }
    }

    /**
     * Iterates through each cell in the world and moves their lifeform if possible and only once.
     */
    public void moveAll() {
        Set<Lifeform> lifeformsThatMoved = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                Lifeform lifeform = cell.getLifeform();
                if (lifeform != null && !lifeformsThatMoved.contains(lifeform)) {
                    Cell[] validMoves = findValidMoves(cell);
                    if (validMoves.length > 0) {
                        Cell move = lifeform.chooseMove(validMoves);
                        lifeform.eat(move.getLifeform());
                        move.fill(lifeform);
                        cell.clear();
                        lifeformsThatMoved.add(lifeform);
                    }
                }
            }
        }
    }

    /**
     * Returns an array of cells the given cell's lifeform can move to.
     * @param cell contains the lifeform that is moving.
     * @return an array of cells the given cell's lifeform can move to.
     */
    private Cell[] findValidMoves(Cell cell) {
        Lifeform lifeform = cell.getLifeform();
        ArrayList<Cell> validDestinations = new ArrayList<>();
        Move[] moves = lifeform.getMoves();
        if (moves != null) {
            Set<Cell> destinations = findValidMoves(cell, moves);
            for (Cell destination : destinations) {
                if (lifeform.validateEdible(destination.getLifeform())) {
                    validDestinations.add(destination);
                }
            }
        }
        return validDestinations.toArray(new Cell[0]);
    }

    /**
     * Iterates through each cell in the world and reproduces their lifeforms if possible and only once.
     */
    public void reproduceAll() {
        Set<Lifeform> lifeformsThatReproduced = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                Lifeform lifeform = cell.getLifeform();
                if (lifeform != null && !lifeformsThatReproduced.contains(lifeform)) {
                    Cell[] validReproduces = findValidReproduces(cell);
                    int validReproducesLength = validReproduces.length;
                    if (validReproducesLength > 0) {
                        int number = RandomGenerator.nextNumber(validReproducesLength);
                        Lifeform newLifeform = lifeform.createCopy();
                        validReproduces[number].fill(newLifeform);
                        lifeformsThatReproduced.add(newLifeform);
                        lifeformsThatReproduced.add(lifeform);
                    }
                }
            }
        }
    }

    /**
     * Returns an array of cells the given cell's lifeform can reproduce to.
     * @param cell contains the lifeform that is reproducing.
     * @return an array of cells the given cell's lifeform can reproduce to.
     */
    private Cell[] findValidReproduces(Cell cell) {
        int mates = countCellNeighbours(cell, 1);
        int spaces = countCellNeighbours(cell, 0);
        Lifeform lifeform = cell.getLifeform();
        ArrayList<Cell> validDestinations = new ArrayList<>();
        if (lifeform.validateReproduce(mates, spaces)) {
            Set<Cell> destinations = findValidMoves(cell, lifeform.getReproduceMoves());
            for (Cell destination : destinations) {
                if (destination.getLifeform() == null) {
                    validDestinations.add(destination);
                }
            }
        }
        return validDestinations.toArray(new Cell[0]);
    }

    /**
     * Iterates through each cell in the world and finds the given cell. Then checks the cells surrounding it and
     * returns the number of empty cells if the mode is 0; or the number of cells with the same lifeform type of
     * the given cell's lifeform if the mode is 1; or returns 0 otherwise.
     * @param cell to count around.
     * @param mode determines if counting empty neighbours (0); or same neighbours (1).
     * @return the number of empty cells surrounding the given location if the mode is 0; or the number of cells surrounding the given location with the same lifeform type of the given cell's lifeform if the mode is 1; or 0.
     */
    private int countCellNeighbours(Cell cell, int mode) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j] == cell) {
                    return countLocationNeighbours(cell, i, j, mode);
                }
            }
        }
        return 0;
    }

    /**
     * Checks the cells surrounding it and returns the number of empty cells if the mode is 0;
     * or the number of cells with the same lifeform type of the given cell's lifeform if the mode is 1;
     * or returns 0 otherwise.
     * @param cell to check the found cell's lifeform against.
     * @param row vertical location for finding valid destinations.
     * @param col horizontal location for finding valid destinations.
     * @param mode determines if counting empty neighbours (0); or same neighbours (1).
     * @return the number of empty cells surrounding the given location if the mode is 0; or the number of cells surrounding the given location with the same lifeform type of the given cell's lifeform if the mode is 1; or 0.
     */
    private int countLocationNeighbours(Cell cell, int row, int col, int mode) {
        int count = 0;
        for (int k = -1; k < 2; k++) {
            for (int p = -1; p < 2; p++) {
                if (!(k == 0 && p == 0) && isWithinWorld(row + k, col + p)) {
                    Lifeform lifeform = cells[row + k][col + p].getLifeform();
                    count += lifeformCheck(cell, lifeform, mode);
                }
            }
        }
        return count;
    }

    /**
     * Returns 1 if the given lifeform is null and the mode is 0, or
     *              the given lifeform is the same type as the given cell; or
     * Returns 0.
     * @param cell to check against the given lifeform if mode is not 0.
     * @param lifeform to check based on the mode.
     * @param mode determines if checking null (0); or same as cell (1).
     * @return 1 if the given lifeform is null and the mode is 0, or the given lifeform is the same type as the given cell; or 0.
     */
    private int lifeformCheck(Cell cell, Lifeform lifeform, int mode) {
        if (mode == 0) {
            if (lifeform == null) {
                return 1;
            }
        } else if (mode == 1) {
            if (cell.getLifeform().isSame(lifeform)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Checks if the given coordinate (row and col) is within the bounds of the world.
     * @param row vertical position to check against height.
     * @param col horizontal position to check against width.
     * @return true if the given coordinate (row and col) is within the bounds of the world; false otherwise.
     */
    private boolean isWithinWorld(int row, int col) {
        return (row >= 0 && col >= 0 && row < height && col < width);
    }

    /**
     * Returns the 2D array of the world's cells.
     * @return the 2D array of the world's cells.
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Returns the width of the world.
     * @return the width of the world.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the world.
     * @return the height of the world.
     */
    public int getHeight() {
        return height;
    }
}
