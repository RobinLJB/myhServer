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
		<#if 1 ==1>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">现金驿站</h1>
		</header>
		<#else>
		<header class="mui-bar mui-bar-nav index-bar-nav">
			<a class="mui-icon mui-pull-left" data-href="${context}/ucenter/home.html"></a>
			<h1 class="mui-title">现金驿站</h1>
			<a class="mui-icon mui-pull-right" data-href="${context}/noticeList.html"></a>
		</header>
		</#if>
		<nav class="mui-bar mui-bar-tab">
			<a class="mui-tab-item mui-active" data-href="${context}/index.html">
				<span class="mui-icon loan"></span>
				<#if 1 == 1>		
				<span class="mui-tab-label">我要还款</span>
				<#else>
				<span class="mui-tab-label">我要借款</span>
				</#if>
			</a>
			<a class="mui-tab-item" data-href="${context}/attestation.html">
				<span class="mui-icon approve"></span>
				<span class="mui-tab-label">认证中心</span>
			</a>
			<a class="mui-tab-item" data-href="${context}/ucenter/home.html">
				<span class="mui-icon home"></span>
				<span class="mui-tab-label">个人中心</span>
			</a>
		</nav>
		
			<#if 1 == 1>	
			<div class="mui-content">
			<div class="index-top">
				<img src="${context}/asset/mobile/img/logo02.png" >
			</div>
			<div class="mui-content-padded index-padded">
				<div class="index-flo mui-text-center">
					<p class="p1">距离还款日(天)</p>
					<!-- <p class="p1">逾期天数</p> -->
					<h1>7</h1>
					<p class="p2">到期日：2017-3-30</p>
				</div>
				<a  data-href="${context}/refund.html" class="mui-btn mui-btn-primary mui-btn-block index-but">还款/续期</a>
			</div>
			</div>
			<#else>
			<div class="mui-content index-content">
			<div class="mui-card card">
				<a class="card-a" data-href="${context}/borrowDetail.html"></a>
					<div class="mui-card-header mui-row">
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>
				        </div>
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款0次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
				</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<h1><span>信用额度 ￥ </span>500</h1>
					</div>
				</div>
				<div class="mui-card-footer mui-row">
					<div class="mui-col-sm-6 mui-col-xs-6">
				        免息券：0 张
				    </div>
				    <div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				        <a data-href="" class="lj-loan">立即借款<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    </div>
				</div>
				
			</div>
			<div class="mui-card card">
				<a class="card-a" data-href="${context}/borrowDetail.html"></a>
				<div class="mui-card-header mui-row">
				        <div class="mui-col-sm-7 mui-col-xs-7">
				        	<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>飞速下款</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>
				        </div>
				        <div class="mui-col-sm-5 mui-col-xs-5">
				        	<div class="card-header-right">
				        		<a data-href="">成功借款0次<img src="${context}/asset/mobile/img/cg-ioc.png"></a>
				        	</div>
				        </div>
				</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<h1><span>信用额度 ￥ </span>1000</h1>
					</div>
				</div>
				<div class="mui-card-footer mui-row">
					<div class="mui-col-sm-6 mui-col-xs-6">
				        免息券：0 张
				    </div>
				    <div class="mui-col-sm-6 mui-col-xs-6 mui-text-right">
				        <a data-href="" class="lj-loan">立即借款<img src="${context}/asset/mobile/img/fanhui.png"></a>
				    </div>
				</div>
			</div>
			<div class="mui-card card mui-alerat">
				<div class="mui-card-header mui-row">
				        <div class="mui-col-sm-7 mui-col-xs-7" style="width: 100%;">
				        	<div class="card-header-logo">
				        		<img src="${context}/asset/mobile/img/logo02.png" width="100%" height="100%">
				        	</div>
				        	<div class="card-header-title">
				        		<h2>信用不足 敬请期待</h2>
				        		<p>PAPID EXHAUSTING</p>
				        	</div>
				        </div>
				</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<h1 style="color: #78CCFD;"><span>信用额度 ￥ </span>1500</h1>
					</div>
				</div>
				<div class="mui-card-footer mui-row" style="color: #78CCFD;">
					<div class="mui-col-sm-6 mui-col-xs-6">
				        免息券：0 张
				    </div>
				</div>
			</div>
			</div>
			</#if>
			
		<!-- 提示不足弹窗 -->
		<div id="alerat">
			<div class="alerat-content">
				<div class="closure">
					<img src="${context}/asset/mobile/img/close.png" class="close">
				</div>
				<div class="content">
					<div class="tishi">
						<img src="${context}/asset/mobile/img/tishi.png" height="40px">
						<span>提 示</span>
					</div>
					<p>您的信用额度不足，请选择<br/>其他额度</p>
				</div>
				<button type="button" class="mui-btn mui-btn-primary mui-btn-block close">取 消</button>
			</div>
		</div>
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
		})
		$(".mui-alerat").click(function(){
			$("#alerat").show();
		});
		$(".close").click(function(){
			$("#alerat").hide();
		});
		
	</script>
	</html>