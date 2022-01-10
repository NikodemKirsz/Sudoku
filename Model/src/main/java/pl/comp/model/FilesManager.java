package pl.comp.model;

import java.io.File;

public class FilesManager {
    private static final String SUDOKU_BOARD_NAME= "saved_board";
    private static final String SUDOKU_BOARD_ORIGINAL_NAME = "saved_board_original";

    public static final String FILES_PATH = "./files";
    public static final String SUDOKU_BOARD_PATH = FILES_PATH + "/" + SUDOKU_BOARD_NAME;
    public static final String SUDOKU_BOARD_ORIGINAL_PATH = FILES_PATH + "/" + SUDOKU_BOARD_ORIGINAL_NAME;

    private FilesManager() {
    }

    public static String getPath(final String fileName) {
        return FILES_PATH + "/" + fileName;
    }

    public static boolean ensureFilesDirExists() {
        var filesDirectory = new File(FILES_PATH);

        if (filesDirectory.exists()) {
            return true;
        }
        else {
            return filesDirectory.mkdir();
        }
    }
}
