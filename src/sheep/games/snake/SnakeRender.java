package sheep.games.snake;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;

/**
 * Handles the rendering for the Snake game in the sheet.
 */
public class SnakeRender {

    /** Stores the sheet to render the game */
    private final Sheet sheet;

    /** Stores the cell state for rendering information */
    private final SnakeCellState cellState;

    /**
     * Constructs a new snake game renderer
     *
     * @param sheet         The sheet in which the game will be rendered
     * @param cellState     The apple and snake states to render
     */
    public SnakeRender(Sheet sheet, SnakeCellState cellState) {
        this.sheet = sheet;
        this.cellState = cellState;
    }

    /**
     * Renders a new snake state
     *
     * @param nextSnakeCells A list containing the snake cell locations to render in the sheet.
     */
    public void snakeRender(List<CellLocation> nextSnakeCells) {
        // Iterate over all cells in the sheet
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                // Create a location object for the current cell
                CellLocation location = new CellLocation(row, column);

                // Check if the current cell is in the list of snake cells
                if (nextSnakeCells.contains(location)) {
                    // If the cell is part of the snake, update its value to represent the snake
                    sheet.update(row, column, "1");
                }
            }
        }
    }

    /**
     * Renders the apples for the snake game
     *
     * @param appleCells The list of apple locations to render in the sheet
     */
    public void appleRender(List<CellLocation> appleCells) {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {

                // Create a location object for the current cell
                CellLocation location = new CellLocation(row, column);

                // Check if the current cell is in the list of apple cells
                if (appleCells.contains(location)) {
                    // If the cell is an apple, update its value to represent the apple
                    sheet.update(row, column, "2");
                }
            }
        }
    }

    /**
     * Renders the overall Snake game
     */
    public void playSnake() {
        // Clear the sheet before rendering the snake and apple
        sheet.clear();

        // Render the apples
        appleRender(this.cellState.getAppleCells());

        // Render the snake
        snakeRender(this.cellState.getNextSnakeCells());
    }
}
