package sheep.games.snake;

import sheep.sheets.CellLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents the state of cells in the Snake game.
 * It maintains lists of cell locations for the next snake cells, old snake cells, and apple cells.
 * This class provides methods to manipulate these cell lists and retrieve their contents.
 */

public class SnakeCellState {

    /** The list of cell locations for the next snake cells. */
    private List<CellLocation> nextSnakeCells = new ArrayList<>();

    /** The list of cell locations for the old snake cells. */
    private List<CellLocation> oldSnakeCells = new ArrayList<>();

    /** The list of cell locations for the apple cells. */
    private List<CellLocation> appleCells = new ArrayList<>();

    /**
     * Returns the list of cell locations for the next snake cells.
     *
     * @return The list of cell locations for the next snake cells.
     */
    public List<CellLocation> getNextSnakeCells() {
        return nextSnakeCells;
    }

    /**
     * Adds a cell location to the list of next snake cells.
     *
     * @param location The cell location to add.
     */
    public void nextSnakeCellsAdd(CellLocation location) {
        this.nextSnakeCells.add(location);
    }

    /**
     * Resets the list of next snake cells to an empty list.
     */
    public void resetNextSnakeCells() {
        this.nextSnakeCells = new ArrayList<>();
    }

    /**
     * Returns the list of cell locations for the apple cells.
     *
     * @return The list of cell locations for the apple cells.
     */
    public List<CellLocation> getAppleCells() {
        return appleCells;
    }

    /**
     * Adds a cell location to the list of apple cells.
     *
     * @param location The cell location to add.
     */
    public void appleCellsAdd(CellLocation location) {
        this.appleCells.add(location);
    }

    /**
     * Resets the list of apple cells to an empty list.
     */
    public void resetAppleCells() {
        this.appleCells = new ArrayList<>();
    }

    /**
     * Removes a cell location from the list of apple cells.
     *
     * @param location The cell location to remove.
     */
    public void appleCellsRemove(CellLocation location) {
        this.appleCells.remove(location);
    }

    /**
     * Adds all cell locations from another list to a specified list.
     *
     * @param sumCells The list to which the cell locations will be added.
     * @param addingCells The list of cell locations to add.
     */
    public void addAll(List<CellLocation> sumCells, List<CellLocation> addingCells) {
        sumCells.addAll(addingCells);
    }

    /**
     * Returns the list of cell locations for the old snake cells.
     *
     * @return The list of cell locations for the old snake cells.
     */
    public List<CellLocation> getOldSnakeCells() {
        return oldSnakeCells;
    }

    /**
     * Adds a cell location to the list of old snake cells.
     *
     * @param location The cell location to add.
     */
    public void oldSnakeCellsAdd(CellLocation location) {
        this.oldSnakeCells.add(location);
    }

    /**
     * Returns the cell location at the specified index in the list of old snake cells.
     *
     * @param i The index of the cell location to retrieve.
     * @return The cell location at the specified index.
     */
    public CellLocation oldSnakeCellsGet(int i) {
        return this.oldSnakeCells.get(i);
    }

    /**
     * Resets the list of old snake cells to an empty list.
     */
    public void resetOldSnakeCells() {
        this.oldSnakeCells = new ArrayList<>();
    }
}
