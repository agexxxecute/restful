package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    public Connection getConnection() {
        Connection connection = null;
        String filename = "db.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        Properties properties = new Properties();

        try(InputStream inputStream = classLoader.getResourceAsStream(filename);
        ) {
            properties.load(inputStream);
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
