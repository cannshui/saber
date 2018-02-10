package dn.cs.saber.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import dn.cs.saber.dao.BaseDao;
import dn.cs.saber.dao.UserDao;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.RespEnum;
import dn.cs.saber.vo.RespStatus;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public User getUserByEmail(String email) {
		return super.get("getUserByEmail", email);
	}

	@Override
	public User getUserByName(String name) {
		return super.get("getUserByName", name);
	}

	@Override
	public User getUser(int id) {
		return super.get("getUser", id);
	}

	@Override
	public boolean checkUser(int id) {
		return id != 0 &&  getUser(id) != null;
	}

	@Override
	public void addUser(User user) {
		int id = super.add("insertUser", user);
		user.setId(id);
	}

	@Override
	public RespStatus<String> chcekUser(User user) {
		//MessageFormat mf = new MessageFormat("{\"code\": {1}, \"info\": \"{2}\"}");
		if (user == null) {
			RespStatus<String> resp = RespEnum.custom(RespEnum.NR, "User instance can not be null.");
			return resp;
		}
		String name = StringUtils.trimWhitespace(user.getName());
		String email = StringUtils.trimWhitespace(user.getEmail());
		if (!StringUtils.hasLength(name)) {
			RespStatus<String> resp =  RespEnum.custom(RespEnum.LR, "User name is empty.");
			return resp;
		}
		if (!StringUtils.hasLength(email)) {
			RespStatus<String> resp =  RespEnum.custom(RespEnum.LR, "User email is empty.");
			return resp;
		}
		User u1 = getUserByName(name);
		User u2 = getUserByEmail(email);
		if (u1 != null && u2 != null) {
			int u1Id = u1.getId();
			int u2Id = u2.getId();
			if (u1Id == u2Id) {
				/* An existed user. */
				user.setId(u1.getId());
			} else {
				RespStatus<String> resp =  RespEnum.custom(RespEnum.CF, "User name[" + name + "] and email[" + email + "] had been used.");
				return resp;
			}
		} else if (u1 == null && u2 == null) {
			/* New user. Do insert. */
			addUser(user);
		} else if (u1 != null) {
			RespStatus<String> resp =  RespEnum.custom(RespEnum.CF, "User name[" + name + "] had been used.");
			return resp;
		} else if (u2 != null) {
			RespStatus<String> resp =  RespEnum.custom(RespEnum.CF, "User email[" + email + "] had been used.");
			return resp;
		}
		return RespEnum.custom(RespEnum.OK, "Add user successfully.");
	}

}