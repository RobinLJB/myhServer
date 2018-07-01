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
			<h1 class="mui-title">常见问题</h1>
		</header>
		<div class="mui-content notice">
			<div class="mui-card" style="margin-top: 8px !important;">
				<div class="mui-card-footer">
					<a style="padding-top: 10px;color: #333;" class="mui-navigate-right" data-href="${context}/mobile/ucenter/problem.html?type=1">借款攻略</a>
				</div>
				<a data-href="${context}/mobile/ucenter/problem.html?type=1"></a>
			</div>
			<div class="mui-card" style="margin-top: 8px !important;">
				<div class="mui-card-footer">
					<a style="padding-top: 10px;color: #333;" class="mui-navigate-right" data-href="${context}/mobile/ucenter/problem.html?type=2">身份认证</a>
				</div>
				<a data-href="${context}/mobile/ucenter/problem.html?type=2"></a>
			</div>
			<div class="mui-card" style="margin-top: 8px !important;">
				<div class="mui-card-footer">
					<a style="padding-top: 10px;color: #333;" class="mui-navigate-right" data-href="${context}/mobile/ucenter/problem.html?type=3">自助还款</a>
				</div>
				<a data-href="${context}/mobile/ucenter/problem.html?type=3"></a>
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