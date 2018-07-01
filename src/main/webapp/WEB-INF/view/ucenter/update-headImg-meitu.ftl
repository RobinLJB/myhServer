<#assign context="${rc.contextPath}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>美图WEB开放平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 
<script type="text/javascript" src="${context}/asset/public/plugins/jquery.min.js"></script>
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
<script type="text/javascript">
window.onload=function(){
	xiuxiu.embedSWF("altContent",5,"100%","100%");
       /*第1个参数是加载编辑器div容器，第2个参数是编辑器类型，第3个参数是div容器宽，第4个参数是div容器高*/
    xiuxiu.setUploadType(2);
	xiuxiu.setUploadURL("${urlWeb}/upload/image.do?type=1");//修改为您自己的上传接收图片程序
	xiuxiu.onBeforeUpload = function(data, id){
		xiuxiu.setUploadArgs({filetype: data.type, type: "image", filename: data.name });
	}

	xiuxiu.onInit = function ()
	{
		xiuxiu.loadPhoto("http://open.web.meitu.com/sources/images/1.jpg");
	}	
	xiuxiu.onUploadResponse = function (data)
	{
		data=$.parseJSON(data);
		$.post("${context}/ucenter/updateHeadImg.do",{"headImg":data.absPath},function(resp){
			if(resp.code==0){
				$("#headImg",window.parent.document).attr("src","${context}"+data.absPath);
				parent.layer.close(1);
			}else{
				spark.alert(resp.message,"error");
			}
		});
		// alert("上传响应" + data); 可以开启调试
	}
}

</script>
<style type="text/css">
	html, body { height:100%; overflow:hidden; }
	body { margin:0; }
</style>
</head>
<body>
<div id="altContent">
	<h1>美图秀秀</h1>
</div>
	<script src="${context}/asset/public/plugins/sweetalert/sweetalert.min.js" type="text/javascript"></script>
	<script src="${context}/asset/public/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="${context}/asset/front/js/front.js" type="text/javascript"></script>
</body>
</html>