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
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title"><#if borrowStatus==8>还 款<#elseif borrowStatus==9>逾期</#if></h1>
		</header>
		<div class="mui-content">
			<input type="hidden" id="borrowId" value="${borrowId}">
			<input type="hidden" id="totalfee" value="">
			<div style="padding:20px 50px;">
				<div id="segmentedControl" class="mui-segmented-control refund-tab">
					
					<#if borrowStatus==8>
						<a class="mui-control-item mui-active" href="#item1">我要还款</a>
						<a class="mui-control-item" href="#item2">我要续期</a>
					<#elseif borrowStatus==9><a class="mui-control-item mui-active" href="#item1">已逾期</a></#if>
				</div>
			</div>
			<div>
				<div id="item1" class="mui-control-content mui-active">
					<div class="refund-riqi">
						
						<#if borrowStatus==8>
						<h1>${remainday!0}</h1>
						<p>距离还款日(天)</p>
						<#elseif borrowStatus==9><h1>${overduedays!0}</h1>
						<p>逾期天数(天)</p></#if>
						
					</div>
					<ul class="mui-table-view refund-table">
						 <li class="mui-table-view-cell"><#if borrowStatus==8><span>到期日：</span>${needRepayDate?date}<#elseif borrowStatus==9><span>到期日：</span>${appiontDate?date}</#if></li>
				         <#if borrowStatus==8>
				         <li class="mui-table-view-cell bankCardNo" ><span >选择银行卡：</span><#if normalBorrowMap.repayBankCardNo??>${normalBorrowMap.repayBankCardNo!}<#else>${normalBorrowMap.bankCardNo!}</#if></li>
						<#elseif borrowStatus==9>
						<li class="mui-table-view-cell bankCardNo" ><span >选择银行卡：</span><#if overdueBorrowMap.repayBankCardNo??>${overdueBorrowMap.repayBankCardNo!}<#else>${overdueBorrowMap.bankCardNo!}</#if></li>
						</#if>
					</ul>
					<ul class="mui-table-view refund-table" style="margin-top: 10px;">
						 <li class="mui-text-center" style="padding: 12px 15px;color: #000;border-bottom:1px dotted #a5a4a4;">还款费用</li>
						
				         <li class="mui-table-view-cell"><span>剩余本金：</span>${benJin!0}元</li>
				          <li class="mui-table-view-cell"><span>管理费：</span>${feetotal!0}元</li>
				           <#if borrowStatus==9>
				           <li class="mui-table-view-cell"><span>逾期费：</span>${overdueFee!0}元</li>
				           </#if>
				         <li class="mui-table-view-cell"><span>应还总额：</span><font color="#F4201E">${total!}元</font></li>
					</ul>
					<div class="mui-content-padded but" style="padding-top: 50px;">
						<button type="button" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">立即还款</button>
					</div>
				</div>
				<div id="item2" class="mui-control-content">
					 <div class="mui-row mui-text-center renewal">
					 	<div class="renewal-title">请选择续期天数</div>
				        <div class="mui-col-sm-6 mui-col-xs-6">
				           <span class="addDays">7</span>
				        </div>
				        <div class="mui-col-sm-6 mui-col-xs-6">
				          	<span class="addDays">14</span>
				        </div>
				    </div>
				    <ul class="mui-table-view refund-table" style="margin-bottom: 15px;">
				    	<input type="hidden" value="${benJin!}" id="benjin">
				    	<input type="hidden" value="${bankCardNo!}" id="bankCardNo">
						 <li class="mui-table-view-cell" id="newRepayDay"><span>续期还款日：</span></li>
				         
				         <#if borrowStatus==8>
						
				         <li class="mui-table-view-cell bankCardNo" ><span>选择银行卡：</span><#if normalBorrowMap.repayBankCardNo??>${normalBorrowMap.repayBankCardNo!}<#else>${normalBorrowMap.bankCardNo!}</#if></li>
					<#elseif borrowStatus==9>空</#if>
					</ul>
					<ul class="mui-table-view refund-table">
						 <li class="mui-text-center" style="color: #000;border-bottom:1px dotted #a5a4a4;">续期费用</li>
						 <li class="mui-table-view-cell" id="xinFee"><span>信审费：</span>0.00元</li>
				         <li class="mui-table-view-cell" id="serviceFee"><span>服务费：</span>0.00元</li>
				         <li class="mui-table-view-cell" id="shouFee"><span>手续费：</span>0.00元</li>
				         <li class="mui-table-view-cell" id="fontss"><span>总共应扣：</span><font color="#F4201E" id="fosntss">0.00</font>元</li>
					</ul>
					<div class="mui-content-padded but" style="padding-top: 50px;">
						<button type="button" id="fee" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">立即支付</button>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$('.renewal span').click(function(){
			$('.renewal span').removeClass("active");
			$(this).addClass("active");
		})
		
		var borrowDays=0;
		var fontss=0;
		
		//计算所需费用
		$(".addDays").click(function() {
			borrowDays=$(this).html();
			var params = {};
			params['benJin'] = $("#benjin").val();
			params['borrowId'] = $("#borrowId").val();
			params['borrowDays'] =borrowDays;
			days=borrowDays;
			$.post("${context}/mobile/borrow/calculateRenewalFee.do", params, function(data) {
				if(data.code == 0) {
					$("#xinFee").html("<span>信审费：</span>"+data.data.xinFee+"元");
					$("#serviceFee").html("<span>服务费：</span>"+data.data.serviceFee+"元");
					$("#shouFee").html("<span>手续费：</span>"+data.data.shouFee+"元");
					$("#benjintotal").html("<span>到期还款：</span>"+data.data.amount+"元");
					$("#newRepayDay").html("<span>续期还款日：</span>"+data.data.newRepayDay);
					$("#fontss").html("<span>总费用：</span>"+data.data.total+"元");
					$("#totalfee").val(data.data.total);
					
				} else {
					alert(data.message);
				}
			}, 'json');

		});
		
		
		//支付续期费用
		$("#fee").click(function() {
		if(borrowDays==0){
			alert("请先选择续期天数");
			return;
		}
			var params = {};
			params['borrowDays'] = borrowDays;
			params['bankCardNo'] = $("#bankCardNo").val();
			
			params['totalFee'] =$("#totalfee").val();
		
			$.post("${context}/mobile/borrow/confirmRenewalRepay.do", params, function(data) {
				if(data.code == 0) {
					window.location="${context}/mobile/borrow/borrowPath.html";
					
				} else {
					alert(data.message);
				}
			}, 'json');

		});
		
		
		//立即还款
		$("#btn_yq").click(function() {
			var borrowDays=$(this).html();
			var params = {};
			params['bankCardNo'] = $("#bankCardNo").val();
			$.post("${context}/mobile/borrow/normalRepay.do", params, function(data) {
				if(data.code == 0) {
					window.location="${context}/mobile/borrow/borrowPath.html";
					
				} else {
					alert(data.message);
				}
			}, 'json');

		});
		
		//选择银行卡
		$(".bankCardNo").click(function() {
			window.location="${context}/mobile/borrow/bankCardList.html";

		});
		
	</script>
	</html>