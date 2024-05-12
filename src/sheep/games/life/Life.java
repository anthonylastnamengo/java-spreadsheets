package sheep.games.life;

import sheep.expression.Expression;
import sheep.features.Feature;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Prompt;
import sheep.ui.Tick;
import sheep.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Life implements Feature, Tick {

    private final Sheet sheet;
    private boolean started = false;

    @Override
    public void register(UI ui) {
        ui.onTick(this);
        // Feature to start game of life simulation: gol-start
        ui.addFeature("gol-start", "Start Game of Life", new LifeStart());
        // Feature to stop simulation: gol-end
        ui.addFeature("gol-end", "Stop Game of Life", new LifeEnd());
    }

    public class LifeStart implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = true;
            onTick(prompt);
        }
    }

    public class LifeEnd implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = false;
        }
    }


    public Life(Sheet sheet) {
        this.sheet = sheet;
    }

    // Appropriate tick callback to be registered
    // When simulation is running, sheet is updated according to rules
    @Override
    public boolean onTick(Prompt prompt) {
        if (!started) {
            return false;
        }
        // Starts sim
        simulateLife(this.sheet);
        return true;
    }

    public void simulateLife(Sheet sheet) {

        List<CellLocation> nextOns = new ArrayList<>();
        // SCRATCH:
        // use this to control game of life flow
        // refresh and render the sheet here?

        // Iterate through each cell in the sheet
        // For each cell, check whether the cell is On in the next state (isOnNext)
        // if isOnNext returns true, add said CellLocation to a List

        // if isOnNext returns false, skip over

        // on tick (i.e. each time simulate life is called), update said cells to 1
        // any CellLocations not in the List will be updated to a Nothing instance?
        // (Or just an empty string see how it goes)

        // END SCRATCH

        // Iterate through each cell in the sheet:
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                // Access the cell at the current row and column
                CellLocation location = new CellLocation(row, column);

                // If the cell will be on in the next state
                // Add to the nextOns list
                if (isOnNext(location)) {
                    nextOns.add(location);
                }
            }
        }

        // Render the next state of the sheets
        renderNext(nextOns);
    }

    public boolean isOnNext(CellLocation cell) {
        // SCRATCH:
        // Given a certain cell, check the surrounding cells with edgeSum
        // Thus return a boolean whether the cell will be on in the next tick or not
        // If cell == "1":
            // Any on cell with fewer than two on neighbours turns off
            // Any on cell with two or three on neighbours stays on
            // Any on cell with more than three on neighbours turns off
        // If cell != "1":
            // Any off cell with exactly three on neighbours turns on, otherwise it stays off

        // END SCRATCH

        Expression cellValue = sheet.valueAt(cell);
        int edgeSum = getEdgeSum(cell);

        if (Objects.equals(cellValue.render(), "1")) {
            // Apply rules for "ON" cells

            if (edgeSum == 2 || edgeSum == 3) {
                // Any on cell with two or three on neighbours stays on
                return true;
            }
        }

        if (!Objects.equals(cellValue.render(), "1")) {
            // Apply rules for "OFF" cells
            if (edgeSum == 3) {
                // Any off cell with exactly three on neighbours turns on
                return true;
            }
        }

        // Otherwise cell turns off next state
        return false;
    }


    public int getEdgeSum(CellLocation cell) {
        // SCRATCH:
        // Calculate the edge sum of a specific cell
        // Returns the edge sum of that cell
        // Aims to help isOnNext

        // Extract the value of the cell
        Expression cellValue = sheet.valueAt(cell);

        // Extract the row and column of the cell
        int cellRow = cell.getRow();
        int cellCol = cell.getColumn();

        // Initialise a sum of the edge values to determine next state
        int edgeSum = 0;

        // Iterate through all neighbors
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // Calculate the row and column of the neighbor
                int neighborRow = cellRow + i;
                int neighborCol = cellCol + j;

                // Check if the neighbor is within the bounds of the sheet
                if (neighborRow < 0 || neighborRow >= sheet.getRows() ||
                        neighborCol < 0 || neighborCol >= sheet.getColumns()) {
                    // Neighbor is out of bounds, so skip to the next iteration
                    continue;
                }

                // For each neighbor, extract the value of the cell
                CellLocation neighbourLocation = new CellLocation(neighborRow, neighborCol);
                String neighbourValue = sheet.valueAt(neighbourLocation).render();

                // If the cell is on, add 1 to the edge sum
                if (Objects.equals(neighbourValue, "1")) {
                    edgeSum += 1;
                }
            }
        }

        // If the value of the cell is on, subtract one from the edge sum.

        if (Objects.equals(cellValue.render(), "1")) {
            edgeSum -= 1;
        }

        return edgeSum;
    }

    public void renderNext(List<CellLocation> nextOns) {
        // Iterate through each cell in the sheet
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                // Access the cell at the current row and column
                CellLocation location = new CellLocation(row, column);

                // If the cell is in the nextOns list, set its value to "1" (on), else set it to ""
                if (nextOns.contains(location)) {
                    sheet.update(row, column, "1");
                } else {
                    sheet.update(row, column, "");
                }
            }
        }
    }

}


