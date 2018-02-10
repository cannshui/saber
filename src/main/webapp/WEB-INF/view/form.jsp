<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${root}/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${root}/css/table.css" />
<style type="text/css">
	.asterisk {
		color: red;
	}
</style>

<script type="text/javascript" src="${root}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${root}/js/RESTful-table.js"></script>
<script type="text/javascript">
$(function() {
	
	var config = {
		"dataUrl": "${root}/simple/hello"
	};
	
	//restfulTable(config);
	
	var columns = [
		{field: "id", width: 10, checkbox: true},
		{field: "userName", name: "Name", width: 20},
		{field: "age", name: "Age", width: 20},
		{field: "oper", name: 'operation&nbsp;&nbsp;<a id="addRow" href="javascript: void(0)">New</a>&nbsp;&nbsp;<a id="deleteRows" href="javascript: void(0)">Delete</a>', formatter: function(row) {
			return '<a href="javascript: void(0)" class="reset-btn" rel="' + row.id + '"><span>Reset</span></a>&nbsp;&nbsp;' +
			'<a href="javascript: void(0)" class="save-btn" rel="' + row.id + '"><span>Save</span></a>&nbsp;&nbsp;' +
			'<a href="javascript: void(0)" class="delete-btn" rel="' + row.id + '"><span>Delete</span></a>';
		}}
	];
	
	/* Table model. */
	var gData;
	$.ajax({
		type: "GET",
		async: false,
		url: "${root}/simple/hello",
		success: function(data) {
			gData = data
		}
	});
	
	
	var gI = gData.length - 1;
	
	console.log(49, gData, gI);
	
	function toRow(row, i) {
		var rowStr = '<tr id="row' + i + '">';
		for (var i = 0, l = columns.length; i < l; i++) {
			var column = columns[i];
			var cellStr = '<td>';
			if (column.formatter) {
				cellStr += column.formatter(row);
			} else {
				var k = column.field;
				var v = row[k];
				if (!v) {
					v = '';
				}
				if (column.checkbox) {
					cellStr += '<input class="choose-row" name="' + k + '" type="checkbox" value="' + v + '"/>';
				} else {
					cellStr += '<input class="box" name="' + k + '" type="text" value="' + v + '"/>';
				}
			}
			cellStr += '</td>';
			rowStr += cellStr;
		}
		rowStr += '</tr>';
		return rowStr;
	}
	
	var tableHtml = '<thead><tr>';
	
	var tableHead = '<thead><tr>';
	for (var i = 0, l = columns.length; i < l; i++) {
		var column = columns[i];
		var c = column.name;
		if (column.checkbox) {
			c = '<input class="choose-all" type="checkbox"/>';
		}
		var s = '';
		if (column.width) {
			s = 'style="width: ' + column.width + 'px;"';
		}
		tableHead += '<th ' + s + '>' + c + '</th>';
	}
	tableHead += '</tr></thead>';
		
	var tableBody = '<tbody>';
	for (var i = 0, l = gData.length; i < l; i++) {
		var row = gData[i];
		tableBody += toRow(row, i);
		
	}
	tableBody += '</tbody>'
	
	console.log(tableHead);
	
	var jRestTable = $('#datatable');
	$(tableHead).appendTo(jRestTable);
	$(tableBody).appendTo(jRestTable);
	
	$('.reset-btn').click(function() {
		var jTr = $(this).closest("tr");
		var i = jTr.attr("id").substring(3);
		var model = gData[i];
		var jBoxs = jTr.find(".box");
		for (var j = 0, l = jBoxs.length; j < l; j++) {
			var jBox = jBoxs.eq(j);
			jBox.val(model[jBox.attr("name")]);
		}
	});
	
	$("#datatable").on("click", ".save-btn", function() {
	/* $(".save-btn").click(function() { */
		
		console.log(122, "fire...");
	
		var jThis = $(this);
		var jTr = jThis.closest("tr");
		var i = jTr.attr("id").substring(3);
		
		var model = gData[i];
		var method = model ? 'PUT' : 'POST';
		
		console.log(130, i, model);
		
		var jBoxs = jTr.find(".box");
		
		var params = {};
		for (var j = 0, l = jBoxs.length; j < l; j++) {
			
			var jBox = jBoxs.eq(j);
			var nV = jBox.val();
			var k = jBox.attr("name");
			
			if (model) {
				/* ("" == undefined) = false, and ("" != undefined) = false "*/
				if (nV == model[k] || (nV == "" && model[k] == undefined)) {
					continue;
				}
			} else {
				// Add new row.
				if (nV == "") {
					continue;
				}
			}
			params[k] = nV;
		}
		
		console.log(params);
		
		if ($.isEmptyObject(params)) {
			console.log("Nothing changed...");
			return;
		}
		
		/* TODO: While */
		var id = jThis.attr("rel");
		$.ajax({
			url: "${root}/simple/user/" + id,
			type: method,
			dataType: 'JSON', 
			data: params,
			success: function(data) {
				console.log(154, data);
				if ('POST' == method) {
					jThis.attr("rel", data.id);
					
					/* Update table model.*/
					gData[i] = data;
				}
			}
		});
		
	});
	
	$(".delete-btn").click(function() {
		var jThis = $(this);
		var id = jThis.attr("rel");
		
		$.ajax({
			url: "${root}/simple/user/" + id,
			type: 'DELETE',
			dataType: 'JSON', 
			success: function(data) {
				console.log(169, data);
				var jTr = jThis.closest("tr");
				jTr.remove();
			}
		});
	});
	
	$(".choose-all").click(function() {
		var s = $(this).attr("checked");
		$(".choose-row").attr("checked", s ? true : false);
	});
	
	$("#addRow").click(function() {
		var jTbody = jRestTable.find('tbody');
		var jLast = jRestTable.find("tbody tr:last");
		/* TODO: Cache this for optimization. */
		var jNew = jLast.clone();
		/* Clean existed values. */
		gI += 1;
		jNew.attr("id", "row" + gI);
		var jBoxs = jNew.find(".box");
		for (var j = 0, l = jBoxs.length; j < l; j++) {
			var jBox = jBoxs.eq(j);
			jBox.val('');
		}
		jTbody.append(jNew);
	});
	
	$("#deleteRows").click(function() {
		var jRows = $("tbody td .choose-row:checked");
		var l = jRows.length;
		if (l == 0) {
			return;
		}
		/* TODO: Pick id. Put id at checkbox attribute, better? */
		var ids = [];
		var jTrs = jRows.closest('tr');
		for (var i = 0; i < l; i++) {
			var j = jTrs.eq(i).attr('id').substring(3);
			ids[i] = gData[j].id;
		}
		
		console.log(ids, toQueryStr({id: ids}));
		
		$.ajax({
			url: "${root}/simple/user",// + '?' + toQueryStr({id: ids}),
			type: 'DELETE',
			data: {id: ids},
			success: function(data) {
				console.log(244, data);
				jTrs.remove();
			}
		});
	});
});
	
/**
 * convert obj to query param.
 */
function toQueryStr(obj) {
	var str = "";
	for (var k in obj) {
		var v = obj[k];
		if (typeof v == "object") {
			if (v instanceof Array) {
				for (var i in v) {
					str += k + "=" + v[i] + "&";
				}
			}
		} else {
			str += k + "=" + v + "&";
		}
	}
	if (str != "") { 
		str = str.substring(0, str.length - 1);
	}
	return str;
}

</script>

<title>RESTful-table</title>
</head>
<body>
<div class="content">
<table id="datatable" class="ui-table">
</table>
</div>
</body>
</html>