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
			<h1 class="mui-title">手机认证</h1>
		</header>
		<div class="mui-content">
		<input type="hidden" value="${phoneToken!}" id="phoneToken"> 
		<input type="hidden" value="${sid!}" id="sid">
		<input type="hidden" value="${servicePwd!}" id="servicePwd">
		
			<form class="mui-input-group mobile">
			<input type="hidden" value="${phoneToken!}" id="phoneToken"> 
		<input type="hidden" value="${sid!}" id="sid">
				
				<button id="mobileLogin" type="button" style="background: #00b0ff;" class="mui-btn mui-btn-primary mui-btn-block button btn-bear">查询详情</button>
			</form>
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
		
		$("#mobileLogin").click(function() {
			var params = {};
			
			$.post("${context}/mobile/borrow/queryMobileDetailss.do", params, function(data) {
				if(data.code == '0') {
					window.location = "${context}/mobile/borrow/attestation.html";
				} else {
					alert(data.message);
				}
			});

		});
		
	</script>
	</html>