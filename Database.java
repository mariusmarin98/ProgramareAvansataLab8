package lab8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

    private static Database instance;
    private Connection connection;

    private Database() throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/MusicAlbums";
            String username = "postgres";
            String password = "1998";
            this.connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        } else if (instance.getConnection().isClosed()) {
            instance = new Database();
        }
        return instance;
    }
}