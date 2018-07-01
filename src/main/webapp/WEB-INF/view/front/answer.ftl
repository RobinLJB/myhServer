<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>信贷</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
	</head>
	<body>
    <!--<header class="mui-bar mui-bar-nav">
			<a data-href="${context}/mobile/login.html" class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h3 class="mui-title">运营商服务协议</h3>
	</header>-->
	<div class="mui-content mui-content-padded">
		<h5>运营商服务协议</h5>
		<p class="p2">永金贷运营方在此郑重提示您，本文系您与“永金贷”（包括域名为手机客户端及其运营方，以下简称“我们”、“平台”或“本平台”）之间的法律协议，请您认真阅读并理解本协议。您通过平台点击确认本协议的，即表示您同意遵循本协议的所有约定，本协议在您和平台之间具有法律约束力。如发生法律纠纷，您不得以未仔细阅读或理解本协议为由进行抗辩。</p>
		<p>请仔阅读并知晓以下合约计划购买温馨提示：</p>
		<p>1.1 注册用户需满足下列条件：<br>
		（1）按照工信部通讯产品实名制要求，为保证用户的合法权益，请用户认真填写机主本人的姓名、身份证号，阅读、确认相关协议内容。实名制后，可凭借身份证方便在运营商营业厅办理各项业务。若冒用他人证件下单、恶意产生欠费的，京东将通过法律途径追责；<br>
		（2）若因用户本身是联通黑名单用户或是老客户资料不完善导致无法开户的，用户需要前往联通营业厅解除黑名单限制或完善客户资料；<br>
		（3）用户下单后，如遇联通调整资费政策、手机不可办理合约、所选手机号码不可用，京东会主动告知用户相关情况，用户需要三天内确认是否按照新资费办理。用户也可以取消原订单，重新下单。超期京东将取消订单。<br>
		（4）收货时请务必出示用户本人身份证原件，不能出示身份证原件的用户京东有权拒绝投递订单。配送员会提示您在相关单据上签字确认。<br>
		</p>
	</div>
	</body>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
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
	</html>