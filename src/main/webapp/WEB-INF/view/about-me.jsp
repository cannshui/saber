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
                <!--
                <li class="a-i-title">K.I.S.S.er</li>
                <li class="a-i-ex">做到 K.I.S.S 好难，需要有很高的水平，才能将事情变得 Simple 和 Stupid。</li>
                <li class="a-i-title">一个动漫迷</li>
                <li class="a-i-ex">请叫我，<span style="color: white;">伪宅·真·声控·梦想·一般男性</span>。</li>
                <li class="a-i-title">有只喵</li>
                <li class="a-i-ex">半夜，跳到我床头的那一生喵，啊，我真是<span title="《黑之契约者》琥珀思念黑时所说">“被它打败了”</span>。</li>
                -->
                <li class="a-i-title">Contact me</li>
                <li class="a-i-ex"><p><a target="_blank" href="http://weibo.com/3708729994">cannshui</a></p></li>
            </ul>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>
