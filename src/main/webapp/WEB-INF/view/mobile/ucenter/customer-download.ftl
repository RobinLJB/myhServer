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
		<div class="mui-content wwx-box " style="height: 100%; text-align: center;">
			<div class="wwx-box-margin">
				<img style="width: 70%;" src="${context}/asset/mobile/img/wx2.png" alt="" />
				<p style="white-space: nowrap;">长按识别二维码关注“信汇钱宝”领款</p>
				<div class="wwx-lines mui-clearfix">
					<div class="line mui-pull-left"></div>
					<div class="line mui-pull-right"></div>
				</div>
				<p style="margin: 30px 20px; font-size: 20px; font-weight: 800;">或下载APP领款</p>
				<button type="button" class="mui-btn mui-btn-white mui-btn-block wwx-withdraw">立即领钱</button>

			</div>
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
	