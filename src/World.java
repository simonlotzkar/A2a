public class World {
    private Cell[][] cells;
    private int width;
    private int height;
    private RandomGenerator randomGenerator;

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
        int number = randomGenerator.nextNumber(100);
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

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells1D[i*width+j] = cells[i][j];
            }
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

    //iterates through each cell times times and if it has a lifeform, creates an array using findValidMoves(cell).
    // then, uses RandomGenerator to pick a random cell in the array of valid destinations. then calls the original
    // cell lifeform's eat() function with the chosen destination's contents. then calls fill() on the destination
    // cell using the lifeform in the original cell. then calls clear() on the original cell.
    public void moveAll(int times) {
        // stub
    }

    //uses the cell's lifeform's getMoves(), then iterates over each and adds the cells in the array returned by
    // findDestinations(cell, cell, move). next, if any of those cells contains a lifeform, they must also pass the
    // original lifeform's validateEdible(lifeform).
    private Cell[] findValidMoves(Cell cell) {
        return null; //stub
    }

    //finds each cell that is (x+/-n or y+/-n) away from the current cell, where n is the first entry in a move's
    // array; then if the array contains another entry it moves in the opposite dimension that it did before.
    private Cell[] findDestinations(Cell origin, Cell terminus, Move move) {
        return null; //stub
    }

    // iterates through each cell times times and if it has a lifeform, creates an array using
    // findValidReproduces(cell). then, uses RandomGenerator to pick a random cell in the array
    // of valid destinations. then calls the original cell lifeform's eat() function with the
    // chosen destination's contents. then calls fill() on the destination cell using the
    // lifeform in the original cell. then calls clear() on the original cell.
    public void reproduceAll(int times) {
        // stub
    }

    // uses validateReproduce(mates, space), where mates is found by countSame(cell), and space is found by calling
    // countSame(null). if true, uses the cell's lifeform's getReproduceMoves(), then iterates over each and adds the
    // cells in the array returned by findDestinations(cell, cell, move) to a new array. finally, uses RandomGenerator
    // to pick a random cell in that array. then calls fill(lifeform) on that cell.
    private void reproduce(Cell cell) {
        //stub
    }

    // iterates over the cells surrounding the given cell and counts each that has a lifeform of the same type.
    private int countSameNeighbors(Cell cell) {
        return 0; //stub
    }
}
