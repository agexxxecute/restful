package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    public static Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();

        try(FileInputStream fis = new FileInputStream("C:\\Users\\Egor\\IdeaProjects\\restful2\\src\\main\\resources\\db.properties");
        ) {
            properties.load(fis);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
        } catch(SQLException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
