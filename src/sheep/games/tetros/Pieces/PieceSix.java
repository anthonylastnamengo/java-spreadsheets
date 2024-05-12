package sheep.games.tetros.Pieces;

import sheep.games.tetros.TetrosPiece;
import sheep.sheets.CellLocation;

import java.util.List;

public class PieceSix implements TetrosPiece {
    @Override
    public int getFallingType() {
        return 6;
    }

    @Override
    public void getPieceContents(List<CellLocation> contents) {
        contents.add(new CellLocation(0, 0));
        contents.add(new CellLocation(1, 0));
        contents.add(new CellLocation(2, 0));
        contents.add(new CellLocation(3, 0));
    }
}
