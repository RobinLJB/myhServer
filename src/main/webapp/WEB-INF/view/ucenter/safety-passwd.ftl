<@override name="head">
</@override>
<@override name="body">
	<!-- 会员中心主页 -->
	<h3>修改密码</h3>
	<br/><br/>
	<form id="form" class="form-horizontal" method="post" action="${context}/ucenter/safety/passwd.do">
		<div class="form-group">
			<label class="col-sm-2 control-label">当前密码</label>
			<div class="col-md-4">
				<input id="originPasswd"  minlength="6"  maxlength="18" type="password" class="form-control required" name="originPasswd" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">新密码</label>
			<div class="col-md-4">
				<input id="newPasswd"  minlength="6"  maxlength="18" type="password" class="form-control required" name="newPasswd" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">新密码确认</label>
			<div class="col-md-4">
				<input equalTo="#newPasswd"  minlength="6"  maxlength="18" id="newPasswd2" type="password" class="form-control required" name="newPasswd2" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-4 col-sm-offset-2">
				<button type="submit" id="btn_passwd" class="btn btn-primary">提交</button>
			</div>
		</div>
	</form>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script>
	$(document).keyup(function(e){ 
		if(e.keyCode==13){ 
			$('#btn_passwd').trigger("click");
		} 
	}); 
	FormValidation.validation('#form',function(resp){
			if(resp.code==0){
				spark.alert(resp.message,"success");
				location.href = "${context}/ucenter/home.html";
			}else{
			 spark.alert(resp.message,"error");
			}
		});
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-update.ftl" />