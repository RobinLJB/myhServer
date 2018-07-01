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
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">联系客服</h1>
		</header>
		<div class="mui-content wx-box " style="height: 100%; text-align: center;">
			<div class="wx-box-margin " style="background-color: #FFFFFF;">
				<div class=" mui-text-center wx-pic" style="margin-bottom: 20px;">
					<img class="mui-inline" src="${context}/asset/mobile/img/wx.png" alt="" />
					<img class="mui-inline" src="${context}/asset/mobile/img/d.png" alt="" />
					<img class="mui-inline" src="${context}/asset/mobile/img/kf.png" alt="" />
				</div>
				<p style="white-space:nowrap; ">微信ID：<span style="color:#4E4E4E" ;>lijian13925492081</span></p>
				<p style="white-space:nowrap; margin-bottom: 20px;">加微信，联系客服申请提现</p>
				<img style="width: 80%;" src="${context}/asset/mobile/img/--.png" alt="" />
				<img style="width: 80%; margin: 0px 0;" src="${context}/asset/mobile/img/wx1.png" alt="" />
				<p>或<span style="color: #FA9F36;">微信扫一扫</span></p>
			</div>
			<p>客服电话-<span style="color:#373737;">0551-65565052</span></p>
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
	</script>
	</html>
	