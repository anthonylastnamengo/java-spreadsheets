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

/**
 * Represents the Game of Life simulation.
 * Implements the Feature and Tick interfaces for integration with the UI.
 */
public class Life implements Feature, Tick {

    /** The sheet used to simulate the Game of Life. */
    private final Sheet sheet;

    /** Stores whether the Game of Life simulation has started or not. */
    private boolean started = false;

    /**
     * Registers the features required for the Game of Life simulation.
     *
     * @param ui An instance of the UI class used to register features.
     */
    @Override
    public void register(UI ui) {
        ui.onTick(this);
        // Feature to start the Game of Life simulation
        ui.addFeature("gol-start", "Start Game of Life", new LifeStart());
        // Feature to stop the Game of Life simulation
        ui.addFeature("gol-end", "Stop Game of Life", new LifeEnd());
    }

    /**
     * Represents the action of starting the Game of Life simulation.
     */
    public class LifeStart implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = true;
            onTick(prompt);
        }
    }

    /**
     * Represents the action of stopping the Game of Life simulation.
     */
    public class LifeEnd implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = false;
        }
    }

    /**
     * Constructs a Game of Life simulation with the specified sheet.
     *
     * @param sheet The sheet to use for the simulation.
     */
    public Life(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Runs the Game of Life simulation on each tick.
     *
     * @param prompt The prompt to interact with the user interface.
     * @return True if the simulation continues, false otherwise.
     */
    @Override
    public boolean onTick(Prompt prompt) {
        if (!started) {
            return false;
        }
        simulateLife(this.sheet);
        return true;
    }

    /**
     * Simulates the Game of Life on the provided sheet.
     *
     * @param sheet The sheet representing the current state of the simulation.
     */
    public void simulateLife(Sheet sheet) {
        List<CellLocation> nextOns = new ArrayList<>();

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

    /**
     * Decides if a cell is on in the next tick or not
     *
     * @param cell the cell to determine next state
     *
     * @return true if the cell is on in the next tick, false if otherwise
     */
    public boolean isOnNext(CellLocation cell) {

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
            // Any off cell with exactly three on neighbours turns on
            return edgeSum == 3;
        }

        // Otherwise cell turns off next state
        return false;
    }


    /**
     * Obtain the sum of neighbour values around a cell
     *
     * @param cell The cell to find the edge sum of
     *
     * @return  The edge sum of the cell input
     */
    public int getEdgeSum(CellLocation cell) {
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
                if (neighborRow < 0 || neighborRow >= sheet.getRows()
                        || neighborCol < 0 || neighborCol >= sheet.getColumns()) {
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

    /**
     * Renders the next cells to be turned on
     * 
     * @param nextOns The list of cells to be turned on in the next tick
     */
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


