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
	</head>
<style>
	
</style>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">身份认证</h1>
			<!--<a class="mui-icon mui-per-right" data-href="javascript:;" id="acc_sure"></a>-->
		</header>
		<div class="mui-content">
			<form class="mui-input-group personal" style="margin-top: 6px;" name="form" action="" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="borrowId" value="${borrowId!}">
				<div class="mui-input-row">
					<label>姓名</label>
					<input type="text" name="name" value="${realname!}">
				</div>
				<div class="mui-input-row">
					<label>身份证号</label>
					<input type="text" name="cardno" value="${cardNo!}">
				</div>
				<div class="mui-input-row" style="height: inherit;">
				<input id="imgPath" type="hidden" name="imgPath" value="" />
					<label style="padding-top: 50px;line-height: 1.2;">身份证照<br/><span style="color: #888;font-size: 12px">手持身份证</span></label>
					<!--<div class="album-old">
						<div class="upload-btn btn-old"><input type="file" name="" id="file0"></div>
						<div class="upload-img "></div>
					</div>-->

					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreview" src="${context}/asset/mobile/img/identity.png" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<a href="javascript:;" class="upload-btn tc acc_scicon" style="left: 15%;"></a>
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFile" multiple class="ph08" />
						</div>
					</div>

				</div>
			</form>
			<button type="button" id="save" class="mui-btn mui-btn-blue mui-btn-block btn-bear" style="border-radius: 25px;">提 交</button>
			<div class="hint">
				<div class="hint-title">
					<img src="${context}/asset/mobile/img/hint1.png">
					<span>温馨提示</span>
					<img src="${context}/asset/mobile/img/hint2.png">
				</div>
				<div class="hint-content">
					1.拍摄身份证照片务必区分人像面、国徽面。 <br/> 2.身份证照片需本人、清晰且完整
				</div>
			</div>

		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<!--<script type="text/javascript" src="${context}/asset/mobile/js/zepto.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/iscroll-zoom.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/script.js"></script>-->
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		
		//上传
		$(function() {
			/*document.documentElement.style.fontSize=document.documentElement.clientWidth*12/320+'px';*/
			$(window).on("load", function() {
				$("#loading").fadeOut();
			})
			// ========================================浮层控制
			$("#tip .pack a").on("click", function() {
				$("#tip").fadeOut()
				$("#tip .pack p").html("")
				return false;
			})

			$("#acc_sure").on("tap", function() {
			mui.alert( "等待审核", "提交成功",function() {} )
         
			return false;
		})
			$("#file0").change(function() {
				if(this.files && this.files[0]) {
					var objUrl = getObjectURL(this.files[0]);
					console.log("objUrl = " + objUrl);
					if(objUrl) {
						$("#img0").attr("src", objUrl);
						$("#file0").click(function(e) {
							$("#img0").attr("src", objUrl);
						});
					} else {
						//IE下，使用滤镜
						this.select();
						var imgSrc = document.selection.createRange().text;
						var localImagId = document.getElementById("sss");
						//图片异常的捕捉，防止用户修改后缀来伪造图片
						try {
							preload.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = data;
						} catch(e) {
							this._error("filter error");
							return;
						}
						this.img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + data + "\")";
					}
				}
			});
			//建立一個可存取到該file的url
			function getObjectURL(file) {
				var url = null;
				if(window.createObjectURL != undefined) { // basic
					url = window.createObjectURL(file);
				} else if(window.URL != undefined) { // mozilla(firefox)
					url = window.URL.createObjectURL(file);
				} else if(window.webkitURL != undefined) { // webkit or chrome
					url = window.webkitURL.createObjectURL(file);
				}
				return url;
			}
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
		
		
		
		$("#save").click(function() {
			var params = {};
			params['imgPath'] = $('input[id="imgPath"]').val();
			params['borrowId'] = $('input[name="borrowId"]').val();
			var  borrowId= $('input[name="borrowId"]').val();
			if($('input[id="imgPath"]').val() == "") {
				mui.alert("请上传图片");
				return false;
			}
			
			$.post("${context}/mobile/borrow/identityForImage.do", params, function(data) {
				if(data.code == 0) {
					location.href = "${context}/mobile/borrow/borrowStatus.html?borrowId="+borrowId;
				} else {
					alert(data.message);
				}
			}, 'json');

			

		});
	</script>

	</html>