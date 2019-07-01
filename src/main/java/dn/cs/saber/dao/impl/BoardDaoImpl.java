package dn.cs.saber.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import dn.cs.saber.dao.UserDao;
import dn.cs.saber.dao.mapper.BoardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import dn.cs.saber.dao.BoardDao;
import dn.cs.saber.domain.Board;
import dn.cs.saber.vo.PageData;

/**
 * @author dn
 * @since 1.0.0
 */
@Repository
public class BoardDaoImpl implements BoardDao {

    private static final String SELECT_ROOT_BOARD_MESSAGE_COUNT_SQL = "select count(1) from board where reply is null";
    private static final String SELECT_ROOT_BOARD_MESSAGE_SQL = "select * from board where reply is null order by cts desc limit ?, ?";
    private static final String SELECT_BOARD_MESSAGE_REPLIES_SQL = "select * from board where reply = ? order by cts desc";
    private static final String INSERT_BOARD_SQL = "insert into board (user, content, reply, ip) values (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public PageData<Board> getPagedBoardMessages(PageData<Board> pageData) {
        Integer total = this.jdbcTemplate.queryForObject(SELECT_ROOT_BOARD_MESSAGE_COUNT_SQL, Integer.class);
        if (total == 0) {
            return pageData;
        }
        List<Board> boards = this.jdbcTemplate.query(SELECT_ROOT_BOARD_MESSAGE_SQL, new Object[]{pageData.getIndex(), pageData.getSize()}, new BoardRowMapper());
        boards.forEach(this::completeBoardMessage);
        pageData.setTotal(total);
        pageData.setDatas(boards);
        return pageData;
    }

    private void completeBoardMessage(Board board) {
        board.setUser(this.userDao.getUser(board.getUserId()));
        /* set replies */
        List<Board> replies = this.jdbcTemplate.query(SELECT_BOARD_MESSAGE_REPLIES_SQL, new Object[]{board.getId()}, new BoardRowMapper());
        board.setReplies(replies);
        replies.forEach(this::completeBoardMessage);
    }

    @Override
    public void addBoardMessage(Board boardMessage) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_BOARD_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, boardMessage.getUser().getId());
            ps.setString(2, boardMessage.getContent());
            Integer replyId = boardMessage.getReply();
            if (replyId == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, replyId);
            }
            ps.setString(4, boardMessage.getIp());
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        boardMessage.setId(id);
    }

}
