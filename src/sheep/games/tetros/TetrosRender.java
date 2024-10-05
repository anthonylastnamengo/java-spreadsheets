package sheep.games.tetros;

import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;
import sheep.games.snake.GameState;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;

/**
 * Handles the rendering of Tetros game grid.
 */
public class TetrosRender {

    /** The sheet representing the Tetros game grid. */
    private Sheet sheet;

    /** Manages the game state of Tetros. */
    private TetrosGameState gameState;

    /** Manages the state of the cells in the Tetros game grid. */
    private TetrosCellState cellState;

    /**
     * Initializes a new TetrosRender instance with the specified parameters.
     *
     * @param sheet     The sheet representing the Tetros game grid
     * @param gameState Manages the game state of Tetros
     * @param cellState Manages the state of the cells in the Tetros game grid
     */
    public TetrosRender(Sheet sheet, TetrosGameState gameState, TetrosCellState cellState) {
        this.sheet = sheet;
        this.gameState = gameState;
        this.cellState = cellState;
    }

    /**
     * Clears the rendered Tetros falling piece from the sheet.
     */
    public void unrender() {
        for (CellLocation cell : this.cellState.getContents()) {
            try {
                this.sheet.update(cell, new Nothing());
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Renders the specified items on the Tetros game grid.
     *
     * @param items The list of cell locations representing the Tetros piece to be rendered
     */
    public void render(List<CellLocation> items) {
        for (CellLocation cell : items) {
            try {
                sheet.update(cell, new Constant(this.gameState.getFallingType()));
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }
}
