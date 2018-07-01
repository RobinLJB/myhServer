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
	<body class="borrow_cont">
		<header class="mui-bar mui-bar-nav">
			 <a class="mui-icon mui-icon-left-nav mui-pull-left" href="${context}/mobile/home.html"></a>
			<div id="segmentedControl" class="mui-segmented-control refund-tab">
				<a class="mui-control-item mui-active" href="#item1"<#if borrowStatus==9>id="active_a"</#if>>还款</a>
				<a class="mui-control-item" <#if borrowStatus==9>href="#" id="disabled_a"<#else>href="#item2"</#if>>续期</a>
			</div>
		</header>
		<div class="mui-content">
			<input type="hidden" id="borrowId" value="${borrowId}">
			<input type="hidden" id="totalfee" value="">
			<input type="hidden" id="borrowStatus" value="${borrowStatus}">
			<span id="overTime" style="display: none;">7</span>
			<div>
				<div id="item1" class="mui-control-content mui-active">
					<div class="refund-riqi">
						<div class="circle-box">
							<#if borrowStatus==8>
								<p>距离还款日</p>
								<h1 class="text">${remainday!0}</h1>
							<#elseif borrowStatus==9>
								<p>逾期天数</p>
								<h1 class="text">${overduedays!0}</h1>
							</#if>
							<!--<img style="width: 20px;" src="${context}/asset/mobile/img/tree.png"/>-->
							<p class="canv_p"><#if borrowStatus==8><span>借款到期日：</span>${needRepayDate?date}<#elseif borrowStatus==9><span>借款到期日：</span>${appiontDate?date}</#if></p>					
							<canvas id="canvas"></canvas>
						</div>
					</div>
					<ul class="mui-table-view refund-table">
						 <!--<li class.="mui-table-view-cell"><#if borrowStatus==8><span>到期还款：</span>${benJin!0}元<#elseif borrowStatus==9><span>到期还款：</span>${benJin!0}元</#if></li>-->
				         
				         <li class="mui-table-view-cell bankCardNo" ><span >已绑定银行卡：</span>${bankCardNo!}</li>
						
					</ul>
					<ul class="mui-table-view refund-table span_l" style="margin-top: 10px;">
						 <li class="mui-text-center" style="padding: 12px 15px;color: #000;border-bottom:1px dotted #a5a4a4;">还款费用</li>
						
						 <li class="mui-table-view-cell mui-text-right"><span>还款管理费：</span>￥${feetotal!0}元</li>
				         <li class="mui-table-view-cell mui-text-right"><span>本金：</span>￥${benJin!0}元</li>
				           <#if borrowStatus==9>
				           <li class="mui-table-view-cell mui-text-right"><span>逾期费用：</span>￥${overdueFee!0}元</li>
				           </#if>
				         <li class="mui-table-view-cell mui-text-right"><span>应还总额：</span><font color="#F6672F">￥${total!}元</font></li>
					</ul>
					<div class="mui-content-padded but" style="padding-top: 35px;padding-bottom: 0;">
						<button type="button" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">立即还款</button>
					</div>
				</div>
				<div id="item2" class="mui-control-content">
					<!--<div class="refund-riqi">
						<div class="circle-box">
							<p>续期天数(天)</p>
							<h1 class="addDays text">7</h1>
						<img style="width: 20px;" src="${context}/asset/mobile/img/tree.png"/>
						<p id="newRepayDay"></p>
						<canvas id="canvas2"></canvas>
						</div>
					</div>-->
					<div class="mui-row mui-text-center renewal">
					 	<div class="renewal-title">请选择续期天数</div>
				        <div class="mui-col-sm-6 mui-col-xs-6">
				           <span class="addDays active">7天</span>
				        </div>
				        <div class="mui-col-sm-6 mui-col-xs-6">
				          	<span class="addDays">14天</span>
				        </div>
				    </div>
				    <ul class="mui-table-view refund-table span_l" style="margin-bottom: 7px;">
				    	<input type="hidden" value="${benJin!}" id="benjin">
				    	<input type="hidden" value="${bankCardNo!}" id="bankCardNo">
						 <!--<li class="mui-table-view-cell" id="newRepayDay"><span>续期还款日：</span></li>-->
				         <!--<li class="mui-table-view-cell"><span>借贷本金：</span>${benJin!0}元</li>-->
				        <#if borrowStatus==8>
				         <li class="mui-table-view-cell bankCardNo mui-text-right" ><span>已绑定银行卡：</span><#if normalBorrowMap.repayBankCardNo??>${normalBorrowMap.repayBankCardNo!}<#else>${normalBorrowMap.bankCardNo!}</#if></li>
				         <li class="mui-table-view-cell mui-text-right" id="newRepayDay"><span>续期还款日：</span></li>
				         <li class="mui-table-view-cell mui-text-right" ><span>借贷本金：</span>￥${benJin!0}元</li>
						<#elseif borrowStatus==9></#if>
					</ul>
					<ul class="mui-table-view refund-table span_l">
						 <li class="mui-text-center" style="color: #000;border-bottom:1px dotted #a5a4a4;">续期费用</li>
						 <li class="mui-table-view-cell mui-text-right" id="xinFee"><span>信审费：</span>￥0.00元</li>
				         <li class="mui-table-view-cell mui-text-right" id="serviceFee"><span>管理费：</span>￥0.00元</li>
				         <li class="mui-table-view-cell mui-text-right" id="shouFee"><span>手续费：</span>￥0.00元</li>
				         <li class="mui-table-view-cell mui-text-right" id="renewalFee"><span>续期费：</span>￥0.00元</li>
				         <li class="mui-table-view-cell mui-text-right" id="fontss"><span>总费用：：</span><font color="#F6672F">￥0.00</font>元</li>
					</ul>
					<div class="mui-content-padded but" style="padding-top: 20px;padding-bottom: 0;">
						<button type="button" id="fee" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">立即支付</button>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/round.js"></script>
	
	<script type="text/javascript">
		$(function(){
//			mui('body').on('tap','a', function() {
//          var url = this.getAttribute("data-href");
//          window.location.href=url;
//          });
            
			$(".addDays").eq(0).trigger('click');
		})
		
		var borrowDays=0;
		var fontss=0;
		
		//计算所需费用
		$(".addDays").click(function() {
			$(".addDays").removeClass('active')
			$(this).addClass('active');
			borrowDays=parseInt($(this).html()) ;
			var params = {};
			params['benJin'] = $("#benjin").val();
			params['borrowId'] = $("#borrowId").val();
			params['borrowDays'] =borrowDays;
			days=borrowDays;
			$.post("${context}/mobile/borrow/calculateRenewalFee.do", params, function(data) {
				if(data.code == 0) {
					$("#xinFee").html("<span>信审费：</span>￥"+data.data.xinFee+"元");
					$("#serviceFee").html("<span>管理费：</span>￥"+data.data.serviceFee+"元");
					$("#shouFee").html("<span>手续费：</span>￥"+data.data.shouFee+"元");
					$("#renewalFee").html("<span>续期费：</span>￥"+data.data.renewalFee+"元");
					$("#benjintotal").html("<span>到期还款：</span>￥"+data.data.amount+"元");
					$("#newRepayDay").html("<span>续期还款日：</span>"+data.data.newRepayDay);
					$("#fontss").html("<span>总费用：</span><font color='#F6672F'>￥"+data.data.total+"元</font>");
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
			$(this).attr('disabled',true);
			var params = {};
			params['borrowDays'] = borrowDays;
			params['bankCardNo'] = $("#bankCardNo").val();
			
			params['totalFee'] =$("#totalfee").val();
			mui.confirm('确认现在续期吗？','',['确定','取消'],function(e){
				if(e.index=='0'){
					$.post("${context}/mobile/borrow/confirmRenewalRepay.do", params, function(data) {
						if(data.code == 0) {
							mui.alert("续期成功");
							window.location="${context}/mobile/borrow/borrowPath.html";
							
						} else {
							mui.alert(data.message);
							$("#fee").attr('disabled',false)
						}
					}, 'json');
				}else{
					$("#fee").attr('disabled',false)
				}
			})
			

		});
		
		
		//立即还款
		$("#btn_yq").click(function() {
			$(this).attr('disabled',true);
			var borrowDays=$(this).html();
			var params = {};
			params['bankCardNo'] = $("#bankCardNo").val();
			mui.confirm('确认现在还款吗？','',['确定','取消'],function(e){
				if(e.index=='0'){
					$.post("${context}/mobile/borrow/normalRepay.do", params, function(data) {
						if(data.code == 0) {
							mui.alert("还款成功");
							window.location="${context}/mobile/borrow/borrowPath.html";
							
						} else {
							mui.alert(data.message);
							$("#btn_yq").attr('disabled',false)
						}
					}, 'json');
				}else{
					$("#btn_yq").attr('disabled',false)
				}
			})
			

		});
		
		
		
		//圆环1
		var borrowStatus=$("#borrowStatus").val();
		if(borrowStatus==9){
			round($('#canvas'),$('#overTime'))
		}else{
			round($('#canvas'),$('.text:eq(0)'))
		}
		//圆环2
//		round($('#canvas2'),$('.addDays'))
		
	</script>
	</html>