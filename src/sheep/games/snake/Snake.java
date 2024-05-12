package sheep.games.snake;

import sheep.expression.TypeError;
import sheep.expression.basic.Nothing;
import sheep.features.Feature;
import sheep.games.random.RandomCell;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;
import sheep.ui.Tick;
import sheep.ui.UI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Snake implements Feature, Tick {
    // Stores whether snake game has started or not
    private boolean started = false;

    // Generates random cell for apple
    private RandomCell randomCell;

    // Stores the next snake cells to be rendered
    private List<CellLocation> nextSnakeCells;

    // Stores the old snake cells
    private List<CellLocation> oldSnakeCells;

    // Stores the apple cells
    private List<CellLocation> appleCells;

    // Stores the current snakeHead
    private CellLocation snakeHead;

    // Stores the current direction parsed from the keystroke
    // Initially snake is travelling in south direction
    private int currentRowShift = 1;
    private int currentColShift = 0;
    private boolean tickUpdate = false;

    Sheet sheet;

    @Override
    public void register(UI ui) {
        ui.onTick(this);

        ui.addFeature("snake", "Start Snake", new SnakeStart());

        // Add movement functionality
        // w -> up
        ui.onKey("w", "Move Up", this.parseMove(-1, 0));
        // a -> left
        ui.onKey("a", "Move Left", this.parseMove(0, -1));
        // s -> down
        ui.onKey("s", "Move Down", this.parseMove(1, 0));
        // d -> right
        ui.onKey("d", "Move Right", this.parseMove(0, 1));

    }

    @Override
    public boolean onTick(Prompt prompt) {
        if (!started) {
            return false;
        } else {
            nextSnake(currentRowShift, currentColShift);

            if (checkLoss()) {
                prompt.message("Game Over!");
                reset();
            } else if (checkWin()) {
                prompt.message("You Won!");
                reset();
            }
            // Render
            playSnake();
        }
        return true;
    }

    private void reset() {
        started = false;
        sheet.clear();
        this.oldSnakeCells = new ArrayList<>();
        this.nextSnakeCells = new ArrayList<>();
        this.appleCells = new ArrayList<>();
        currentColShift = 0;
        currentRowShift = 1;
        snakeHead = null;

    }

    public void playSnake() {

        // Clear the sheet before rendering the snake and apple
        sheet.clear();

        // Render the apples
        appleRender(this.appleCells);

        // Render the snake
        snakeRender(this.nextSnakeCells);
    }

    // Rendering logic: renders apple and renders snake
    private void appleRender(List<CellLocation> appleCells) {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {

                // Create a location object for the current cell
                CellLocation location = new CellLocation(row, column);

                // Check if the current cell is in the list of snake cells

                if (appleCells.contains(location)) {
                    // If the cell is part of the snake, update its value to represent the snake
                    sheet.update(row, column, "2"); // Assuming "1" represents the snake's body
                }
            }
        }
    }

    private void addApples() {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {

                CellLocation location = new CellLocation(row, column);

                String cellValue = this.sheet.valueAt(location).render();

                if (!Objects.equals(cellValue, "1") & !Objects.equals(cellValue, "")) {
                    if (!appleCells.contains(location)) {
                        appleCells.add(location);

                    }
                }
            }
        }
    }

    public void snakeRender(List<CellLocation> nextSnakeCells) {
        // Iterate over all cells in the sheet
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {

                // Create a location object for the current cell
                CellLocation location = new CellLocation(row, column);

                // Check if the current cell is in the list of snake cells
                if (nextSnakeCells.contains(location)) {
                    // If the cell is part of the snake, update its value to represent the snake
                    sheet.update(row, column, "1"); // Assuming "1" represents the snake's body
                }
            }
        }
    }

    private boolean checkLoss() {
        return !this.sheet.contains(this.snakeHead) || hitItself();
    }

    private boolean checkWin() {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                CellLocation cell = new CellLocation(row, column);

                if (Objects.equals(this.sheet.valueAt(cell).render(), "")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hitItself() {
        HashSet<CellLocation> seen = new HashSet<>();
        for (CellLocation location : nextSnakeCells) {
            if(seen.contains(location)) {
                return true;
            }
            seen.add(location);
        }
        return false;
    }

    public Snake(Sheet sheet, RandomCell randomCell) {
        this.sheet = sheet;
        this.randomCell = randomCell;
        this.nextSnakeCells = new ArrayList<>();
        this.oldSnakeCells = new ArrayList<>();
        this.appleCells = new ArrayList<>();
    }

    // Passing the row shift and column shift into a new class
    public Perform parseMove(int rowShift, int colShift) {
        return new Move(rowShift, colShift);
    }

    // New class for Move will compute the move
    public class Move implements Perform {
        private final int rowShift;
        private final int colShift;

        public Move(int rowShift, int colShift) {
            this.rowShift = rowShift;
            this.colShift = colShift;
        }

        @Override
        public void perform(int row, int column, Prompt prompt) {
            if (!started) {
                return;
            }
            // Update the current direction when a move is performed
            currentRowShift = rowShift;
            currentColShift = colShift;
        }
    }

    public void nextSnake(int rowShift, int colShift) {
        copyOld();
        // Initialise the previous snake to be the old snake

        // Sets the new List<CellLocation> nextSnakeCells, returns void
        // Takes in the oldSnakeCells, and applies a row shift and column shift to the head of the snake
        // For each body segment after the head of the snake, shift row and column to match old row and column
        // of piece before it
        // add all new snake segments to a List<CellLocation> and set nextSnakeCells to be this

        // Check if the next head of the snake coincides with an apple

        int headRow = this.snakeHead.getRow();
        int headCol = this.snakeHead.getColumn();

        CellLocation nextHead = new CellLocation(headRow + rowShift, headCol + colShift);

        // Update the head of the snake to the next position

        this.nextSnakeCells = new ArrayList<>();
        this.snakeHead = nextHead;

        if(checkApple(this.snakeHead)) {
            appleLogic();
            this.tickUpdate = true;
        }

        this.nextSnakeCells.add(this.snakeHead);

        // Update the body segments of the snake
        for (int i = 0; i < oldSnakeCells.size() - 1; i++) {
            this.nextSnakeCells.add(oldSnakeCells.get(i));
        }
    }
    private void appleLogic() {
        setApple();
        appleCells.remove(this.snakeHead);
    }
    public void copyOld() {
        if(this.oldSnakeCells.isEmpty()) {
            this.oldSnakeCells.add(snakeHead);
        } else {
            // Method initialises the new old cells list
            this.oldSnakeCells = new ArrayList<>();

            this.oldSnakeCells.addAll(nextSnakeCells);

            if (this.tickUpdate) {
                this.oldSnakeCells.add(snakeHead);
                tickUpdate = false;
            }

        }
    }

    public boolean checkApple(CellLocation nextHead) {
        return appleCells.contains(nextHead);
    }

    public class SnakeStart implements Perform {
        @Override
        public void perform(int row, int column, Prompt prompt) {
            started = true;
            // Spawn the head of the snake at the selected start position

            newHead(row, column);
            addApples();
        }
    }
    public void newHead(int row, int column) {
        // Create and set the newHead as specified by row and column
        this.sheet.update(row, column, "1");
        this.snakeHead = new CellLocation(row, column);
    }

    public void setApple() {
        // Creates a new apple on the sheets.
        CellLocation newApple = randomCell.pick();

        if (nextSnakeCells.contains(newApple) || appleCells.contains(newApple)) {
            return;
        }
        appleCells.add(newApple);
    }

}