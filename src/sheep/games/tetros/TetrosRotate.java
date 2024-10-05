package sheep.games.tetros;

import sheep.sheets.CellLocation;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the rotation of Tetros pieces.
 */
public class TetrosRotate implements Perform {

    /** The direction of rotation (clockwise or counterclockwise). */
    private final int direction;

    /** Manages the game state of Tetros. */
    private TetrosGameState gameState;

    /** Manages the state of the cells in the Tetros game grid. */
    private TetrosCellState cellState;

    /** Renders the Tetros game grid. */
    private TetrosRender renderer;

    /** Checks for valid movements and rotations in the Tetros game grid. */
    private TetrosChecker checker;

    /**
     * Initializes a new TetrosRotate instance with the specified parameters.
     *
     * @param direction The direction of rotation (clockwise or counterclockwise)
     * @param gameState Manages the game state of Tetros
     * @param cellState Manages the state of the cells in the Tetros game grid
     * @param renderer  Renders the Tetros game grid
     * @param checker   Checks for valid movements and rotations in the Tetros game grid
     */
    public TetrosRotate(int direction, TetrosGameState gameState, TetrosCellState cellState,
                        TetrosRender renderer, TetrosChecker checker) {
        this.direction = direction;
        this.gameState = gameState;
        this.cellState = cellState;
        this.renderer = renderer;
        this.checker = checker;
    }

    /**
     * Performs the rotation of Tetros pieces.
     *
     * @param row    The row position of the Tetros piece
     * @param column The column position of the Tetros piece
     * @param prompt Provides a mechanism to interact with the user interface after rotation, if required
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        if (!gameState.isStarted()) {
            return;
        }
        flip(direction);
    }

    /**
     * Flips the Tetros piece based on the specified direction.
     *
     * @param direction The direction of rotation (clockwise or counterclockwise)
     */
    private void flip(int direction) {
        // Calculate the centroid of the Tetros piece
        int x = 0;
        int y = 0;
        for (CellLocation cellLocation : this.cellState.getContents()) {
            x += cellLocation.getColumn();
            y += cellLocation.getRow();
        }
        x /= this.cellState.getContents().size();
        y /= this.cellState.getContents().size();

        // Create a new list to store the updated cell locations after rotation
        List<CellLocation> newCells = new ArrayList<>();

        // Apply rotation to each cell of the Tetros piece
        for (CellLocation location : this.cellState.getContents()) {
            // Calculate the new coordinates after rotation
            int lx = x + ((y - location.getRow()) * direction);
            int ly = y + ((x - location.getColumn()) * direction);

            // Create a new CellLocation object with the rotated coordinates
            CellLocation replacement = new CellLocation(ly, lx);
            // Add the rotated cell location to the new list
            newCells.add(replacement);
        }

        // Check if the new rotated cells are within the bounds of the game grid
        if (!this.checker.inBounds(newCells)) {
            return; // If out of bounds, do not perform rotation
        }

        // If rotation is valid, update the cell state with the new rotated cell locations
        this.renderer.unrender(); // Clear the previous rendering
        this.cellState.setContents(newCells); // Update the cell state with the rotated cells
        this.renderer.render(this.cellState.getContents()); // Render the updated Tetros piece
    }
}
