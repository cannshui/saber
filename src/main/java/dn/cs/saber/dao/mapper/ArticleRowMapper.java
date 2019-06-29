package dn.cs.saber.dao.mapper;

import dn.cs.saber.domain.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author dn
 * @since 2.0.0
 */
public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article po = new Article();
        po.setId(rs.getInt("id"));
        po.setTitle(rs.getString("title"));
        po.setUserId(rs.getInt("author"));
        po.setPreview(rs.getString("preview"));
        po.setHitCount(rs.getInt("hit_count"));
        po.setReadCount(rs.getInt("read_count"));
        po.setUsefulCount(rs.getInt("useful_count"));
        po.setRatingCount(rs.getInt("rating_count"));
        po.setRating(rs.getFloat("rating"));
        po.setType(rs.getInt("type"));
        po.setTag(rs.getString("tag"));
        po.setCommentCount(rs.getInt("comment_count"));
        po.setRating(rs.getFloat("rating"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            po.setPublishTime(sdf.parse(rs.getString("cts")));
            po.setRePublishTime(sdf.parse(rs.getString("uts")));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return po;
    }

}
