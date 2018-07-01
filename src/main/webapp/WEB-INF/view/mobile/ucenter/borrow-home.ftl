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
		<input type="hidden" id="memberStatus" value="${memberStatus!0}">
		
		<header class="mui-bar mui-bar-nav index-bar-nav">
			<h1 class="mui-title">信汇钱宝</h1>
			<a class="mui-icon mui-pull-right" data-href="${context}/mobile/ucenter/moreQuestion.html?type=1"></a>
		</header>
		
		<nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item mui-active" data-href="${context}/mobile/borrow/borrowPath.html">
				<span class="mui-icon loan"></span>
				<span class="mui-tab-label">我要借款</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/borrow/attestation.html">
				<span class="mui-icon approve"></span>
				<span class="mui-tab-label">认证中心</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/mobile/home.html">
				<span class="mui-icon home"></span>
				<span class="mui-tab-label">个人中心</span>
			</a>
		</nav>
			
			<div class="mui-content">
			<div class="mui-content-padded index-padded">
				<div class="refund-riqi">
					<div class="circle-box">
						<#if borrowStatus==8>
							<p>距离还款日(天)</p>
							<h1 class="text">${remainday!0}</h1>
						<#elseif borrowStatus==9>
							<p>逾期天数(天)</p>
							<h1 class="text">${overduedays!0}</h1>
						</#if>
						<img style="width: 20px;" src="${context}/asset/mobile/img/tree.png"/>
						<p><#if borrowStatus==8><span>借贷到期日：</span>${needRepayDate?date}<#elseif borrowStatus==9><span>借贷到期日：</span>${appiontDate?date}</#if></p>					
						<!--<div class="text" id="text">&yen;0.00</div>-->
						<canvas id="canvas"></canvas>
					</div>
				</div>
				<a data-href="#" class="mui-btn mui-btn-primary mui-btn-block index-but">还款/续期</a>
			</div>
			</div>
			
			
		
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/round.js"></script>
	
	<script type="text/javascript">
		//圆环1
		round($('#canvas'),$('.text:eq(0)'))
	</script>
	</html>
