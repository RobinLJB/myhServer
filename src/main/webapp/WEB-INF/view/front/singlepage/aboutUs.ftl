<#assign context="${rc.contextPath}">
<!doctype html>
<html>

	<head>
		<meta charset="utf-8">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	   	<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <title>关于我们</title>
	    <script src="${context}/asset/front/mui/js/mui.min.js"></script>
	    <link href="${context}/asset/front/mui/css/mui.min.css" rel="stylesheet"/>
	    <link rel="stylesheet" type="text/css" href="${context}/asset/front/mui/css/add.css"/>
	    <script type="text/javascript" charset="utf-8">
	      	mui.init();
	    </script>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav top">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">关于我们</h1>
		</header>
		<div class="mui-content">
				<div id="segmentedControl" class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary aboutus_tab">
					<a class="mui-control-item mui-active" href="#item1">关于我们</a>
					<a class="mui-control-item" href="#item2">产品介绍</a>
				</div>
			
				<div id="item1" class="mui-control-content  mui-active">
					<img src="${context}/asset/front/img/a4.png" width="100%"/>
					<div class="item1-content">
						优选app是一款立足优质资产端的供应链金融平台，提供大型国企供应链金融理财产品。平台注册资本5000万，对接使用新浪支付提供的资金账户服务，优选钱包希望能将客户财富的增值给客户带来生活品质的提升。
					</div>		
				</div>
				<div id="item2" class="mui-control-content">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">
								<img src="${context}/asset/front/img/a2.png" />国资系列供应链
							</a>
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">
								<img src="${context}/asset/front/img/a3.png" />上市系列供应链
							</a>
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">
								<img src="${context}/asset/front/img/a1.png" />央企系列供应链
							</a>
						</li>
					</ul>
					<div class="item1-content">
						优选app是一款立足优质资产端的供应链金融平台，提供大型国企供应链金融理财产品。平台注册资本5000万，对接使用新浪支付提供的资金账户服务，优选钱包希望能将客户财富的增值给客户带来生活品质的提升。
					</div>	
				</div>
		</div>
	</body>

</html>