package sheep.games.tetros;

import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;
import sheep.features.Feature;
import sheep.games.random.RandomTile;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.*;

import java.util.*;

/**
 * A version of the game Tetris: Tetros.
 * Holds and runs the functionality of Tetros.
 */
public class Tetros implements Tick, Feature {

    /** The sheet to run Tetros */
    private final Sheet sheet;

    /** "Switch" to indicate whether Tetros is currently running or not */
    private boolean started = false;

    /** A falling type that indicates which piece is currently falling */
    private int fallingType = 1;

    /** The contents of the sheet, stores the Tetros cells to be rendered. */
    private List<CellLocation> contents = new ArrayList<>();

    /** A random tile that is used to generate a new random piece */
    public final RandomTile randomTile;


    /**
     * Initialises and stores the sheet and tile randomizer to be used for Tetros.
     *
     * @param sheet         The sheet to play Tetros in
     * @param randomTile    The random tile generator to generate new pieces.
     */
    public Tetros(Sheet sheet, RandomTile randomTile) {
        this.sheet = sheet;
        this.randomTile = randomTile;
    }


    /**
     * Registers the features which allow Tetros to start.
     * Also registers handling of key presses in the Sheet for the game functionality.
     *
     * @param ui    An instance of the UI class which allows the sheets UI to register features.
     */
    @Override
    public void register(UI ui) {
        // Initialises an action listener on each tick.
        ui.onTick(this);

        // Adds a feature that allows the user to start a game of Tetros
        ui.addFeature("tetros", "Start Tetros", new GameStart());

        // Records specific keys to actions in the Tetros implementation
        ui.onKey("a", "Move Left", this.getMove(-1));
        ui.onKey("d", "Move Right", this.getMove(1));
        ui.onKey("q", "Rotate Left", this.getRotate(-1));
        ui.onKey("e", "Rotate Right", this.getRotate(1));
        ui.onKey("s", "Drop", this.getMove(2));
    }


    /**
     * Holds the game functionality that will be called on each tick of the game starting.
     *
     * @param prompt Provide a mechanism to interact with the user interface
     *               after a tick occurs, if required.
     * @return A boolean that indicates if the program should continue ticking.
     */
    @Override
    public boolean onTick(Prompt prompt) {
        // If the game has not started don't tick.
        if (!started) {
            return false;
        }

        // Listen for a losing condition
        if (this.dropTile()) {
            // If the game detects a piece has gone over the top row, stop the game
            if (drop()) {

                // Display a message to the user
                prompt.message("Game Over!");
                // Stop the game
                started = false;
            }
        }
        // If no losing condition is found, check if a user has a fill a row with pieces
        checkLineFilled();

        return true;
    }


    private boolean isStopper(CellLocation location) {
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

    public boolean dropTile() {
        List<CellLocation> newContents = new ArrayList<>();

        for (CellLocation tile : contents) {
            newContents.add(new CellLocation(tile.getRow() + 1, tile.getColumn()));
        }
        unrender();

        for (CellLocation newLoc : newContents) {
            if (isStopper(newLoc)) {
                ununrender(contents);
                return true;
            }
        }

        ununrender(newContents);
        this.contents = newContents;
        return false;
    }

    public void fullDrop() {
        while (!dropTile()) {

        }
    }

    public void shift(int x) {
        if (x == 2) {
            fullDrop();
            return;
        }
        List<CellLocation> newContents = new ArrayList<>();
        for (CellLocation tile : contents) {
            newContents.add(new CellLocation(tile.getRow(), tile.getColumn() + x));
        }
        if (inBounds(newContents)) {
            unrender();
            ununrender(newContents);
            this.contents = newContents;
        }
    }

    public void unrender() {
        for (CellLocation cell : contents) {
            try {
                sheet.update(cell, new Nothing());
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void ununrender(List<CellLocation> items) {
        for (CellLocation cell : items) {
            try {
                sheet.update(cell, new Constant(fallingType));
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean drop() {
        contents = new ArrayList<>();
        newPiece();
        for (CellLocation location : contents) {
            // Tests for game over
            // if the
            if (!sheet.valueAt(location).render().equals("")) {
                return true;
            }
        }
        ununrender(contents);

        return false;
    }


    /**
     * Generates a new Piece by creating a new TetrosPiece instance
     */
    private void newPiece() {
        // Generate a new random piece value
        int tilePicker = randomTile.pick();

        // Generate a new piece
        TetrosPieceFactory newFallingPiece = new TetrosPieceFactory(tilePicker, this.contents);
        newFallingPiece.generatePiece();

        // Change the current falling type
        this.fallingType = newFallingPiece.getFallingType();
    }


    private void flip(int direction) {
        int x = 0;
        int y = 0;
        for (CellLocation cellLocation : contents) {
            x += cellLocation.getColumn();
            y += cellLocation.getRow();
        }
        x /= contents.size(); y /= contents.size();
        List<CellLocation> newCells = new ArrayList<>();
        for (CellLocation location : contents) {
            int lx = x + ((y - location.getRow()) * direction);
            int ly = y + ((x - location.getColumn()) * direction);
            CellLocation replacement = new CellLocation(ly, lx);
            newCells.add(replacement);
        }
        if (!inBounds(newCells)) {
            return;
        }
        unrender();
        contents = newCells;
        ununrender(newCells);
    }


    private void checkLineFilled() {
        for (int row = sheet.getRows() - 1; row >= 0; row--) {
            boolean full = true;
            for (int col = 0 ; col < sheet.getColumns(); col++) {
                if (sheet.valueAt(row, col).getContent().equals("")) {
                    full = false;
                }
            }
            if (full) {
                for (int rowX = row; rowX > 0; rowX--) {
                    for (int col = 0 ; col < sheet.getColumns(); col++) {
                        try {
                            if (contents.contains(new CellLocation(rowX - 1, col))) {
                                continue;
                            }
                            sheet.update(new CellLocation(rowX, col), sheet.valueAt(new CellLocation(rowX - 1, col)));
                        } catch (TypeError e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                row = row + 1;
            }
        }
    }

    public Perform getMove(int direction) {
        return new Move(direction);
    }
    public Perform getRotate(int direction) {
        return new Rotate(direction);
    }

    public class GameStart implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = true;
            drop();
        }
    }

    public class Move implements Perform {
        private final int direction;

        public Move(int direction) {
            this.direction = direction;
        }

        @Override
        public void perform(int row, int column, Prompt prompt) {
            if (!started) {
                return;
            }
            shift(direction);
        }
    }

    public class Rotate implements Perform {
        private final int direction;

        public Rotate(int direction) {
            this.direction = direction;
        }

        @Override
        public void perform(int row, int column, Prompt prompt) {
            if (!started) {
                return;
            }
            flip(direction);
        }
    }
}