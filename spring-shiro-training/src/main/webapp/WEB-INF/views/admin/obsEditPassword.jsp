<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传IPOE账号修改密码</title>
<%@ include file="/commons/basejs.jsp" %>
</head>
<body>
	<h2>Obs批量上传需修改密码的IPOE账号</h2>
<!-- 	<div> -->
<!-- 		<select name="moreEditArea" id="moreEditArea"> -->
<!-- 			<option value="">请选择地区</option> -->
<!-- 			<option value="3">广州</option> -->
<!-- 			<option value="8">佛山</option> -->
<!-- 			<option value="50">深圳</option> -->
<!-- 			<option value="53">东莞</option> -->
<!-- 			<option value="55">江门</option> -->
<!-- 			<option value="57">潮州</option> -->
<!-- 			<option value="58">惠州</option> -->
<!-- 			<option value="61">揭阳</option> -->
<!-- 			<option value="63">茂名</option> -->
<!-- 			<option value="65">中山</option> -->
<!-- 			<option value="67">珠海</option> -->
<!-- 			<option value="69">汕头</option> -->
<!-- 			<option value="70">肇庆</option> -->
<!-- 			<option value="72">湛江</option> -->
<!-- 			<option value="74">阳江</option> -->
<!-- 			<option value="76">韶关</option> -->
<!-- 			<option value="78">清远</option> -->
<!-- 			<option value="83">云浮</option> -->
<!-- 			<option value="85">梅州</option> -->
<!-- 			<option value="89">河源</option> -->
<!-- 			<option value="92">汕尾</option> -->
<!-- 		</select> -->
<!-- 	</div> -->

	<form class="ui form" id="formEditId" method="POST"
		enctype="multipart/form-data" name="form">
		<div class="inputFrame">
			文件上传： <input type="file" name="editFile" id="fileEditBatchAccountField" /> <input
				type="hidden" type="text" id="areaEditText" name="areaEditText"><br />
			<input type="button" class="upload-button" name="submit"
				onclick="return ajaxEditBatchAccountFileUpload();" value="上传" />
		</div>
	</form>
	
	<br />
	<br />

</body>
<script type="text/javascript">
// 	$("#moreEditArea").change(function() {
// 		$("#areaEditText").val($("#moreEditArea").val());
// 	});

	function ajaxEditBatchAccountFileUpload() {
		$.ajaxFileUpload({
			url : '${pageContext.request.contextPath}/fileUpLoad/getEditPwUpLoadFile', //servlet的地址
			secureuri : false,
			fileElementId : "fileEditBatchAccountField", //文件控件的id
			dataType : 'json',
			data : $('#formEditId').serialize(),
			success : function(data, status) {
				if(data.result == "success") {
					alert("上传成功");
				}else if(data.result == "failed") {
					alert("上传出错,请联系管理员")
				}
			},
			error : function(data, status, e) {
				alert("上传出错");
			}
		});
	}
	
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/ajaxfileupload.js"></script>
</html>