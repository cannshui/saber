package dn.cs.saber.dao;

import dn.cs.saber.domain.Board;
import dn.cs.saber.vo.PageData;

/**
 * Data Access Object for board.
 * <br> 1. Query board messages.
 * <br> 2. Add board messages.
 *
 * @author dn
 * @since 1.0.0
 */
public interface BoardDao {

    /**
     * Query paged board messages. Order by update time DESC.
     *
     * @param pageData pageData
     * @return PageData
     */
    PageData<Board> getPagedBoardMessages(PageData<Board> pageData);

    /**
     * Insert a board message.
     *
     * @param boardMessage boardMessage
     */
    void addBoardMessage(Board boardMessage);

}
