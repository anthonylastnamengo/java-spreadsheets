package sheep.games.tetros;

import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;
import sheep.games.snake.GameState;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;

public class TetrosRender {
    private Sheet sheet;

    private TetrosGameState gameState;
    private TetrosCellState cellState;

    public TetrosRender(Sheet sheet, TetrosGameState gameState, TetrosCellState cellState) {
        this.sheet = sheet;
        this.gameState = gameState;
        this.cellState = cellState;
    }

    public void unrender() {
        for (CellLocation cell : this.cellState.getContents()) {
            try {
                this.sheet.update(cell, new Nothing());
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

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
