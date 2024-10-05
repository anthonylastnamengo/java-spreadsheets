package sheep.features.files;

import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

/**
 * The Save class implements the Perform interface and represents the functionality to save a sheet to a file.
 * It prompts the user for a file path, attempts to save the sheet to the specified file path, and encodes the sheet data for saving.
 */
public class Save implements Perform {

    /** The input file path provided by the user */
    private String filePathInput;

    /** The actual file path obtained from the input */
    private Path trueFilePath;

    /** The sheet to be saved */
    private Sheet sheet;

    /**
     * Constructs a new Save instance with the given sheet.
     *
     * @param sheet The sheet to be saved.
     */
    public Save(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Prompts the user for a file path and attempts to save the sheet to the specified file path.
     * Encodes the sheet data for saving.
     *
     * @param row     The row coordinate of the cell where the action is performed (unused).
     * @param column  The column coordinate of the cell where the action is performed (unused).
     * @param prompt  The prompt interface for interacting with the user.
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        // Prompt the user for a file path
        Optional<String> optionalPath = prompt.ask("Save file as:");

        // Obtain the file path from the Optional<String>
        if (optionalPath.isPresent()) {
            this.filePathInput = optionalPath.get();
        } else {
            // Return if the user cancels
            return;
        }

        // Convert the file path to a Path instance
        this.trueFilePath = Paths.get("./" + this.filePathInput);

        // Save the sheet to the specified file path
        save(this.trueFilePath, prompt);
    }

    /**
     * Saves the sheet to the specified file path.
     * Encodes the sheet data for saving.
     *
     * @param filePath The file path where the sheet will be saved.
     * @param prompt   The prompt interface for interacting with the user.
     */
    public void save(Path filePath, Prompt prompt) {
        try {
            // Create the file and write the encoded sheet data to it
            Path writtenFile = Files.createFile(filePath);
            Files.writeString(writtenFile, saveEncode(this.sheet));
        } catch (IOException e) {
            // Handle invalid file path
            prompt.message("Invalid file path.");
        }
    }

    /**
     * Encodes the sheet data for saving.
     * Replaces empty strings with spaces.
     *
     * @param sheet The sheet to be encoded.
     * @return A string representation of the encoded sheet data.
     */
    public String saveEncode(Sheet sheet) {
        // Encode the sheet data for saving
        StringJoiner builder = new StringJoiner("\n");
        for (int row = 0; row < sheet.getRows(); row++) {

            StringJoiner rowEncoding = new StringJoiner("|");

            for (int column = 0; column < sheet.getColumns(); column++) {
                String formula = sheet.formulaAt(new CellLocation(row, column)).render();

                if (Objects.equals(formula, "")) {
                    // Replace empty strings with spaces
                    rowEncoding.add(" ");
                } else {
                    rowEncoding.add(formula);
                }
            }
            builder.add(rowEncoding.toString());
        }
        return builder.toString();
    }
}
