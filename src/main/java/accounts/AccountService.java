package accounts;

import dbService.DBService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AccountService {

    public static void addNewUser(UserProfile userProfile) {
        try {
            Session session = DBService.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(userProfile);
            tx.commit();
            session.close();
        } catch(HibernateException e){
            throw new HibernateException(e);
        }
    }

    public static UserProfile getUserByLogin(String login) {
        try{
            Session session = DBService.sessionFactory.openSession();
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
