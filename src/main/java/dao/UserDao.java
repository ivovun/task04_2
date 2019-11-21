package dao;

import exception.DBException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    boolean insertUser(User user)  throws DBException;

    User selectUser(Long id) throws DBException;

    User selectUserByLogin(String login) throws DBException;

    List<User> selectAllUsers() throws DBException;

    boolean deleteUser(Long id) throws DBException;

    boolean updateUser(User user) throws DBException;
}

