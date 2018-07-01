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
			<h1 class="mui-title">消息详情</h1>
		</header>
		<div class="mui-content notice">
			<div class="mui-card" style="margin-top: 8px !important;">
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<p style="color: #333;">【优惠券到账通知】</p>
						<p style="margin: 0;font-size: 12px">10.00元【现金券】已打到您的账户，【个人中心】-【优惠券】查看。</p>
					</div>
				</div>
				<div class="mui-card-footer" style="padding: 8px 15px;">
					<a data-href="javascript:;">2017年3月27日 13:28</a>
				</div>	
			</div>
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
		})
	</script>
	</html>