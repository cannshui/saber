<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="dncs-head">
    <h3 class="h3-title h-logo" title="我们所过的每个平凡的日常，也许就是连续发生的奇迹...">
        <span class="h-l-nichijou"></span>
        <a href="${root}/">Saber::记录我想记录的 :)</a>
    </h3>
    <div class="menu">
        <ul>
            <li><a href="${root}/index/type/1/page/1">原创</a></li>
            <li><a href="${root}/index/type/2/page/1">译文</a></li>
            <li><a id="tagBtn" href="javascript:void(0)">标签</a></li>
            <li><a href="${root}/board">留言</a></li>
            <li><a href="${root}/about">关于</a></li>
            <li><a href="https://github.com/cannshui/saber" target="_blank" title="Fork me on Github">Github</a></li>
        </ul>
    </div>
    <div id="langTags" class="h-tags"></div>
</div>
<script type="text/javascript">
$(function() {
    var tags = JSON.parse(localStorage.getItem('saber-tags'));
    if (tags) {
        console.log('Clear it by yourself ^_^.');
        renderTags(tags);
    } else {
        $.get('${root}/tags', function(resp) {
            if (resp.code != 200) {
                console.log('Get tags failed.');
                return;
            }
            var tags = resp.data;
            renderTags(tags);
            localStorage.setItem('saber-tags', JSON.stringify(tags));
        });
    }

    function renderTags(tags) {
        var jTags = $('#langTags');
        var s = '';
        for (var i = 0, l = tags.length; i < l; i++) {
            var tag = tags[i];
            s += '<a href="${root}/index/tag/' + tag.id + '/page/1">' + tag.name + '</a>';
        }
        $(s).appendTo(jTags);
        /* after render, do bind */
        $('#tagBtn').click(function() {
            $('#langTags').slideToggle(200);
        });
    }
});
</script>
