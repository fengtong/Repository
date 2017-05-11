<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Obs查询</title>
<%@ include file="/commons/basejs.jsp" %>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	
	<h2>Obs查询</h2>
	<p>查询结果</p>
	<div style="margin: 20px 0;"></div>
	<table>
		<tr>
			<td>开始时间:</td>
			<td><input id="bgDate" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser"></td>
			<td>结束时间:</td>
			<td><input id="edDate" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser"></td>
		</tr>
		<tr>
			<td>地区:</td>
			<td><select id="createNodeMark" class="easyui-combobox" name="createNodeMark"
				style="width: 171px;">
					<option value="">请选择地区</option>
					<option value="3">广州</option>
					<option value="8">佛山</option>
					<option value="50">深圳</option>
					<option value="53">东莞</option>
					<option value="55">江门</option>
					<option value="57">潮州</option>
					<option value="58">惠州</option>
					<option value="61">揭阳</option>
					<option value="63">茂名</option>
					<option value="65">中山</option>
					<option value="67">珠海</option>
					<option value="69">汕头</option>
					<option value="70">肇庆</option>
					<option value="72">湛江</option>
					<option value="74">阳江</option>
					<option value="76">韶关</option>
					<option value="78">清远</option>
					<option value="83">云浮</option>
					<option value="85">梅州</option>
					<option value="89">河源</option>
					<option value="92">汕尾</option>
			</select></td>
			<td>查询:</td>
			<td><a href="javascript:void(0)" id="queryData"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'"
				style="width: 80px">Search</a></td>
		</tr>
	</table>

	<div style="margin: 20px 0;"></div>

	<%-- 表格 --%>
	<div id="divObsArea">
		<table id="divObsAreaTable">
		</table>
	</div>

</body>
<script type="text/javascript">

	function fixWidth(percent) {
		return $("#divObsArea").width() * percent;
	}
	
	var AREA_JSON = { 
			"3":"广州",
			"8":"佛山",
			"50":"深圳",
			"53":"东莞",
			"55":"江门",
			"57":"潮州",
			"58":"惠州",
			"61":"揭阳",
			"63":"茂名",
			"65":"中山",
			"67":"珠海",
			"69":"汕头",
			"70":"肇庆",
			"72":"湛江",
			"74":"阳江",
			"76":"韶关",
			"78":"清远",
			"83":"云浮",
			"85":"梅州",
			"89":"河源",
			"92":"汕尾"
	};
	
	function getAreaTag(value) {
		for(var curr in AREA_JSON){
			if(curr == value) {
				return AREA_JSON[curr];
			}
		}
	}
	
	$("#queryData").click(function() {
		var params = {
			createNodeMark : $("#createNodeMark").combobox('getValue'),
			bgDate : $("#bgDate").textbox('getValue'),
			edDate : $("#edDate").textbox('getValue')
		}; 
		$("#divObsAreaTable").datagrid({
			url : '${pageContext.request.contextPath}/fileUpLoad/searchQuery',
			queryParams : params,
			width : 'auto',
			height : 'auto',
			fitColumns: true,
			remoteSort : false, //true为服务器端排序
			rownumbers : false, //是否加行号
			pagination : true, //是否显式分页
			pageSize : 10, //页容量，必须和pageList对应起来，否则会报错
			pageNumber : 1, //默认显示第几页
			pageList : [ 10, 20, 30, 40, 50 ],//分页中下拉选项的数值
			columns : [ [ {
				field : 'id',
				title : '序号',
				width : fixWidth(0.15)
			}, {
				field : 'cityArea',
				title : '地区',
				formatter : function(value,row){
					if(value == undefined)
					{
						return "";
					}else {
						return getAreaTag(value);
					}
				},
				width : fixWidth(0.15)
			}, {
				field : 'fileName',
				title : '文件名',
				width : fixWidth(0.15)
			}, {
				field : 'fileListCount',
				title : '总数',
				width : fixWidth(0.15)
			}, {
				field : 'fileRepeatCount',
				title : '文件去重条数',
				width : fixWidth(0.15)
			}, {
				field : 'status',
				title : '状态',
				formatter : function(value,low){
					if(value == "0")
					{
						return "未处理";
					}else if(value == "1") {
						return "正在处理"
					}else if(value == "2") {
						return "失败"
					}else if(value == "3") {
						return "全部成功"
					}
				},
				width : fixWidth(0.15)
			}, {
				field : 'result',
				title : '结果',
				width : fixWidth(0.15)
			}, {
				field : 'uploadTime',
				title : '上传时间',
				formatter : function(value,low){
					if(value == undefined)
					{
						return;
					}
					return timeFormatter(value.time);
				},
				width : fixWidth(0.15)
			}, {
				field : 'action',
				title : '操作',
				formatter : function(value,row){
					if(row.status == "3" || row.status == "2") {
						var linkBtn = "<a href='javascript:void(0)' id='downloadFile' onClick='downloadFile(\""+ row.id +"\")'>下载</a>";
						return linkBtn;
					}else {
						return "";
					}
				},
				width : fixWidth(0.15)
			} ] ],
			onLoadSuccess : function(data) {
				if(data.total == 0)
				{
					$("#divObsAreaTable").datagrid('appendRow',{
						id : '<div style="text-align:center;">没有相关数据..</div>'
					}).datagrid('mergeCells',{
						index : 0,
						field : 'id',
						colspan : 9
					});
					$("#divObsAreaTable").closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
				}else{
					$("#divObsAreaTable").closest('div.datagrid-wrap').find('div.datagrid-pager').show();
				}
			},
		});
	});
	
	function downloadFile(id){
		 var url = '${pageContext.request.contextPath}/fileUpLoad/downloadFile';
		 var form = $("<form>");   //定义一个form表单
		 form.attr('style', 'display:none');   //在form表单中添加查询参数
		 form.attr('target', '');
		 form.attr('method', 'get');
		 form.attr('action', url);
		 var input = $('<input>');
		 input.attr('type', 'hidden');
		 input.attr('name', 'inputStr')
		 input.attr('value',id);
		 $('body').append(form);  //将表单放置在web中
		 form.append(input);   //将查询参数控件提交到表单上
		 form.submit();
	}
	
	function trimSpace(str){ 
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	}
	
	function timeFormatter(time){
		var date = new Date(time);
		var Y = date.getFullYear() + '-';
		var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
		var D = date.getDate() < 10 ? '0'+date.getDate()+' ' : date.getDate() + ' ';
		var h = date.getHours() < 10 ? '0'+date.getHours() + ':' : date.getHours() + ':';
		var m = date.getMinutes() < 10 ? '0'+date.getMinutes() + ':' : date.getMinutes() + ':';
		var s = date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds(); 
		return Y+M+D+h+m+s;
	}
	
	
</script>
</html>