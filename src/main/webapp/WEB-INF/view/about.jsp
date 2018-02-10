<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/decorator/common.jsp" %>
<title>About</title>
</head>
<body>
<div class="container">
	<%@ include file="/WEB-INF/view/decorator/head.jsp" %>
	<div class="step-bar"><a href="${root}/index">首页</a> &gt;&gt; <a href="${root}/about">About</a> &gt;&gt; <a href="${root}/about/me">ME</a></div>
	<div class="dncs-body clearfix">
		<div class="left about">
			<div class="about-info">
			<ul>
				<li class="a-i-title">K.I.S.S.</li>
				<li class="a-i-ex">Keep It Simple and Stupid.</li>
				<li class="a-i-title">基于 Markdown</li>
				<li class="a-i-ex">
					<ul>
						<li>基于 Markdown 标准语法编写文章，然后导出 HTML 格式。
						<li>请去掉 <code>&lt;html&gt;</code> 标记，整个 <code>&lt;head&gt;</code> 标记内容，和 <code>&lt;body&gt;</code> 标记。
					即，<strong>只保留</strong> <code>&lt;body&gt;</code> 内的正文内容，目的在于<strong>避免</strong>样式冲突。
						<li>通过<strong>导航 &gt;&gt; <a href="${root}/article/new">投稿</a></strong>发布到本站。
						<li>本站暂不考虑提供博客文章的在线编写方式。但在后续版本中考虑接受 Markdown 输入，如评论、文章简介、留言处。
					</ul>
				</li>
				<li class="a-i-title">用到 CSS3</li>
				<li class="a-i-ex">
					用到 CSS3， 这意味着，需要<strong>现代</strong>浏览器进行访问，才能看到效果。
				</li>
				<li class="a-i-title">名字 & 邮箱</li>
				<li class="a-i-ex">
					名字和邮箱，可以想象成您浏览本站的<strong>用户名</strong>和<strong>密码</strong>，名字和邮箱必须是唯一的，并且相对应。
					他们会出现在文章评论和留言板中。<span title="以一个程序员的**红心**保证！">只会用作交流之用。</span>
					后续版本中，学习利用诸如新浪微博等帐号登录的实现方式。
				</li>
				<li class="a-i-title">留言板</li>
				<li class="a-i-ex">
					留言板对于任何人开放。若你愿意的话，可以在留言的时候留下名字和邮箱，衷心希望你这样。
					留言板里，你可以随意发挥，可以尽情对本站进行吐槽，指出错误，提出改进意见。
				</li>
				<li class="a-i-title">CTRL + ALT + F</li>
				<li class="a-i-ex">
					<code>CTRL + ALT + F</code> 组合键扩大内容区域，隐藏网站头、尾和右侧导航栏。
					回复原样请刷新。:)
				</li>
				<li class="a-i-title">感谢</li>
				<li class="a-i-ex">
					<ul>
						<!--
						<li><a target="_blank" href="http://www.openshift.com">OpenShift</a> 的空间。
						<li><a target="_blank" href="http://www.dot.tk">Dot TK</a> 的域名。
						-->
						<li><a target="_blank" href="http://zh.wikipedia.org/zh-cn/%E9%98%B2%E7%81%AB%E9%95%BF%E5%9F%8E" title="呵呵哒">GFW</a>.
					</ul>
				</li>
				<li class="a-i-title">Contributers</li>
				<li class="a-i-ex">
					<ul>
						<li><a target="_blank" href="http://weibo.com/Lennynan">Lenny</a>
					</ul>
				</li>
			</ul>
			</div>
		</div>
		<div class="right"><jsp:include page="/WEB-INF/view/decorator/right.jsp"></jsp:include></div>
	</div>
	<%@ include file="/WEB-INF/view/decorator/foot.jsp" %>
</div>
</body>
</html>