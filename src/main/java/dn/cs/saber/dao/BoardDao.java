package dn.cs.saber.dao;

import java.util.List;

import dn.cs.saber.domain.Board;
import dn.cs.saber.vo.PageData;

/**
 * Data Access Object for board.
 * <br> 1. Query board messages.
 * <br> 2. Add board messages.
 * 
 * @author Nen Den
 */
public interface BoardDao {

	/**
	 * Query all the board messages in insertion order.
	 * **For Test Only**.
	 * @return
	 */
	List<Board> getBoardMessages();

	/**
	 * Query paged board messages. Order by update time DESC.
	 * 
	 * @param pageData
	 * @return
	 */
	PageData<Board> getPagedBoardMessages(PageData<Board> pageData);

	/**
	 * Insert a board message.
	 * 
	 * @param boardMessage
	 */
	void addBoardMessage(Board boardMessage);

	
}
