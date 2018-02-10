<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<div class="r-a-block">
	<ul>
		<li class="r-l-title">导航</li>
		<li class="clearfix"><a href="${root}/index/cat/1/page/1"><span class="right-cat">原创</span><span class="right-cat-go">&gt;&gt;</span></a></li>
		<li class="clearfix"><a href="${root}/index/cat/2/page/1""><span class="right-cat">译文</span><span class="right-cat-go">&gt;&gt;</span></a></li>
		<li class="clearfix"><a href="${root}/article/new"><span class="right-cat">投稿</span><span class="right-cat-go">&gt;&gt;</span></a></li>
	</ul>
</div>
<div class="r-a-block">
	<h5 class="r-l-title">标签</h5>
	<div id="langTags" class="r-a-b-tags">
		<a href=""><code>Loading...</code></a>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$.get('${root}/tags', function(resp) {
		if (resp.code != 200) {
			console.log('Get tags failed.');
			return;
		}
		var jTags = $('#langTags');
		jTags.empty();
		var datas = resp.data;
		var s = '';
		for (var i = 0, l = datas.length; i < l; i++) {
			var data = datas[i];
			s += '<a href="${root}/index/tag/' + data.id + '/page/1"><code>' + data.name + '</code></a> ';
		}
		$(s).appendTo(jTags);
	});
});
</script>