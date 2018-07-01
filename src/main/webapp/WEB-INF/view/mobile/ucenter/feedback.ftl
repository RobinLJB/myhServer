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
		<style type="text/css">
			textarea{font-size: 14px;border-color: #eee;border-radius: 0;}
			button.mui-btn{padding:10px 0;border-radius:25px;width:90%;border:none;background-color:#F6672F;margin:25px auto}
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">平台反馈</h1>
		</header>
		<div class="mui-content" style="background: #eaeaea;">
			<div class="mui-content-padded">
				<textarea rows="6" id="details" placeholder="请提出您的宝贵意见,有助于我们做的更好..."></textarea>
				<button type="button" id="busstton" class="mui-btn mui-btn-primary mui-btn-block">提 交</button>
			</div>
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
		
		$('#busstton').click(function(){
		var details=$("#details").val();
		$.ajax({
			url:"${context}/mobile/ucenter/adviseSubmit.do",
			data:{details:details},
			type:"post",
			dataType:"json",
			success:function(res){
				if(res.code == 0){
					mui.alert('谢谢您的真诚反馈！',function(){
						window.location="${context}/mobile/home.html";
					});
				}
				else {
					mui.alert(res.message);
				}
			}
		});
			
	});	
	</script>
	</html>