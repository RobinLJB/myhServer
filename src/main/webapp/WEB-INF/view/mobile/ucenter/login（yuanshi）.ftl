<#assign context="${rc.contextPath}">
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="utf-8">
		<title>${webname}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
	
		<link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
	</head>

	<body>
		
		<div class="mui-content index-content" style="background: #fff;">
			<div class="login_top">
				<header class="mui-bar mui-bar-nav index-bar-nav" style="background: none;">
					<h1 class="mui-title">登 录</h1>
				</header>
			</div>
			<div class="mui-content-padded">
				<div class="mui-text-center login-logo">
					<img src="${context}/asset/mobile/img/xjqbLogo.png">
				</div>
				<form class="mui-input-group login-li" method="post" action="login.do" id="formid">
				<input type="hidden" id="requestCode" value="${requestCode!0}">
				<input type="hidden" id="reruestType" value="${reruestType!0}">
					<div class="mui-input-row">
						<img src="${context}/asset/mobile/img/yonghuming.png">
						<input type="text" name="username" id="username" class="mui-input-clear" placeholder="请输入手机号码" />
						<span class="mui-icon mui-icon-clear mui-hidden"></span>
					</div>
					<div class="mui-input-row" style="position: relative;">
						<img src="${context}/asset/mobile/img/mima.png">
						<input type="text" id="checkcode" class="mui-input-clear" name="checkcode" placeholder="请输入验证码" />
						<span class="mui-icon mui-icon-clear mui-hidden" style="right: 140px;"></span>
						<input type="button" style="width: 30%;font-size: 14px;position: absolute;color: #F6672F;top: 10px;
    right: 20px;background-color: #fff;padding-left: 2px;z-index: 999;" id="getCheckCode" value="获取验证码" class="yanzheng" onclick="settime(this)">
					</div>
					<div class="mui-row login-li-3">
						<div class="mui-col-sm-5 mui-col-xs-5 login-li-3l">
							<a data-href="javascript:;" id="confirmBtn" style="display: inline-block;width: 100%;">已换手机号<img src="${context}/asset/mobile/img/wenhao.png"></a>
						</div>
						<div class="mui-col-sm-7 mui-col-xs-7 mui-text-right">
							<div class="mui-checkbox mui-left xieyi">
								<label style="padding:0">
							  	<input name="checkbox1" value="Item 1" type="checkbox" id="checkbox1">
							  我同意
							  	
							  	<a data-href="${context}/regesister.html" aria-expanded="true">《借款服务协议》</a>
							  </label>

							</div>
						</div>
					</div>
					<div class="mui-button-row login-but">
						<button id="btnLogin" type="button" class="mui-btn mui-btn-primary mui-btn-block">开始借款</button>
					</div>
				</form>
			</div>
		</div>
		<div id="copyText" style="display: none;">111111</div>
		<!-- 提示换手机号弹窗 -->
		<!--<div id="alerat">
			<div class="alerat-content">
				<div class="content">
					<p>尊敬的用户，如果之前现金驿站</p>
					<p>账户手机号停用，请联系人工客服</p>
					<p>客服微信：xianjinyizhan</p>
				</div>
				<button type="button" class="mui-btn mui-btn-block close">我知道了</button>
				<button type="button" class="mui-btn mui-btn-block close">复制微信号</button>
			</div>
		</div>-->
	</body>

	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/clipboard.min.js"></script>
	
	<script type="text/javascript">
		mui.init({
			swipeBack: true //右滑关闭功能  
		});
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})

		//弹出窗
		document.getElementById("confirmBtn").addEventListener('tap', function() {
			var btnArray = ['复制微信号', '我知道了'];
			mui.confirm('尊敬的用户，如果之前信汇钱宝账户手机号停用，请联系信汇钱宝人工客服，客服微信：xianjinqianbao2017', '', btnArray, function(e) {
				if(e.index==0){
					if($('.mui-popup-backdrop').hasClass('mui-active')){
					 	$('span.mui-popup-button').eq(0).attr('id','copyBtn');
							 var clipboard = new Clipboard('#copyBtn',{
								        text: function() {
								            return 'xianjinqianbao2017';
								        }
								    });
								
								    clipboard.on('success', function(e) {
								        mui.toast('复制成功！')
								    });
								
								    clipboard.on('error', function(e) {
								        mui.toast('此浏览器不支持自动复制！请手动输入！');
								    });	
						$('#copyBtn').trigger('click');
					 }
				}
			})
			
			
		});
		
		
						

		//验证码倒计时
		var countdown = 60;

		function settime(obj) {
			if(countdown == 0) {
				obj.removeAttribute("disabled");
				obj.value = "获取验证码";
				countdown = 60;                   
				return;
			} else {
				obj.setAttribute("disabled", true);
				obj.value = "重新发送(" + countdown + ")";
				countdown--;
			}
			setTimeout(function() {
				settime(obj)
			}, 1000)
		}
		
		//注册
		$("#register").click(function() {
			location.href = "${redirect!'${context}/regesister.html'}";
		});
		
		//登录0
		$("#btnLogin").click(function() {
			var params = {};
			params['username'] = $('input[name="username"]').val();
			params['checkcode'] = $('input[name="checkcode"]').val();
			params['requestCode'] = $("#requestCode").val();
			params['reruestType'] = $("#reruestType").val();
			if($('input[name="username"]').val() == "") {
				mui.alert("用户名不能为空");
				return false;
			}
			if($('input[name="checkcode"]').val() == "") {
				mui.alert("验证码不能为空");
				return false;
			}
			if(!$("#checkbox1").is(":checked")) {
				mui.alert("请勾选协议");
			} else {
				$.post("${context}/mobile/login.do", params, function(data) {
					if(data.code == '0') {
						location.href = "${redirect!'${context}/mobile/borrow/borrowPath.html'}";
					} else {
					mui.alert(data.message);
					}
				}, 'json');

			}

		});



$('#getCheckCode').on('click',function(){
			var phone=$('#username').val();
			if(phone==null || phone==""){
				alert("请输入手机号");
				return;
			}
			$.ajax({
				url:"${context}/mobile/sendPhoneCode2.do",
				data:{phone:phone},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == 0){
						//startTick();
					}
					else {
						mui.alert(res.message,"提示");
						$('#getCheckCode').attr('disabled',false);
					}
				},
				error:function(){
					mui.alert(res.message)
					$(this).attr('disabled',false);
				}
			});
			
			
		});	
	</script>

	</html>