package service;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserService {

    public static final String userRoleName = "user";
    public static final String userRoleSessionAttributeName = "customerUser";
    public static final String adminRoleName = "admin";
    public static final String adminRoleSessionAttributeName = "adminUser";


    boolean insertUser(User user) throws DBException;

    User selectUser(long id) throws DBException;

    User selectUserByLogin(String login) throws DBException;

    List<User> selectAllUsers()throws DBException;

    void deleteUser(long id) throws DBException;

    void updateUser(User user) throws DBException;
}
