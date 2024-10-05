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

    /** A random tile that is used to generate a new random piece */
    public final RandomTile randomTile;

    private TetrosRender renderer;

    private TetrosCellState cellState;

    private TetrosGameState gameState;


    /**
     * Initialises and stores the sheet and tile randomizer to be used for Tetros.
     *
     * @param sheet         The sheet to play Tetros in
     * @param randomTile    The random tile generator to generate new pieces.
     */
    public Tetros(Sheet sheet, RandomTile randomTile) {
        this.sheet = sheet;
        this.randomTile = randomTile;

        this.renderer = new TetrosRender(this.sheet, this.gameState, this.cellState);
        this.cellState = new TetrosCellState();
        this.gameState = new TetrosGameState();
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
        if (!this.gameState.isStarted()) {
            return false;
        }

        if (this.dropTile()) {
            // If the game detects a piece has gone over the top row, stop the game
            if (drop()) {

                // Display a message to the user
                prompt.message("Game Over!");
                // Stop the game
                this.gameState.setStarted(false);
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

        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow() + 1, tile.getColumn()));
        }

        renderer.unrender();

        for (CellLocation newLoc : newContents) {
            if (isStopper(newLoc)) {
                renderer.render(this.cellState.getContents());
                return true;
            }
        }

        this.cellState.setContents(newContents);
        renderer.render(this.cellState.getContents());
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
        for (CellLocation tile : this.cellState.getContents()) {
            newContents.add(new CellLocation(tile.getRow(), tile.getColumn() + x));
        }
        if (inBounds(newContents)) {
            renderer.unrender();
            // this.contents = newContents;
            this.cellState.setContents(newContents);
            renderer.render(this.cellState.getContents());
        }
    }

    public void unrender() {
        for (CellLocation cell : this.cellState.getContents()) {
            try {
                sheet.update(cell, new Nothing());
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void render(List<CellLocation> items) {
        for (CellLocation cell : items) {
            try {
                sheet.update(cell, new Constant(this.gameState.getFallingType()));
            } catch (TypeError e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean drop() {
        this.cellState.resetContents();

        newPiece();
        for (CellLocation location : this.cellState.getContents()) {
            if (!sheet.valueAt(location).render().equals("")) {
                return true;
            }
        }

        renderer.render(this.cellState.getContents());

        return false;
    }


    /**
     * Generates a new Piece by creating a new TetrosPiece instance
     */
    private void newPiece() {
        // Generate a new random piece value
        int tilePicker = randomTile.pick();

        // Generate a new piece
        TetrosPieceFactory newFallingPiece = new TetrosPieceFactory(tilePicker,
                this.cellState,
                this.gameState);
        newFallingPiece.generatePiece();

    }


    private void flip(int direction) {
        int x = 0;
        int y = 0;
        for (CellLocation cellLocation : this.cellState.getContents()) {
            x += cellLocation.getColumn();
            y += cellLocation.getRow();
        }
        x /= this.cellState.getContents().size(); y /= this.cellState.getContents().size();
        List<CellLocation> newCells = new ArrayList<>();

        for (CellLocation location : this.cellState.getContents()) {
            int lx = x + ((y - location.getRow()) * direction);
            int ly = y + ((x - location.getColumn()) * direction);
            CellLocation replacement = new CellLocation(ly, lx);
            newCells.add(replacement);
        }
        if (!inBounds(newCells)) {
            return;
        }
        renderer.unrender();

        this.cellState.setContents(newCells);

        renderer.render(this.cellState.getContents());
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
                            if (this.cellState.getContents().contains(new CellLocation(rowX - 1, col))) {
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
            gameState.setStarted(true);
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
            if (!gameState.isStarted()) {
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
            if (!gameState.isStarted()) {
                return;
            }
            flip(direction);
        }
    }
}