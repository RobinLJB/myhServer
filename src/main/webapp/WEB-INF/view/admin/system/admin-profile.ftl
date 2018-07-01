<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="role:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">修改密码</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div> 
	</div>
	<form id="userForm" action="${context}/admin/profile/password/update.do" class="form-horizontal" role="form" style="margin-top:20px;">
		<div class="form-body">
			<@token name="USER_PROFILE" />
			
			<div class="form-group">
				<label class="col-md-3 control-label">原密码
				<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="oldpassword" placeholder="请输入原密码" type="password" name="oldpassword" class="form-control" placeholder="">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">新密码
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="password" type="password" name="password" class="form-control required" placeholder="请输入新密码">
				</div>
			</div>
		</div>
		<div class="form-actions">
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn green">提交</button>
				</div>
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
		FormValidation.validation('#userForm',function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
			}
			else spark.notify(resp.message,'error');
		});
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />