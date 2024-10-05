package sheep.games.tetros.pieces;

import sheep.games.tetros.TetrosPiece;
import sheep.sheets.CellLocation;

import java.util.List;

/**
 * Implementation of a piece of falling type 3
 */
public class PieceThree implements TetrosPiece {

    /**
     * Gets the falling type of the generated piece
     *
     * @return the falling type of the piece: 3
     */
    @Override
    public int getFallingType() {
        return 3;
    }

    /**
     * Updates the contents of the sheet for the new three piece
     *
     * @param contents The contents to be rendered in the sheet
     */
    @Override
    public void updatePieceContents(List<CellLocation> contents) {
        contents.add(new CellLocation(0, 0));
        contents.add(new CellLocation(0, 1));
        contents.add(new CellLocation(1, 0));
        contents.add(new CellLocation(1, 1));
    }
}
