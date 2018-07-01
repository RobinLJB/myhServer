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
		
	<style type="text/css">
		#headImg {height: 55px;width: 100px;position: absolute;left: 50%;transform: translate(-50%,-50%);top: 50%;opacity: 0;}
	</style>

	<body>
		<nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item" data-href="${context}/mobile/borrow/borrowPath.html">
				<span class="mui-icon loan"></span>
				<span class="mui-tab-label">我要借款</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/borrow/attestation.html">
				<span class="mui-icon approve"></span>
				<span class="mui-tab-label">认证中心</span>
			</a>
			<a class="mui-tab-item mui-active" data-href="${context}/mobile/home.html">
				<span class="mui-icon home"></span>
				<span class="mui-tab-label">个人中心</span>
			</a>
		</nav>
		<div class="mui-content" style="background: #fff;">
			<div class="home-top">
				<header class="mui-bar mui-bar-nav" style="background: none;">
					<a class=" mui-icon mui-icon-left-nav mui-pull-left"></a>
					<h1 class="mui-title">个人中心</h1>
				</header>
				<form name="form0" id="form0" style="position: relative;">
					<input type="file" name="file0" id="headImg" multiple="multiple" />
					<br>
					<div class="img-upload">
						<img src="<#if member.memberImgPath??>${member.memberImgPath!}<#else>${context}/asset/mobile/img/user_pic.png</#if>" id="img0">
					</div>
				</form>
				<!--<img src="${context}/asset/mobile/img/user_pic.png">-->
				<p>${member.mobilePhone!}</p>
			</div>
			<div class="mui-row mui-text-center home-top-string">
				<!--<div class="mui-col-sm-6 mui-col-xs-6">
					免息券：0 张
				</div>
				<i class="middle"></i>-->
				<a class="mui-col-sm-12 mui-col-xs-12" data-href="javascript:;">
					推广佣金：${member.commisionSum!0} 元
				</a>
				<a type="button" id="withdraw_home" class="mui-btn mui-btn-outlined" data-href="${context}/mobile/ucenter/customService.html">提现</a>
			</div>
			<ul class="mui-table-view mui-grid-view mui-grid-9 home_ul_grid" style="background: #fff;">
		        <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a data-href="${context}/mobile/ucenter/borrowList.html">
		               <img src="${context}/asset/mobile/img/z11.png" />
		                <div class="mui-media-body">我的借款</div>
		        	</a>
		        </li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a data-href="${context}/mobile/ucenter/invitation.html">
		                <img src="${context}/asset/mobile/img/z22.png" />
		                <div class="mui-media-body">邀请好友</div>
		        	</a>
		        </li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a data-href="${context}/mobile/ucenter/customService.html">
		        		<img src="${context}/asset/mobile/img/z33.png" />
		                <div class="mui-media-body">联系客服</div>
		        	</a>
		        </li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a data-href="${context}/mobile/ucenter/feedback.html">
		                <img src="${context}/asset/mobile/img/z44.png" />
		                <div class="mui-media-body">平台反馈</div>
		        	</a>
		        </li>
		         <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a data-href="${context}/mobile/ucenter/moreQuestion.html?type=1">
		                <img src="${context}/asset/mobile/img/z55.png" />
		                <div class="mui-media-body">常见问题</div>
		        	</a>
		        </li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-4">
		        	<a href="#" data-href="${context}/mobile/logout.html">
			            <img src="${context}/asset/mobile/img/z66.png" />
			            <div class="mui-media-body">退出登录</div>
		        	</a>
		        </li>
		    </ul>
			<!--<ul class="mui-table-view mui-table-view-chevron home-li">
				<li class="mui-table-view-cell mui-media" style="margin-top: 8px;">
					<a class="mui-navigate-right" data-href="${context}/mobile/ucenter/borrowList.html">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-1.png">
						<div class="mui-media-body">
							我的借款
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a class='mui-navigate-right' data-href="${context}/mobile/ucenter/invitation.html">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-2.png">
						<div class="mui-media-body">
							邀请好友
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" style="margin-top: 8px;">
					<a class="mui-navigate-right" data-href="${context}/mobile/ucenter/customService.html">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-3.png">
						<div class="mui-media-body">
							联系客服
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a class="mui-navigate-right" data-href="${context}/mobile/ucenter/feedback.html">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-4.png">
						<div class="mui-media-body">
							平台反馈
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a class='mui-navigate-right' data-href="${context}/mobile/ucenter/moreQuestion.html?type=1">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-5.png">
						<div class="mui-media-body">
							常见问题
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" style="margin-top: 8px;">
					<a data-href="${context}/mobile/logout.html">
						<img class="mui-media-object mui-pull-left" src="${context}/asset/mobile/img/home-6.png">
						<div class="mui-media-body">
							退出登录
						</div>
					</a>
				</li>
			</ul>-->
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript">
		mui.init({
			swipeBack:true
		});
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		
		$('header a').on('tap',function(){
			mui.back();
		})

		//上传
		$('#headImg').AjaxFileUpload({
		//处理文件上传操作的服务器端地址
		action: '${context}/upload/image.do?type=3',
		onComplete: function(filename, resp) { //服务器响应成功时的处理函数
			if(resp.code == 0){
//			alert(resp.absPath);
				$('#img0').attr('src',resp.absPath);
				var params = {};
				params['imgpath'] = resp.absPath;
				$.post("${context}/mobile/ucenter/savePeopleImg.do", params, function(data) {
					if(data.code == '0') {
						mui.alert("修改成功");
					} else {
					mui.alert(data.message);
					}
				});
			}
			else {
				spark.notify(resp.message,'error');
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
		
		
		
		
		
	</script>

	</html>
	
