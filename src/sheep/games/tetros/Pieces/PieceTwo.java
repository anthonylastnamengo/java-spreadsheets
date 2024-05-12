package sheep.games.tetros.Pieces;

import sheep.games.tetros.TetrosPiece;
import sheep.sheets.CellLocation;

import java.util.List;

public class PieceTwo implements TetrosPiece {
    @Override
    public int getFallingType() {
        return 2;
    }

    @Override
    public void getPieceContents(List<CellLocation> contents) {
        contents.add(new CellLocation(0, 1));
        contents.add(new CellLocation(0, 2));
        contents.add(new CellLocation(1, 1));
        contents.add(new CellLocation(0, 1));
    }
}
