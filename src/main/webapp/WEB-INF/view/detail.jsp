<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<script type="text/javascript">
$(function() {
    new ArticleCatalog();

    var _rvi = DNCS.fmtNum('${article.rating}', 1);
    DNCS.set('_jcb', $('#commentBox')).set('_jun', $('#userName')).set('_jue', $('#userEmail'))
        .set('_jcc', $("#commentCnt")).set('_jcs', $('#comments')).set('_cminl', 20).set('_cmaxl', 500)
        .set('_curl', '${root}/article/${id}/comment').set('_ai', '${id}');
    DNCS.set('_jars', $('.a-rating')).set('_jarv', $('.a-rating-v')).set('_jrc', $('.rating-count'))
        .set('_rurl', '${root}/article/${article.id}/rate').set('_rvi', _rvi);
    DNCS.applyRules({el: $('#userName'), required: true, rules: [{validType: 'length[6,10]', message: '用户名长度在 6 ~ 10 之间'}]});
    DNCS.applyRules({el: $('#userEmail'), required: true, rules: [{validType: 'email', message: '输入你的邮箱以方便交流。请放心，一定为你保密。'}]});
    DNCS.applyRules({el: $('#commentBox'), required: true, rules: [{validType: 'length[20,500]', message: '请输入评论内容，长度在 20 ~ 500 之间。'}]});

    renderRating(_rvi);

    var jARating = DNCS.get('_jars');
    var jARV = DNCS.get('_jarv');

    setTimeout(function() {
        DNCS.xhrPut('${root}/article/${article.id}');
        var jRC = $('.a-b-readCount');
        var rc = jRC.text();
        jRC.text(parseInt(rc) + 1);
        jARating.click(increaseRating);
        jARV.click(decreaseRating);
    }, 60 * 1000);

    jARating.mouseover(function() {
        var rv = jARating.index(this) + 1;
        renderRating(rv);
    });

    jARating.mouseout(function() {
        jARating.css('background', 'grey');
        var _rv = DNCS.get('_rv') || DNCS.get('_rvi');
        renderRating(_rv);
    });

    $("#submitBtn").click(submitComment);
    $('#comments').on('click', '.p-reply-btn', replyComment);
});
</script>
<title>${article.title}</title>
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/view/decorator/head.jsp" %>
    <div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; 文章详情</div>
    <div class="dncs-body clearfix">
        <div class="left detail">
            <div class="d-content">
                <fmt:formatDate value="${article.publishTime}" pattern="yyyy" var="articlePath" />
                <jsp:include page="${articlePath}/${article.id}.html" />
                <div class="author-bar">
                    <span>${article.author.name}</span>
                    <span><c:if test="${article.type == 1}">原创</c:if><c:if test="${article.type == 2}">译文</c:if></span>
                    <span><fmt:formatDate pattern="yyyy-MM-dd" value="${article.publishTime}" /> Create.</span>
                    <span><fmt:formatDate pattern="yyyy-MM-dd" value="${article.rePublishTime}" /> Update.</span>
                    <span><a class="a-b-readCount">${article.readCount}</a> 次阅读</span>
                    <span><a class="rating-count">${article.ratingCount}</a> 人评分</span>
                    <span><label>平均得分</label><a class="a-rating-v" title="clear my rate?">${article.rating}</a></span>
                    <span>
                        <label>评分</label>
                        <a class="a-rating" title="很差"></a>
                        <a class="a-rating" title="一般"></a>
                        <a class="a-rating" title="普通"></a>
                        <a class="a-rating" title="有用"></a>
                        <a class="a-rating" title="很好"></a>
                    </span>
                </div>
            </div>
            <div id="comments" class="comments">
                <h3>评论 / 共 <span id="commentCnt">${article.commentCount}</span> 条</h3>
                <c:forEach items="${comments}" var="comment">
                <div class="comment">
                    <p class="c-poster clearfix">
                        <span class="p-name"><span class="p-r-name">${comment.user.name}</span></span>
                        <span class="p-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${comment.cts}" /></span>
                        <a class="p-reply-btn" href="#new-comment" rel="${comment.id}">回复</a>
                    </p>
                    <p class="c-content">${comment.content}</p>
                    <div class="c-replies">
                        <c:forEach items="${comment.replies}" var="reply">
                            <blockquote>
                                <p class="c-poster clearfix">
                                    <span class="p-name"><span class="p-r-name">${reply.user.name}</span>@${reply.replies[0].user.name}</span>
                                    <span class="p-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${reply.cts}" /></span>
                                    <a class="p-reply-btn" href="#new-comment" rel="${reply.id}">回复</a>
                                </p>
                                <p class="c-content">${reply.content}</p>
                            </blockquote>
                        </c:forEach>
                    </div>
                </div>
                </c:forEach>
            </div>
            <div class="new-comment">
                <a name="new-comment"></a>
                <h3>写评论</h3>
                <div>
                    <textarea id="commentBox" class="comment-box" rows="" cols=""></textarea>
                    <div class="poster">
                        <div class="form-group">
                            <label for="userName">你的名字</label>
                            <input id="userName" class="f-g-i-box" type="text" placeholder="你的名字" title="Enter your prefer name." value=""/>
                        </div>
                        <div class="form-group">
                            <label for="userName">你的邮箱</label>
                            <input id="userEmail" class="f-g-i-box" type="text" placeholder="你的邮箱" title="Enter your real email." value=""/>
                        </div>
                        <div class="form-group">
                            <a id="submitBtn" class="submit-btn">发表</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>
