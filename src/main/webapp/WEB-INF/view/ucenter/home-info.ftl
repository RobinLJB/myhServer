<@override name="head">
<style>
.form-group .col-sm-10{    width: 40%;}
.form-group input{border-radius: 5px;}
.form-horizontal{    padding: 35px;}
.form-group select{ border-radius: 5px;}
.form-group .form-control{margin-top:0px;}
.form-group .radio-inline{    padding-left: 35px;}
</style>
</@override>
<@override name="body">
 <div class="r_main">
		<div class="tabtil form-group">
        <ul>
		<li class="on">上传资料</li>
		<li><a href="${context}/ucenter/infoimg.html">上传证件</a></li>
        </ul>
        </div>
	<form class="form-horizontal" id="form" action="${context}/ucenter/info.do"  method="POST">
	
	 <div class="form-group">
		<label  class="col-sm-2 control-label">性别</label>
		 <label class="radio-inline">
		  <input type="radio" name="sex" value="男">男</label>
		<label class="radio-inline">
		  <input type="radio" name="sex" value="女">女</label>
	</div>
 
  
  <div class="form-group">
    <label  class="col-sm-2 control-label">出生日期</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="birthday" placeholder="格式：2016-01-01">
    </div>
  </div>
  
  <div class="form-group">
	<label  class="col-sm-2 control-label">年收入</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="income">
    </div>
  </div>
 
	 <div class="form-group">
		<label  class="col-sm-2 control-label">婚姻状况</label>
		 <label class="radio-inline">
		  <input type="radio" name="marry" value="已婚">已婚</label>
		<label class="radio-inline">
		  <input type="radio" name="marry" value="未婚">未婚</label>
	</div>
	
  <div class="form-group">
	<label  class="col-sm-2 control-label">教育程度</label>
	<div class="col-sm-10">
    <select class="form-control" name="education">
	 
	  <option>初中及以下</option>
	  <option>高中</option>
	  <option>大专</option>
	  <option>本科</option>
	  <option>其他</option>
	   <option selected="selected">--请选择--</option>
	</select>
	 </div>
  </div>

  <div class="form-group">
	<label  class="col-sm-2 control-label">工作城市</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="workCity">
    </div>
  </div>

	 <div class="form-group">
		<label  class="col-sm-2 control-label">有无社保</label>
		 <label class="radio-inline">
		  <input type="radio" name="socialSecurity" value="有">有</label>
		<label class="radio-inline">
		  <input type="radio" name="socialSecurity" value="无">无</label>
	</div>
	
  <div class="form-group">
	<label  class="col-sm-2 control-label">从事行业</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="industry">
    </div>
  </div>
  
	 <div class="form-group">
		<label  class="col-sm-2 control-label">有无房产</label>
		 <label class="radio-inline">
		  <input type="radio" name="hasHourse" value="有">有</label>
		<label class="radio-inline">
		  <input type="radio" name="hasHourse" value="无">无</label>
	</div>
	
	 <div class="form-group">
		<label  class="col-sm-2 control-label">有无车辆</label>
		 <label class="radio-inline">
		  <input type="radio" name="hasCar" value="有">有</label>
		<label class="radio-inline">
		  <input type="radio" name="hasCar" value="无">无</label>
	</div>
	
  <div class="form-group">
	<label  class="col-sm-2 control-label">职位</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="jobs">
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
	  <button type="submit" class="btn btn-success">提交</button>
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
	FormValidation.validation('#form',function(resp){
			if(resp.code==0){
				spark.alert("提交成功","success");
				window.location.href = "home.html";
			}else{
			spark.alert("提交失败","error");
			}
		});
		});
		
	$(document).ready(function() {
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-10 a').addClass('active').parents().show();
	})
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />