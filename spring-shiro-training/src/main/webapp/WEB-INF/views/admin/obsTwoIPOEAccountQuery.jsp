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

	<h2>IPOE账号精确查询</h2>
	<table>
		<tr>
			<td>账号:</td>
			<td><input type="text" class="easyui-textbox" id="userTwoAccount"/></td>
			<td><a href="javascript:void(0)" id="searchTwoAcData"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'"
				style="width: 80px">查询</a></td>
			<td><label>备注:"IPOE账号格式为小写mac地址@IPTV，例如208b37693f2b@IPTV"</label></td>
				
				
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
	
	function fixTwoAcWidth(percent) {
		return $("#divObsSearchAccount").width() * percent;
	}
	
	function closeDialog() {
	    $('#ReceiveFeedBackDialog').dialog('close');
	}
	
	function showMsgData(message) {
		$.messager.alert('内容提示',message);
	    closeDialog();
	}
	
	//查询账号是否已被激活
	$("#searchTwoAcData").click(function() {
		if(trimSpace($("#userTwoAccount").textbox('getValue')) == "") {
			showMsgData("请输入要查询的IPOE账号!");
			return;
		}
		var params = {
			userName : trimSpace($("#userTwoAccount").textbox('getValue'))
		}; 
		$("#divObsSearchAccountTable").datagrid({
			url : '${pageContext.request.contextPath}/fileUpLoad/twoSearchUserName',
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
				width : fixTwoAcWidth(0.15)
			}, {
				field : 'fileName',
				title : '文件名',
				width : fixTwoAcWidth(0.15)
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
				width : fixTwoAcWidth(0.15)
			},{
				field : 'result',
				title : '结果',
				width : fixTwoAcWidth(0.15)
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
				width : fixTwoAcWidth(0.15)
			} ] ],
			onLoadSuccess : function(data) {
				if(data.total == 0)
				{
					$("#divObsSearchAccountTable").datagrid('appendRow',{
						userName : '<div style="text-align:center;">没有相关数据，请联系机顶盒厂家，让厂家走机顶盒MAC地址报备流程！</div>'
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