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

public class Load implements Perform {

    private Sheet sheet;
    private String filePathInput;

    private Path trueFilePath;

    private List<String> fileLines;

    public Load(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void perform(int row, int column, Prompt prompt) {
        Optional<String> optionalPath = prompt.ask("Open file from:");

        if (optionalPath.isPresent()) {
            this.filePathInput = optionalPath.get();
        } else {
            // Most times this is just the case where the user cancels
            return;
        }

        this.trueFilePath = Paths.get(this.filePathInput);

        // If the file path inputted does not exist, inform the user
        if (!Files.exists(this.trueFilePath)) {
            prompt.message("File path does not exist.");
            return;
        }

        // If it does exist, attempt to load the file path.
        load(this.trueFilePath);
    }

    public void load(Path trueFilePath) {
        try {
            this.fileLines = Files.readAllLines(trueFilePath);

            // Obtain the row and column size from the file to be opened
            int rowSize = this.fileLines.size();
            int colSize = this.fileLines.getFirst().split("\\|").length;

            // Clear the sheet
            this.sheet.clear();
            // Update the dimensions of the current sheet
            this.sheet.updateDimensions(rowSize, colSize);

            for (int i = 0; i < rowSize; i++) {
                // For each row in the fileLines list:
                String line = this.fileLines.get(i);

                // Split the rows on the pipe and store the results in an array
                String[] cells = (line.split("\\|"));

                // Iterate through the array to get the cell
                for (int j = 0; j < colSize; j++) {
                    String formula = cells[j];

                    if (Objects.equals(formula, " ")) {
                        this.sheet.update(i, j, "");
                    } else {
                        this.sheet.update(i, j, formula);
                    }
                }
            }

        } catch (IOException e) {
            return;
        }
    }
}
