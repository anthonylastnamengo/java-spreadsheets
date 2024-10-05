package sheep.games.tetros;

import sheep.sheets.CellLocation;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.util.ArrayList;
import java.util.List;

public class TetrosMove implements Perform {
    private final int direction;
    private TetrosGameState gameState;
    private TetrosCellState cellState;
    private TetrosRender renderer;
    private TetrosDropper dropper;
    private CheckGame checker;

    public TetrosMove(int direction, TetrosGameState gameState, TetrosCellState cellState, TetrosRender renderer, TetrosDropper dropper, CheckGame checker) {
        this.direction = direction;
        this.gameState = gameState;
        this.cellState = cellState;
        this.renderer = renderer;
        this.dropper = dropper;
        this.checker = checker;
    }

    @Override
    public void perform(int row, int column, Prompt prompt) {
        if (!gameState.isStarted()) {
            return;
        }
        shift(direction);
    }

    public void shift(int x) {
        if (x == 2) {
            this.dropper.fullDrop();
            return;
        }
        List<CellLocation> newContents = new ArrayList<>();
        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow(), tile.getColumn() + x));
        }
        if (this.checker.inBounds(newContents)) {
            this.renderer.unrender();
            this.cellState.setContents(newContents);
            this.renderer.render(this.cellState.getContents());
        }
    }
}
