package sheep.games.tetros;

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
    private final RandomTile randomTile;

    /** Responsible for rendering the game state */
    private TetrosRender renderer;

    /** Manages the state of cells in the game */
    private TetrosCellState cellState;

    /** Manages the overall game state */
    private TetrosGameState gameState;

    /** Checks for game over conditions and filled lines */
    private TetrosChecker checker;

    /** Handles dropping of tetrominoes */
    private TetrosDropper dropper;

    /**
     * Initializes a new instance of Tetros.
     *
     * @param sheet The sheet to play Tetros in
     * @param randomTile The random tile generator to generate new pieces
     */
    public Tetros(Sheet sheet, RandomTile randomTile) {
        this.sheet = sheet;
        this.randomTile = randomTile;

        this.cellState = new TetrosCellState();
        this.gameState = new TetrosGameState();
        this.renderer = new TetrosRender(this.sheet, this.gameState, this.cellState);
        this.checker = new TetrosChecker(this.sheet, this.cellState);

        this.dropper = new TetrosDropper(this.sheet,
                this.cellState,
                this.renderer,
                this.checker,
                this.randomTile,
                this.gameState);

    }

    /**
     * Registers features and key bindings for Tetros.
     *
     * @param ui An instance of the UI class which allows the sheets UI to register features.
     */
    @Override
    public void register(UI ui) {
        // Initialises an action listener on each tick.
        ui.onTick(this);

        // Adds a feature that allows the user to start a game of Tetros
        ui.addFeature("tetros", "Start Tetros", new GameStart(this.gameState, this.dropper));

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

        if (this.dropper.dropTile()) {
            // If the game detects a piece has gone over the top row, stop the game
            if (this.dropper.drop()) {
                // Display a message to the user
                prompt.message("Game Over!");
                // Stop the game
                this.gameState.setStarted(false);
            }
        }
        // If no losing condition is found, check if a user has filled a row with pieces
        this.checker.checkLineFilled();

        return true;
    }

    /**
     * Gets a Perform object for moving in a specified direction.
     *
     * @param direction The direction of movement (-1 for left, 1 for right, 2 for drop)
     * @return A Perform object for the specified movement direction
     */
    public Perform getMove(int direction) {
        return new TetrosMove(direction, this.gameState,
                this.cellState,
                this.dropper,
                this.renderer,
                this.checker);
    }

    /**
     * Gets a Perform object for rotating in a specified direction.
     *
     * @param direction The direction of rotation (-1 for left, 1 for right)
     * @return A Perform object for the specified rotation direction
     */
    public Perform getRotate(int direction) {
        return new TetrosRotate(direction, this.gameState,
                this.cellState,
                this.renderer,
                this.checker);
    }
}