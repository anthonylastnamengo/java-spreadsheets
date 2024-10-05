package sheep.games.tetros;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;

import java.util.List;

public class CheckGame {

    private Sheet sheet;

    public CheckGame(Sheet sheet) {
        this.sheet = sheet;
    }

    public boolean isStopper(CellLocation location) {
        if (location.getRow() >= sheet.getRows()) {
            return true;
        }
        if (location.getColumn() >= sheet.getColumns()) {
            return true;
        }
        return !sheet.valueAt(location.getRow(), location.getColumn()).getContent().equals("");
    }

    public boolean inBounds(List<CellLocation> locations) {
        for (CellLocation location : locations) {
            if (!sheet.contains(location)) {
                return false;
            }
        }
        return true;
    }
}

