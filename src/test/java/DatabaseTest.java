import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import junit.framework.TestCase;
import model.User;
import service.Database;

public class DatabaseTest extends TestCase {
    public DatabaseTest() {
    }

    public void testAddUser() throws SQLException {
        Database database = new Database();
        User user = new User(999, "a1", "Robert");
        database.addUser(user);
        User user1 = database.getUser(user.getUserId());
        assertNotNull(user1);
        database.deleteUser(user.getUserId());
    }

    public void testPrintAllUsers() throws SQLException {
        Database database = new Database();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        User user = new User(999, "a1", "Robert");
        database.addUser(user);
        database.printAllUsers();
        database.deleteUser(user.getUserId());
        String expectedOutput = "User ID: 999, User guid: a1, User name: Robert";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    public void testDeleteAllUsers() throws SQLException {
        Database database = new Database();
        User user = new User(999, "a1", "Robert");
        database.addUser(user);
        database.deleteAllUsers();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        database.printAllUsers();
        assertTrue(outContent.toString().isEmpty());
    }

    public void testCloseConnection() throws SQLException {
        Database database = new Database();
        database.closeConnection();
        assertTrue(database.getConnection().isClosed());
    }

    public void testDeleteUser() throws SQLException {
        Database database = new Database();
        User user = new User(999, "a1", "Robert");
        database.addUser(user);
        database.deleteUser(user.getUserId());
        User user1 = database.getUser(user.getUserId());
        assertNull(user1);
    }

    public void testGetUser() throws SQLException {
        Database database = new Database();
        User user = new User(999, "a1", "Robert");
        database.addUser(user);
        User user1 = database.getUser(user.getUserId());
        assertNotNull(user1);
        database.deleteUser(user.getUserId());
    }

    public void testGetConnection() throws SQLException {
        Database database = new Database();
        assertNotNull(database.getConnection());
    }
}
