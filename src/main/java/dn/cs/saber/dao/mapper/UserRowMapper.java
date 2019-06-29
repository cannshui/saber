package dn.cs.saber.dao.mapper;

import dn.cs.saber.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author dn
 * @since 2.0.0
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User po = new User();
        po.setId(rs.getInt("id"));
        po.setName(rs.getString("name"));
        po.setEmail(rs.getString("email"));
        po.setIp(rs.getString("ip"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            po.setCts(sdf.parse(rs.getString("cts")));
            po.setUts(sdf.parse(rs.getString("uts")));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return po;
    }

}
