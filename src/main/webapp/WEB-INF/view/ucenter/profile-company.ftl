<@override name="head">
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<style>
.form-group .col-sm-10{width:60%;}
.form-group input{border-radius: 5px;}
.form-group select{ border-radius: 5px;}
.form-group .form-control{margin-top:0px;}
.form-group .radio-inline{padding-left: 35px;}
.form-border{
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
.form-horizontal {
    margin-left: 66px;
    /* width: 828px; */
    /* margin: 0 auto; */
}
.form-group .control-label {
    width: 150px!important;
    padding-top: 7px;
    margin-bottom: 0;
    text-align: right;
        position: relative;
    min-height: 1px;
    padding-right: 15px;
    padding-left: 15px;
}
.form-group .col-sm-10 {
    width: 62%;
}
.form-horizontal {
    width: 97%;
    margin: 0 auto;
}
</style>
<style>
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
.file-holder img{
  width: 80px;
}

.{
	    float: left;
    width: 48%;
}
.df{    color: #888;
    width: 60%;
    display: inline-block;
    padding-top: 7px;
    padding-left: 13px;    float: left;}
</style>
</@override>
<@override name="body">
 <div class="r_main">
  	<form class="form-horizontal" id="form" action="${context}/ucenter/company/profile.do"  method="POST">
   
    <#if isCheck == "0">
      <div class="alert alert-warning">
        <h3>请您填写企业信息</h3>
      </div>
    <#elseif isCheck == "1">
      <div class="alert alert-info">
        <h3>资料信息正在审核中</h3>
      </div>
    <#elseif isCheck == "2">
       <div class="alert alert-success">
        <h3>资料信息审核通过</h3>
      </div>
    <#elseif isCheck == "3">
       <div class="alert alert-danger">
        <h3>资料信息审核不通过</h3>
      </div>
    </#if>
    <div style="border-bottom: 1px dashed #ECECEC;">
  	<h3 class="form-border">企业基本信息</h3>
    
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">公司名称
  		<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="company_name">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">注册资金(元)
  		<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required number" minlength="2"  maxlength="50" name="registered_fund">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">公司地址
  		<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="address">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">营业执照号
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="license_no">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">营业执照地址
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="license_address">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">营业执照过期日
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="license_expire_date" id="license_expire_date" data-date-format="yyyymmdd"  readonly placeholder="格式为20160202">
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">营业范围
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<textarea style="height: 120px" class="form-control required" name="business_scope"></textarea>
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">联系电话
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="telephone">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">联系邮箱
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required email" name="email">
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">组织机构代码
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="organization_no" style="width: 40%;float: left;">
          <span class="df">(企业三证合一可以填写营业执照号)</span>
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">企业简介
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<textarea name="summary" class="form-control required"></textarea>
    	</div>
  	</div>
  	</div>
  	<div style="border-bottom: 1px dashed #CECECE;width: 100%;">
  	<h3 class="form-border">企业法人信息</h3>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">企业法人
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="legal_person">
    	</div>
  	</div>
  	<div class="form-group ">
  		<label class="col-sm-2 control-label">法人身份证号
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required chinaCardNo"  name="cert_no">
    	</div>
  	</div>
  	<div class="form-group" style="width:395px;">
  		<label class="col-sm-2 control-label">法人手机号
  			<span class="required " aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required chinaMobile" name="legal_person_phone">
    	</div>
  	</div>
  	</div>
  	<h3 class="form-border">企业开户银行</h3>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">开户银行
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
          <select class="form-control required" name="bank_code">
              <option value="BOC">中国银行</option>
              <option value="ICBC">中国工商银行</option>
              <option value="ABC">中国农业银行</option>
              <option value="CCB">中国建设银行</option>
              <option value="COMM">交通银行</option>
              <option value="CMB">招商银行</option>
          </select>
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">开户省份
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="province">
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">开户城市
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="city">
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">开户支行名称
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="bank_branch">
    	</div>
  	</div>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">银行卡号
  			<span class="required" aria-required="true">*</span>
  		</label>
  		<div class="col-sm-10">
      		<input type="text" class="form-control required" name="bank_account_no">
    	</div>
  	</div>
  	<h3 class="form-border">企业证件</h3>
  	<div class="form-group">
  		<label class="col-sm-2 control-label">公司营业执照</label>
  		<div class="col-sm-10">
       <#if profile2??><img src="${profile2.assetyyzz!''}" style="max-width: 120px"></#if>
			   <div class="demo">
            <div id="assetyyzz" class="input-upload" data-type="yyzz"></div>
          </div>
          <input type="hidden" name="assetyyzz" id="yyzz" /> 
           <span class="df">(三证合一可以填写营业执照)</span>
    	</div>
  	</div>
    <div class="form-group">
      <label class="col-sm-2 control-label">组织机构代码证</label>
      <div class="col-sm-10">
       <#if profile2??><img src="${profile2.assetzzjgz!''}" style="max-width: 120px"></#if>
          <div class="demo">
            <div id="assetzzjgz" class="input-upload" data-type="zzjgz"></div>
          </div>
          <input type="hidden" name="assetzzjgz" id="zzjgz" /> 
           <span class="df">(三证合一可以填写营业执照)</span>
        </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">税务登记证</label>
      <div class="col-sm-10">
        
        <#if profile2??><img src="${profile2.assetswdjz!''}" style="max-width: 120px"></#if>
       <div class="demo">
            <div id="assetswdjz" class="input-upload" data-type="swdjz"></div>
        </div>
        <input type="hidden" name="assetswdjz" id="swdjz" /> 
         <span class="df">(三证合一可以填写营业执照)</span>
      </div>
    </div>

    <div class="form-group">
      <label class="col-sm-2 control-label">银行开户许可证</label>
      <div class="col-sm-10">
       <#if profile2??><img src="${profile2.assetjsxkz!''}" style="max-width: 120px"></#if>
        <input type="hidden" name="assetjsxkz" id="jsxkz" /> 
        <div class="demo">
            <div id="assetjsxkz" class="input-upload" data-type="jsxkz"></div>
        </div>
       
      </div>
    </div>

    <div class="form-group">
      <label class="col-sm-2 control-label">法人身份证正面</label>
      <div class="col-sm-10">
       <#if profile2??><img src="${profile2.assetfrzjz!''}" style="max-width: 120px"></#if>
        <input type="hidden" name="assetfrzjz" id="frzjz" /> 
        <div class="demo">
            <div id="assetfrzjz" class="input-upload" data-type="frzjz"></div>
        </div>
       
      </div>
    </div>

    <div class="form-group">
      <label class="col-sm-2 control-label">法人身份证反面</label>
      <div class="col-sm-10">
      <#if profile2??><img src="${profile2.assetfrzjf!''}" style="max-width: 120px"></#if>
        <input type="hidden" name="assetfrzjf" id="frzjf" /> 
       <div class="demo">
            <div id="assetfrzjf" class="input-upload" data-type="frzjf"></div>
        </div>
       
      </div>
    </div>
    <#if (isCheck == "0") || (isCheck == "3")>   
      	<div class="form-group">
    	    <div class="col-sm-offset-2 col-sm-10">
    	       <button type="submit" id="submit" class="btn btn-success">提交</button>
    	    </div>
      	</div>
    </#if>
	</form>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
<script type="text/javascript" src='${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'></script> 
 
<script>
$(function(){
	$.validator.addMethod("chinaMobile", function(value, element) {      
		return /^1[34578]\d{9}$/.test(value);       
	}, "非法手机号");
	$.validator.addMethod("chinaCardNo", function(value, element) {      
		return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);  		
	}, "身份证号码格式不正确");
	FormValidation.validation('#form',function(resp){
		if(resp.code==0){
			spark.alert("提交成功","success");
		  spark.reload();
		}else{
		  spark.alert("提交失败:"+resp.message,"error");
		}
	});
  $('.myaccount').addClass('active').parents().show();
  $('.submeun-6 a').addClass('active').parents().show();
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
	$('#license_expire_date').datetimepicker({language:'zh',format: 'yyyymmdd',startDate:new Date(),minView:2,autoclose:true});

});
<#if (isCheck == "0") || (isCheck == "3")>
$('.input-upload').each(function(){
	var type = $(this).attr('data-type');
	$(this).diyUpload({
	  url:"${context}/ucenter/upload/file.do?type="+type,
	  success:function( data ) {
		$('#'+type).val(data.message);
	  },error:function( err ) {
		spark.alert(err);
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
});
</#if>
</script>
<script>
 
	<#if profile2??>
		$("input[name='company_name']").val('${profile2.company_name}');
    $("input[name='registered_fund']").val('${profile2.registered_fund}');
    $("input[name='address']").val('${profile2.address!}');
    $("input[name='license_no']").val('${profile2.license_no}');
    $("input[name='license_address']").val('${profile2.license_address!''}');
    $("input[name='license_expire_date']").val('${profile2.license_expire_date!''}');
    $("textarea[name='business_scope']").val('${profile2.business_scope!''}');
    $("input[name='telephone']").val('${profile2.telephone!''}');
    $("input[name='email']").val('${profile2.email!''}');
    $("input[name='organization_no']").val('${profile2.organization_no!''}');
    $("textarea[name='summary']").val('${profile2.summary!''}');
    $("input[name='legal_person']").val('${profile2.legal_person!''}');
    $("input[name='cert_no']").val('${profile2.cert_no}');
    $("input[name='legal_person_phone']").val('${profile2.legal_person_phone}');
    $("select[name='bank_code']").val('${profile2.bank_code}');
    $("input[name='province']").val('${profile2.province}');
    $("input[name='city']").val('${profile2.city}');
    $("input[name='bank_branch']").val('${profile2.bank_branch}');
    $("input[name='bank_account_no']").val('${profile2.bank_account_no}');
	</#if>
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />