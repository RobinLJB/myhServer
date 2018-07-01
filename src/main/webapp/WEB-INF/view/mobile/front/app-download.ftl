<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>功德融 兴德致远</title>
<meta http-equiv="keywords" content="${SEO_KEY}">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
<link href="${context}/asset/mobile/css/mui.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		
		<!--App自定义的css-->
		<link rel="stylesheet" href="${context}/asset/mobile/css/app.css" />
<style>
html,body{height:100%;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif}
body{background-color: #fff;}
.main .logo{padding: 80px 0 40px;}
.wrapper{position: relative;}
.wrapper h3{display: inline-block;}
.main .logo>img{width: 120px;height: 120px; border-radius: 17.54%;}
.icon-ios{ background: url('${context}/asset/front/img/ios.png');}
.icon-android{background:url('${context}/asset/front/img/android.png')}  
.icon-android,.icon-ios{height: 32px;width: 32px;display: inline-block;position: absolute;right: 61%;top: 0;}
.down{padding: 12px 46px;min-width: 200px;border-radius: 40px;margin-top: 20px;}
.hint{height: 0;background:url("${context}/asset/front/merchant/450.png");background-size: cover ;width: 100%;transition:height 1s;}
</style>
</head>
<body class="body">
    <div class="hint mui-text-center"></div>
	<div class="main">
		<div class="logo mui-text-center">
			<img src="${context}/asset/front/img/logo24.png"/>
		</div>
		<div class="mui-text-center wrapper">
            <i id="icon" class=""></i>
            <h3></h3>
		</div>
		<div class=" mui-text-center">
			<a id="popup"  class="mui-btn down"  href="">下载安装</a>
		</div>
    </div>
</body>
<script src="${context}/asset/public/plugins/jquery.min.js"></script>
<script src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="${context}/asset/mobile/js/mui.min.js"></script>
	<script>
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
	</script>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
<script type="text/javascript">
$(function(){
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
    	document.getElementById('icon').className  = 'icon-ios';
    	$(".down").attr("href","${ios.url}"); 
    } else {
    	document.getElementById('icon').className = 'icon-android';
        $(".down").attr("href","${android.url}"); 
    }
})

document.getElementById("popup").addEventListener("tap",function () {
    var ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
    	$(".hint").css("height","150px");
    }
}); 
</script>
</html>