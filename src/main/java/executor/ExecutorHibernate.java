package executor;

import exception.DBException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ExecutorHibernate  {
    private Session session;
    private final SessionFactory sessionFactory;

    public ExecutorHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.openSession();
    }

    public Session getSession() {
        return session;
    }

    public <T> T execQuery( ResultHandler<T, Session , DBException> handler) throws DBException  {
        // !!!!  ИСПОЛЬЗУЮ null так как офиц код https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/example-weblog-code.html
        // из официального туториала на jboss.org см. место со слов: "Transaction tx = null;"  -->> https://docs.jboss.org/hibernate/core/3.3/reference/en/html/transactions.html
        Transaction transaction = null;
        T value; // можно (T) new Object(); // если боимся null
        try {
            transaction = session.beginTransaction();
            value = handler.handle(session);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw he;
        } finally {
            session.close();
            session = sessionFactory.openSession();
        }
        return value;
    }

}
