package sheep.games.tetros;

import sheep.sheets.CellLocation;

import java.util.List;

public interface TetrosPiece {
    int getFallingType();

    void getPieceContents(List<CellLocation> contents);
}
