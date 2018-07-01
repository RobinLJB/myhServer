<@override name="head">
<link href="${context}/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${context}/asset/ucenter/css/jbox.css" rel="stylesheet" />
<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
<style>

.form-horizontal{
	margin:50px auto;
	width:1000px;
}
.form-horizontal .control-label{font-weight: 400}
.form-title{
	margin:10px auto;
	text-align: center;
}
.form-group .form-control {
    margin-top: 0;
}

		.con{width: 1000px;margin: 0 auto;text-align: center;}
		.hengtiao{height: 5px;width: 150px;background: #1ab5eb;margin: 0 auto;}
		.tit{padding-top: 50px;
    font-size: 25px;
    padding-bottom: 27px;
    font-family: "微软雅黑";}
    .tj{
    	position: absolute;

    bottom: -31px;
    font-size: 20px;
    }
    
    .t{
    	  width: 280px;
        padding-left: 32px;
    	    
    height: 42px;
    border: 1px solid #dadada;
    border-radius: 8px;
    margin-left: 50px;
    position: relative;
    }
    .bfh{
       position: absolute;
    top: 10px;
    right: 279px;
    bottom: 0px;
    color: #dadada;
    line-height: 48px;
    width: 45px;
    height: 45px;

    /* background: #ffffff; */
    border-left: 1px solid #dedede;
    border-top-left-radius: 1px;
    border-bottom-left-radius: 3px;
    }
    #from .tjan{
    	background: #1ab5eb;
    margin: 0 auto;
    width: 336px;
    font-size: 20px;
    /* height: 55px; */
    border-radius: 10px;
    color: white;
    margin-top: 32px;
    }
 .form-horizontal{
    padding-top: 50px;
    text-align: center;
}
.form-title{
	margin:10px auto;
	text-align: center;
}

@media (min-width: 768px)
bootstrap.min.css:5
.col-sm-offset-3 {
   margin-left: 0px;
}

.inputs{
    width: 68%;
    float: left;
}
.ss{
	    width: 100%;
    height: 78px;
    clear: both;
    font-size: 15px;
}
.rtyu {
    margin-top: 83px;
    margin-bottom: 83px;
    border: 1px solid #eccec;
    height: 950px;
    width: 100%;

    font-size: 18px;
    color: black;
    border: solid 1px #d7dbdc;
}
.footer1 {
    clear: both;
    color: #3b2213;
    background-color: white;
}
.edui-default .edui-editor {
    border: 1px solid #d4d4d4;
    background-color: white;
    width: 900px;
    position: relative;
    overflow: visible;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
}
.demo{padding-bottom: 20px;width: 540px;}
#InputFile{    position: absolute;
    opacity: 0;
    left: 14px;
    top: 0;
    height: 34px;
    width: 84px;
    margin: 0;
    padding: 0;
    cursor: pointer;}
.form-group {
    margin-bottom: 25px;
}

</style>
</@override>
<@override name="body">

<div><img style="width: 100%;"  src="${context}/asset/front/images/wygd1.png"/></div>
<div class="con">
	<div class="tit">申请更简单</div>
	<div style="text-align: center;">
		<div class="hengtiao"></div>
		</div>
	 <div style="position: relative;">
		<img style="width: 100%;padding-top:86px;"  src="${context}/asset/front/images/liucheng.png"/>
		<div class="tj">
			<span style="padding-left: 65px;">提交申请</span>
			<span style="padding-left: 311px;">等待审核</span>
			<span style="padding-left: 321px;">融资成功</span></div>
	 </div>
</div>
	<div class="container" style="background: white;margin-top: 20px;">
	<div id="errorTip" style="color: red;font-weight: bold;font-size: 20px;"></div>
	<form id="form" action="${context}/ucenter/addLoan.do" class="form-horizontal" method="POST" style="margin-top:25px;">
		<input type="hidden" id="mortgage" name="paramMap.mortgage" />
		<input type="hidden" id="codemap" name="paramMap.codemap" />
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">借款标题：</label>
				<div class="col-md-8">
					<input type="text" name="title" maxlength="40" id="title" class="form-control required" placeholder="请输入借款标题,40字以内"/>
				</div>
			</div>
			<div class="form-group  col-md-6">
				<label class="col-sm-4 control-label">借款类型：</label>
				<div class="col-md-8">
					<select name="productType" id="productType" class="form-control">
					    <option value="2">民生标</option>
					    <option value="10">供链标</option>
					</select>
				</div>
			</div>	
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">借款总额(元)：</label>
				<div class="col-md-8">
					<input type="text" name="amount" onkeyup="clearNoNum(this)" id="amount" class="form-control required number"/>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">封面图片上传：</label>
				<div class="col-sm-8">
					<div class="demo">
						<div id="cover"></div>
					</div>
					<p style="color: gray;text-align: left;padding: 5px 10px;">(该图片用于标的封面显示，图片格式限于png、jpg、gif、jpeg格式，上传图片不得超过5M)</p>
				</div>
				<input type="hidden" id="imgPath" name="imgPath"  class="required"/>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">年利率%：</label>
				<div class="col-md-8">
						<input type="text" name="annualRate" onkeyup="clearNoNum(this)" id="annualRate" class="form-control required number"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">借款期限：</label>
				<div class="col-md-8">
					<select name="deadLine" id="deadLine" class="form-control">
					    <@select group="LOAN_DEADLINE_M"   />
					</select>

					<select name="deadDay" id="deadDay" class="form-control" style="display: none">
					     <@select group="LOAN_DEADLINE_D"   />
					</select>
					<div class="checkbox" style="text-align: left;">
						<label>
							<input id="isDayThe" type="checkbox" value="1" name="daythe" /> 天标
						</label>
					</div>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">还款方式：</label>
				<div class="col-md-8">
					<select name="paymentMode" id="paymentMode" class="form-control required">
						<@select group="CATE_PAYMENT"   />
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">最低投标金额(元)：</label>
				<div class="col-md-8">
					<select name="minTenderedSum" id="minTenderedSum" class="form-control">
					    <option value="0">0元</option>
					    <@select group="LOAN_minTenderedSum"   />
					</select>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">最多投标金额(元)：</label>
				<div class="col-md-8">
					<select name="maxTenderedSum" id="maxTenderedSum" class="form-control">
					    <option>没有限制</option>
					    <@select group="LOAN_maxTenderedSum"   />
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">信息披露图片：</label>
				<div class="col-sm-8">
					<div class="demo">
						<div id="material" ></div>
					</div>
					<p style="color: gray;text-align: left;padding: 5px 10px;">(该图片用于标的信息披露显示，图片格式限于png、jpg、gif、jpeg格式，上传图片不得超过5M)</p>
					<a href="${context}/article/59.html" target="_blank" style="display: block;text-align: left;color:red">《借款人提交资料清单》</a>
				</div>
				<input type="hidden" id="Uploadphotos" name="Uploadphotos"  class="required"/>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label">筹标期限：</label>
				<div class="col-md-8">
					<input type="hidden" name="validateDay" id="validateDay"/>
					<select name="raiseTerm" id="raiseTerm" class="form-control">
					    <option>--请选择--</option>
						<@select group="LOAN_raiseTerm"   />
					</select>
				</div>
			</div>
		</div>
		 <div class="row">
           <div class="form-group col-md-12">
			    <label class="col-sm-2 control-label">借款详情：</label>
				<div class="col-md-10" style="padding-right: 45px;">
					<textarea name="detail" style="height:420px;width:100%;" id="detail" class="required"></textarea>
				</div>	
			</div>
        </div>
		<div class="row">
			<div  style="clear:both;width:115px;margin-top: 20px;margin-bottom:70px;    text-align: center;margin: auto;">
				<button type="submit" id="submit" class="btn btn-primary btn-lg pull-right">提交发布</button>
			</div>
		</div>
 </form>
</div>
	

<div style="background: #f7f7f7;height: 50px;width: 100%;clear: both;"></div>

 

</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"  src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/swfobject.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/jquery.uploadify.min.js"></script>
<!--以下多图上传js-->
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
<script type="text/javascript"  src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
<script type="text/javascript">
 
function clearNoNum(obj){  
  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数   
  if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
   obj.value= parseFloat(obj.value);  
  }  
}

FormValidation.validation('#form',function(resp){
		if(resp.code==0){
			spark.alert(resp.message,"success");
		    location.href = "${context}/ucenter/myloan/applying.html";
		}else{
		  spark.alert(resp.message,"error");
		}
	});


 
//标的封面
$('#cover').diyUpload({
	url:'${context}/upload/image.do?type=1',
	success:function( data ) {
		var imgPath = $("#imgPath").val();
		if (imgPath == "") {
			$("#imgPath").val(data.absPath);
		}
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
		var Uploadphotos = $("#Uploadphotos").val();
		if (Uploadphotos == "") {
			$("#Uploadphotos").val(data.absPath);
		}else{
			Uploadphotos+=","+data.absPath;
			$("#Uploadphotos").val(Uploadphotos);
		}
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
var ue = UE.getEditor('detail',{
		serverUrl:"${context}/ueditor/dispatch.do",
		imageUrlPrefix:"${context}"
	});
$("#isDayThe").on('click',function(){
	if($("#isDayThe").is(':checked')){
		$("#isDayThe").val(2);
	}else{
		$("#isDayThe").val(1);
	}
	$("#deadLine,#deadDay").toggle();
})
$("#submit").on('click',function(){
	if ( $(".diyStart").length > 0 ) { 
		spark.alert("请先点击上传图片"); 
	} 
})
$(function(){
	$(".ground-item2>a").addClass("active");
});
</script>
 
</@override>

<@layout name="/public/front-base.ftl" />