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
			.mui-backdrop {
			    background-color: rgba(0, 0, 0, .6);
			} 
		</style>
	</head>
<style>
	
</style>
	<body style="background: #efeff4;">
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">身份认证</h1>
			<!--<a class="mui-icon mui-per-right" data-href="javascript:;" id="acc_sure"></a>-->
		</header>
		<div class="mui-content">
			<form class="mui-input-group personal" name="form" action="" method="POST" enctype="multipart/form-data" style="margin-top: 6px;margin-bottom: 0;">
				<div class="mui-input-row">
					<label>姓名</label>
					<input type="text" name="name" placeholder="张三">
				</div>
				<div class="mui-input-row">
					<label>身份证号</label>
					<input type="text" name="cardno" placeholder="如：340825199308221234">
				</div>
				<!--身份证A-->
				<div class="mui-input-row" style="height: inherit;">
					<input id="imgPathA" type="hidden" name="imgPathA" value="" />
					<label style="padding-top: 50px;line-height: 1.2;">身份证照<br/><span style="color: #888;font-size: 12px">身份证正面照</span></label>
					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreviewA" src="${context}/asset/mobile/img/identity.png" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<!--<a href="javascript:;" class="upload-btn tc acc_scicon" style="left: 35%;"></a>-->
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFileA" multiple class="ph08" />
						</div>
					</div>
				</div>
				<!--身份证B-->
				<div class="mui-input-row" style="height: inherit;margin-bottom: 8px;">
					<input id="imgPathB" type="hidden" name="imgPathB" value="" />
					<label style="padding-top: 50px;line-height: 1.2;">身份证照<br/><span style="color: #888;font-size: 12px">身份证反面照</span></label>
					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreviewB" src="${context}/asset/mobile/img/identityB.png" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<!--<a href="javascript:;" class="upload-btn tc acc_scicon" style="left: 35%;"></a>-->
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFileB" multiple class="ph08" />
						</div>
					</div>
				</div>
				<!--芝麻信用截图-->
				<div class="mui-input-row" style="height: inherit;">
					<input id="imgPathZmxy" type="hidden" name="imgPathZmxy" value="" />
					<label style="padding-top: 50px;line-height: 1.2;">芝麻信用<br/><span style="color: #888;font-size: 12px">上传芝麻信用分截图</span></label>
					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreviewZmxy" src="${context}/asset/mobile/img/identityC.jpg" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<!--<a href="javascript:;" class="upload-btn tc acc_scicon" style="left: 35%;"></a>-->
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFileZmxy" multiple class="ph08" />
						</div>
					</div>
				</div>
				<!--淘宝收货地址截图-->
				<div class="mui-input-row" style="height: inherit;">
					<input id="imgPathTaobao" type="hidden" name="imgPathTaobao" value="" />
					<label style="padding-top: 50px;line-height: 1.2;">淘宝收货地址<br/><span style="color: #888;font-size: 12px">上传淘宝收货地址截图</span></label>
					<div class="iden acc_img">
						<div id="sss">
							<img class="acc_imgin" id="imgPreviewTaobao" src="${context}/asset/mobile/img/identityC.jpg" style="max-height: 115px;max-width: 100%">
						</div>
						<div class="acc_sc" style="max-height: 115px;">
							<!--<a href="javascript:;" class="upload-btn tc acc_scicon" style="left: 35%;"></a>-->
							<input type="file" name="file0" accept="image/jpg,image/jpeg,image/png" id="InputFileTaobao" multiple class="ph08" />
						</div>
					</div>
				</div>
				
			</form>
			<button type="button" id="save" class="mui-btn mui-btn-blue mui-btn-block btn-bear" style="border-radius: 25px;margin-bottom: 25px;">提 交</button>
			<!--<div class="hint" style="margin-top: 0;">
				<div class="hint-title">
					<img src="${context}/asset/mobile/img/hint1.png">
					<span>温馨提示</span>
					<img src="${context}/asset/mobile/img/hint2.png">
				</div>
				<div class="hint-content">
					1.拍摄身份证照片务必区分人像面、国徽面。 <br/> 2.身份证照片需本人、清晰且完整 <br/> 3.必须是真实，否则芝麻认证失败
				</div>
			</div>-->

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
			mui.alert("请填入真实信息，否则认证无法通过");
		})
			
		
		imgUpload('#InputFileA','#imgPathA','#imgPreviewA')
		imgUpload('#InputFileB','#imgPathB','#imgPreviewB')
		imgUpload('#InputFileZmxy','#imgPathZmxy','#imgPreviewZmxy')
		imgUpload('#InputFileTaobao','#imgPathTaobao','#imgPreviewTaobao')
		
		function imgUpload(dom,inputDom,imgDom){
			$(dom).AjaxFileUpload({
				//处理文件上传操作的服务器端地址
				action: '${context}/upload/image.do?type=3',
				onSubmit: function(filename) { 
					mui.toast('正在上传请稍等...')
				}, 
				onComplete: function(filename, resp) { //服务器响应成功时的处理函数
					if(resp.code == 0){
						$(inputDom).val(resp.absPath);
						$(imgDom).attr('src',resp.absPath);
						mask.close();
					}
					else {
						mui.alert(resp.message);
						mask.close();
					}
					$(dom).on('click',function(){
						fileClick();
					});
				}
			});
			$(dom).on('click',function(){
				fileClick();
			});
		}
		var mask = mui.createMask();
		function fileClick(){
			mask.show()
		}
		
		
		$("#save").click(function() {
			var params = {};
			params['realname'] = $('input[name="name"]').val();
			params['cardno'] = $('input[name="cardno"]').val();
			params['cardimg'] = $('input[id="imgPathA"]').val();
			params['cardimgB'] = $('input[id="imgPathB"]').val();
			params['Zmxyimg'] = $('input[id="imgPathZmxy"]').val();
			params['Taobaoimg'] = $('input[id="imgPathTaobao"]').val();
			
			if($('input[name="name"]').val() == "") {
				mui.alert("请输入姓名");
				return false;
			}
			if($('input[name="cardno"]').val() == "") {
				mui.alert("请输入身份证");
				return false;
			}
			if($('input[id="imgPathA"]').val() == ""||$('input[id="imgPathB"]').val() == ""||$('input[id="imgPathZmxy"]').val() == ""||$('input[id="imgPathTaobao"]').val() == "") {
				mui.alert("请上传完整图片");
				return false;
			}
			
			$.post("${context}/mobile/borrow/saveIdentityCard.do", params, function(data) {
				if(data.code == 0) {
					mui.alert(data.message,function(){
						location.href = "${context}/mobile/borrow/attestation.html";
					});
				} else {
					mui.alert(data.message);
				}
			}, 'json');

		});
	</script>

	</html>