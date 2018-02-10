package dn.cs.saber.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import dn.cs.saber.dao.BaseDao;
import dn.cs.saber.dao.BoardDao;
import dn.cs.saber.domain.Board;
import dn.cs.saber.vo.PageData;

@Repository
public class BoardDaoImpl extends BaseDao implements BoardDao {

	@Override
	public List<Board> getBoardMessages() {
		return super.getList("getBoardMessages");
	}

	@Override
	public PageData<Board> getPagedBoardMessages(PageData<Board> pageData) {
		Integer total = super.get("getLevel0BoardMessagesNumber");
		List<Board> datas = super.getList("getPagedBoardMessages", pageData);
		pageData.setTotal(total);
		pageData.setDatas(datas);
		return pageData;
	}

	@Override
	public void addBoardMessage(Board boardMessage) {
		int id = super.add("insertBoard", boardMessage);
		boardMessage.setId(id);
	}

}