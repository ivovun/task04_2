package service;

import DaoFactory.UserDaoFactory;
import dao.UserDao;
import dao.UserHibernateDAO;
import exception.DBException;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;
import util.PropertyReader;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userServiceImpl;
    private User defaultAdmin;

    private UserDao getUserDao() {
        return new UserDaoFactory().create(PropertyReader.getProperty("typeOfDaoRealisation"));
    }

    public static UserServiceImpl getInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
            try {
                userServiceImpl.defaultAdmin = userServiceImpl.selectUserByLogin("admin");
                if (userServiceImpl.defaultAdmin == null) {
                    userServiceImpl.insertUser(new User("admin", "admin", "admin", "admin"));
                    userServiceImpl.defaultAdmin = userServiceImpl.selectUserByLogin("admin");
                }

            } catch (DBException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to create default admin!");
            }
        }
        return userServiceImpl;
    }

    @Override
    public boolean insertUser(User user) throws DBException {
        getUserDao().insertUser(user);
        return true;
    }

    @Override
    public User selectUser(long id) throws DBException {
        return getUserDao().selectUser(id);
    }

    @Override
    public User selectUserByLogin(String login) throws DBException {
        return getUserDao().selectUserByLogin(login);
    }

    @Override
    public List<User> selectAllUsers() throws DBException {
        return getUserDao().selectAllUsers();
    }

    @Override
    public void deleteUser(long id) throws DBException {
        getUserDao().deleteUser(id);
    }

    @Override
    public void updateUser(User user) throws DBException {
        getUserDao().updateUser(user);
    }

}
