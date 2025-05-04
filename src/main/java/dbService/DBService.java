package dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getMysqlConnection();
    }

    public static Connection getMysqlConnection() {
        try {
            Dotenv dotenv = Dotenv.load();
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").        //db type
                    append(dotenv.get("DB_HOST")).           //host name
                    append(":").
                    append(dotenv.get("DB_PORT")).                //port
                    append("/").
                    append(dotenv.get("DB_NAME")).          //db name
                    append("?user=").
                    append(dotenv.get("DB_USER")).          //login
                    append("&password=").
                    append(dotenv.get("DB_PASS"));       //password

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());

            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
