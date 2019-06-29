package dn.cs.saber.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dn.cs.saber.dao.ArticleDao;
import dn.cs.saber.dao.UserDao;
import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.Comment;
import dn.cs.saber.domain.Tag;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.RespEnum;
import dn.cs.saber.vo.RespStatus;

/**
 * Controller for things about article.
 *
 * @author dn
 * @since 1.0.0
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    /**
     * Show a article.
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String getArticleView(@PathVariable("id") int id, Model model) {
        this.articleDao.increaseHitCount(id);
        Article article = this.articleDao.getArticle(id);
        model.addAttribute("article", article);
        model.addAttribute("comments", this.articleDao.getArticleComments(id));
        return "detail";
    }

    /**
     * Rating for a article.
     */
    @RequestMapping(value = "article/{id}/rate", method = RequestMethod.POST, headers = {"Accept=application/json"})
    @ResponseBody
    public RespStatus<String> increaseArticleRating(@PathVariable("id") int id,
                                                    @RequestParam("r") int rating) {
        this.articleDao.increaseRating(id, rating);
        return RespEnum.OK.resp;
    }

    /**
     * Clear a rating for a article.
     */
    @RequestMapping(value = "article/{id}/rate", method = RequestMethod.PUT, headers = {"Accept=application/json"})
    @ResponseBody
    public RespStatus<String> decreaseArticleRating(@PathVariable("id") int id,
                                                    @RequestParam("r") int rating) {
        this.articleDao.decreaseRating(id, rating);
        return RespEnum.OK.resp;
    }

    /**
     * Increase read count.
     */
    @RequestMapping(value = "article/{id}", method = RequestMethod.PUT, headers = {"Accept=application/json"})
    @ResponseBody
    public RespStatus<String> increaseReadCount(@PathVariable("id") int id) {
        this.articleDao.increaseReadCount(id);
        return RespEnum.OK.resp;
    }

    /**
     * Add a comment.
     */
    @RequestMapping(value = "article/{id}/comment", method = RequestMethod.POST, headers = {"Accept=application/json"})
    @ResponseBody
    public Object addComment(@PathVariable("id") int id,
                             @ModelAttribute Comment comment, HttpServletRequest request) {
        User user = comment.getUser();
        user.setIp(getClientIpAddress(request));
        /* User id would be set. */
        RespStatus<String> resp = this.userDao.checkUser(user);
        if (resp.getCode() != 200) {
            throw new IllegalArgumentException(resp.toString());
        }
        this.articleDao.addComment(comment);
        return comment;
    }

    /**
     * Get all the tags.
     */
    @RequestMapping(value = "tags", method = RequestMethod.GET, headers = {"Accept=application/json"})
    @ResponseBody
    public RespStatus<List<Tag>> getTags() {
        List<Tag> tags = this.articleDao.getCachedTags();
        return RespEnum.custom(RespEnum.OK, tags);
    }

    /**
     * Show create article view.
     */
    @RequestMapping("/article/new")
    public String getCreatorView(Model model) {
        List<Tag> tags = this.articleDao.getCachedTags();
        model.addAttribute("tags", tags);
        return "creator";
    }

    /**
     * Save a article.
     * No transaction support for saving article detail to database and
     * file to file system.
     */
    @RequestMapping(value = "/article/new", method = RequestMethod.POST)
    public String addNewArticle(@ModelAttribute("article") Article article,
                                @RequestParam("mdFile") MultipartFile file,
                                HttpServletRequest request, Model model) {
        if (file.isEmpty()) {
            RespStatus<String> resp = RespEnum.custom(RespEnum.LR, "File content can not be empty.");
            model.addAttribute("resp", resp);
            return "creator";
        }
        if (file.getSize() < 2048) {
            RespStatus<String> resp = RespEnum.custom(RespEnum.LTL, "Are you sure this file size is okay?");
            model.addAttribute("resp", resp);
            return "creator";
        }
        User author = article.getAuthor();
        author.setIp(getClientIpAddress(request));
        RespStatus<String> resp = this.userDao.checkUser(author);
        if (resp.getCode() != 200) {
            model.addAttribute("resp", resp);
            return "creator";
        }
        this.articleDao.addArticle(article);
        int id = article.getId();

        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        String rootPath = request.getServletContext().getRealPath("/");
        String path = String.format("%s/WEB-INF/view/%d", rootPath, y);
        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            f.mkdirs();
        }
        try {
            file.transferTo(new File(path, id + ".html"));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "redirect:new";
    }

    private static final String[] HEADERS_TO_TRY = {"X-Forwarded-For",
            "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0
                    && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}