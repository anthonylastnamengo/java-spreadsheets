package sheep.games.tetros;

import sheep.games.snake.GameState;
import sheep.sheets.CellLocation;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a movement action in the game of Tetros.
 * Performs the movement based on the specified direction.
 */
public class TetrosMove implements Perform {

    /** The direction of the movement: -1 for left, 1 for right, 2 for full drop. */
    private final int direction;

    /** Manages the game state of Tetros. */
    private TetrosGameState gameState;

    /** Manages the state of the cells in the Tetros game grid. */
    private TetrosCellState cellState;

    /** Handles the dropping of Tetros pieces. */
    private TetrosDropper dropper;

    /** Renders the Tetros game grid. */
    private TetrosRender renderer;

    /** Checks the validity of Tetros game actions. */
    private TetrosChecker checker;

    /**
     * Initializes a new TetrosMove instance with the specified parameters.
     *
     * @param direction The direction of the movement
     * @param gameState Manages the game state of Tetros
     * @param cellState Manages the state of the cells in the Tetros game grid
     * @param dropper Handles the dropping of Tetros pieces
     * @param renderer Renders the Tetros game grid
     * @param checker Checks the validity of Tetros game actions
     */
    public TetrosMove(int direction, TetrosGameState gameState, TetrosCellState cellState,
                      TetrosDropper dropper, TetrosRender renderer, TetrosChecker checker) {
        this.direction = direction;
        this.gameState = gameState;
        this.cellState = cellState;
        this.dropper = dropper;
        this.renderer = renderer;
        this.checker = checker;
    }

    /**
     * Performs the movement action in the Tetros game.
     *
     * @param row     The row position (unused)
     * @param column  The column position (unused)
     * @param prompt  Provides a mechanism to interact with the user interface after the movement
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        if (!gameState.isStarted()) {
            return;
        }
        shift(direction);
    }

    /**
     * Shifts the Tetros piece horizontally based on the specified direction.
     *
     * @param x The direction of the movement: -1 for left, 1 for right, 2 for full drop
     */
    public void shift(int x) {
        // Full drop if x is 2
        if (x == 2) {
            this.dropper.fullDrop();
            return;
        }

        // Initialise a new contents list
        List<CellLocation> newContents = new ArrayList<>();

        // In the new contents list, shift each cell of the falling piece by x
        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow(), tile.getColumn() + x));
        }

        // Render the new contents if in bounds
        if (this.checker.inBounds(newContents)) {
            this.renderer.unrender();
            this.cellState.setContents(newContents);
            this.renderer.render(this.cellState.getContents());
        }
    }
}
