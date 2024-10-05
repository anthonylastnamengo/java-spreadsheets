package sheep.games.snake;

import sheep.features.Feature;
import sheep.games.random.RandomCell;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;
import sheep.ui.Tick;
import sheep.ui.UI;

/**
 * An implementation of the snake game for a sheets instance.
 */
public class Snake implements Feature, Tick {

    /** A picker for a random cell */
    private RandomCell randomCell;

    /** Handler for food/apple spawning in the sheets */
    private final AppleFactory appleFactory;

    /** Handler for snake growth, movement and eating logic in the sheets */
    private final SnakeFactory snakeFactory;

    /** Storage for the game state */
    private final GameState gameState;

    /** Storage for the state of the sheet cells */
    private final SnakeCellState cellState;

    /** A renderer for the snake game in the sheets */
    private final SnakeRender renderer;

    /** A checker for game over logic */
    private final CheckGameOver checker;

    /** The sheet in which the user is playing Snake */
    private final Sheet sheet;

    /**
     * Constructs a new Snake game
     *
     * @param sheet         The sheet in which to display the Snake game
     * @param randomCell    The random cell picker to pick a new cell for an apple spawn
     */
    public Snake(Sheet sheet, RandomCell randomCell) {
        // Store the sheet and random cell input
        this.sheet = sheet;
        this.randomCell = randomCell;

        // Initialise the game state and cell state
        this.gameState = new GameState();
        this.cellState = new SnakeCellState();

        // Initialise the factories for handling Apple and Snake logic
        this.appleFactory = new AppleFactory(this.sheet, this.randomCell, this.cellState);
        this.snakeFactory = new SnakeFactory(this.appleFactory, this.cellState);

        // Initialise the game renderer and game over checker
        this.renderer = new SnakeRender(this.sheet, this.cellState);
        this.checker = new CheckGameOver(this.sheet, this.snakeFactory, this.cellState);
    }

    /**
     * Registers a feature to start a new Snake game, and registers key press listeners
     *
     * @param ui An arbitrary user interface for the Sheet
     */
    @Override
    public void register(UI ui) {
        // Run the game on each tick
        ui.onTick(this);

        // Add a feature to the Sheets to start a new Game of snake
        ui.addFeature("snake", "Start Snake", new SnakeStart(this.gameState,
                this.appleFactory,
                this.snakeFactory,
                this.sheet));

        // Register movement functionality through a key press listener:
        // w -> up
        ui.onKey("w", "Move Up", this.parseMove("UP"));
        // a -> left
        ui.onKey("a", "Move Left", this.parseMove("LEFT"));
        // s -> down
        ui.onKey("s", "Move Down", this.parseMove("DOWN"));
        // d -> right
        ui.onKey("d", "Move Right", this.parseMove("RIGHT"));

        ui.setTickSpeed(100);
    }

    /**
     * Details what actions the game should complete on each tick.
     *
     * @param prompt Provide a mechanism to interact with the user interface
     *               after a tick occurs, if required.
     *
     * @return A boolean indicating whether the Sheet should continue ticking.
     */
    @Override
    public boolean onTick(Prompt prompt) {

        // If the game has not started, do not tick
        if (!this.gameState.isStarted()) {
            return false;

        } else {
            // Compute the next snake positions in the snake factory
            this.snakeFactory.nextSnake(this.gameState.getCurrentRowShift(),
                    this.gameState.getCurrentColShift());

            // Checking for win/loss:
            if (this.checker.checkLoss()) {
                // If game has been lost
                // Display losing message
                prompt.message("Game Over!");
                // Reset the sheet and game to blank
                reset();
            } else if (this.checker.checkWin()) {
                // If game has been won
                // Display winning message
                prompt.message("You Won!");
                // Reset the sheet and game to blank
                reset();
            }

            // Render the game of snake after computing next snake positions
            this.renderer.playSnake();
        }

        // Continue ticking the game
        return true;
    }

    /**
     * Resets the game's states and clears the board.
     */
    private void reset() {
        // Switch the game off
        this.gameState.setStarted(false);

        // Clear the sheet completely.
        sheet.clear();

        // Reset the old snake cells, apple cells and next snake cells
        this.cellState.resetOldSnakeCells();
        this.cellState.resetAppleCells();
        this.cellState.resetNextSnakeCells();

        // Reset to the default south movement for a new game
        this.gameState.setCurrentRowShift(1);
        this.gameState.setCurrentColShift(0);

        // Reset the current snake head
        this.snakeFactory.setSnakeHead(null);

    }

    /**
     * Parses the move from the register method into a new Move
     *
     * @param direction A string representing the direction to move
     *
     * @return A new move in the direction of the inputted move
     */
    public Perform parseMove(String direction) {
        return new SnakeMove(direction, gameState);
    }

}