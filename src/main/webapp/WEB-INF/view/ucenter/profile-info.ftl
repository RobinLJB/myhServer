<@override name="head">
<style>
.form-group .col-sm-10{width: 40%;}
.form-group input{border-radius: 5px;}
.form-group select{ border-radius: 5px;}
.form-group .form-control{margin-top:0px;}
.form-group .radio-inline{padding-left: 35px;}
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
    <li class="active">
    	<a href="${context}/ucenter/info.html">个人资料</a>
    </li>
    <li>
    	<a href="${context}/ucenter/creditre.html">信用审核</a>
    </li>
  </ul>
  <!-- Tab panes -->
  <div class="tab-content">
    	<form class="form-horizontal" id="form" action="${context}/ucenter/person/profile.do"  method="POST">
    	<div class="form-group">
    	<label  class="col-sm-2 control-label">审核状态</label>
	    <div class="col-sm-10">
	      <p class="form-control-static">
		      	<#if isCheck=="0">
					新增个人资料
				</#if>
				<#if isCheck=="2">
					审核成功
				</#if>
				<#if isCheck=="3">
					审核失败
				</#if>
			</p>
	    </div>
  	</div>
  	<div class="form-group">
		<label  class="col-sm-offset-3 col-sm-2 control-label">真实姓名
		</label>
		<div class="col-sm-4">
		    <input type="hidden" name="realname" value="${Session.MEMBER.realName!}" />
		    <p class="form-control-static">${Session.MEMBER.realName!}</p>
		</div>
    </div>
    <div class="form-group">
		<label  class="col-sm-offset-3 col-sm-2 control-label">身份证号
		</label>
		<div class="col-sm-4">
			<input type="hidden" name="cardNo" value="${Session.MEMBER.identNo!}" />
			<p class="form-control-static">${Session.MEMBER.identNo!}</p>
		</div>
    </div>
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
	      <input type="text" class="form-control form_datetime required" name="birthday"  value="2017-01-01" readonly >
	    </div>
  	</div>
  
  <div class="form-group">
	<label  class="col-sm-2 control-label">年收入</label>
    <div class="col-sm-10">
     	<input type="text" class="form-control required number" minlength="2" number="true"  maxlength="20" placeholder="单位：万元" name="income"  >
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
		<select class="form-control required"  name="education">
				 <option selected="selected">--请选择--</option>
				 <option value="初中及以下">初中及以下</option>
				 <option value="高中">高中</option>
				 <option value="大专">大专</option>
				 <option value="本科">本科</option>
				 <option value="研究生及以上">研究生及以上</option>
			</select>
	 </div>
  </div>

  <div class="form-group">
	<label  class="col-sm-2 control-label">工作城市</label>
    <div class="col-sm-10" id="element_id">
     	<select class="form-control required province" style="width: 48%;float: left;" name="province"  id="province"></select> 
		<select class="form-control required city" style="width: 48%;float: right;" name="city"  id="city"></select> 
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
		<select id="industry" class="form-control" name="industry" >
				<option value="">请选择</option>
				<option value="农、林、牧、渔业">农、林、牧、渔业</option>
				<option value="采矿业">采矿业</option>
				<option value="电力、热力、燃气及水的生产和供应业">电力、热力、燃气及水的生产和供应业</option>
				<option value="制造业">制造业</option>
				<option value="教育">教育</option>
				<option value="环境和公共设施管理业">环境和公共设施管理业</option>
				<option value="建筑业">建筑业</option>
				<option value="交通运输、仓储业和邮政业">交通运输、仓储业和邮政业</option>
				<option value="信息传输、计算机服务和软件业">信息传输、计算机服务和软件业</option>
				<option value="批发和零售业">批发和零售业</option>
				<option value="住宿、餐饮业">住宿、餐饮业</option>
				<option value="金融、保险业">金融、保险业</option>
				<option value="房地产业">房地产业</option>
				<option value="文化、体育、娱乐业">文化、体育、娱乐业</option>
				<option value="综合（含投资类、主业不明显)">综合（含投资类、主业不明显)</option>
				<option value="其它">其它</option>
			</select>
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
      		<input type="text"  class="form-control required" minlength="2"   maxlength="18"  name="jobs">
    	</div>
  	</div>
  	<#if isCheck!="2"> 	
	  	<div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-success">提交</button>
		    </div>
	  	</div>
	</#if>
	</form>
    </div>
    
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/swfobject.js'></script>  
<script type="text/javascript" src='${context}/asset/public/plugins/uploadify/jquery.uploadify.min.js'></script> 
<script type="text/javascript" src='${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'></script> 
<script type="text/javascript"  src="${context}/asset/public/plugins/cxselect/js/jquery.cxselect.min.js"></script>

<script>
	<#if profile??>
		$("input[name='realname']").val('${profile.realname}');

		$("input[name='cardNo']").val('${profile.cardNo}');

		$("input[name='sex'][value='${profile.sex}']").prop('checked',true);

		$("input[name='birthday']").val('${profile.birthday}');

		$("input[name='income']").val('${profile.income}');

		$("input[name='marry'][value='${profile.marry}']").prop('checked',true);

		$("select[name='education']").val('${profile.education}');
		$("select[name='province']").attr('data-value','${profile.province}');
		$("select[name='city']").attr('data-value','${profile.city}');

		$("input[name='socialSecurity'][value='${profile.socialSecurity}']").prop('checked',true);

		$("select[name='industry']").val('${profile.industry}');

		$("input[name='hasHourse'][value='${profile.hasHourse}']").prop('checked',true);

		$("input[name='hasCar'][value='${profile.hasCar}']").prop('checked',true);

		$("input[name='jobs']").val('${profile.jobs!}');
	</#if>
	<#if isCheck=="2">
		$('input,select').prop('disabled',true);
	</#if>
</script>
<script>
$(function(){
	FormValidation.validation('#form',function(resp){
		if(resp.code==0){
			spark.alert("提交成功","success");
		  location.reload();
		}else{
		  spark.alert("提交失败","error");
		}
	});
	$('.myaccount').addClass('active').parents().show();
	$('.submeun-6 a').addClass('active').parents().show();
});
$.fn.datetimepicker.dates['zh'] = {  
        days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],  
        daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],  
        daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],  
        months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],  
        monthsShort:  ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],  
        meridiem:    ["上午", "下午"],  
        //suffix:      ["st", "nd", "rd", "th"],  
        today:       "今天"  
}; 
$(".form_datetime").datetimepicker({ minView: "month",
    language:  'zh',
    format: 'yyyy-mm-dd',
    todayBtn:  1,
    autoclose: 1
});
$('#element_id').cxSelect({ 
	url: '${context}/asset/public/plugins/cxselect/js/city.min.js' ,
	selects: ['province', 'city'], 
	nodata: 'none' 
}); 

</script>

</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />