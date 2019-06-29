package dn.cs.saber.dao.impl;

import dn.cs.saber.dao.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import dn.cs.saber.dao.UserDao;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.RespEnum;
import dn.cs.saber.vo.RespStatus;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author dn
 * @since 1.0.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String SELECT_USER_SQL = "select * from user where id = ?";
    private static final String SELECT_USER_BY_EMAIL_SQL = "select * from user where email = ?";
    private static final String SELECT_USER_BY_NAME_SQL = "select * from user where name = ?";
    private static final String INSERT_USER_SQL = "insert into user (name, email, ip) values (?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUser(int id) {
        return DataAccessUtils.singleResult(this.jdbcTemplate.query(SELECT_USER_SQL, new Object[]{id}, new UserRowMapper()));
    }

    @Override
    public User getUserByEmail(String email) {
        return DataAccessUtils.singleResult(this.jdbcTemplate.query(SELECT_USER_BY_EMAIL_SQL, new Object[]{email}, new UserRowMapper()));
    }

    @Override
    public User getUserByName(String name) {
        return DataAccessUtils.singleResult(this.jdbcTemplate.query(SELECT_USER_BY_NAME_SQL, new Object[]{name}, new UserRowMapper()));
    }

    @Override
    public void addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getIp());
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        user.setId(id);
    }

    @Override
    public RespStatus<String> checkUser(User user) {
        if (user == null) {
            return RespEnum.custom(RespEnum.NR, "User instance can not be null.");
        }
        String name = StringUtils.trimWhitespace(user.getName());
        String email = StringUtils.trimWhitespace(user.getEmail());
        if (!StringUtils.hasLength(name)) {
            return RespEnum.custom(RespEnum.LR, "User name is empty.");
        }
        if (!StringUtils.hasLength(email)) {
            return RespEnum.custom(RespEnum.LR, "User email is empty.");
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
                return RespEnum.custom(RespEnum.CF, "User name[" + name + "] and email[" + email + "] had been used.");
            }
        } else if (u1 == null && u2 == null) {
            /* New user. Do insert. */
            addUser(user);
        } else if (u1 != null) {
            return RespEnum.custom(RespEnum.CF, "User name[" + name + "] had been used.");
        } else if (u2 != null) {
            return RespEnum.custom(RespEnum.CF, "User email[" + email + "] had been used.");
        }
        return RespEnum.custom(RespEnum.OK, "Add user successfully.");
    }

}
