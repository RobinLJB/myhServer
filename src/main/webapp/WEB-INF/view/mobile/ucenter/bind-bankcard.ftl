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
		<link href="${context}/asset/mobile/css/mui.picker.css" rel="stylesheet" />
		<link href="${context}/asset/mobile/css/mui.poppicker.css" rel="stylesheet" />
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">认证银行卡</h1>
		</header>
		<div class="mui-content">
				<form class="mui-input-group personal" action="${context}/mobile/borrow/bindBankCard.html" method="post">
					<div class="mui-input-row" style="margin-top: 8px;">
						<label>姓名</label>
						<input type="text"  value="${realName!}" disabled="true" readonly>
					</div>
					<div class="mui-input-row" style="margin-top: 8px;">
						<label>银行卡号</label>
						<input type="text" id="bankCard" name="bankCard" placeholder="请输入银行卡号">
					</div>
					<div class="mui-content-padded">
						<button type="submit" id="saveBank" class="mui-btn mui-btn-primary mui-btn-block bankcard-btn">提交</button>
					</div>
				</form>
				
		</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.picker.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.poppicker.js"></script>
	<script src="${context}/asset/mobile/js/city.data.js" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/asset/mobile/js/city.data-3.js" type="text/javascript" charset="utf-8"></script>
		
	<script type="text/javascript">
		mui.init({
			swipeBack: true //右滑关闭功能  
		});
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
		})
	</script>
	<script type="text/javascript">
     
	
	</script>

	</html>