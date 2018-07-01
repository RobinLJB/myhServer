<@override name="head">
</@override>
<@override name="body">
<div class="reg-cont">
	<form id="form" method="post" action="${context}/ucenter/findPwd.do">
		<div class="form-group group">	请输入您要找回登录密码的账户</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">账 户：</label>
			<div class="col-md-4">
				<input name="phone" class="form-control" type="text" placeholder="用户名">
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-4 col-sm-offset-2">
				<button type="submit" class="btn btn-primary">提交</button>
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
<script>
	$(function(){
		FormValidation.validation('#form',function(resp){
			if(resp.code==0){
				window.location.href = "findPwd2.html";
			}else{
			alert(resp.message);}
		});
		});
		
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />