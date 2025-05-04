package dbService;

import accounts.UserProfile;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private static final SessionFactory sessionFactory = createSessionFactory(getMySqlConfiguration());

    private static Configuration getMySqlConfiguration() {
        Dotenv dotenv = Dotenv.load();
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", DbUrlBuilder());
        configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USER"));
        configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASS"));
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static String DbUrlBuilder(){
        Dotenv dotenv = Dotenv.load();
        StringBuilder url = new StringBuilder();
        url.
                append("jdbc:mysql://").        //db type
                append(dotenv.get("DB_HOST")).           //host name
                append(":").
                append(dotenv.get("DB_PORT")).                //port
                append("/").
                append(dotenv.get("DB_NAME"));          //db name

        return url.toString();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
