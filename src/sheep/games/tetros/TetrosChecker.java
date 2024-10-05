package sheep.games.tetros;

import sheep.expression.TypeError;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;

/**
 * Checks the game state and sheet contents in Tetros for various conditions.
 * Provides methods to determine if a cell is a stopper, if locations are within sheet bounds,
 * and checks for filled lines on the sheet to clear them.
 */
public class TetrosChecker {

    /** The sheet where the game is played. */
    private final Sheet sheet;

    /** The state of Tetros cells on the sheet. */
    private final TetrosCellState cellState;

    /**
     * Constructs a TetrosChecker with the specified sheet and cell state.
     *
     * @param sheet     The sheet where the game is played
     * @param cellState The state of Tetros cells on the sheet
     */
    public TetrosChecker(Sheet sheet, TetrosCellState cellState) {
        this.sheet = sheet;
        this.cellState = cellState;
    }

    /**
     * Checks if a cell is a stopper, preventing further movement or placement.
     *
     * @param location The location of the cell to be checked
     * @return True if the cell is a stopper, false otherwise
     */
    public boolean isStopper(CellLocation location) {
        if (location.getRow() >= sheet.getRows()) {
            return true;
        }
        if (location.getColumn() >= sheet.getColumns()) {
            return true;
        }

        return !sheet.valueAt(location.getRow(), location.getColumn()).getContent().isEmpty();
    }

    /**
     * Checks if a list of cell locations is within the bounds of the sheet.
     *
     * @param locations The list of cell locations to be checked
     * @return True if all locations are within the sheet bounds, false otherwise
     */
    public boolean inBounds(List<CellLocation> locations) {
        for (CellLocation location : locations) {
            if (!sheet.contains(location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for filled lines on the sheet and clears them.
     * When a line is filled, the cells above it are moved down to fill the gap.
     */
    public void checkLineFilled() {
        // Iterating through each cell
        for (int row = sheet.getRows() - 1; row >= 0; row--) {
            // Initialise the checker for a full line
            boolean full = true;

            for (int col = 0; col < sheet.getColumns(); col++) {
                // If there is an empty cell anywhere in the row, the line is not full
                if (sheet.valueAt(row, col).getContent().isEmpty()) {
                    full = false;
                }
            }

            // If the row is checked to be full
            if (full) {
                for (int rowX = row; rowX > 0; rowX--) {
                    // iterating through each cell in the row and column selected
                    for (int col = 0; col < sheet.getColumns(); col++) {

                        // Try shifting every cell above the row downward
                        try {
                            if (this.cellState.getContents().contains(
                                    new CellLocation(rowX - 1, col))) {
                                continue;
                            }
                            sheet.update(new CellLocation(rowX, col),
                                    sheet.valueAt(new CellLocation(rowX - 1, col)));

                        } catch (TypeError e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Move onto the next row to continue filling in the space
                row += 1;
            }
        }
    }
}
