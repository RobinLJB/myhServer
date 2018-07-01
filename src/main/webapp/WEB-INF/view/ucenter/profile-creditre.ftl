<@override name="head">
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<style>
.form-group .col-sm-10{width: 40%;}
.form-group input{border-radius: 5px;}
.form-group select{ border-radius: 5px;}
.form-group .form-control{margin-top:0px;}
.form-group .radio-inline{padding-left: 35px;}
.form-horizontal .form-group{padding-bottom: 45px}
.form-border {
    padding: 4px 12px;
    margin: 10px 0 30px;
    font-weight: 500;
    font-size: 20px;
    border-left: 7px solid #fd852f;
}
.control-label .required {
    color: #e02222;
    font-size: 12px;
    padding-left: 2px;
}
.form-group .control-label{padding: 7px 15px 0;}
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
.form-horizontal {
    width: 97%;
    margin: 0 auto;
}
.nav>li>a{font-size: 16px;    padding: 10px 15px!important;}
.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover{border:none;border-bottom: 2px solid #fd852f;border-bottom-color: #fd852f;}
</style>
</@override>
<@override name="body">
 <div class="r_main">
 <h3 class="form-border">个人认证</h3>
 <ul class="nav nav-tabs" style="margin: 0 auto 30px;width: 85%;">
    <li>
    	<a href="${context}/ucenter/info.html">个人资料</a>
    </li>
    <li class="active">
    	<a href="${context}/ucenter/creditre.html">信用审核</a>
    </li>
  </ul>
  <!-- Tab panes -->
  <div class="tab-content">
    	<form class="form-horizontal" id="form" action="${context}/ucenter/creditreview.do"  method="POST">
    		<div class="form-group">
		  		<label class="col-sm-2 control-label">身份证认证</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="idcardb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.idcard!''}" style="max-width: 120px"></#if>
		        	<input type="hidden" name="idcard" id="idcard" <#if profile2??>value="${profile2.idcard!''}"</#if>/> 
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<label class="col-sm-2 control-label">户口本认证</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="bookletb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.booklet!''}" style="max-width: 120px"></#if>
		        	<input type="hidden" name="booklet" id="booklet" <#if profile2??>value="${profile2.booklet!''}"</#if> /> 
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<label class="col-sm-2 control-label">收入证明</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="earningb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.earning!''}" style="max-width: 120px"></#if>
		        	<input type="hidden" name="earning" id="earning" <#if profile2??>value="${profile2.earning!''}"</#if> /> 
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<label class="col-sm-2 control-label">征信记录</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="creditb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.credit!''}" style="max-width: 120px"></#if>
		        		<input type="hidden" name="credit" id="credit" <#if profile2??>value="${profile2.credit!''}"</#if> /> 
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<label class="col-sm-2 control-label">房产证明</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="houseb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.house!''}" style="max-width: 120px"></#if>
		        	<input type="hidden" name="house" id="house" <#if profile2??>value="${profile2.house!''}"</#if> /> 
		    	</div>
		  	</div>
		  	<div class="form-group">
		  		<label class="col-sm-2 control-label">工作证明</label>
		  		<div class="col-sm-10">
					<div class="demo">
		            	<div id="workb"></div>
		          	</div>
		          	<#if profile2??><img src="${profile2.work!''}" style="max-width: 120px"></#if>
		        	<input type="hidden" name="work" id="work" <#if profile2??>value="${profile2.work!''}"</#if> /> 
		    	</div>
		  	</div>
		  	<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-success">提交</button>
			    </div>
		  	</div>
    	</form>
    </div>



	
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
<script type="text/javascript">
	FormValidation.validation('#form',function(resp){
		if(resp.code==0){
			spark.alert("提交成功");
			location.reload();
		}else{
		  spark.alert("提交失败4");
		}
	});
</script>
<script type="text/javascript">
//身份证认证
$('#idcardb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#idcard").val(data.absPath);
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
//户口本认证
$('#bookletb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#booklet").val(data.absPath);
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
//收入证明
$('#earningb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#earning").val(data.absPath);
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
//征信记录
$('#creditb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#credit").val(data.absPath);
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
//房产证明
$('#houseb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#house").val(data.absPath);
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
//工作证明
$('#workb').diyUpload({
  url:'${context}/upload/image.do?type=1',
  success:function( data ) {
    $("#work").val(data.absPath);
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

</script>

</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />