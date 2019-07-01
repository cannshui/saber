package dn.cs.saber.dao.mapper;

import dn.cs.saber.domain.Board;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author dn
 * @since 2.0.0
 */
public class BoardRowMapper implements RowMapper<Board> {

    @Override
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
        Board po = new Board();
        po.setId(rs.getInt("id"));
        po.setUserId(rs.getInt("user"));
        po.setIp(rs.getString("ip"));
        po.setContent(rs.getString("content"));
        Object replyId = rs.getObject("reply");
        if (replyId != null) {
            po.setReply((int) replyId);
        }
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
