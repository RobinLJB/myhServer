<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<html>
<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="keywords" content="${SEO_KEY}">
		<meta http-equiv="description" content="${SEO_DESC}">
		<script src="${context}/asset/mobile/js/mui.min.js"></script>
		<link href="${context}/asset/mobile/css/mui.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="${context}/asset/mobile/css/notice.css" />
		<style>
		.main {background-color: #ffffff;margin-top: 55px;padding: 0}
		.article{padding: 10px;}
		h1{font-size: 20px;padding-bottom: 10px;font-weight: 400;}
		.article-body{text-indent: 2em;font-size: 16px;overflow: hidden;}
		.article-addition{text-align:right;font-size: 12px}
		img{max-width: 90%}
		</style>
		<script type="text/javascript" charset="UTF-8">
			mui.init();
		</script>
</head>
<body>
	
	<div class="main">
		<div class="article">
			<h1 class="article-title">${article.title}</h1>
			
			<div class="article-body">${article.content}</div>
			
			<p class="article-addition">
				<span>发布时间：${article.publishTime[0..10]}</span>
			</p>


		</div>
		<div style="padding: 20px 20px 10px;font-size: 15px;overflow: hidden;">
			<a style="float: left;" <#if artPrevious != -1>data-href="${context}/mobile/ucenter/problem/${artPrevious}.html?type=${cateId}" <#else> data-href="javascript:void(mui.alert('已经是第一篇'));" </#if> >上一篇</a>  
			<a style="float: right;" style="display: inline-block;margin-left: 10px;padding-left: 11px;border-left: 1px solid #ccc;" 
			<#if artNext != -1>data-href="${context}/mobile/ucenter/problem/${artNext}.html?type=${cateId}" <#else> data-href="javascript:void(mui.alert('已经是最后一篇'));" </#if> >下一篇</a>
		</div>
	</div>
</body>
	<script type="text/javascript">
		  mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
	</script>
</html>