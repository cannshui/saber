package dn.cs.saber.dao.mapper;

import dn.cs.saber.domain.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author dn
 * @since 2.0.0
 */
public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment po = new Comment();
        po.setId(rs.getInt("id"));
        po.setArticle(rs.getInt("article"));
        po.setUserId(rs.getInt("user"));
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
