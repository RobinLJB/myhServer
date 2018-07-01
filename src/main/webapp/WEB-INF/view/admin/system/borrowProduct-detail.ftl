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
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:loan" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="${context}/admin/cms/article.html">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<form class="form-horizontal" id="articleform" method="post" action="${context}/admin/borrowSet/borrow/update.do" style="margin-top:20px;">
	
			<input type="hidden" name="id" value="<#if resultMap??>${resultMap.id!}<#else>-1</#if>" >
			
			<div class="form-group">
				<label for="amount" class="col-sm-2 control-label">借款金额</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="amount" id="amount" placeholder="请输入大于0的整数" value="<#if resultMap??>${resultMap.amount!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="borrowdays" class="col-sm-2 control-label">借款天数</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="borrowdays" id="borrowdays" placeholder="请输入大于0的整数" value="<#if resultMap??>${resultMap.borrowdays!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="xinfee" class="col-sm-2 control-label">信审费率</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="xinfee" id="xinfee" placeholder="请输入0-1之间的数字" value="<#if resultMap??>${resultMap.xinfee!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="servicefee" class="col-sm-2 control-label">服务费率</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="servicefee" id="servicefee" placeholder="请输入0-1之间的数字" value="<#if resultMap??>${resultMap.servicefee!}</#if>">
				</div>
			</div>
			
			
			<div class="form-group">
				<label for="shoufee" class="col-sm-2 control-label">手续费率</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="shoufee" id="shoufee" placeholder="请输入0-1之间的数字" value="<#if resultMap??>${resultMap.shoufee!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
				<label for="overduefee" class="col-sm-2 control-label">逾期费率</label>
				<div class="col-sm-5">
				  <input type="text"  class="form-control required " number="true" name="overduefee" id="overduefee" placeholder="请输入0-1之间的数字" value="<#if resultMap??>${resultMap.overduefee!}</#if>">
				</div>
			</div>
			
			<div class="form-group">
		    	<label class="col-sm-2 control-label">使用状态</label>
		     	<div class="col-sm-3">
			        <label class="radio-inline">
						<input type="radio" name="status" value="1" <#if resultMap??><#if resultMap.status=="1">checked</#if></#if>>使用中
					</label>
					<label class="radio-inline">
						<input type="radio" name="status" value="2"  <#if resultMap??><#if resultMap.status=="2">checked</#if></#if>/>未使用
					</label>
					
				</div>
		    </div>
		
			
		  	<div class="form-group">
		  		<div class="col-sm-10 col-sm-offset-2">
		  			 <button id="sasve" type="submit" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off">保存</button>
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
				window.location="${context}/admin/borrowSet/borrow.html";
			}else{
				alert(resp.message);
			 }
		});
		
	})
	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />