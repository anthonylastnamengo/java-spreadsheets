package sheep.games.tetros;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.ArrayList;
import java.util.List;

public class TetrosDropper {

    private TetrosCellState cellState;
    private TetrosRender renderer;
    private NewPiece newPieceSpawner;
    private Sheet sheet;
    private CheckGame checker;

    public TetrosDropper(TetrosCellState cellState, TetrosRender renderer, NewPiece newPieceSpawner, Sheet sheet, CheckGame checker) {
        this.cellState = cellState;
        this.renderer = renderer;
        this.newPieceSpawner = newPieceSpawner;
        this.sheet = sheet;
        this.checker = checker;
    }

    public boolean dropTile() {
        List<CellLocation> newContents = new ArrayList<>();

        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow() + 1, tile.getColumn()));
        }

        this.renderer.unrender();

        for (CellLocation newLoc : newContents) {
            if (this.checker.isStopper(newLoc)) {
                this.renderer.render(this.cellState.getContents());
                return true;
            }
        }

        this.cellState.setContents(newContents);
        this.renderer.render(this.cellState.getContents());
        return false;
    }

    public void fullDrop() {
        while (!dropTile()) {

        }
    }

    public boolean drop() {
        this.cellState.resetContents();

        this.newPieceSpawner.newPiece();
        for (CellLocation location : this.cellState.getContents()) {
            if (!this.sheet.valueAt(location).render().equals("")) {
                return true;
            }
        }

        this.renderer.render(this.cellState.getContents());

        return false;
    }
}
