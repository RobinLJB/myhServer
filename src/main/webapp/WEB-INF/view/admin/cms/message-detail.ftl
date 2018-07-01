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
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="message:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">信息管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form class="form-horizontal" id="messageform" style="margin-top:20px;">
	    <input type="hidden" name="id" value="<#if messageMap??>${messageMap.id!}</#if>" >
	    <div class="form-group">
		    <label for="inputEmail3" class="col-sm-1 control-label">标题</label>
		    <div class="col-sm-5">
		      <input type="text" class="form-control" name="columName" id="inputEmail3" placeholder="文章标题" value="<#if messageMap??>${messageMap.columName!}</#if>">
		    </div>
		  </div>
		  <div class="form-group">
		     <label class="col-sm-1 control-label">排序</label>
		     <div class="col-sm-5">
				  <input type="text" name="sort" class="form-control" value="<#if messageMap??>${messageMap.sort!}</#if>">
		     </div>
		  </div>
		 <div class="form-group">
		    <label for="detail" class="col-sm-1 control-label">信息内容</label>
		    <div class="col-sm-10">
		      <textarea name="content" style="height:420px;" id="detail"><#if messageMap??>${messageMap.content!}</#if></textarea>
		    </div>
		  </div>
		  <button id="save" type="button" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">保存</button>
	</form>
</div>	
</@override>
<@override name="script">
<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
 var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
 //保存
 $('#save').click(function(){
   var $btn = $(this).button('loading');
    $.ajax({
       url:'${context}/admin/cms/message/update.do',
       type:'post',
       data:$('#messageform').serialize(),
       success:function(data){
         if(data.code == 0){
             spark.notify('操作成功','success');
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