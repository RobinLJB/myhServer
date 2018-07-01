<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal-default-theme.css">
  <style>
	.remodal {
     box-sizing: border-box; 
     width: 0; 
     margin: 0; 
     padding: 0; 
    -webkit-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0);
    color: #2b2e38; 
    background: none; 
}
  </style>
</@override>
<@override name="body">
<div class="reg-cont">
	<form id="form" class="form-horizontal" method="post" action="${context}/ucenter/findPwd2.do">
		<div class="form-group group">验证身份，请填写以下信息：</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">账 户：</label>
			<div class="col-md-4">
				${phone?substring(0,3)}****${phone?substring(7,11)}
			</div>
		</div>
		<input type="hidden" />
		<input type="hidden" id="phone" value="${phone}" />
			<div data-remodal-id="modal">
				<div class="dialog-main" style=" position: fixed;top: -250px;z-index: 999;width: 286px;margin-left:-143px;"> 
					<div class="dialog-hd"><h2>验证码</h2><span class="line"></span></div> 
					<div class="form-group">
						<div class="dialog-bd">
							<input type="text"  style="height: 35px;" class="form-control"  id="code" placeholder="请输入验证码"> 
							<img id="captchaImg" onclick="refreshCaptcha()" title="看不清楚？点击刷新" src="${context}/captcha.do?cid=PWD"  style="    margin-top: -55px;margin-left: 130px;width:100px" />
						</div>
					</div>
					<div class="dialog-bt">
						<a href="javascript:;" class="btn btn-success" id="getCheckCode">确定</a>
						<a href="javascript:;" data-remodal-action="close"  class="btn btn-warning">取消</a>
					</div>
				</div>
			</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">密码</label>
			<div class="col-md-4">
				<input  type="password" class="form-control required" name="password" aria-required="true">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">验证码:</label>
			<div class="col-md-2">
				<input type="text"  style="margin-top: 0px;" class="form-control required" name="code" value="" placeholder="请输入验证码" aria-required="true"> 
			</div>
			<div class="col-md-2">
				<button class="btn getcode" onclick="showRepayment(1)" id="getCheckCode1"  type="button">获取验证码</button>
			</div>
		</div>
		
		
		
		<div class="form-group">
			<div class="col-md-4 col-sm-offset-2">
				<button type="submit" class="btn btn-primary">提 交</button>
			</div>
		</div>
		
		
	</form>
	</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
	function refreshCaptcha(){
		$('#captchaImg').attr('src','${context}/captcha.do?cid=PWD&_time='+new Date().getTime());
	}
 
	function showRepayment(id){
		$('[data-remodal-id=modal]').remodal().open();
	}

</script>
<script>
	$('.submeun-4').addClass('selected').parents().show();
	$(function(){
		FormValidation.validation('#form',function(resp){
			if(resp.code==0){
				window.location.href = "login.html";
			}else{
			spark.alert(resp.message,"error");
			}
		});
	//发送验证码
	$('#getCheckCode').on('click',function(){
		var phone = $("#phone").val();
		var code = $("#code").val();
		$('#getCheckCode1').attr('disabled',true);
		$.ajax({
			url:"${context}/ucenter/sendCode.do",
			data:{phone:phone,code:code,verify:"PWD"},
			type:"post",
			dataType:"json",
			success:function(res){
				$('[data-remodal-id=modal]').remodal().close();
				if(res.code == 0){
					startTick();
					
				}
				else {
					spark.alert(res.message,"error");
					$('#getCheckCode1').attr('disabled',false);
				}
			},
			error:function(){
				$('#getCheckCode1').attr('disabled',false);
			}
		});
	});	
	function startTick(){
		window.tick = 60;
		window.clock = setInterval(function(){
		$("#getCheckCode1").attr("disabled", "true");
			if(window.tick >0){
				$('#getCheckCode1').text('剩余时间('+window.tick+'s)');
				window.tick--;
			}
			else {
				$('#getCheckCode1').text('获取验证码');
				clearInterval(window.clock);
			}
		},1000);
	}		

	
	
	
	});	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />