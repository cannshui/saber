<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<title>About me</title>
</head>
<body>
<div class="container">
    <%@ include file="/WEB-INF/view/decorator/head.jsp" %>
    <div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; <a href="${root}/about">About</a> &gt;&gt; <a href="${root}/about/me">ME</a></div>
    <div class="dncs-body clearfix">
        <div class="left about">
            <div class="about-info">
            <ul>
                <li class="a-i-title">一个程序员</li>
                <li class="a-i-ex">作为一个蹩脚的程序员，相当成功。</li>
                <li class="a-i-title">Contact me</li>
                <li class="a-i-ex"><p><a target="_blank" href="https://github.com/cannshui">cannshui</a></p></li>
            </ul>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>
