package sheep.games.snake;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.HashSet;
import java.util.Objects;

/**
 * The class provides methods to check whether the game is over in the Snake game.
 * It checks for conditions such as loss and win based on the current state of the game.
 */
public class CheckGameOver {

    /** The sheet representing the game board. */
    private final Sheet sheet;

    /** The factory for creating and manipulating snake objects. */
    private final SnakeFactory snakeFactory;

    /** The state of cells in the game board. */
    private final SnakeCellState cellState;

    /**
     * Constructs a new checker object with the specified sheet, snake factory, and cell state.
     *
     * @param sheet The game board sheet.
     * @param snakeFactory The factory for creating and manipulating snake objects.
     * @param cellState The state of cells in the game board.
     */
    public CheckGameOver(Sheet sheet, SnakeFactory snakeFactory, SnakeCellState cellState) {
        this.sheet = sheet;
        this.snakeFactory = snakeFactory;
        this.cellState = cellState;
    }

    /**
     * Checks whether the game is lost.
     *
     * @return true if the game is lost, false otherwise.
     */
    public boolean checkLoss() {
        // Check if the snake head is out of bounds, or if the snake has hit itself
        return !this.sheet.contains(this.snakeFactory.getSnakeHead()) || hitItself();
    }

    /**
     * Checks whether the game is won.
     *
     * @return true if the game is won, false otherwise.
     */
    public boolean checkWin() {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                CellLocation cell = new CellLocation(row, column);

                // If there is no longer space for the snake to move, then the game has been won.
                if (Objects.equals(this.sheet.valueAt(cell).render(), "")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the snake has hit itself.
     *
     * @return true if the snake has hit itself, false otherwise.
     */
    private boolean hitItself() {
        HashSet<CellLocation> seen = new HashSet<>();
        for (CellLocation location : this.cellState.getNextSnakeCells()) {

            // Checks whether the snake state list contains duplicates
            if (seen.contains(location)) {
                return true;
            }
            seen.add(location);
        }

        return false;
    }
}
