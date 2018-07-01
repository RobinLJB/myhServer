<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
		<meta charset="utf-8">
		<title>功德融 兴德致远</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="keywords" content="${SEO_KEY}">
		<meta http-equiv="description" content="${SEO_DESC}">
		<script src="${context}/asset/mobile/js/mui.min.js"></script>
		<link href="${context}/asset/mobile/css/mui.min.css" rel="stylesheet"/>
		<script type="text/javascript" charset="UTF-8">
			mui.init();
		</script>
		<style type="text/css">
			html,body,.mui-content{height: 100%;background-color: #fff}
			.mui-btn{padding: 9px 0;background: #F25752;border-color: #F25752;margin-top: 50px;}
	    	h1{margin: 10px 0;font-size: 21px;font-weight: 400; }
		</style>
   
</head>
<body>
 <div class="mui-content" style="padding:30px;text-align: center;margin-top:5%;">
		 	<h1 style="margin:10px 0;">处理成功</h1>
		 	<a data-href="${redirect!'${context}/ucenter/home.html'}" class="mui-btn mui-btn-block mui-btn-primary">确定</a>
 </div>
</body>
	<script type="text/javascript">
		  mui('body').on('tap','a', function() {
			var url = this.getAttribute("data-href");  
			window.location.href=url;
			});
	</script>
</html>




<div class="mui-content">
			<div class="index-top">
				<img src="${context}/asset/mobile/img/logo02.png"" >
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