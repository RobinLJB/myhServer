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
			<h1 class="mui-title">我的银行卡</h1>
		</header>
		<div class="mui-content">
			<ul class="mui-table-view bankcard">
			<li class="mui-table-view-cell  mui-text-center" >
				    	<a data-href="${context}/mobile/borrow/addNewBankCard.html"></a>
				    	<img src="${context}/asset/mobile/img/addimg.png" style="width: 20px;margin-right: 5px;vertical-align: top;">绑定银行卡
			    	</li>
			    	
				<#if cardList??>
					<#list cardList as lists>
						
						<li class="mui-table-view-cell">
					    	<span  class="banks" datas="${lists.cardNo!}">
					    	<#if lists.bankNo=="0102">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/gsb1.png">
					    			<#elseif lists.bankNo=="0103">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/nyb1.png">
					    			<#elseif lists.bankNo=="0104">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/zgb1.png">
					    			<#elseif lists.bankNo=="0105">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/jsb1.png">
					    			<#elseif lists.bankNo=="0301">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/jtb1.png">
					    			<#elseif lists.bankNo=="0308">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/zhyb1.png">
					    			<#elseif lists.bankNo=="0403">
					    				<img style="width:50px;height:50px;" style="width:50px;height:50px;" src="${context}/asset/mobile/img/youz1.png">
					    			<#elseif lists.bankNo=="0303">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/guangd1.png">
					    			<#elseif lists.bankNo=="0305">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/mings1.png">
					    			<#elseif lists.bankNo=="0310">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/pufb1.png">
					    			<#elseif lists.bankNo=="0302">
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/zhxin1.png">
					    			<#else>
					    				<img style="width:50px;height:50px;" src="${context}/asset/mobile/img/bangzhangbank.png">
					    			</#if>
					    	<div class="bankdet">
					    		<#if lists.bankNo=="0102">
					    				<h3>中国工商银行</h3>
					    			<#elseif lists.bankNo=="0103">
					    				<h3>中国农业银行</h3>
					    			<#elseif lists.bankNo=="0104">
					    				<h3>中国银行</h3>
					    			<#elseif lists.bankNo=="0105">
					    				<h3>中国建设银行</h3>
					    			<#elseif lists.bankNo=="0301">
					    				<h3>交通银行</h3>
					    			<#elseif lists.bankNo=="0308">
					    				<h3>招商银行</h3>
					    			<#elseif lists.bankNo=="0403">
					    				<h3>邮政储蓄银行</h3>
					    			<#elseif lists.bankNo=="0303">
					    				<h3>中国光大银行</h3>
					    			<#elseif lists.bankNo=="0305">
					    				<h3>中国民生银行</h3>
					    			<#elseif lists.bankNo=="0310">
					    				<h3>上海浦东发展银行</h3>
					    			<#elseif lists.bankNo=="0302">
					    				<h3>中信银行</h3>
					    			<#elseif lists.bankNo=="0319">
					    				<h3>徽商银行</h3>
					    			<#else>
					    			
					    				<h3>${lists.bankName!}</h3>
					    			</#if>
					    		
					    		<p>${lists.cardNo!}</p>
					    	</div>
					    	</span>
				    	</li>
			    	</#list>
				<#else>
					<li class="mui-table-view-cell bound mui-text-center">
				    	<a data-href="${context}/addBankcard.html"></a>
				    	<img src="${context}/mobile/borrow/addNewBankCard.html">绑定银行卡
			    	</li>
				</#if>
			    
			    
			</ul>
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
		
		
		//选择银行卡
		$(".banks").click(function() {
			var cardNo=$(this).attr("datas");
			var params = {};
			params['bankCardNo'] = cardNo;
			
			if(cardNo == "") {
				mui.alert("请选择银行卡");
				return false;
			}
			
			$.post("${context}/mobile/borrow/selectBankCard.do", params, function(data) {
				if(data.code == '0') {
				//接受借款的银行卡
					location.href = "${context}/mobile/borrow/borrowStatus.html?borrowId="+data.message;
				} else if(data.code == '100'){
				//选择扣款的银行卡
					location.href = "${context}/mobile/borrow/borrowPath.html";
				}else{
					alert(data.message);
				}
			}, 'json');

		});
	</script>
	</html>