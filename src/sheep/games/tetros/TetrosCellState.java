package sheep.games.tetros;

import sheep.sheets.CellLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of Tetros cells on the sheet.
 * Keeps track of the locations of Tetros cells to be rendered.
 */
public class TetrosCellState {

    /** The contents of the sheet, storing the locations of Tetros cells to be rendered. */
    private List<CellLocation> contents = new ArrayList<>();

    /**
     * Retrieves the list of cell locations representing Tetros cells.
     *
     * @return The list of cell locations representing Tetros cells
     */
    public List<CellLocation> getContents() {
        return contents;
    }

    /**
     * Sets the list of cell locations representing Tetros cells.
     *
     * @param contents The list of cell locations representing Tetros cells
     */
    public void setContents(List<CellLocation> contents) {
        this.contents = contents;
    }

    /**
     * Resets the list of Tetros cell locations to an empty list.
     */
    public void resetContents() {
        this.contents = new ArrayList<>();
    }
}
