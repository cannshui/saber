<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<script type="text/javascript">
$(function() {
    new PageBar({total: ${pageData.total}, page: ${pageData.page}, size: ${pageData.size}, url: '${root}/index/'}).render();
});
</script>
<title>Index</title>
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/view/decorator/head.jsp" %>
    <div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; <a href="${root}/index">最新发布</a></div>
    <div class="dncs-body clearfix">
        <div class="left">
            <c:forEach items="${pageData.datas}" var="article">
            <div class="article">
                <h5 class="h5-title a-title">${article.title}</h5>
                <h6 class="a-author">
                    <span>${article.author.name}, </span>
                    <span><fmt:formatDate pattern="yyyy-MM-dd" value="${article.publishTime}" /></span>
                </h6>
                <div class="a-preview">${article.preview}</div>
                <div class="a-full-link"><a href="${root}/article/${article.id}">阅读正文 &gt;&gt;</a></div>
                <div class="a-other">
                    <div class="a-o-left">
                        <a class="a-type" href="${root}/index/type/${article.type}/page/1"><c:if test="${article.type == 1}">#原创</c:if><c:if test="${article.type == 2}">#译文</c:if></a>
                        <span class="a-o-l-tags">
                            <c:forEach items="${article.tags}" var="tag">
                            <a href="${root}/index/tag/${tag.id}/page/1">${tag.name}</a>
                            </c:forEach>
                        </span>
                    </div>
                    <div class="a-o-right">
                        <span>阅读 (${article.readCount})</span>
                        <span>评论 (${article.commentCount})</span>
                        <span>评分 (${article.ratingCount} 人, ${article.rating} 分)</span>
                    </div>
                </div>
            </div>
            </c:forEach>
            <div class="page-bar"></div>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>
