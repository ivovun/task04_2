package service;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserService {

    static final String userRoleName = "user";
    static final String customerUser = "customerUser";
    static final String adminRoleName = "admin";
    static final String adminUser = "adminUser";


    boolean insertUser(User user) throws DBException;

    User selectUser(long id) throws DBException;

    User selectUserByLogin(String login) throws DBException;

    List<User> selectAllUsers() throws DBException;

    void deleteUser(long id) throws DBException;

    void updateUser(User user) throws DBException;
}
