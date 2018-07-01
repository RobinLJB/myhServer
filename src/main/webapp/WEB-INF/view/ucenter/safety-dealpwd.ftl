<@override name="head">
</@override>
<@override name="body">
	<!-- 会员中心主页 -->
	<form id="form" class="form-horizontal" method="post" action="${context}/ucenter/safety/dealpwd/update.do">
		<div class="form-group">
			<label class="col-sm-2 control-label">当前支付密码</label>
			<div class="col-md-4">
				<input id="originPasswd" type="password" class="form-control required" name="originPasswd" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">新支付密码</label>
			<div class="col-md-4">
				<input id="newPasswd" type="password" class="form-control required" name="newPasswd" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">确认密码</label>
			<div class="col-md-4">
				<input equalTo="#newPasswd" id="newPasswd2" type="password" class="form-control required" name="newPasswd2" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-4 col-sm-offset-2">
				<button type="submit" class="btn btn-primary">提交</button>
			</div>
		</div>
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
			if(resp.code == 0){
				alert('修改成功');
				location.reload();
			}
			else alert(resp.message);
		});
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />