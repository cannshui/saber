package dn.cs.saber.dao;

import dn.cs.saber.domain.User;
import dn.cs.saber.vo.RespStatus;

/**
 * Data Access Object for user.
 * <br> 1. Query user in kinds of ways.
 * <br> 2. Add a user.
 *
 * @author Nen Den
 * @since 1.0.0
 */
public interface UserDao {

    /**
     * Query the specific user by primary key id.
     *
     * @param id primary key id.
     * @return User
     */
    User getUser(int id);

    /**
     * Query the specific user by email.
     * WARN: Email should be unique. Means only return one user.
     *
     * @param email unique email
     * @return User
     */
    User getUserByEmail(String email);

    /**
     * Query the specific user by name.
     * WARN: Name should be unique. Means only return one user.
     *
     * @param name unique name
     * @return User
     */
    User getUserByName(String name);

    /**
     * Check if a user is existed by name and email in user object.
     * If not existed, create a new user has the name and email.
     *
     * @param user user
     * @return check result, in JSON String.
     */
    RespStatus<String> checkUser(User user);

    /**
     * Insert a user.
     *
     * @param user user
     */
    void addUser(User user);

}
