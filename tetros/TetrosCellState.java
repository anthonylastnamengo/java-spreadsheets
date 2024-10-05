package sheep.games.tetros;

import sheep.sheets.CellLocation;

import java.util.ArrayList;
import java.util.List;

public class TetrosCellState {

    /** The contents of the sheet, stores the Tetros cells to be rendered. */
    private List<CellLocation> contents = new ArrayList<>();

    public List<CellLocation> getContents() {
        return contents;
    }

    public void contentsAdd(CellLocation location) {
        this.contents.add(location);
    }

    public void contentsRemove(CellLocation location) {
        this.contents.remove(location);
    }


    public void setContents(List<CellLocation> contents) {
        this.contents = contents;
    }

    public void resetContents() {
        this.contents = new ArrayList<>();
    }
}
