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
	<input type="hidden" id="borrowId" value="${borrowMap.id}" />
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">我的借款</h1>
		</header>
		<div class="mui-content loan-det-content">
			<ul class="mui-table-view" style="margin-top: 12px">
				<#if borrowMap??>
				 <li class="mui-table-view-cell">
				 	<span>申请时间：</span>${borrowMap.fristSubmitTime!}
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>借款编号：</span>${borrowMap.borrowNo!}
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>借款金额：</span>${borrowMap.benJin!}元
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>借款期限：</span>${borrowMap.borrowDate!}天
				 </li>
				
				 <li class="mui-table-view-cell">
				 	<span>续期期限：</span>${borrowMap.addBorrowDay!0}天
				 </li>
			</ul>
			<ul class="mui-table-view">
				 <li class="mui-table-view-cell">
				 	<span>打款日：</span>${borrowMap.secondAuditTime!}
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>预定还款日：</span>${borrowMap.appointmentTime!} 
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>实际还款日：</span>${borrowMap.finalRepayTime!}
				 </li>
			</ul>
			<ul class="mui-table-view">
				 <li class="mui-table-view-cell">
				 	<span>信审费：</span>${borrowMap.xinFee!}元
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>服务费：</span>${borrowMap.serviceFee!}元
				 </li>
				  <li class="mui-table-view-cell">
				 	<span>手续费：</span>${borrowMap.shouFee!}元
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>免息券减免：</span>0元
				 </li>
				 <li class="mui-table-view-cell">
				 	<span>到期还款总额：</span>${total!0}元
				 </li>
				 
				 <li class="mui-table-view-cell">
				 	<span>借款状态：</span>
				 	<div class="cat" style="color:#23B1FD;display: inline-block;">
				 		<#if borrowMap.borrowStatus=="1">
				        		申请已提交后台审核
				        	<#elseif borrowMap.borrowStatus=="2">
				        		已认领
				        	<#elseif borrowMap.borrowStatus=="3">
				        		初审失败
				        	<#elseif borrowMap.borrowStatus=="4">
				        		初审成功
				        	<#elseif borrowMap.borrowStatus=="5">
				        		等待复审
				        	<#elseif borrowMap.borrowStatus=="6">
				        		复审成功
				        	<#elseif borrowMap.borrowStatus=="7">
				        		复审失败
				        	<#elseif borrowMap.borrowStatus=="8">
				        		还款期间
				        	<#elseif borrowMap.borrowStatus=="9">
				        		已经逾期
				        	<#elseif borrowMap.borrowStatus=="10">
				        	还款完成
				        	<#elseif borrowMap.borrowStatus=="12">
				        	已取消
				        	</#if>
				    </div>
				 </li>
				 <#else>
				 没有数据
				 </#if>
			</ul>
			<#if borrowMap.borrowStatus=="3" || borrowMap.borrowStatus=="6" || borrowMap.borrowStatus=="7" || borrowMap.borrowStatus=="12" || borrowMap.borrowStatus=="10" || borrowMap.borrowStatus=="9" || borrowMap.borrowStatus=="8">
				<br>
			<#else>
			<div class="mui-content-padded but">
				<button type="button" id="buttons" class="mui-btn mui-btn-primary mui-btn-block">取消申请</button>
			</div>
			</#if>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		
		
		$('#buttons').click(function(){
		var borrowId=$('#borrowId').val();
			$.ajax({
				url:"${context}/mobile/ucenter/cancelBorrow.do",
				data:{borrowId:borrowId},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == 0){
						mui.alert(res.message,function(){
							window.location='${context}/mobile/home.html';	
						});
					}
					else {
						mui.alert(res.message);
					}
				}
				
			});
		})
			
			
			
		
	</script>
	</html>