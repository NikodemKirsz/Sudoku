package pl.comp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import org.javatuples.Pair;
import pl.comp.exceptions.DatabaseException;
import pl.comp.exceptions.EmptyRecordException;
import pl.comp.exceptions.OutOfDatabaseException;
import pl.comp.exceptions.ProszeNieUzywacTejMetodyException;

import java.sql.*;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    final int SAVED_BOARDS_COUNT = 5;
    final String DB_PATH;
    final String CONNECTION_URL;

    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("bundle");

    private boolean initialized = false;

    public JdbcSudokuBoardDao(final String filePath) {
        this.DB_PATH = filePath;
        CONNECTION_URL = "jdbc:sqlite:" + DB_PATH;
    }

    @Override
    public SudokuBoard read() {
        return null;
    }

    public Pair<SudokuBoard, SudokuBoard> readBoth(int index) {

        if (this.isRecordEmpty(index)) {
            throw new EmptyRecordException();
        }

        var board = new SudokuBoard();
        var originalBoard = new SudokuBoard();

        String queues = "SELECT board, originalBoard FROM Boards WHERE id = "
                + index + ";";

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queues);

            String boardFields = rs.getString("board");
            String originalBoardFields = rs.getString("originalBoard");

            for (int i = 0; i < 81; i++) {
                board.set(i / 9, i % 9,
                        Character.getNumericValue(boardFields.charAt(i)));
                originalBoard.set(i / 9, i % 9,
                        Character.getNumericValue(originalBoardFields.charAt(i)));
            }

            logger.info("SudokuBoards read.");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }

        return Pair.with(board, originalBoard);
    }

    @Override
    public void write(SudokuBoard obj) {
        throw new ProszeNieUzywacTejMetodyException("Ta metoda jest w tej klasie bezuzyteczna, " +
                "ale tak nma kazaliu w zadaniu, a my posluszni studenci - robimy jak nam kaza");
    }

    public void updateBoard(int index, SudokuBoard modified, SudokuBoard original) {
        if (indexOutOfRange(index)) { throw new OutOfDatabaseException(); }
        
        String queues = "UPDATE Boards SET name = ?, board = ?, originalBoard = ? WHERE id = ?";

        var boardFields = new StringBuilder(81);
        var originalBoardFields = new StringBuilder(81);

        for (int i = 0; i < 81; i++) {
            boardFields.append(modified.getField(i / 9, i % 9).getFieldValue());
            originalBoardFields.append(original.getField(i / 9, i % 9).getFieldValue());
        }

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setString(1, "SB(" + LocalDateTime.now() + ")");
            pstmt.setString(2, boardFields.toString());
            pstmt.setString(3, originalBoardFields.toString());
            pstmt.setInt(4, index);
            pstmt.executeUpdate();
            logger.info("Record modified.");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }
    }

    public void initialize() {
        if (initialized) return;
        this.ensureTableValidity();
        initialized = true;
    }

    public boolean isRecordEmpty(int index) {
        try {
            return getName(index).equals("empty");
        } catch (DatabaseException exception) {
            logger.error(exception.getLocalizedMessage());
        }
        return false;
    }

    private String getName(int index) {
        if (indexOutOfRange(index)) { throw new OutOfDatabaseException(); }

        String queues = """ 
                            SELECT *
                            FROM Boards
                            WHERE id = ?;
                        """;

        try (var conn = DriverManager.getConnection(CONNECTION_URL)){
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setInt(1, index);
            ResultSet rs = pstmt.executeQuery();

            return rs.getString("name");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }

        return null;
    }

    private void ensureTableValidity() {

        if (checkTableExists()) {
            if (checkTableValidity()) {
                logger.info("Table exists and is valid");
            }
            else {
                logger.info("Table exists but is not valid!\nCreating new table and filling with defaults");
                dropTable();
                createTable();
                fillTableWithDefaults();
            }
        }
        else {
            logger.info("Table deos not exist!\nCreating new table and filling with defaults");
            createTable();
            fillTableWithDefaults();
        }
    }

    private boolean checkTableValidity() {
        String queues = "SELECT * FROM Boards";

        try (var conn = DriverManager.getConnection(CONNECTION_URL)){
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queues);

            int count = 0;
            while (rs.next()) {
                int index = rs.getInt("id");
                if (indexOutOfRange(index)) {
                    return false;
                }
                count++;
            }
            if (count != 5) {
                return false;
            }
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
            return false;
        }
        return true;
    }

    private boolean checkTableExists() {
        String queues = "SELECT * FROM Boards";

        try (var conn = DriverManager.getConnection(CONNECTION_URL)){
            Statement statement = conn.createStatement();
            statement.executeQuery(queues);
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
            return false;
        }
        return true;
    }

    private void fillTableWithDefaults() {
        String queues = """
                INSERT INTO Boards VALUES
                    (0, "empty", "", ""),
                    (1, "empty", "", ""),
                    (2, "empty", "", ""),
                    (3, "empty", "", ""),
                    (4, "empty", "", "");
                """;

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            statement.execute(queues);
            logger.info("Filled Table");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }
    }

    private void createTable() {
        String queues = """
                CREATE TABLE Boards (
                     id integer PRIMARY KEY,
                     name text NOT NULL,
                     board text,
                     originalBoard text
                );
                """;

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            statement.execute(queues);
            logger.info("Table created");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }
    }

    private void dropTable() {
        String queues = """
                DROP TABLE Boards
                """;

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            statement.execute(queues);
            logger.info("Table Dropped");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString("cause"), databaseException.getCause());
        }
    }

    private boolean indexOutOfRange(int index) {
        return (index < 0 || index >= SAVED_BOARDS_COUNT);
    }
}
