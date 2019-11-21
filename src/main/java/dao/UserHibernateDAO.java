package dao;

import exception.DBException;
import executor.ExecutorHibernate;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserHibernateDAO implements UserDao {
    private ExecutorHibernate executor;

    public UserHibernateDAO(SessionFactory sessionFactory) {
        this.executor = new ExecutorHibernate(sessionFactory);
    }

    @Override
    public boolean insertUser(User user) throws DBException {
        return executor.execQuery(result -> {
            executor.getSession().save(new User(user.getName(), user.getLogin(), user.getPassword(), user.getRole()));
            return true;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public User selectUser(Long id) throws DBException {
        return executor.execQuery(  result -> {
            List<User> users = (ArrayList<User>) executor.getSession().createQuery("FROM User user where user.id='"+id+"'").list();
            if ( users.size() <= 0) {
                return null;
            }
            return users.get(0);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public User selectUserByLogin(String login) throws DBException {
        return executor.execQuery(  result -> {
            List<User> users = (ArrayList<User>) executor.getSession().createQuery("FROM User user where user.login='"+login+"'").list();
            if ( users.size() <= 0) {
                return null;
            }
            return users.get(0);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> selectAllUsers() throws DBException {
        return executor.execQuery(result -> (List<User>) executor.getSession().createQuery("FROM User").list());
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        return executor.execQuery((Session session) -> {
            session.delete(session.byId(User.class).getReference(id));
            return true;
        });
    }

    @Override
    public boolean updateUser(User user) throws DBException {
        return executor.execQuery((Session session) -> {
            session.update(user);
            return true;
        });
    }
}
