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
<input type="hidden" id="MENU_ACTIVE_ID" value="site:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">站点信息管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<form action="javascript:;" method="post" class="form-horizontal" id="sysinfo" style="margin-top:20px;overflow:hidden;">
       <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="sitename" class="control-label col-sm-2">站点名称</label>
                <div class="col-sm-9">
		    		<input id="sitename" type="text" class="form-control" name="sitename" value="<#if siteMap??>${siteMap.siteName!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="companyname" class="control-label col-sm-2">公司名称</label>
                <div class="col-sm-9">
		    		<input id="companyname" class="form-control" name="companyname" value="<#if siteMap??>${siteMap.companyName!}</#if>">
	    		</div>
           </div>
       </div> 
         <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="email" class="control-label col-sm-2">邮箱</label>
                <div class="col-sm-9">
		    		<input id="email" type="email"  class="form-control" name="email" value="<#if siteMap??>${siteMap.postcode!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="address" class="control-label col-sm-2">地址</label>
                <div class="col-sm-9">
		    		<input id="address" class="form-control" name="address" value="<#if siteMap??>${siteMap.address!}</#if>">
	    		</div>
           </div>
       </div> 
         <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="chargename" class="control-label col-sm-2">负责人</label>
                <div class="col-sm-9">
		    		<input id="chargename" type="text" class="form-control" name="chargename" value="<#if siteMap??>${siteMap.principal!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="contactname" class="control-label col-sm-2">联系人</label>
                <div class="col-sm-9">
		    		<input id="contactname" class="form-control" name="contactname" value="<#if siteMap??>${siteMap.contact!}</#if>" >
	    		</div>
           </div>
       </div> 
         <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="mobilephone" class="control-label col-sm-2">电话号码</label>
                <div class="col-sm-9">
		    		<input id="mobilephone" type="text" class="form-control" type="text"  name="mobilephone" value="<#if siteMap??>${siteMap.telephone!}</#if>" >
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="telephone" class="control-label col-sm-2">手机号码</label>
                <div class="col-sm-9">
		    		<input id="telephone" class="form-control" name="telephone" value="<#if siteMap??>${siteMap.cellphone!}</#if>">
	    		</div>
           </div>
       </div> 
        <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="fax" class="control-label col-sm-2">传真</label>
                <div class="col-sm-9">
		    		<input id="fax" type="text" class="form-control" name="fax" value="<#if siteMap??>${siteMap.fax!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="contactemail" class="control-label col-sm-2">联系人邮箱</label>
                <div class="col-sm-9">
		    		<input id="contactemail" class="form-control" name="contactemail" type="email" value="<#if siteMap??>${siteMap.email!}</#if>">
	    		</div>
           </div>
       </div> 
        <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="qq" class="control-label col-sm-2">QQ</label>
                <div class="col-sm-9">
		    		<input id="qq" type="text" class="form-control" name="qq" value="<#if siteMap??>${siteMap.qq!}</#if>" >
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="serverphone" class="control-label col-sm-2">服务电话</label>
                <div class="col-sm-9">
		    		<input id="serverphone" class="form-control" name="serverphone" type="tel"  value="<#if siteMap??>${siteMap.servicePhone!}</#if>">
	    		</div>
           </div>
       </div> 
        <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="icpnumber" class="control-label col-sm-2">ICP证书号</label>
                <div class="col-sm-9">
		    		<input id="icpnumber" type="text" class="form-control" name="icpnumber" value="<#if siteMap??>${siteMap.certificate!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="domainname" class="control-label col-sm-2">站点域名</label>
                <div class="col-sm-9">
		    		<input id="domainname" class="form-control" name="domainname" value="<#if siteMap??>${siteMap.regionName!}</#if>">
	    		</div>
           </div>
       </div> 
        <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="personnum" class="control-label col-sm-2">加入人数</label>
                <div class="col-sm-9">
		    		<input id="personnum" type="number" min="0" class="form-control" name="personnum" value="<#if siteMap??>${siteMap.joinperson!}</#if>">
	    		</div>
	    		</div>
           </div> 
            <div class="form-group col-md-6">
                <label for="completeamount" class="control-label col-sm-2">完成金额(万)</label>
                <div class="col-sm-9">
		    		<input id="completeamount" type="text"  class="form-control" name="completeamount" value="<#if siteMap??>${siteMap.totalmoney!}</#if>" >
	    		</div>
           </div>
       </div> 
       <div class="row">
            <div class="form-group col-md-6">
                <div class="row">
                <label for="platformname" class="control-label col-sm-2">平台名称</label>
                <div class="col-sm-9">
		    		<input id="platformname" type="text" class="form-control" name="platformname" value="<#if siteMap??>${siteMap.platformName!}</#if>">
	    		</div>
	    		</div>
           </div> 
       </div> 
       		<input type="hidden" id="id" name="id" value="<#if siteMap??>${siteMap.id!}</#if>">
            <button id="submit" type="submit" class="btn btn-default pull-right" style="margin-right: 58px;">保存</button> 
    </form>     
</div>	
</@override>
<@override name="script">
  <script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
  <script src="${context}/asset/public/plugins/jquery-validation/js/additional-methods.min.js"></script>
  <script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
  <script>
   var sysinfor =  $("#sysinfo");
   sysinfor.validate({
        rules: {
		   sitename: "required",
		   contactemail: {
		    required: true,
		    email: true
		   },
		   companyname: {
		    required: true
		   },
		   email: {
		    required: true,
		    email: true
		   },
		  address: {
		    required: true
		  },
		  chargename: {
		   required: true
		  },
		  contactname: {
		   required: true
		  },
		  mobilephone: {
		   required: true
		  },
		 telephone: {
		   required: true
		 },
		 fax: {
		   required: true
		 },
		 qq: {
		   required: true
		 },
		 serverphone: {
		   required: true
		 },
		 icpnumber: {
		   required: true
		 },
		 domainname: {
		   required: true
		 },
		 personnum: {
		   required: true
		 },
		 completeamount: {
		   required: true
		 },
		 platformname: {
		   required: true
		 }
     }, 
	  submitHandler: function(form){      
		    var $btn = $('submit').button('loading');
		    var id = $.trim($('#id').val())? $('#id').val() : '-1';
		    
		    $.ajax({
		       url:'${context}/admin/site/'+id+'.do',
		       type:'post',
		       data:$('form').serialize(),
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
	    },
	    
    });
  </script>
</@override>
<@layout name="/admin/layout/main.ftl" />