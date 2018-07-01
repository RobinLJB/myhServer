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
				<a href="${context}/admin/platformCost.html">平台收费信息</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form id="roleForm" action="${context}/admin/platformCost/update/${costMap.id}.do" class="form-horizontal" method="post" role="form" style="margin-top:20px;">
		<div class="form-body">
			<div class="form-group">
				<label class="col-md-3 control-label">名称
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<p class="form-control-static">${costMap.name!}</p>
				</div>
			</div>
			
		
				<div class="form-group">
					<label class="col-md-3 control-label">数值
						<span class="required">*</span>
					</label>
					<div class="col-md-4">
						<input name="value" value="${costMap.value!}" type="text" name="title" class="form-control required" placeholder="请输入角色名">
					</div>
				</div>
			
			
			<div class="form-group">
				<label class="col-md-3 control-label">描述
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input name="review" value="${costMap.review!}" type="text" name="title" class="form-control required" placeholder="请输入角色名">
				</div>
			</div>
			
			
		</div>
		<div class="form-actions">
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" id="submit" class="btn green">提交</button>
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
		spark.redirect('${context}/admin/platformCost.html');
		}
		else spark.notify(resp.message,'error');
	});
});	

	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />