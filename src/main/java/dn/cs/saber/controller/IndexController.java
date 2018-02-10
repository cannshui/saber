package dn.cs.saber.controller;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dn.cs.saber.dao.ArticleDao;
import dn.cs.saber.domain.Article;
import dn.cs.saber.vo.PageData;

/**
 * Kinds of ways for searching articles.
 * <p> AND some other simple services, contains:
 * <br> 1. about infos.
 * <br> 2. error redirect while error happens. two types, 404 and others.
 * 
 * @author Nen Den
 */
@Controller
public class IndexController {

	private static final Logger LOGGER = Logger.getLogger(IndexController.class);

	@Autowired
	private ArticleDao articleDao;

	/**
	 * Index page, Same to '/index/1'.
	 * 
	 * @param pageData
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndexView(@ModelAttribute PageData<Article> pageData,
			Model model) {
		getPagedArticles(1, pageData, model);
		return "index";
	}

	/**
	 * List latest articles.
	 * <p>
	 * Pagination support.
	 * 
	 * @param page
	 *            Current page number.
	 * @param pageData
	 * @param model
	 * @return
	 */
	@RequestMapping("index/{page}")
	public String getPagedArticles(@PathVariable("page") int page,
			@ModelAttribute PageData<Article> pageData, Model model) {
		pageData.setPage(page);
		pageData = this.articleDao.getArticles(pageData);
		model.addAttribute("pageData", pageData);
		return "index";
	}

	/**
	 * Filter articles by type[(1(Original))|(2(Translation))] and
	 * tag[Unix|Linux|Java|...].
	 * <p> Pagination support.
	 * 
	 * @param cat (1|2),(Unix|Linux|Java[|...])
	 * @param page Current page.
	 * @param pageData
	 * @param model
	 * @return
	 */
	@RequestMapping("/index/cat/{cat}/page/{page}")
	public String getPagedArticlesByCat(@PathVariable("cat") String cat,
			@PathVariable("page") int page,
			@ModelAttribute PageData<Article> pageData, Model model) {
		String type = null;
		String tag = null;
		String[] arr = cat.split(",");
		if (arr.length == 2) {
			type = arr[1];
			cat = arr[2];
		} else {
			if (cat.equals("1") || cat.equals("2")) {
				type = cat;
			} else {
				tag = cat;
			}
		}
		pageData = this.articleDao.getPagedArticlesByCat(pageData, type, tag);
		model.addAttribute("pageData", pageData);
		return "index";
	}

	/**
	 * Filter articles only by tag[Unix|Linux|Java|...].
	 * <p> Pagination support.
	 * 
	 * @param tag Unix|Linux|Java[|...]
	 * @param page Current page.
	 * @param pageData
	 * @param model
	 * @return
	 */
	@RequestMapping("/index/tag/{tag}/page/{page}")
	public String getPagedArticlesByTag(@PathVariable("tag") String tag,
			@PathVariable("page") int page,
			@ModelAttribute PageData<Article> pageData, Model model) {
		pageData = this.articleDao.getPagedArticlesByCat(pageData, null, tag);
		model.addAttribute("pageData", pageData);
		return "index";
	}

	/**
	 * Filter articles only by type[(1(Original))|(2(Translation))].
	 * <p> Pagination support.
	 * 
	 * @param type 1(Original))|(2(Translation)
	 * @param page Current page.
	 * @param pageData
	 * @param model
	 * @return
	 */
	@RequestMapping("/index/type/{type}/page/{page}")
	public String getPagedArticlesByType(@PathVariable("type") String type,
			@PathVariable("page") int page,
			@ModelAttribute PageData<Article> pageData, Model model) {
		pageData = this.articleDao.getPagedArticlesByCat(pageData, type, null);
		model.addAttribute("pageData", pageData);
		return "index";
	}

	/**
	 * Something about my site.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/about")
	public String getAboutView() {
		return "about";
	}

	/**
	 * Something about me.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/me")
	public String getAboutMeView() {
		return "about-me";
	}

	/**
	 * Dangerous because treat code by path variable. This may cause a
	 * malicious attack by illegal code value. Although not so dangerous.
	 * 
	 * <p> TODO: check on code. OR use explicit url like /error/404, /error/500 ...
	 * 
	 * @param code
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("error/{code}")
	public String redirectError(@PathVariable("code") String code,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		/* retrieve some useful information from the request */
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		String exceptionMessage = getExceptionMessage(throwable, statusCode);

		String requestUri = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		String message = MessageFormat.format(
				" {0} returned for {1} with message {2}.", statusCode,
				requestUri, exceptionMessage);

		model.addAttribute("errorMessage", message);
		LOGGER.error(message);
		return "error/" + code;
	}

	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		String str = "";
		if (throwable != null) {
			throwable.printStackTrace();
			str += throwable.getMessage();
		}
		if (statusCode != null) {
			HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
			str += httpStatus.getReasonPhrase();
		}
		return str;
	}

}