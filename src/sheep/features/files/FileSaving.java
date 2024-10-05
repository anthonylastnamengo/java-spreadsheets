package sheep.features.files;

import sheep.features.Feature;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;
import sheep.ui.Tick;
import sheep.ui.UI;

/**
 * The FileSaving class implements the Feature interface and represents the feature to save a sheet to a file.
 * It allows users to register the "Save As" feature with the UI.
 */
public class FileSaving implements Feature {

    /** The sheet to be saved */
    private final Sheet sheet;

    /**
     * Constructs a new FileSaving feature with the given sheet.
     *
     * @param sheet The sheet to be saved.
     */
    public FileSaving(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Registers the "Save As" feature with the provided UI.
     *
     * @param ui The user interface where the feature will be registered.
     */
    @Override
    public void register(UI ui) {
        // Register callback function for the "Save As" feature
        ui.addFeature("save-file", "Save As", new Save(this.sheet));
    }

}
