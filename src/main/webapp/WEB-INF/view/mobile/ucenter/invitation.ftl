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
			#img{position: fixed;top: 0px;height: 100%;width: 100%;display: none}
			#withdraw{top:-3px;left:5px;padding:3px 8px!important}
			html,body,.mui-content{height: 100%;}
		</style>
	</head>
	<body>
		<div class="mui-content" id="invitation_cont">
			<header class="mui-bar mui-bar-nav" style="background: none;">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">邀请好友</h1>
			</header>
			<p class="ii_p1">已成功邀请${member.invateActive!0}位好友赚取<font color="#F6672F">0</font>元佣金</p>
			<div class="mui-content-padded invitation-but">
				<button type="button" id="btn_gg" class="mui-btn mui-btn-primary mui-btn-block"><span>邀请好友</span></button>
			</div>
			<div class="ii_p2 mui-text-center">复制下方的邀请链接向好友推荐</br>邀请链接</div>
			<p class="mui-text-center invUrl ii_p3"></p>
			<!--<p class=" ii_p2 font_12 mui-text-center"><font color="#666666">活动细则</font></br>
				1.注册用户邀请好友，成功注册并贷款成功即可获得佣金88元</br>
				2.好友成功注册还可获得贷款优惠券，贷款立即可用。
			</p>-->
			<!--<img src="${context}/asset/mobile/img/invitation.png" width="100%">
			 <div class="mui-row mui-text-center invitation-xinxi">
		        <div class="mui-col-sm-6 mui-col-xs-6">
		            成功邀请好友：${member.invateActive!0}位
		            <i></i>
		        </div>
		        <div class="mui-col-sm-6 mui-col-xs-6">
		           获取佣金：${member.commisionSum!0}元<button type="button" id="withdraw" class="mui-btn mui-btn-primary">提现</button>
		        </div>
		    </div>
			<div class="mui-content-padded invitation-but but">
				<button type="button" id="btn_yq" class="mui-btn mui-btn-primary mui-btn-block">邀请好友</button>
			</div>
			<p class="mui-text-center font_12"><span class="font_14 color_black">参与活动规则</span></br>
				1、分享页面，邀请好友注册</br>
				2、好友借款成功，即可获得推广佣金20元
			</p>-->
		</div>
		<img id="img" src="${context}/asset/mobile/img/fenxiang.png">
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script type="text/javascript" language="javascript" src="${context}/asset/mobile/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/clipboard.min.js"></script>
	
		<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
			
			var invUrl='';
			$.post('${context}/mobile/ucenter/extensionLink.do',{},function(res){
				if(res.code=='0'){
					invUrl=res.data.url;
					console.log(invUrl);
					$('.invUrl').html(invUrl);
				}
			},'json')
			
			var clipboard = new Clipboard('#btn_gg',{
				text: function() {
					return invUrl;
				}
			});
									
			clipboard.on('success', function(e) {
				mui.toast('复制成功！发送给朋友吧~')
			});
									
			clipboard.on('error', function(e) {
				mui.toast('此浏览器不支持自动复制！请手动复制！');
			});	
		})
		
		
		
		
		

//	var done = document.getElementById('btn_gg');
//
//	done.addEventListener('tap', function() {
//		$("#img").show("slow");
//	});
//
//	var img = document.getElementById('img');
//	img.addEventListener('tap', function() {
//		$(this).hide("slow");
//	});
	
	
	$('#withdraw').click(function(){
		window.location="${context}/mobile/ucenter/customService.html";	
	});	
	</script>

	</html>