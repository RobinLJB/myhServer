<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<style>
.checkbox, 
.form-horizontal 
.checkbox-inline, 
.form-horizontal .radio, 
.form-horizontal .radio-inline {
     padding-top: 0; 
     margin-top:4px;
 }
 #InputFile{
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
<input type="hidden" id="MENU_ACTIVE_ID" value="article:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/cms/article.html">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<form class="form-horizontal" id="articleform" style="margin-top:20px;">
			<input type="hidden" name="id" value="${resultMap.id!}" >
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">标题</label>
				<div class="col-sm-5">
				  <input type="text" class="form-control" name="title" id="inputEmail3" placeholder="文章标题" value="${resultMap.title!}">
				</div>
			</div>
			
			
			
			<div class="form-group">
		    <label for="type" class="col-sm-2 control-label">文章类型</label>
		    <div class="col-sm-3">
		       <select class="form-control" name="type" id="type">
					<@select group="CATE_ARTICLE" selected="${resultMap.cate_id!''}" />
			    </select>
		    </div>
			</div>
			<div class="form-group">
		    	<label class="col-sm-2 control-label">显示</label>
		     	<div class="col-sm-3">
			     	<#if (resultMap.isShow?? && resultMap.isShow='0')>  
			        <label class="radio-inline">
						<input type="radio" name="isShow" value="1" >是
					</label>
					<label class="radio-inline">
						<input type="radio" name="isShow" value="0" checked /> 否
					</label>
					<#else>
					 <label class="radio-inline">
						<input type="radio" name="isShow" value="1" checked />是
					</label>
					<label class="radio-inline">
						<input type="radio" name="isShow" value="0"> 否
					</label>
					</#if>
				</div>
		    </div>
		
			<div class="form-group">
				<label class="col-sm-2 control-label">排序</label>
				<div class="col-sm-3">
					<input type="text" name="sort" class="form-control" value="<#if resultMap??>${resultMap.sort!'1'}</#if>">
				</div>
			</div>
			<div class="form-group">
			    <label for="detail" class="col-sm-2 control-label">文章内容</label>
			    <div class="col-sm-10">
			      <textarea name="detail" style="height:420px;" id="detail"><#if resultMap??>${resultMap.content!}</#if></textarea>
			    </div>
		  	</div>
		<!--   	<div class="form-group">
				<label class="col-sm-2 control-label">文章外链</label>
				<div class="col-sm-3">
					<input type="text" name="link" class="form-control" value="<#if resultMap??>${resultMap.link!}</#if>">
				</div>
			</div> -->
		  	<div class="form-group">
		  		<div class="col-sm-10 col-sm-offset-2">
		  			 <button id="save" type="button" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">保存</button>
		  		</div>
		  	</div>
	</form>
</div>	
</@override>
<@override name="script">
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
	$('#InputFile').AjaxFileUpload({
		//处理文件上传操作的服务器端地址
		action: '${context}/upload/image.do?type=3',
		onComplete: function(filename, resp) { //服务器响应成功时的处理函数
			if(resp.code == 0){
				$('#imgPath').val(resp.absPath);
				$('#imgPreview').attr('src',resp.absPath);
			}
			else {
				spark.notify(resp.message,'error');
			}
		}
	});
	
	
	var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.do",
		imageUrlPrefix:"${context}"
	});
	//保存
	$('#save').click(function(){
		var $btn = $(this).button('loading');
		$.ajax({
			url:'${context}/admin/cms/article/update.do',
			type:'post',
			data:$('#articleform').serialize(),
			success:function(data){
				if(data.code == 0){
					spark.notify('操作成功','success');
					location.href="${context}/admin/cms/article.html";
				}else{
					spark.notify('操作失败','error');
				}
				setTimeout(function(){
					$btn.button('reset');
				},3000);
			}
		});
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />