<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
		<style type="text/css">
			.face_box img{width:70%;margin:0 auto;display:block}
			button.mui-btn{margin-top:25px;padding:10px 0;border-radius:5px;border:none;background-color:#F6672F}
			#face_rec.personal{background:0 0;margin-bottom:0!important}
			#face_rec .iden{margin:0 auto;width:75%;height:180px;padding:15px 15px 15px 0;float:none}
			#face_rec .iden img{max-height:160px!important;max-width:100%;margin:0 auto;display:block}
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">人脸识别</h1>
		</header>
		<div class="mui-content" style="background: #fdefe1;">
			<div class="mui-content-padded">
				<p>人脸识别(请上传清晰本人正面照)</p>
				<form id="face_rec" class="mui-input-group personal" style="margin-top: 6px;" name="form" action="" method="POST" enctype="multipart/form-data">

					<input id="imgPath" type="hidden" name="imgPath" value="" />
					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreview" src="${context}/asset/mobile/img/face.png" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFile" multiple class="ph08" />
						</div>
					</div>
				</form>
				<button type="button" id="busstton" class="mui-btn mui-btn-primary mui-btn-block">提 交</button>
			</div>
			<div class="hint">
				<div class="hint-title">
					<img src="${context}/asset/mobile/img/hint1.png">
					<span>温馨提示</span>
					<img src="${context}/asset/mobile/img/hint2.png">
				</div>
				<div class="hint-content">
					1.拍摄照片务必是本人正脸、清晰且完整。 <br/> 2.拍摄照片务必真实，否则认证失败 
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		
		$('#InputFile').AjaxFileUpload({
			//处理文件上传操作的服务器端地址
			action: '${context}/upload/image.do?type=3',
			onComplete: function(filename, resp) { //服务器响应成功时的处理函数
				if(resp.code == 0){
					$('#imgPath').val(resp.absPath);
					$('#imgPreview').attr('src',resp.absPath);
				}
				else {
					spark.notify(resp.message,'error');
				}
			}
		});
		
		$("#busstton").click(function() {
		
			var params = {};
			params['cardimg'] = $('input[id="imgPath"]').val();
		
			if($('input[id="imgPath"]').val() == "") {
				mui.alert("请上传真实图片");
				return false;
			}
			
			$.post("${context}/mobile/borrow/saveIdentityCard.do", params, function(data) {
				if(data.code == 0) {
					mui.alert('上传成功，等待审核');
					location.href = "${context}/mobile/borrow/attestation.html";
				} else {
					mui.alert(data.message);
				}
			}, 'json');

		});


	</script>
	</html>