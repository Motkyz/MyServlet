package accounts;

import dbService.DBService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class AccountService {
    private static DBService dbService = new DBService();

    public static void addNewUser(UserProfile userProfile) throws SQLException {
        try {
            Session session = dbService.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(userProfile);
            tx.commit();
            session.close();
        } catch(HibernateException e){
            throw new HibernateException(e);
        }
    }

    public static UserProfile getUserByLogin(String login) throws SQLException {
        try{
            Session session = dbService.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            UserProfile userProfile = session.load(UserProfile.class, login);
            tx.commit();
            session.close();

            return userProfile;
        }catch (HibernateException e){
            throw new HibernateException(e);
        }
    }
}
