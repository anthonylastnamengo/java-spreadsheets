package sheep.games.snake;

import sheep.sheets.CellLocation;

/**
 * The class is responsible for managing the state and behavior of the snake in the game.
 */
public class SnakeFactory {

    /** The current location of the snake's head. */
    private CellLocation snakeHead;

    /** The factory responsible for managing apples in the game. */
    private final AppleFactory appleFactory;

    /** The state of the game cells. */
    private final SnakeCellState cellState;

    /** Indicates whether the snake has been updated during the game tick. */
    private boolean appleEaten = false;

    /**
     * Constructs a new Snake factory with the specified apple factory and cell state.
     *
     * @param appleFactory The factory responsible for managing apples in the game.
     * @param cellState    The state of the game cells.
     */
    public SnakeFactory(AppleFactory appleFactory, SnakeCellState cellState) {
        this.appleFactory = appleFactory;
        this.cellState = cellState;
    }

    /**
     * Gets the current location of the snake's head.
     *
     * @return The current location of the snake's head.
     */
    public CellLocation getSnakeHead() {
        return snakeHead;
    }

    /**
     * Sets the current location of the snake's head.
     *
     * @param snakeHead The new location of the snake's head.
     */
    public void setSnakeHead(CellLocation snakeHead) {
        this.snakeHead = snakeHead;
    }

    /**
     * Updates the state of the snake for the next game tick.
     *
     * @param rowShift The shift in the row direction for the snake.
     * @param colShift The shift in the column direction for the snake.
     */
    public void nextSnake(int rowShift, int colShift) {
        // First make a copy of the old snake position
        copyOld();

        // Obtain the old snake head row and column
        int headRow = this.snakeHead.getRow();
        int headCol = this.snakeHead.getColumn();

        // Obtain the new snake head location based on the input row and column shift
        CellLocation nextHead = new CellLocation(headRow + rowShift, headCol + colShift);

        // Reset the next snake cells
        this.cellState.resetNextSnakeCells();

        // Set the new snake head to be rendered
        this.snakeHead = nextHead;

        // Check apple eating logic
        if (appleFactory.containsApple(this.snakeHead)) {
            // If the snake will eat an apple on its next turn, set a new apple
            appleFactory.setApple(this.cellState.getNextSnakeCells());

            // Remove the apple the snake is about to eat from the apple state
            appleFactory.removeApple(this.snakeHead);

            // Set the switch to true to grow the snake on the next tick
            this.appleEaten = true;
        }

        // Add the snake head to the next snake state to be rendered
        this.cellState.nextSnakeCellsAdd(this.snakeHead);


        // Each following snake body segment replaces the old position of the segment before it
        for (int i = 0; i < this.cellState.getOldSnakeCells().size() - 1; i++) {
            this.cellState.nextSnakeCellsAdd(this.cellState.oldSnakeCellsGet(i));
        }
    }

    /**
     * Copies the previous state of the snake to the old snake cells list.
     * If the old snake cells list is empty, adds the snake's head to it.
     */
    private void copyOld() {
        if (this.cellState.getOldSnakeCells().isEmpty()) {
            this.cellState.oldSnakeCellsAdd(snakeHead);

        } else {
            // Clear the old snake state
            this.cellState.resetOldSnakeCells();

            // Add all the present snake cells to the old snake cells
            // This is to prepare for the next tick
            this.cellState.addAll(this.cellState.getOldSnakeCells(),
                    this.cellState.getNextSnakeCells());

            // If an apple has been eaten on the previous tick
            if (this.appleEaten) {
                // Add a new segment to the old snake cells to grow the snake
                this.cellState.oldSnakeCellsAdd(snakeHead);

                // Set the switch back to false
                this.appleEaten = false;
            }
        }
    }
}
