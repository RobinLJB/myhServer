<@override name="head">
<style>
#imgPreview{
height:100px;
margin-bottom:10px;
}
.btn-upload{
	position:relative;
}
.btn-upload input[type="file"] {
    position: absolute;
    opacity: 0;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    margin: 0;
    padding: 0;
    cursor: pointer;
}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/cms/attach.html">附件管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">附件编辑</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form action="${context}/admin/cms/attach/update.do" method="post" class="form-horizontal" id="form" style="margin-top:20px;">
		<input type="hidden" name="id" value="${attachMap.id!'-1'}" />
		<div class="form-group">
			<label for="type" class="col-sm-2 control-label">标题
			<span class="required">*</span>
			</label>
			<div class="col-sm-4">
				<input type="text" id="title" name="title" class="form-control required" value="${attachMap.title!}">
			</div>
		</div>
		<div class="form-group">
			<label for="type" class="col-sm-2 control-label">文件名称</label>
			<div class="col-sm-4">
				<input id="filename" type="text" name="filename" class="form-control" value="${attachMap.filename!}" />
			</div>
		</div>
		<div class="form-group">
			<input id="attachPath" type="hidden" name="path" value="${attachMap.path!}" />
			<label class="control-label col-sm-2">附件上传</label>
			<div class="col-sm-4">
				<div class="img-holder">
					<span class="btn-upload btn btn-sm btn-primary btn-shadow">
						<input type="file" accept="*/*" id="InputFile" name="file" />
						<i class="fa fa-file"></i>&nbsp;选择文件
					</span>
				</div>
				<p class="file-holder"></p>
			</div>
		</div>
		<div class="form-group">
			<label for="sort" class="col-sm-2 control-label">序号
			<span class="required">*</span>
			</label>
			<div class="col-sm-4">
				<input id="sort" value="${attachMap.sort!'1'}" type="text" class="form-control required number" name="sort" />
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button id="save" type="submit" class="btn btn-primary">提交</button>
			</div>
		</div>
	</form>
</div>
</@override>
<@override name="script">
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	FormValidation.validation('#form',function(resp){
		if(resp.code == 0){
			spark.notify(resp.message);
			spark.redirect('${context}/admin/cms/attach.html');
		}
		else spark.notify(resp.message,'error');
	});
	$('#InputFile').AjaxFileUpload({
		//处理文件上传操作的服务器端地址
		action: '${context}/upload/attach.do',
		onComplete: function(filename, resp) { //服务器响应成功时的处理函数
			$('#filename').val(filename);
			if(resp.code == 0){
				$('#attachPath').val(resp.absPath);
				$('.file-holder').append('<a href="'+resp.absPath+'" target="_blank">'+filename+'</a>');
			}
			else {
				spark.notify(resp.message,'error');
			}
		}
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />