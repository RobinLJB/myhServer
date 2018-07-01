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
<style>
	
</style>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">银行卡认证</h1>
			<!--<a class="mui-icon mui-per-right" data-href="javascript:;" id="acc_sure"></a>-->
		</header>
		<div class="mui-content">
			<form class="mui-input-group personal" style="margin-top: 6px;" name="form" action="" method="POST" enctype="multipart/form-data">
				<input type="hidden" id="bankCardId" value="${bankCardId!}" >
				<div class="mui-input-row">
					<label>手机号</label>
					<input type="text" id="phone" value="${standPhone!}" placeholder="手机号">
				</div>
				<div class="mui-input-row" style="position: relative;">
						
						<input type="text" id="checkcode" class="mui-input-clear" name="checkcode" placeholder="请输入验证码" />
						<span class="mui-icon mui-icon-clear mui-hidden" style="right: 140px;"></span>
						<input type="button" style="width: 30%;font-size: 14px;position: absolute;color: #24B2FE;top: 5px;
    right: 20px;background-color: #fff;padding-left: 2px;z-index: 999;border-left: 1px solid #c8c7cc;" id="getCheckCode" value="获取验证码" class="yanzheng" onclick="settime(this)">
					</div>
				
			</form>
			<button type="button" id="save" class="mui-btn mui-btn-blue mui-btn-block btn-bear" style="border-radius: 25px;">提 交</button>
		

		</div>
	</body>
	<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<!--<script type="text/javascript" src="${context}/asset/mobile/js/zepto.min.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/iscroll-zoom.js"></script>
	<script type="text/javascript" src="${context}/asset/mobile/js/script.js"></script>-->
	<script type="text/javascript">
		$(function() {
			mui('body').on('tap', 'a', function() {
				var url = this.getAttribute("data-href");
				window.location.href = url;
			});
		})
		
		
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
		
		
		
	$('#getCheckCode').on('click',function(){
			var bankCardId=$('#bankCardId').val();
			var phone=$('#phone').val();
			$.ajax({
				url:"${context}/mobile/borrow/bankCardQianyueStepA.do",
				data:{bankCardId:bankCardId,phone:phone},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.code == 0){
						startTick();
					}
					else if(res.code == -1){
						alert(res.message);
						window.location="${context}/mobile/borrow/bankCardList.html";
					}else{
						alert(res.message);
						window.location="${context}/mobile/borrow/bankCardList.html";
					}
				},
				error:function(){
					$(this).attr('disabled',false);
				}
			});
			
		});	
		
		
		$("#save").click(function() {
			var params = {};
			params['mobileCode'] = $("#checkcode").val();
			params['bankCardId'] =$("#bankCardId").val();
			
			if($("#checkcode").val() == "") {
				mui.alert("请输入验证码");
				return false;
			}
			
			$.post("${context}/mobile/borrow/bankCardQianyueStepB.do", params, function(data) {
				if(data.code == 0) {
					location.href = "${context}/mobile/borrow/bankCardList.html?bankCardId="+$("#bankCardId").val()+"&mobileCode="+$("#checkcode").val();
				} else {
					alert(data.message);
					window.location="${context}/mobile/borrow/bankCardList.html";
				}
			}, 'json');


		});
	</script>

	</html>