package sheep.games.tetros;

import sheep.games.random.RandomTile;
import sheep.games.snake.GameState;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the dropping and movement of Tetros pieces within the game.
 * Controls the descent of pieces, checks for stoppers and boundaries, and initiates the generation of new pieces.
 */
public class TetrosDropper {

    /** The sheet where the game is played. */
    private Sheet sheet;

    /** The state of Tetros cells on the sheet. */
    private TetrosCellState cellState;

    /** Handles rendering of Tetros pieces on the sheet. */
    private TetrosRender renderer;

    /** Checks various conditions related to the game state and cell contents. */
    private TetrosChecker checker;

    /** Generates random Tetros pieces. */
    private RandomTile randomTile;

    /** Manages the overall game state of Tetros. */
    private TetrosGameState gameState;

    /**
     * Constructs a TetrosDropper with the specified parameters.
     *
     * @param sheet      The sheet where the game is played
     * @param cellState  The state of Tetros cells on the sheet
     * @param renderer   Handles rendering of Tetros pieces on the sheet
     * @param checker    Checks various conditions related to the game state and cell contents
     * @param randomTile Generates random Tetros pieces
     * @param gameState  Manages the overall game state of Tetros
     */
    public TetrosDropper(Sheet sheet, TetrosCellState cellState, TetrosRender renderer,
                         TetrosChecker checker, RandomTile randomTile, TetrosGameState gameState) {

        this.sheet = sheet;
        this.cellState = cellState;
        this.renderer = renderer;
        this.checker = checker;
        this.randomTile = randomTile;
        this.gameState = gameState;
    }

    /**
     * Drops the current Tetros piece by one row if possible.
     *
     * @return True if the piece encounters a stopper or reaches the bottom, false otherwise
     */
    public boolean dropTile() {
        List<CellLocation> newContents = new ArrayList<>();

        // Calculate new positions for the piece
        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow() + 1, tile.getColumn()));
        }

        // Unrender the previous position of the piece
        this.renderer.unrender();

        // Check for stoppers or boundaries in the new positions
        for (CellLocation newLoc : newContents) {
            if (this.checker.isStopper(newLoc)) {
                // If a stopper is encountered, re-render the previous position and return true
                this.renderer.render(this.cellState.getContents());
                return true;
            }
        }

        // Update the cell state with the new positions and render them
        this.cellState.setContents(newContents);
        this.renderer.render(this.cellState.getContents());
        return false;
    }

    /**
     * Continuously drops the Tetros piece until it encounters a stopper or reaches the bottom.
     */
    public void fullDrop() {
        while (!dropTile()) {
            // Keep dropping the piece until it encounters a stopper
        }
    }

    /**
     * Drops the Tetros piece to the bottom of the sheet and generates a new piece.
     *
     * @return True if the new piece encounters a stopper, false otherwise
     */
    public boolean drop() {
        // Reset the cell state to remove the previous piece
        this.cellState.resetContents();

        // Generate a new piece
        newPiece();

        // Check if the new piece immediately encounters a stopper
        for (CellLocation location : this.cellState.getContents()) {
            if (!sheet.valueAt(location).render().equals("")) {
                return true;
            }
        }

        // Render the new piece
        this.renderer.render(this.cellState.getContents());
        return false;
    }

    /**
     * Generates a new Tetros piece and updates the cell state.
     */
    public void newPiece() {
        // Generate a new random piece value
        int tilePicker = randomTile.pick();

        // Generate a new piece
        TetrosPieceFactory newFallingPiece = new TetrosPieceFactory(tilePicker,
                this.cellState,
                this.gameState);
        newFallingPiece.generatePiece();
    }
}

