package sheep.games.tetros.Pieces;

import sheep.games.tetros.TetrosPiece;
import sheep.sheets.CellLocation;

import java.util.List;

public class PieceFive implements TetrosPiece {
    @Override
    public int getFallingType() {
        return 5;
    }

    @Override
    public void getPieceContents(List<CellLocation> contents) {
        contents.add(new CellLocation(0, 1));
        contents.add(new CellLocation(1, 1));
        contents.add(new CellLocation(2, 1));
        contents.add(new CellLocation(2, 0));
    }
}
