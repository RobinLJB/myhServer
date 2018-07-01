<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="admin:list" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/user.html">用户管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">用户详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form id="userForm" action="${context}/admin/user/update/${user.id}.do" class="form-horizontal" role="form" style="margin-top:20px;">
		<div class="form-body">
			<@token name="USER_DETAIL" />
			<div class="form-group">
				<label class="col-md-3 control-label">用户名
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="username" value="${user.username!}" type="text" name="username" class="form-control required" placeholder="请输入用户名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">部门
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<select name="departmentId" class="form-control required">
					<option value="">--请选择--</option>
					<#list departments as department>
						<option <#if user.departmentId?? && user.departmentId == department.id>selected</#if> value="${department.id}">${department.name}</option>
					</#list>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">角色
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<select name="roleId" class="form-control required">
					<option value="">--请选择--</option>
					<#list roles as role>
						<option <#if user.roleId?? && user.roleId == role.id>selected</#if> value="${role.id}">${role.name}</option>
					</#list>
					</select>
				</div>
			</div>
			<#if user.username??>
			<div class="form-group">
				<label class="col-md-3 control-label">密码
				</label>
				<div class="col-md-4">
					<input id="password" type="password" name="password" class="form-control" placeholder="">
					<span class="help-block">密码不为空时表示修改密码</span>
				</div>
			</div>
			<#else>
			<div class="form-group">
				<label class="col-md-3 control-label">密码
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="password" type="password" name="password" class="form-control required" placeholder="请输入密码">
				</div>
			</div>
			</#if>
			<div class="form-group">
				<label class="col-md-3 control-label">状态
				</label>
				<div class="col-md-4">
					<div class="input-group">
						<div class="icheck-inline">
							<#if user.username??>
							<label><input type="radio" <#if user.enable == '1'>checked</#if> name="enable" value="1" class="icheck">启用</label>
							<label><input type="radio" <#if user.enable == '2'>checked</#if> name="enable" value="2" class="icheck">禁用</label>
							<#else>
							<label><input type="radio" name="enable" value="1" checked class="icheck">启用</label>
							<label><input type="radio" name="enable" value="2" class="icheck">禁用</label>
							</#if>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">真实姓名
				</label>
				<div class="col-md-4">
					<input id="realName" value="${user.realName!}" type="text" name="realName" class="form-control" placeholder="用户的真实姓名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">手机号
				</label>
				<div class="col-md-4">
					<input id="mobilePhone" value="${user.mobilePhone!}" type="text" name="mobilePhone" class="form-control" placeholder="请输入用户手机号">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">描述</label>
				<div class="col-md-4">
					<textarea id="desc" name="remark" class="form-control" rows="3">${user.remark!}</textarea>
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
				spark.redirect('${context}/admin/user.html');
			}
			else spark.notify(resp.message,'error');
		});
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />