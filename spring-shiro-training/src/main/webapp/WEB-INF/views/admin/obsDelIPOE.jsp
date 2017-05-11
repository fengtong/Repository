<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传需删除的IPOE账号</title>
<%@ include file="/commons/basejs.jsp" %>
</head>
<body>
	<h2>Obs批量上传需删除的IPOE账号</h2>

	<form class="ui form" id="formEditId" method="POST"
		enctype="multipart/form-data" name="form">
		<div class="inputFrame">
			文件上传： <input type="file" name="delFile" id="fileDelBatchAccountField" /> <input
				type="hidden" type="text" id="areaDelText" name="areaDelText"><br />
			<input type="button" class="upload-button" name="submit"
				onclick="return ajaxDelBatchAccountFileUpload();" value="上传" />
		</div>
	</form>
	
	<br />
	<br />

</body>
<script type="text/javascript">

	function ajaxDelBatchAccountFileUpload() {
		$.ajaxFileUpload({
			url : '${pageContext.request.contextPath}/fileUpLoad/getDelPwUpLoadFile', //servlet的地址
			secureuri : false,
			fileElementId : "fileDelBatchAccountField", //文件控件的id
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