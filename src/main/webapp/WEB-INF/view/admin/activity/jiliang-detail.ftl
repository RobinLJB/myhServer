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
<input type="hidden" id="MENU_ACTIVE_ID" value="activity:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/cms/article.html">推广管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">计量管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<form class="form-horizontal" id="articleform" method="post"  style="margin-top:20px;">

			<input type="hidden" name="id" value="<#if resultMap??>${resultMap.id!}<#else>-1</#if>" >
			
			<div class="form-group">
				<label for="url" class="col-sm-2 control-label">推广链接</label>
				<div class="col-sm-5">
				  <span><#if resultMap??>${resultMap.url!}</#if></span>
				</div>
			</div>
			
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">推广名称</label>
				<div class="col-sm-5">
				  <input type="text" minlength="2" maxlength="20" class="form-control required " name="name" placeholder="推广名称" value="<#if resultMap??>${resultMap.name!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="key" class="col-sm-2 control-label">KEY</label>
				<div class="col-sm-5">
				  <input type="text" minlength="6"  maxlength="20"  class="form-control required " name="key" id="key" placeholder="唯一标示" value="<#if resultMap??>${resultMap.onlyKey!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">登陆用户</label>
				<div class="col-sm-5">
				  <input type="text"   maxlength="20"  class="form-control required " name="username" id="username" placeholder="登陆用户名" value="<#if resultMap??>${resultMap.username!}</#if>">
				</div>
			</div>
		
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">查询密码</label>
				<div class="col-sm-5">
				  <input type="text" minlength="6"  maxlength="10"  class="form-control required " name="password" id="password" placeholder="查询密码" value="<#if resultMap??>${resultMap.password!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">推广手机</label>
				<div class="col-sm-5">
				  <input type="text" minlength="11"  maxlength="11" number="true" class="form-control required " name="phone" id="phone" placeholder="推广手机" value="<#if resultMap??>${resultMap.phone!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="qq" class="col-sm-2 control-label">QQ</label>
				<div class="col-sm-5">
				  <input type="text" class="form-control" name="qq" id="qq" placeholder="QQ" value="<#if resultMap??>${resultMap.qq!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="review" class="col-sm-2 control-label">备注</label>
				<div class="col-sm-5">
				  <input type="text" class="form-control required" name="review" id="review" placeholder="备注" value="<#if resultMap??>${resultMap.review!}</#if>">
				</div>
			</div>
			
		
			<div class="form-group">
		    	<label class="col-sm-2 control-label">查询状态</label>
		     	<div class="col-sm-3">
			        <label class="radio-inline">
						<input type="radio" name="isShow" value="1" <#if resultMap??><#if resultMap.status=="1">checked</#if></#if>>是
					</label>
					<label class="radio-inline">
						<input type="radio" name="isShow" value="2"  <#if resultMap??><#if resultMap.status=="2">checked</#if></#if>/> 否
					</label>
					
				</div>
		    </div>
		
			
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
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script>
	
	$(function(){
		FormValidation.validation('#articleform',function(resp){
			if(resp.code == 0){
			spark.notify(resp.message);
			spark.redirect('${context}/admin/extension/group.html');
		}
		else spark.notify(resp.message,'error');
		});
		
	})
	
	$("#save").click(function(){
	
		$.ajax({
			url:'${context}/admin/extension/group/update.do',
			type:'post',
			data:$('#articleform').serialize(),
			success:function(data){
				if(data.code == 0){
					spark.notify('新建成功');
				spark.redirect('${context}/admin/extension/group.html');
				}else{
					spark.notify(data.message,'error');
				}
				setTimeout(function(){
					$btn.button('reset');
				},3000);
			}
		});
		
	})
	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />