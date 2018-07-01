<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
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
.oldImg li{
	list-style-type:none;
	float: left;
    width: 120px;
	margin-left:10px;
}
#imgEvent>div:nth-child(2),#imgOrganizationUpload>div:nth-child(2)
{
    position: relative !important;
    left: 0!important;
    top: -42px !important;
}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="welfare:index" />
<div class="page-content">
 
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">募集管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">编辑</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>	
	<form class="form-horizontal" id="loanform" style="margin-top:20px;" action="${context}/admin/welfare/edit.do" method="post">
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
					<label for="title" class="col-sm-3 control-label">募集标题<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required" name="title" id="title" placeholder="募集标题" value="<#if loanMap??>${loanMap.title!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">借款金额(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					  <input type="text" class="form-control required number" name="amount" id="amount" placeholder="借款金额" value="<#if loanMap??>${loanMap.amount!}</#if>">
					</div>
				</div>	
			</td>
 	 	 
		</tr>

		
		<tr>
			<td>
				<div class="form-group">
					<label for="amount" class="col-sm-3 control-label">最低可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8">
					 	<input type="text" class="form-control required number" name="minTenderedSum" id="minTenderedSum" placeholder="最低可投" value="<#if loanMap??>${loanMap.min_invest_amount!}</#if>">
					</div>
				</div>	
			</td>
			<td>
				<div class="form-group">
					<label for="annual_rate" class="col-sm-3 control-label">最多可投(元)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number" name="maxTenderedSum" id="maxTenderedSum" placeholder="最多可投" value="<#if loanMap??>${loanMap.max_invest_amount!}</#if>">
					</div>
				</div>		
			</td>
		</tr>
		<tr> 
			<td>
				<div class="form-group">
					<label for="raise_term" class="col-sm-3 control-label">筹标期限(天)<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-8  ">
						 <input type="text" class="form-control required number"  name="raise_term" id="raise_term" placeholder="筹标期限" value="<#if loanMap??>${loanMap.raise_term!}</#if>">
					</div>
				</div>		
			</td>
			<td>
				<div class="form-group">
				<label for="open_time" class="col-sm-3 control-label">开标时间</label>
					<div class="col-sm-8  ">
					<input type="text" class="form-control"   name="open_time" id="open_time" data-date-format="yyyy-mm-dd hh:ii"   placeholder="不填则为初审时间" value="${loanMap.open_time!}">  
					</div>
				</div>	
			</td>
		</tr>
	 
		<tr>
			<td  colspan="2">
				<div class="form-group">
					<label for="cover" class="col-sm-2 control-label">封面图片<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9">
						<input type="hidden" id="cover" name="cover" class="required" value="<#if loanMap??>${loanMap.cover!}</#if>" />
						<img id="yscover" style="width:120px;height:100px;" src="<#if loanMap??>${loanMap.cover!}</#if>">
						<div class="demo">
							<div id="coverUpload" ></div>
						</div>
					</div>
				</div>	
			</td>
		</tr>
		<tr>
			<td  colspan="2">
				<div class="form-group">
					<label for="imgOrganization" class="col-sm-2 control-label">公益机构图片<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9" id="imgOrganizationMap">
						<input type="hidden" id="imgOrganization" name="imgOrganization" value="<#if loanMap??>${loanMap.img_organization!}</#if>">
						<ul id="oldimgOrganization"  class="oldImg"></ul>
						<div class="demo">
							<div id="imgOrganizationUpload"></div>
						</div>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td  colspan="2">
				<div class="form-group">
					<label for="event" class="col-sm-2 control-label">公益事件图片<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9" id="imgEventMap">
						<input type="hidden" id="event" name="event" value="<#if loanMap??>${loanMap.event!}</#if>">
						<ul id="oldevent" class="oldImg"></ul>
						<div class="demo">
							<div id="imgEvent"></div>
						</div>
					</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="form-group">
					<label for="organization" class="col-sm-2 control-label">公益组织介绍<span class="required" aria-required="true">*</span></label>
					<div class="col-sm-9">
					  <textarea name="organization" style="height:420px;width:100%;" id="organization" class="required">
					  	<#if loanMap??>${loanMap.organization!}</#if>
					  </textarea>
					</div>
				</div>	
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="form-group">
					<label for="detail" class="col-sm-2 control-label">募集详情<span class="required" aria-required="true">*</span></label>
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
		 <input type="hidden" name="id" value="<#if loanMap??>${loanMap.id!-1}</#if>" > 
	</form>
  
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
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
$(function(){
	func("event");
	func("imgOrganization");
})
//标的封面
$('#coverUpload').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
			$("#yscover").css("display","none");
			$("#cover").val(data.absPath);
	},error:function( err ) {
		alert(err);
	},
	buttonText : '选择图片',
	chunked:true,
	// 分片大小
	chunkSize:512 * 1024,
	//最大上传的文件数量
	fileNumLimit:1,
	//总文件大小
	fileSizeLimit:500000 * 1024,
	// /单个文件大小(单位字节)
	fileSingleSizeLimit:50000 * 1024,
	accept: {}
});




//公益组织图片
$('#imgOrganizationUpload').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
		var Uploadphotos = $("#imgOrganization").val();
		if (Uploadphotos == "") {
			$("#imgOrganization").val(data.absPath);
		}else{
			Uploadphotos+=","+data.absPath;
			$("#imgOrganization").val(Uploadphotos);
		}
		$("#imgOrganizationMap>img").css("display","none");
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
//公益事件图片
$('#imgEvent').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
		var Uploadphotos = $("#event").val();
		if (Uploadphotos == "") {
			$("#event").val(data.absPath);
		}else{
			Uploadphotos+=","+data.absPath;
			$("#event").val(Uploadphotos);
		}
		$("#imgEventMap>img").css("display","none");
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
function delImg(index,id){
	var num = $("#old_"+id+"_"+index).index();
	var imgArr = $("#"+id).val().split(",");
	if(imgArr && imgArr.length > 0){
		imgArr.splice(num, 1);
		$("#old_"+id+"_"+index).remove();
	}
	$("#"+id).val(imgArr.join(","));
}
function func(id){
	var cover = $("#"+id).val().split(",");
	if (cover != null) {
		for(var i=0;i<cover.length;i++){
			$("#old"+id).append("<li id='old_"+id+"_"+i+"'><a style='display: block;' alt='删除图片' href='JavaScript:;' onclick=delImg("+i+",'"+id+"')><img src="+cover[i]+" style='width:120px;height:100px;margin:5px;'/></a></li>");
		}
	}
}
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
 
 
$(function(){
	FormValidation.validation('#loanform',function(resp){
		if(resp.code == 0){
			spark.notify('操作成功','success');
			window.location.href="${context}/admin/welfare/index.html";
		}else{
			spark.notify(resp.message,'error');
		}
		
	});
});
 var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
  var ue2 = UE.getEditor('organization',{
		serverUrl:"${context}/ueditor/dispatch.html"
 });
 $("#cycle_type").change(function(){
	if($("#cycle_type").is(":checked")){
		$("#cycleT").html("天");
	}else{
		$("#cycleT").html("月");
	}
});
 

 $(function(){
	$('#open_time').datetimepicker({lang:'zh-CN',format: 'yyyy-mm-dd hh:ii',startDate:new Date(),autoclose:true});
 }); 
 

</script>
</@override>
<@layout name="/admin/layout/main.ftl" />