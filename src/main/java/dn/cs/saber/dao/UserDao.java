package dn.cs.saber.dao;

import dn.cs.saber.domain.User;
import dn.cs.saber.vo.RespStatus;

/**
 * Data Access Object for user.
 * <br> 1. Query user in kinds of ways.
 * <br> 2. Add a user.
 * 
 * @author Nen Den
 */
public interface UserDao {

	/**
	 * Query the specific user by primary key id.
	 * 
	 * @param id
	 * @return
	 */
	User getUser(int id);

	/**
	 * Query the specific user by email.
	 * WARN: Email should be unique. Means only return one user.
	 * 
	 * @param id
	 * @return
	 */
	User getUserByEmail(String email);

	/**
	 * Query the specific user by name.
	 * WARN: Name should be unique. Means only return one user.
	 * 
	 * @param id
	 * @return
	 */
	User getUserByName(String name);

	/**
	 * Check if a user is existed by name and email in user object.
	 * If not existed, create a new user has the name and email.
	 * 
	 * @return check result, in JSON String.
	 */
	RespStatus<String> chcekUser(User user);

	/**
	 * Check if a user is existed by primary key id.
	 * 
	 * @param id
	 * @return
	 */
	boolean checkUser(int id);

	/**
	 * Insert a user.
	 * 
	 * @param user
	 */
	void addUser(User user);

}