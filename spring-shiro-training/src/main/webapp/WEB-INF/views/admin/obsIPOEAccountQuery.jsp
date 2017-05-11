<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试首页</title>
<%@ include file="/commons/basejs.jsp" %>
</head>
<body>
	<div style="margin: 20px 0;"></div>

	<h2>IPOE账号查询</h2>
	<table>
		<tr>
			<td>账号:</td>
			<td><input type="text" class="easyui-textbox" id="userAccount"/></td>
			<td><a href="javascript:void(0)" id="searchAcData"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'"
				style="width: 80px">查询</a></td>
		</tr>
	</table>

	<div style="margin: 20px 0;"></div>
	
	<%-- 表格 --%>
	<div id="divObsSearchAccount">
		<table id="divObsSearchAccountTable">
		</table>
	</div>

</body>
<script type="text/javascript">
	function trimSpace(str){ 
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	function fixAcWidth(percent) {
		return $("#divObsSearchAccount").width() * percent;
	}
	
	//查询账号是否已被激活
	$("#searchAcData").click(function() {
		var params = {
			userName : trimSpace($("#userAccount").textbox('getValue'))
		}; 
		$("#divObsSearchAccountTable").datagrid({
			url : '${pageContext.request.contextPath}/fileUpLoad/searchUserName',
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
				field : 'userName',
				title : '账号',
				width : fixAcWidth(0.15)
			}, {
				field : 'fileName',
				title : '文件名',
				width : fixAcWidth(0.15)
			}, {
				field : 'status',
				title : '状态',
				formatter : function(value,low){
					if(value == "0")
					{
						return "未处理";
					}else if(value == "1") {
						return "失败"
					}else if(value == "2") {
						return "成功"
					}else if(value == "3") {
						return "账号已被删除"
					}
				},
				width : fixAcWidth(0.15)
			},{
				field : 'result',
				title : '结果',
				width : fixAcWidth(0.15)
			}, {
				field : 'uploadTime',
				title : '上传时间',
				formatter : function(value,low){
					if(value == undefined)
					{
						return;
					}
					return value;
				},
				width : fixAcWidth(0.15)
			} ] ],
			onLoadSuccess : function(data) {
				if(data.total == 0)
				{
					$("#divObsSearchAccountTable").datagrid('appendRow',{
						userName : '<div style="text-align:center;">没有相关数据..</div>'
					}).datagrid('mergeCells',{
						index : 0,
						field : 'userName',
						colspan : 4
					});
					$("#divObsSearchAccountTable").closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
				}else{
					$("#divObsSearchAccountTable").closest('div.datagrid-wrap').find('div.datagrid-pager').show();
				}
			},
		});
	});
	
</script>
</html>