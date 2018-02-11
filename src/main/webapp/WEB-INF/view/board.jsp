<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<script type="text/javascript">
$(function() {
	new PageBar({total: ${pageData.total}, page: ${pageData.page}, size: ${pageData.size}}).render();
	DNCS.set('_jcb', $('#commentBox')).set('_jun', $('#userName')).set('_jue', $('#userEmail'))
		.set('_jcc', $("#commentCnt")).set('_ab', true).set('_jcs', $('#comments'))
		.set('_curl', '${root}/board');
	DNCS.applyRules({el: $('#userName'), required: false, rules: [{validType: 'length[6,10]', message: '用户名长度在 6 ~ 10 之间'}]});
	DNCS.applyRules({el: $('#userEmail'), required: false, rules: [{validType: 'email', message: '输入你的邮箱以方便交流。请放心，一定为你保密。'}]});

	$("#submitBtn").click(submitComment);
	$('#comments').on('click', '.p-reply-btn', replyComment);
});
</script>
<title>Board</title>
</head>
<body>
<div class="container">
	<%@ include file="/WEB-INF/view/decorator/head.jsp" %>
	<div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; <a href="${root}/board">一句话时间</a></div>
	<div class="dncs-body clearfix">
		<div class="left board">
			<div class="new-comment">
				<a name="new-comment"></a>
				<h3>留言</h3>
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
							<a id="submitBtn" class=submit-btn>发表</a>
						</div>
					</div>
				</div>
			</div>
			<div id="comments" class="comments">
				<h3>留言 / 共 <span id="commentCnt">${pageData.total}</span> 条</h3>
				<c:forEach items="${pageData.datas}" var="message">
				<div class="comment">
					<p class="c-poster clearfix">
						<span class="p-name"><span class="p-r-name"><c:if test="${not empty message.user.name}">${message.user.name}</c:if><c:if test="${empty message.user.name}">匿名用户</c:if></span></span>
						<span class="p-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${message.cts}" /></span>
						<a class="p-reply-btn" href="#new-comment" rel="${message.id}">回复</a>
					</p>
					<p class="c-content">${message.content}</p>
					<%-- <c:if test="${not empty comment.replys}"> --%>
					<div class="c-replies">
						<c:forEach items="${message.replys}" var="reply">
							<blockquote>
								<p class="c-poster clearfix">
									<c:if test="${not empty reply.user.name}"><c:set var="replyUserName" value="${reply.user.name}" /></c:if>
									<c:if test="${empty reply.user.name}"><c:set var="replyUserName" value="匿名用户" /></c:if>
									<c:if test="${not empty message.user.name}"><c:set var="messageUserName" value="${message.user.name}" /></c:if>
									<c:if test="${empty message.user.name}"><c:set var="messageUserName" value="匿名用户" /></c:if>
									<span class="p-name"><span class="p-r-name">${replyUserName}</span>@${messageUserName}</span>
									<span class="p-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${reply.cts}" /></span>
									<a class="p-reply-btn" href="#new-comment" rel="${reply.id}">回复</a>
								</p>
								<p class="c-content">${reply.content}</p>
							</blockquote>
						</c:forEach>
					</div>
					<%-- </c:if> --%>
				</div>
				</c:forEach>
				<div class="page-bar"></div>
			</div>
		</div>
		<div class="right"><jsp:include page="/WEB-INF/view/decorator/right.jsp"></jsp:include></div>
	</div>
	<%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>