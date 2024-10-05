package sheep.games.tetros;

import sheep.sheets.CellLocation;

import java.util.List;

/**
 * An interface detailing what a piece should do
 */
public interface TetrosPiece {

    /**
     * Gets the falling type of the generated piece
     *
     * @return the falling type of the new piece
     */
    int getFallingType();


    /**
     * Updates the contents of the sheet for the new piece
     *
     * @param contents The contents to be rendered in the sheet
     */
    void updatePieceContents(List<CellLocation> contents);
}
