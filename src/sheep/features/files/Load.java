package sheep.features.files;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The Load class implements the Perform interface and is responsible for loading
 * the contents of a file into a sheet.
 */
public class Load implements Perform {

    /** The sheet where the contents of the file will be loaded */
    private final Sheet sheet;

    /** The file path inputted by the user */
    private String filePathInput;

    /** The actual file path to be loaded */
    private Path trueFilePath;

    /** The lines read from the file */
    private List<String> fileLines;

    /**
     * Constructs a new Load object with the specified sheet.
     *
     * @param sheet The sheet where the contents of the file will be loaded.
     */
    public Load(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Prompts the user to enter a file path and then attempts to load the file.
     * If the file path does not exist or cannot be loaded, an appropriate message is displayed.
     *
     * @param row     The row where the operation is performed (unused).
     * @param column  The column where the operation is performed (unused).
     * @param prompt  The prompt used to interact with the user.
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        // Prompt the user to enter a file path
        Optional<String> optionalPath = prompt.ask("Open file from:");

        if (optionalPath.isPresent()) {
            this.filePathInput = optionalPath.get();
        } else {
            // Most times this is just the case where the user cancels
            return;
        }

        // Set the true file path
        this.trueFilePath = Paths.get(this.filePathInput);

        // If the file path inputted does not exist, inform the user
        if (!Files.exists(this.trueFilePath)) {
            prompt.message("File path does not exist.");
            return;
        }

        // If it does exist, attempt to load the file path
        load(this.trueFilePath);
    }

    /**
     * Loads the contents of the specified file path into the sheet.
     * If the file cannot be loaded, an IOException is caught.
     *
     * @param trueFilePath The actual file path to be loaded.
     */
    public void load(Path trueFilePath) {
        try {
            // Read all lines from the file
            this.fileLines = Files.readAllLines(trueFilePath);

            // Obtain the row and column size from the file to be opened
            int rowSize = this.fileLines.size();
            int colSize = this.fileLines.getFirst().split("\\|").length;

            // Clear the sheet
            this.sheet.clear();
            // Update the dimensions of the current sheet
            this.sheet.updateDimensions(rowSize, colSize);

            // Iterate over each row in the fileLines list
            for (int i = 0; i < rowSize; i++) {
                // Get the current row from the fileLines list
                String line = this.fileLines.get(i);

                // Split the row on the pipe character and store the cells in an array
                String[] cells = line.split("\\|");

                // Iterate over each cell in the row
                for (int j = 0; j < colSize; j++) {
                    String formula = cells[j];

                    // Update the cell in the sheet with the formula from the file
                    if (Objects.equals(formula, " ")) {
                        this.sheet.update(i, j, "");
                    } else {
                        this.sheet.update(i, j, formula);
                    }
                }
            }

        } catch (IOException e) {
            // Handle IOException if the file cannot be loaded
            return;
        }
    }
}
