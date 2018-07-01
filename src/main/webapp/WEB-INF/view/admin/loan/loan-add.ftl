<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<style>
.checkbox, 
.form-horizontal 
.checkbox-inline, 
.form-horizontal .radio, 
.form-horizontal .radio-inline {
     padding-top: 0; 
     margin-top:4px;
 }
.form-group{
	margin:0;
}
.size1of5{width:50%}
.imgUp{width:150px; height:100px}
#ul_info li{width:150px;float:left;margin-left:7px;list-style-type:none;}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:add" />
<div class="page-content">
	<#if step==1 >
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款申请</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">选择借款账户</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>	
	<h3 class="page-title">
		借款人信息
	</h3>	
	<form class="form-inline" role="form" id="searchform" action="${context}/admin/findMemberByName.do" method="post">
		<div class="form-group">
			<label for="title">申请人</label>
			<input type="text" class="form-control"  name="username"   placeholder="申请人">
		</div>
		<div class="form-group">
			<label for="title">真实姓名</label>
			<input type="text" class="form-control"  name="realname" placeholder="真实姓名">
		</div>			
		<button type="submit" id="search" class="btn btn-default">查找</button>
	</form>
	<table class="table table-bordered" id="searchtable" style="width:500px;margin-top:50px;">
		<tbody>
			
		</tbody>
	</table>
	
	<a  id="addloan" type="button" class="btn btn-primary btn-lg"  href="#" style="margin-left: 141px;display:none">发布借款</a>
	<#elseif step==2 >
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款申请</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">借款详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>	
	<form class="form-horizontal" id="loanform" style="margin-top:20px;" action="${context}/admin/loan/add.do" method="post">
		<input type="hidden" name="applicationId"  id="applicationId" value="<#if loanMap??>${loanMap.id!}</#if>" >
		<input type="hidden" name="uid" value="<#if loanMap??>${loanMap.id!}</#if>" >
		<input type="hidden" name="status" value="<#if loanMap??>${loanMap.status!}</#if>" >
		<input type="hidden" name="flag" value="flag" >
		<table class="table table-bordered">
		<tr>
			<td>
				<div class="form-group">
					<label for="username" class="col-sm-3 control-label">用户名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="username" id="username" placeholder="用户名" value="<#if loanMap??>${loanMap.username!}</#if>" disabled>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="realName" class="col-sm-3 control-label">真实姓名<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="realName" id="realName" placeholder="真实姓名" value="<#if loanMap??>${loanMap.realName!}</#if>"  disabled>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="title" class="col-sm-3 control-label">借款标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="借款标题" value="<#if loanMap??>${loanMap.title!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">借款类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<select name="productType" id="productType" class="form-control">
					 		<#if loanMap??>
					 		<#if loanMap.loan_cate == "2">
					 			<option value="2" selected="selected">民生标</option>
						    	<option value="10">供链标</option>
						    <#elseif  loanMap.loan_cate == "10">
						    	<option value="2">民生标</option>
						    	<option value="10" selected="selected">供链标</option>
					 		</#if>
					 		</#if>
						</select>
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">借款金额(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">年化收益(%)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number" name="annualRate"  id="annualRate"  placeholder="年化收益" value="<#if loanMap??>${loanMap.annual_rate!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">借款期限<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="cycle" id="cycle" placeholder="借款期限" value="<#if loanMap??>${loanMap.cycle!}</#if>">
					</div>
				</div>	
			</td>	
			<td>
				<div class="form-group">
					<label for="cycle" class="col-sm-3 control-label">期限类型<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<select name="cycle_type" id="cycle_type" class="form-control">
					 		<#if loanMap??>
					 		<#if loanMap.cycle_type == "1">
					 			<option value="1" selected="selected">按月</option>
						    	<option value="2">按天</option>
						    <#elseif  loanMap.cycle_type == "2">
						    	<option value="1">按月</option>
						    	<option value="2" selected="selected">按天</option>
					 		</#if>
					 		</#if>
						</select>
					</div>
				</div>	
			</td>		 
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">还款方式<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <select name="paymentMode" id="paymentMode" class="form-control">
						<#if loanMap??>
							<@select group="CATE_PAYMENT" selected="${loanMap.payment_mode}" />
						<#else>
							<@select group="CATE_PAYMENT" />
						</#if>
							
					</select>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">筹标期限(天)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number"  name="raiseTerm" id="raiseTerm" placeholder="年化收益" value="<#if loanMap??>${loanMap.raise_term!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">最低可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<input type="text" class="form-control required number" name="minTenderedSum" id="minTenderedSum" placeholder="年化收益" value="<#if loanMap??>${loanMap.min_invest_amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">最多可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number" name="maxTenderedSum" id="maxTenderedSum" placeholder="年化收益" value="<#if loanMap??>${loanMap.max_invest_amount!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">封面图片<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
						<input type="hidden" id="imgPath" name="imgPath" class="required" value="<#if loanMap??>${loanMap.cover!}</#if>" />
						<img id="yscover"  style="width:120px;height:100px;"  src="<#if loanMap??>${loanMap.cover!}</#if>">
						<div class="demo">
							<div id="cover" ></div>
						</div>
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">信息披露<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8" id="loanMap_cover">
						<input type="hidden" id="Uploadphotos" name="Uploadphotos" value="<#if loanMap??>${loanMap.img_material!}</#if>">
						<div class="demo">
							<div id="material"></div>
						</div>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="form-group">
					<label for="amount" class="col-sm-2 control-label">借款详情<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9">
					  <textarea name="detail" style="height:420px;width:100%;" id="detail" class="required">
					  	<#if loanMap??>${loanMap.detail!}</#if>
					  </textarea>
					</div>
				</div>	
			</td>
		</tr>
		</table>
		  <button id="save" type="submit" class="btn btn-primary" data-loading-text="正在保存..." autocomplete="off" style="margin-left: 141px;">保存</button>
		  
	</form>
	</#if> 
</div>	
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/swfobject.js'></script>  
<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/jquery.uploadify.min.js'></script>  
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
<script type="text/javascript">
//标的封面
$('#cover').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
			$("#yscover").css("display","none");
			$("#imgPath").val(data.absPath);
	},error:function( err ) {
		alert(err);
	},
	buttonText : '选择图片',
	chunked:true,
	// 分片大小
	chunkSize:50000 * 1024,
	//最大上传的文件数量
	fileNumLimit:1,
	//总文件大小
	fileSizeLimit:500000 * 1024,
	// /单个文件大小(单位字节)
	fileSingleSizeLimit:50000 * 1024,
	accept: {}
});




//标的信息
$('#material').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
		var Uploadphotos = $("#Uploadphotos").val(null);
		if (Uploadphotos == "") {
			$("#Uploadphotos").val(data.absPath);
		}else{
			Uploadphotos+=","+data.absPath;
			$("#Uploadphotos").val(Uploadphotos);
		}
		$("#loanMap_cover>img").css("display","none");
	},error:function( err ) {
		alert(err);
	},
	buttonText : '选择图片',
	chunked:true,
	// 分片大小
	chunkSize:50000 * 1024,
	//最大上传的文件数量
	fileNumLimit:50,
	//总文件大小
	fileSizeLimit:500000 * 1024,
	// /单个文件大小(单位字节)
	fileSingleSizeLimit:50000 * 1024,
	accept: {}
});

</script>

<script type="text/javascript">  
// 上传信息披露图片
$("#uploadify").uploadify({
    height        : 30,
    swf           : '${context}/asset/public/plugins/uploadify/uploadify.swf',
    uploader      : '${context}/upload/image.do?type=1',
    fileObjName   : 'files.files',
    buttonText    : '点击上传', // 设置按钮上显示的文本
    queueID  	  : 'some_file_queue',
    width         : 120,
    'onSelect': function(file){
    	//alert(file.name+"---"+file.id);  
    }, // 选择文件时触发的方法 
    
    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
        spark.notify('The file ' + file.name + ' could not be uploaded: ' + errorString,"error");
    },//上传出错后的方法
    
    'onUploadSuccess' : function(file, data, response) { 
    	data = JSON.parse(data);		
    	if(data.code==0){ 
    		$("#imgInfo").parent().show();
    		var path = data.absPath;
    		if(!$("#imgInfo").attr("w")){
    			$("#imgInfo").attr("src",path);
    			$("#imgInfo").attr("w","1");
    			onImgInfo($("#imgInfo").parent());
    		}
    		else{
    			var li=$("#ul_info>li:first-child").clone();
    			li.find("img").removeAttr("id").attr("src",path);
    			$("#ul_info").append(li);
    			onImgInfo(li);
    		}
    	}else{
    		spark.notify(file.name+", "+data.errmsg,"error");
    	}
    	
    }, //上传成功后的方法 
    onComplete: function (event, queueID, fileObj, response, data) {  
        var value = response ; 
    },
});


function onImgInfo(li){
	li.find(".cBtnDel").click(function(e){
		if($(this).siblings("img").attr("id")=="imgInfo"){
			$("#imgInfo").attr("src", "images/default-img.jpg");
			$("#imgInfo").parent().hide();
			$("#imgInfo").removeAttr("w");
		}else{
			$(this).parent().remove();
		}
		changeImgUrl();
	});
	li.mouseenter(function(e){
		li.find(".cBtnDel").show();
	});
	li.mouseleave(function(e){
		li.find(".cBtnDel").hide();
	});
	changeImgUrl();
}
//信息纰漏图片名称
function  changeImgUrl(){
	var imgStr="";
	$("img.imgUp").each(function(i,o){
		if(imgStr!=""){
			imgStr+=",";
		}
		if($(o).attr("src")!="" && $(o).attr("src")!="images/default-img.jpg") { 
			imgStr+=$(o).attr("src"); 
		}
	});
	$("#img_information").val(imgStr);
}
           
 </script> 
<script>
 
<#if step==1 > 
$(function(){
	FormValidation.validation('#searchform',function(data){
		if(data.length!=0){
			$("#searchtable tbody").html("<tr><td>用户名</td><td>"+data.username+"</td></tr>"+
										"<tr><td>真实姓名</td><td>"+data.real_name+"</td></tr>"+
										"<tr><td>手机号</td><td>"+data.mobilePhone+"</td></tr>"+
										"<tr><td>身份证号</td><td>"+data.ident_no+"</td></tr>"+
										"<tr><td>可用余额</td><td>"+data.usable_balance+"</td></tr>");
										
			$("#addloan").attr("href","${context}/admin/loan/add/-"+data.id+".html");							
			$("#addloan").show();
		}else{
			spark.notify("用户不存在",'error');
		}	
	});
});
<#elseif step==2 > 
$(function(){
	FormValidation.validation('#loanform',function(resp){
		if(resp.code == 0){
			spark.notify('操作成功','success');
		}else{
			spark.notify(resp.message,'error');
		}
		
	});
});
 var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
 $("#cycle_type").change(function(){
	if($("#cycle_type").is(":checked")){
		$("#cycleT").html("天");
	}else{
		$("#cycleT").html("月");
	}
});
</#if>

 
 

</script>
</@override>
<@layout name="/admin/layout/main.ftl" />