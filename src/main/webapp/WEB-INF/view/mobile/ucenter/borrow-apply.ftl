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
			<a class="mui-icon mui-icon-left-nav mui-pull-left" href="${context}/mobile/borrow/borrowPath.html"></a>
			<h1 class="mui-title">借款期限</h1>
		</header>
		<div class="mui-content" style="position: relative;">
					<div class="borrow-eie">
						<h1 id="benJin">${benjin!}</h1>
						<p>借款金额(元)</p>
					</div>
					 <div class="mui-row mui-text-center borrow">
				        <div class="mui-col-sm-6 mui-col-xs-6">
				        	<p>借款期限(天)</p>
				           <span class="active days" id="seven">7</span>
				        </div>
				        <div class="mui-col-sm-6 mui-col-xs-6">
				        	<p>借款期限(天)</p>
				          	<span class="days" id="fifth">14</span>
				        </div>
				    </div>
					<ul class="mui-table-view mui-grid-view borrow-table">
						 <li class="mui-table-view-cell mui-text-center" style="font-size: 16px;border-bottom: 1px solid #eee;display: block;padding: 12px 15px;color: #000">一键借款，飞速审核</li>
						<li class="mui-table-view-cell mui-media mui-col-xs-12 mui-col-sm-12 mui-text-left" style="border-right: 1px solid #eee">
		                	<div class="mui-media-body" >信审费(元)：<span id="xinFee"></span></div>
		                </li>
		                <li class="mui-table-view-cell mui-media mui-col-xs-12 mui-col-sm-12 mui-text-left">
		                	<div class="mui-media-body" >服务费(元)：<span id="serviceFee"></span></div>
		                </li>
		                <li class="mui-table-view-cell mui-media mui-col-xs-12 mui-col-sm-12 mui-text-left" style="border-right: 1px solid #eee">
		                	<div class="mui-media-body" >手续费(元)：<span id="shouFee"></span></div>
		                </li>
				         <li class="mui-table-view-cell mui-text-center" style="font-size: 13px;display: block;padding: 30px 15px">预计到期还款(合计)：<font color="#11AC3B"></font></li>
					</ul>
					<div class="mui-content-padded but" style="padding-top: 30px;">
						<button type="button" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">立即借款</button>
					</div>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".days").eq(0).trigger('click')
		})
		
		$('.borrow>div').click(function(){
			$('.borrow span').removeClass("active");
			$(this).children("span").addClass("active");
		})
		var days=0;
		
		//计算所需费用
		$(".days").click(function() {
			$("#btn_yq").attr('disabled',true);
			var borrowDays=$(this).html();
			var params = {};
			params['benJin'] = $("#benJin").text();
			params['borrowDays'] =borrowDays;
			days=borrowDays;
			$.post("${context}/mobile/borrow/calculateFee.do", params, function(data) {
				if(data.code == 0) {
					$("#xinFee").html(data.data.xinFee);
					$("#serviceFee").html(data.data.serviceFee);
					$("#shouFee").html(data.data.xinFee);
					$("font").html("￥"+data.data.total);
					$("#btn_yq").attr('disabled',false);
					
				} else {
					mui.alert(data.message);
					$("#btn_yq").attr('disabled',false);
				}
			}, 'json');

		});
		
		
		//提交借款申请
		$("#btn_yq").click(function() {
			$(this).attr('disabled',true);
			var params = {};
			params['benJin'] = $("#benJin").text();
			params['borrowDays'] =days;
			params['xinFee'] = $("#xinFee").html();
			params['serviceFee'] = $("#serviceFee").html();
			params['shouFee'] = $("#shouFee").html();
			if(days==""){
				mui.alert("请选择天数");
				return;
			}
			$.post("${context}/mobile/borrow/submitBorrow.do", params, function(data) {
				if(data.code == 0) {
					window.location="${context}/mobile/borrow/borrowPath.html";
				} else {
					mui.alert(data.message);
					$("#btn_yq").attr('disabled',false);
				}
			}, 'json');

		});
	</script>
	</html>