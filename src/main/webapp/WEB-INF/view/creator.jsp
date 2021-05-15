<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<script type="text/javascript">
$(function() {
    $("#form")[0].reset();
    var resp = '${resp}';
    console.log(resp);

    DNCS.applyRules({el: $('#title'), required: true, rules: [{validType: 'length[4,200]', message: '标题长度在 4 ~ 200 之间'}]});
    DNCS.applyRules({el: $('#preview'), required: true, rules: [{validType: 'length[50,1000]', message: '简介内容需为 50 ～ 1000 字。'}]});
    DNCS.applyRules({el: $('#mdFile'), required: true, rules: [{validType: 'require', message: '请选择基于 Markdown/html 标准语法编写的文章。'}]});
    DNCS.applyRules({el: $('#userName'), required: true, rules: [{validType: 'length[6,10]', message: '用户名长度在 6 ~ 10 之间'}]});
    DNCS.applyRules({el: $('#userEmail'), required: true, rules: [{validType: 'email', message: '输入你的邮箱以方便交流。请放心，一定为你保密。'}]});

    $('#submitBtn').click(function() {
        if ($('.a-n-tag:checked').length === 0) {
            console.log('未设置 tag')
            return false;
        }
        if(!DNCS.validate()) {
            return false;
        }
    });
});
</script>
<title>New Article</title>
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/view/decorator/head.jsp" %>
    <div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; <a href="${root}/article/new">投稿</a></div>
    <div class="dncs-body clearfix">
        <div class="left creator">
            <form id="form" action="${root}/article/new" method="POST" enctype="multipart/form-data">
                <h5>标题</h5>
                <input id="title" class="a-n-title" name="title" type="text" value="${article.title}" />
                <h5>简介</h5>
                <textarea id="preview" class="a-n-preview" name="preview" rows="" cols="">${article.preview}</textarea>
                <h5 id="tag">选项</h5>
                <div class="option">
                    <input name="type" type="radio" value="1" <c:if test="${article.type == 1}">checked="checked"</c:if> /><span>原创</span>
                    <input name="type" type="radio" value="2" <c:if test="${article.type == 2}">checked="checked"</c:if> /><span>译文</span>
                    <br />
                    <c:forEach items="${tags}" var="tag" varStatus="vs">
                        <input class="a-n-tag" name="tag" type="checkbox" value="${tag.id}"
                                <c:forEach items="${article.tag.split(',')}" var="chosenTag">
                                    <fmt:parseNumber var="chosenTagId" value="${chosenTag}" integerOnly="true" />
                                    <c:if test="${chosenTagId == tag.id}">checked="checked"</c:if>
                                </c:forEach>
                        /><span>${tag.name}</span>
                        <c:if test="${vs.count % 10 == 0}"><br /></c:if>
                    </c:forEach>
                </div>
                <h5>稿件</h5>
                <input id="mdFile" class="a-n-title" name="mdFile" type="file" value=""/>
                <div class="poster">
                    <div class="form-group">
                        <label for="userName">你的名字</label>
                        <input id="userName" class="f-g-i-box" name="author.name" type="text" placeholder="你的名字" title="Enter your prefer name." value="${article.author.name}"/>
                    </div>
                    <div class="form-group">
                        <label for="userName">你的邮箱</label>
                        <input id="userEmail" class="f-g-i-box" name="author.email" type="text" placeholder="你的邮箱" title="Enter your real email." value="${article.author.email}"/>
                    </div>
                    <div class="form-group">
                        <input id="submitBtn" class="submit-btn" type="submit" value="发表">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>
