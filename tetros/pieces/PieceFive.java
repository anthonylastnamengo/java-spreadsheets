package sheep.games.tetros.pieces;

import sheep.games.tetros.TetrosPiece;
import sheep.sheets.CellLocation;

import java.util.List;

/**
 * Implementation of a piece of falling type 5
 */
public class PieceFive implements TetrosPiece {

    /**
     * Gets the falling type of the generated piece
     *
     * @return the falling type of the piece: 5
     */
    @Override
    public int getFallingType() {
        return 5;
    }

    /**
     * Updates the contents of the sheet for the new five piece
     *
     * @param contents The contents to be rendered in the sheet
     */
    @Override
    public void updatePieceContents(List<CellLocation> contents) {
        contents.add(new CellLocation(0, 1));
        contents.add(new CellLocation(1, 1));
        contents.add(new CellLocation(2, 1));
        contents.add(new CellLocation(2, 0));
    }
}
