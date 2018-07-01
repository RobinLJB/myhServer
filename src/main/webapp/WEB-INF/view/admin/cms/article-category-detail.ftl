<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="role:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章分类</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form id="categoryForm" action="${context}/admin/cms/article/category/update/${CategoryMap.id}.do" class="form-horizontal" role="form" style="margin-top:20px;">
		<div class="form-body">
			<@token name="CATEGORY_DETAIL" />
			<div class="form-group">
				<label class="col-md-3 control-label">分类名称
					<span class="required">*</span>
				</label>
				<div class="col-md-4">
					<input id="name" value="${CategoryMap.name!}" type="text" name="name" class="form-control required" placeholder="请输入分类名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">关键词</label>
				<div class="col-md-4">
					<textarea id="keywords" name="keywords" class="form-control" rows="3">${CategoryMap.keywords!}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">描述</label>
				<div class="col-md-4">
					<textarea id="descriptions" name="descriptions" class="form-control" rows="3">${CategoryMap.descriptions!}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">排序</label>
				<div class="col-md-4">
					<input id="sort" name="sort" class="form-control" rows="3" value="${CategoryMap.sort!}"></input>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-3 control-label">状态<span class="required">*</span></label>
				<div class="col-md-4">
					<input type="radio" name="status"   value="1"  class="required" <#if  ((CategoryMap.status)!) =='1' >checked</#if>/><label>显示</label>
					<input type="radio" name="status"   value="2"  class="required" <#if  ((CategoryMap.status)!) =='2' >checked</#if>/><label>隐藏</label>
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
		FormValidation.validation('#categoryForm',function(resp){
			if(resp.code == 0){
				spark.notify(resp.message);
			}
			else spark.notify(resp.message,'error');
		});
		
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />