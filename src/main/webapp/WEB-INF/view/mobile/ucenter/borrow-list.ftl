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
			<h1 class="mui-title">我的借款</h1>
		</header>
		<div class="mui-content loan-content">
			<ul class="mui-table-view">
				<#if borrowList??>
				<#list borrowList as lists>
				 <li class="mui-table-view-cell">
				 	<a data-href="${context}/mobile/ucenter/borrowDetails.html?borrowId=${lists.id}">
				 	<div class="loan-title mui-row">
				        <div class="mui-col-sm-8 mui-col-xs-8">
				            <span>编号：</span>${lists.borrowNo}
				        </div>
				        <div class="mui-col-sm-4 mui-col-xs-4 mui-text-right" style="line-height: 24px;">
				          ${lists.fristSubmitTime}
				        </div>
				    </div>
				    <div class="loan-detail mui-row mui-text-center">
				        <div class="mui-col-sm-4 mui-col-xs-4">
				        	金 额
				        	<p>${lists.benJin}元</p>
				        </div>
				        <div class="mui-col-sm-4 mui-col-xs-4">
				        	期 限
				        	<p>${lists.borrowDate}天</p>
				        </div>
				         <div class="mui-col-sm-4 mui-col-xs-4">
				        	状 态
				        	<#if lists.borrowStatus=="1">
				        		<p>提交审核</p>
				        	<#elseif lists.borrowStatus=="2">
				        		<p>已认领</p>
				        	<#elseif lists.borrowStatus=="3">
				        		<p>初审失败</p>
				        	<#elseif lists.borrowStatus=="4">
				        		<p>初审成功</p>
				        	<#elseif lists.borrowStatus=="5">
				        		<p>等待复审</p>
				        	<#elseif lists.borrowStatus=="6">
				        		<p>复审成功</p>
				        	<#elseif lists.borrowStatus=="7">
				        		<p>复审失败</p>
				        	<#elseif lists.borrowStatus=="8">
				        		<p>还款期间</p>
				        	<#elseif lists.borrowStatus=="9">
				        		<p>已经逾期</p>
				        	<#elseif lists.borrowStatus=="10">
				        		<p>还款完成</p>
				        		<#elseif lists.borrowStatus=="12">
				        		<p>已取消</p>
				        	</#if>
				        </div>
				    </div>
				    </a>
				 </li>
				 </#list>
				 <#else>
				 没有借款
				</#if>
				
			</ul>
		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
	</script>
	</html>