package dn.cs.saber.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dn.cs.saber.dao.BoardDao;
import dn.cs.saber.dao.UserDao;
import dn.cs.saber.domain.Board;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.PageData;
import dn.cs.saber.vo.RespEnum;
import dn.cs.saber.vo.RespStatus;

/**
 * Services for Board.
 *
 * @author dn
 * @since 1.0.0
 */
@Controller
public class BoardController {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;

    /**
     * Show board view.
     */
    @RequestMapping("/board")
    public String getBoardView(Model model) {
        PageData<Board> pageData = new PageData<>();
        pageData.setPage(1);
        pageData.setSize(10);
        getPagedBoardView(1, pageData, model);
        return "board";
    }

    /**
     * Show board view. Pagination support.
     */
    @RequestMapping("/board/{page}")
    public String getPagedBoardView(@PathVariable("page") int page,
                                    @ModelAttribute PageData<Board> pageData, Model model) {
        pageData = this.boardDao.getPagedBoardMessages(pageData);
        model.addAttribute("pageData", pageData);
        return "board";
    }

    /**
     * TODO: The validation is weak. Should validate the full rules
     * so does in front-head.
     */
    @RequestMapping(value = "/board", method = RequestMethod.POST)
    @ResponseBody
    public Object addBoardMessage(@ModelAttribute Board boardMessage,
                                  HttpServletRequest request) {
        String content = StringUtils.trimWhitespace(boardMessage.getContent());
        if (!StringUtils.hasLength(content)) {
            return RespEnum.custom(RespEnum.LR, "Content should not be empty.");
        }
        String clientIp = ArticleController.getClientIpAddress(request);
        boardMessage.setIp(clientIp);
        User user = boardMessage.getUser();
        if (user == null) {
            /* Anonymous user. */
            user = new User();
            boardMessage.setUser(user);
        } else {
            String email = StringUtils.trimWhitespace(user.getEmail());
            String name = StringUtils.trimWhitespace(user.getName());
            boolean emailHasLen = StringUtils.hasLength(email);
            boolean nameHasLen = StringUtils.hasLength(name);
            if (!emailHasLen && !nameHasLen) {
                /* Anonymous user. */
                user.setId(0);
            } else {
                user.setIp(clientIp);
                RespStatus<String> resp = this.userDao.checkUser(user);
                if (resp.getCode() != 200) {
                    return RespEnum.custom(RespEnum.BR, "bad user");
                }
            }
        }
        this.boardDao.addBoardMessage(boardMessage);
        return boardMessage;
    }

}
