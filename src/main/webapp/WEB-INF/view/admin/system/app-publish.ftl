<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="app:list" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/app.html">APP版本</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">发布新版本</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form id="roleForm" action="${context}/admin/app/publish.do" class="form-horizontal" role="form" style="margin-top:20px;">
		<div class="form-body">
			<div class="form-group">
				<label class="col-md-3 control-label">平台
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<select id="platform" name="platform" class="form-control">
						<option value="1">IOS</option>
						<option value="2">Android</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">版本号
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="revision" value="" type="text" name="revision" class="form-control required" placeholder="请输入版本号，如 2.1">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">下载链接
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="url" value="" type="text" name="url" class="form-control required" placeholder="新版下载链接">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">升级说明</label>
				<div class="col-md-4">
					<textarea id="remark" name="remark" class="form-control" rows="3"></textarea>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn green">发布</button>
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
		FormValidation.validation('#roleForm',function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
				spark.redirect("${context}/admin/app.html");
			}
			else spark.notify(resp.message,'error');
		});
		
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />