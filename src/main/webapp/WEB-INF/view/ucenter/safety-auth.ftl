<@override name="head">
<style>
	.col-md-2 {
    width: 34.666667%;
}
</style>
</@override>
<@override name="body">

 
	<h3>验证身份</h3>
	<br/><br/>
	<form id="form" class="form-horizontal" method="post" action="${context}/ucenter/safety/auth.do">
	
		<div class="form-group">
			<label class="col-sm-2 control-label">当前手机号:</label>
			<div class="col-md-4">
				${phone?substring(0,3)}****${phone?substring(7,11)}
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">验证码:</label>
			<div class="col-md-2">
				<input type="text" id="code" class="form-control required" name="code" value="" placeholder="请输入验证码"/> 
			</div>
			<div class="col-md-2">
				<button class="btn getcode" id="getCheckCode" type="button">获取验证码</button>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-4 col-sm-offset-2">
				<button type="submit" class="btn btn-primary">提交</button>
			</div>
		</div>
		<input type="hidden"  name="method" value="PHONE" />
	</form>
	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$('.submeun-4').addClass('selected').parents().show();
	$(function(){
		FormValidation.validation('#form',function(resp){
			if(resp.code==0){
				window.location.href = "${redirect!}";
			}else{
				spark.alert(resp.message,"error");
			}
			
		});
	//发送验证码
	$('#getCheckCode').on('click',function(){
		$(this).attr('disabled',true);
		$.ajax({
			url:"${context}/ucenter/sendMbCode.do",
			data:{verify:"AUTH_PHONE_CODE"},
			type:"post",
			dataType:"json",
			success:function(res){
				if(res.code == 0){
					startTick();
				}
				else {
					spark.alert(res.message,"error");
					$('#getCheckCode').attr('disabled',false);
				}
			},
			error:function(){
				$(this).attr('disabled',false);
			}
		});
	});	
	function startTick(){
		window.tick = 60;
		window.clock = setInterval(function(){
			if(window.tick >0){
				$('#getCheckCode').text('剩余时间('+window.tick+'s)');
				window.tick--;
			}
			else {
				$('#getCheckCode').text('获取验证码');
				$('#getCheckCode').attr('disabled',false);
				clearInterval(window.clock);
			}
		},1000);
	}		
		
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />