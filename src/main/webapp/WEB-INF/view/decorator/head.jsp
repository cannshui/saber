<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="dncs-head">
	<h3 class="h3-title h-logo" title="我们所过的每个平凡的日常，也许就是连续发生的奇迹...">
		<div class="h-l-nichijou"></div>
		<a href="${root}/index">Saber::记录我想记录的 :)</a>
	</h3>
	<div class="menu">
		<ul>
			<li><a href="${root}/about">About</a></li>
			<li><a href="${root}/board">留言板</a></li>
			<li>
				<form id="gF" action="http://guge.io/#q=" target="_blank" method="GET" onsubmit="(function(f){v=f.q.value;if(v)window.open(f.action+v+'+site:'+f.s.value)})(document.forms[0]);return false;">
					<input id="q" name="q" type="text" placeholder="google search" title="Press `enter` for submitting." style="border: 0; width: 120px;" />
					<input id="s" type="hidden" name="sitesearch" value="saber-dncs.rhclould.com">
				</form>
			</li>
		</ul>
	</div>
</div>