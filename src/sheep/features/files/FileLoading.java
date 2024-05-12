package sheep.features.files;

import sheep.features.Feature;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.UI;

import java.io.File;

/**
 * Loading in a file:
 * Takes an existing sheet, populates it with the contents of the txt or whatever file
 * Do this with a for loop?
 */
public class FileLoading implements Feature {

    private Sheet sheet;

    public FileLoading(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void register(UI ui) {
        // Register a callback function for the "Save" feature
        ui.addFeature("load-file", "Open file", new Load(this.sheet));
    }
}
