package sheep.games.snake;

import sheep.games.random.RandomCell;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;
import java.util.Objects;

/**
 * Responsible for the logic of apples in the sheet
 */
public class AppleFactory {

    /** The sheet to spawn the apples in */
    private final Sheet sheet;

    /** The random cell picker to pick a new apple location */
    private final RandomCell randomCell;

    /** The cell state which stores the apple state and snake state */
    private final SnakeCellState cellState;

    /**
     * Construct a new apple factory responsible for handling apple logic
     *
     * @param sheet         The sheet to play the snake game
     * @param randomCell    The random cell picker to pick a new apple location
     * @param cellState     The cell state to store the apple state and snake state
     */
    public AppleFactory(Sheet sheet, RandomCell randomCell, SnakeCellState cellState) {
        this.sheet = sheet;
        this.randomCell = randomCell;
        this.cellState = cellState;
    }

    /**
     * Searches the sheet and finds pre-existing apples, then adds them to the apple state
     */
    public void addApples() {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {

                // Search in each cell in the sheet
                CellLocation location = new CellLocation(row, column);

                // Obtain the String value of the cell
                String cellValue = this.sheet.valueAt(location).render();

                // If the cell value is not a snake or empty, it is an apple.
                if (!Objects.equals(cellValue, "1") & !Objects.equals(cellValue, "")) {
                    // Add the found apple to the apple state
                    if (!this.cellState.getAppleCells().contains(location)) {
                        this.cellState.appleCellsAdd(location);
                    }
                }
            }
        }
    }

    /**
     * Spawns a new apple
     *
     * @param nextSnakeCells    The snake cells to be rendered next
     */
    public void setApple(List<CellLocation> nextSnakeCells) {
        // Creates a new apple on the sheets.
        CellLocation newApple = randomCell.pick();

        // Game prioritises the snake over an apple
        if (nextSnakeCells.contains(newApple)
                || this.cellState.getAppleCells().contains(newApple)) {
            // If the new apple location picked is a snake, or a pre-existing apple:
            // do not update the apple state
            return;
        }

        // If apple location passes the check, add it to the apple state to be rendered.
        this.cellState.appleCellsAdd(newApple);
    }

    /**
     * Removes an apple from the apple state
     *
     * @param location  The location to remove the apple from
     */
    public void removeApple(CellLocation location) {
        // Update the cells states to remove the apple
        this.cellState.appleCellsRemove(location);
    }

    /**
     * Checks if a cell holds an apple or not
     *
     * @param cell  The cell to check if an apple exists
     *
     * @return  A boolean indicating whether the apple exists in the cell or not
     */
    public boolean containsApple(CellLocation cell) {
        return this.cellState.getAppleCells().contains(cell);
    }

}

