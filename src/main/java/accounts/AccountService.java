package accounts;

import dbService.DBService;
import dbService.executor.Executor;

import java.sql.SQLException;

public class AccountService {
    private static final Executor executor = new Executor(DBService.getMysqlConnection());

    public static void addNewUser(UserProfile userProfile) throws SQLException {
        String insert = "INSERT INTO users (login, password, email) values (\'" + userProfile.getLogin() + "\', \'"
                + userProfile.getPass() + "\', \'" + userProfile.getEmail() + "\')";

        executor.execUpdate(insert);
    }

    public static UserProfile getUserByLogin(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = '" + login + "'";
        UserProfile userProfile = executor.execQuery(query, resultSet -> {
            if (resultSet.next()) {
                String login1 = resultSet.getString("login");
                String pass = resultSet.getString("password");
                String email = resultSet.getString("email");

                return new UserProfile(login1, pass, email);
            }

            return null;
        });

        return userProfile;
    }
}
