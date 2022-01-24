package pl.comp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable{

    final String DB_NAME = "sudoku_boards.db";
    final String DB_PATH = FilesManager.getPath(DB_NAME);
    final String connectionUrl = "jdbc:sqlite:" + DB_PATH;

    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);

    @Override
    public SudokuBoard read() {
        Initialize();
        this.selectAll();
        return null;
    }

    @Override
    public void write(SudokuBoard obj) {
        String queues = "INSERT INTO Boards(name, board) VALUES(?,?)";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setString(1, "Normalnie sudoku board");
            pstmt.setString(2, "123123123123");
            pstmt.executeUpdate();
            logger.info("Record added.");

        } catch (SQLException exception) {
            logger.error(exception.toString());
        }
    }

    public void selectAll(){
        String queues = "SELECT * FROM Boards";

        try (var conn = DriverManager.getConnection(connectionUrl)){
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queues);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("board"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ensureTableExists() {
        String queues = """
                CREATE TABLE IF NOT EXISTS Boards (
                     id integer PRIMARY KEY,
                     name text NOT NULL,
                     board text NOT NULL
                );""";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.execute(queues);
            logger.info("Table created.");

        } catch (SQLException exception) {
            logger.error(exception.toString());
        }
    }

    private void Initialize() {
        this.ensureTableExists();
//
//        try (var conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
//            Class.forName("java.sql.Connection");
//            logger.info("Connection succeeded.");
//        }
//        catch (SQLException | ClassNotFoundException e) {
//            logger.error(e.toString());
//        }
    }

    @Override
    public void close() throws Exception {
    }
}





