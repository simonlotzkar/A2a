import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class World {
    private final Cell[][] cells;
    private final int width;
    private final int height;

    //Initializes cells = Cell[width][height] then iterates over the array and initializes each cell using the generate(Cell) method.
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

    //Creates a new Cell. then uses RandomGenerator to generate a number between 0 and 99. Value >=85 calls fill(Herbivore) on cell, value >=65 calls fill(Plant) on cell.
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

    //returns an array of all cells in the 2d array.
    public Cell[] getCellsArray() {
        Cell[] cells1D = new Cell[width * height];
        int index = 0;

        for (int i = 0; i < height; i++) {
            System.arraycopy(cells[i], 0, cells1D, index, width);
            index += width;
        }

        return cells1D;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // finds each cell that is (x+/-n or y+/-n) away from the current cell, where n is the first entry in a move's
    // array; then if the array contains another entry it moves in the opposite dimension that it did before.
    private Set<Cell> findDestinations(Cell origin, Move[] moves) {
        Set<Cell> validDestinations = new HashSet<>();
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

        for (Move m : moves) {
            int stepLength = m.getMovement().getSteps().length;
            if (stepLength == 1) {
                int step = m.getMovement().getSteps()[0] * m.getMagnitude();

                // +X
                if (isWithinWorld(row + step, col)) {
                    validDestinations.add(cells[row + step][col]);
                }
                // -X
                if (isWithinWorld(row - step, col)) {
                    validDestinations.add(cells[row - step][col]);
                }
                // +Y
                if (isWithinWorld(row, col + step)) {
                    validDestinations.add(cells[row][col + step]);
                }
                // -Y
                if (isWithinWorld(row, col - step)) {
                    validDestinations.add(cells[row][col - step]);
                }
            } else if (stepLength == 2) {
                int step0 = m.getMovement().getSteps()[0] * m.getMagnitude();
                int step1 = m.getMovement().getSteps()[1] * m.getMagnitude();

                // +X, +/- Y
                if (isWithinWorld(row + step0, col + step1)) {
                    validDestinations.add(cells[row + step0][col + step1]);
                }
                if (isWithinWorld(row + step0, col - step1)) {
                    validDestinations.add(cells[row + step0][col - step1]);
                }

                // -X, +/- Y
                if (isWithinWorld(row - step0, col + step1)) {
                    validDestinations.add(cells[row - step0][col + step1]);
                }
                if (isWithinWorld(row - step0, col - step1)) {
                    validDestinations.add(cells[row - step0][col - step1]);
                }

                // +Y, +/- X
                if (isWithinWorld(row + step1, col + step0)) {
                    validDestinations.add(cells[row + step1][col + step0]);
                }
                if (isWithinWorld(row - step1, col + step0)) {
                    validDestinations.add(cells[row - step1][col + step0]);
                }

                // -Y, +/- X
                if (isWithinWorld(row + step1, col - step0)) {
                    validDestinations.add(cells[row + step1][col - step0]);
                }
                if (isWithinWorld(row - step1, col - step0)) {
                    validDestinations.add(cells[row - step1][col - step0]);
                }
            }
        }

        return validDestinations;
    }

    // iterates through each cell times times and if it has a lifeform, creates an array using findValidMoves(cell).
    // then, uses RandomGenerator to pick a random cell in the array of valid destinations. then calls the original
    // cell lifeform's eat() function with the chosen destination's contents. then calls fill() on the destination
    // cell using the lifeform in the original cell. then calls clear() on the original cell.
    public void moveAll(int times) {
        int index = 0;
        while (index < times) {
//            System.out.print("\n===========MOVING CELLS===========\n");
            Set<Lifeform> lifeformsThatMoved = new HashSet<>();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Cell cell = cells[i][j];
                    Lifeform lifeform = cell.getLifeform();
                    if (lifeform != null && !lifeformsThatMoved.contains(lifeform)) {
//                        System.out.print("\nMoves for cell["+lifeform.getID()+"]("+j+","+i+"):");
                        Cell[] validMoves = findValidMoves(cell);

//                        for (Cell c : validMoves) {
//                            for (int ii = 0; ii < height; ii++) {
//                                for (int jj = 0; jj < width; jj++) {
//                                    if (cells[ii][jj] == c) {
//                                        System.out.print(" ("+jj+","+ii+")");
//                                    }
//                                }
//                            }
//                        }

                        int validMovesLength = validMoves.length;
                        if (validMovesLength > 0) {
                            int number = RandomGenerator.nextNumber(validMovesLength);
//                            System.out.print("... chose #" + (number+1));
                            lifeform.eat(validMoves[number].getLifeform());
                            validMoves[number].fill(lifeform);
                            cell.clear();
                            lifeformsThatMoved.add(lifeform);
                        }
                    }
                }
            }

            index++;
        }
    }

    // uses the cell's lifeform's getMoves(), then iterates over each and adds the cells in the array returned by
    // findDestinations(cell, moves). next, if any of those cells contains a lifeform, they must also pass the
    // original lifeform's validateEdible(lifeform).
    private Cell[] findValidMoves(Cell cell) {
        Lifeform lifeform = cell.getLifeform();
        ArrayList<Cell> validDestinations = new ArrayList<>();
        Move[] moves = lifeform.getMoves();
        if (moves != null) {
            Set<Cell> destinations = findDestinations(cell, moves);
            for (Cell destination : destinations) {
                if (lifeform.validateEdible(destination.getLifeform())) {
                    validDestinations.add(destination);
                }
            }
        }

        return validDestinations.toArray(new Cell[0]);
    }

    // iterates through each cell times times and if it has a lifeform, calls findValidReproduces(cell) then picks one
    // randomly using randomnumber generator. then calls fill(lifeform) on that cell where lifeform is the same type as
    // the cell.
    public void reproduceAll(int times) {
        int index = 0;
        while (index < times) {
//            System.out.print("\n===========REPRODUCING CELLS===========\n");
            Set<Lifeform> lifeformsThatReproduced = new HashSet<>();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Cell cell = cells[i][j];
                    Lifeform lifeform = cell.getLifeform();
                    if (lifeform != null && !lifeformsThatReproduced.contains(lifeform)) {
//                        System.out.print("\nReproduces for cell["+lifeform.getID()+"]("+j+","+i+"):");
                        Cell[] validReproduces = findValidReproduces(cell);

//                        for (Cell c : validReproduces) {
//                            for (int ii = 0; ii < height; ii++) {
//                                for (int jj = 0; jj < width; jj++) {
//                                    if (cells[ii][jj] == c) {
//                                        System.out.print(" ("+jj+","+ii+")");
//                                    }
//                                }
//                            }
//                        }

                        int validReproducesLength = validReproduces.length;
                        if (validReproducesLength > 0) {
                            int number = RandomGenerator.nextNumber(validReproducesLength);
//                            System.out.print("... chose #" + (number+1));
                            Lifeform newLifeform = lifeform.createCopy();
                            validReproduces[number].fill(newLifeform);
                            lifeformsThatReproduced.add(newLifeform);
                            lifeformsThatReproduced.add(lifeform);
                        }
                    }
                }
            }

            index++;
        }
    }

    // uses validateReproduce(mates, space), where mates is found by countSame(cell, 0), and space is found by calling
    // countSame(Cell, 1). if true, uses the cell's lifeform's getReproduceMoves(), then iterates over each and adds the
    // cells in the array returned by findDestinations(cell, move) to a new array.
    private Cell[] findValidReproduces(Cell cell) {
        int mates = countNeighbors(cell, 0);
        int spaces = countNeighbors(cell, 1);
        Lifeform lifeform = cell.getLifeform();
        ArrayList<Cell> validDestinations = new ArrayList<>();

        if (lifeform.validateReproduce(mates, spaces)) {
            Set<Cell> destinations = findDestinations(cell, lifeform.getReproduceMoves());
            for (Cell destination : destinations) {
                if (destination.getLifeform() == null) {
                    validDestinations.add(destination);
                }
            }
        }
        return validDestinations.toArray(new Cell[0]);
    }

    // iterates over the cells surrounding the given cell and counts each that has a lifeform of the same type
    // if the mode is 0, or each that is null otherwise.
    private int countNeighbors(Cell cell, int mode) {
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j] == cell) {
                    for (int k = -1; k < 2; k++) {
                        for (int p = -1; p < 2; p++) {
                            if (!(k == 0 && p == 0) && isWithinWorld(i + k, j + p)) {
                                Lifeform lifeform = cells[i + k][j + p].getLifeform();
                                if (mode == 0) {
                                    if (cell.getLifeform().isSame(lifeform)) {
                                        count++;
                                    }
                                } else {
                                    if (lifeform == null) {
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private boolean isWithinWorld(int row, int col) {
        return (row >= 0 && col >= 0 && row < height && col < width);
    }
}
