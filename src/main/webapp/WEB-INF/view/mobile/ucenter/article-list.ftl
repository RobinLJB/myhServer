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
		<link href="${context}/asset/mobile/css/mui.min.css" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
		<link href="${context}/asset/mobile/css/app.css" rel="stylesheet"/>
		<link rel="stylesheet" href="${context}/asset/mobile/css/notice.css" />
				<style>
			.mui-control-content{display: block;}
			.mui-row.mui-fullscreen>[class*="mui-col-"] {
				height: 100%;
			}
			.mui-col-xs-3,
			.mui-control-content {
				overflow-y: auto;
				height: 100%;
			}
			.mui-segmented-control .mui-control-item {
				line-height: 50px;
				width: 100%;
			}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
				background-color: #fff;
			}
			#segmentedControls a{    border-bottom: 1px solid #c8c7cc;
    line-height: 50px;
    height: 50px;
    display: block;
    text-align: center;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: inherit;
    overflow: hidden;
    font-size: 15px;}
    .mui-bar-nav~.mui-content {
        padding-top: 0;
    margin-top: 50px;
}
.mui_active{background-color: #fff}
		</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
            <a class="mui-icon mui-icon-left-nav mui-pull-left" data-href="${context}/mobile/home.html"></a>
            <h1 class="mui-title">信汇钱宝</h1>
    </header>

		<div class="mui-content mui-row mui-fullscreen">
			
			
				<div  class="mui-control-content">
					<ul class="mui-table-view">
						<#list arctileList as article>
						<li class="mui-table-view-cell mui-media">
							<a href="${context}/mobile/ucenter/problem/${article.id}.html?type=${type!}">
								
								<div class="mui-media-body">
									<h5>${article.title}</h5>
									<p class="mui-ellipsis">${article.publishTime[0..10]}</p>
								</div>
							</a>		
						</li>
						<#else>
						<li class="mui-table-view-cell mui-media" style="padding: 20px 15px;text-align: center;font-size: 15px;">
								暂无文章
						</li>
						</#list>
					</ul>
				</div>
			</div>
			
		
</body>
<script src="${context}/asset/public/plugins/jquery.min.js"></script>
<script src="${context}/asset/mobile/js/mui.min.js"></script>
<script>
		
	$(function(){
		mui.init({swipeBack: true });	
		mui('body').on('tap', 'a', function() {
	                    var data_href = this.getAttribute("data-href");
	                    var href = this.getAttribute("href");
	                    var url=data_href;
	                    if(!url||url==''){
	                        url=href;
	                    }
	                    window.location.href = url;
	         });
		var menuId = $('#MENU_ACTIVE_ID').val();
		if(menuId){
			var a = $('#segmentedControls a[data-menu-id="'+menuId+'"]');
				a.addClass('mui_active');
		}
	});
</script>
</html>