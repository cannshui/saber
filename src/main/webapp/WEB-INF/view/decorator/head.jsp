<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="dncs-head">
    <h3 class="h3-title h-logo" title="我们所过的每个平凡的日常，也许就是连续发生的奇迹...">
        <div class="h-l-nichijou"></div>
        <a href="${root}/index">Saber::记录我想记录的 :)</a>
    </h3>
    <div class="menu">
        <ul>
            <li><a href="${root}/index/type/1/page/1">原创</a></li>
            <li><a href="${root}/index/type/2/page/1">译文</a></li>
            <li><a id="tagBtn" href="javascript:void(0)">TAG</a></li>
            <li><a href="${root}/about">About</a></li>
            <li><a href="${root}/board">留言板</a></li>
        </ul>
    </div>
    <div id="langTags" class="h-tags"></div>
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

    $('#tagBtn').click(function() {
        $('#langTags').slideToggle(100);
    });
});
</script>
