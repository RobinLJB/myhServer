<@override name="head">
	<link href="${context}/asset/ucenter/css/Uploadphotos.css" rel="stylesheet" />
	<style>
		.form-group .col-sm-10 {
			width: 40%;
		}
		
		.form-group input {
			border-radius: 5px;
		}
		
		.form-horizontal {
			padding: 35px;
		}
		
		.form-group select {
			border-radius: 5px;
		}
		
		.form-group .form-control {
			margin-top: 0px;
		}
		
		.form-group .radio-inline {
			padding-left: 35px;
		}
		
		.form-group .control-label {
			width: 147px!important;
			margin: 0;
			padding: 0;
			color: #666;
			font-size: 14px;
		}
		
		.form-group {
			float: left;
		}
		
		.mj {
			font-size: 25px;
			margin-left: 15px;
			margin-top: 15px;
			margin-bottom: 15px;
			border-bottom: 1px solid #ECECEC;
			padding-bottom: 15px;
		}
		
		.table-bordered {
			border: 1px solid #ddd;
			margin-bottom: 65px;
		}
		
		.form-group .control-label {
			width: 106px!important;
			margin: 0;
			padding: 0;
			color: #666;
			font-size: 14px;
		}
		
		.form-horizontal {
			width: 100%;
			margin: 0 auto;
		}
	</style>
	<link href="${context}/asset/ucenter/css/jbox.css" rel="stylesheet" />
</@override>
<@override name="body">
	<div class="r_main">
		<div class="ci-title">
            <div class="ci-title-inner">
                <h2>募捐申请</h2>
                <b class="line"></b>
            </div>
        </div>
		<form id="form" action="addWelfare.do" class="form-horizontal" method="post" style="margin-top:25px;">
			<div style="padding-left: 60px;">
				<div class="row">
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label">募捐标题：</label>
						<div class="col-md-8">
							<input type="text" id="title" name="title" class="form-control required" />
							<span class="help-block text-danger"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label">募捐金额：</label>
						<div class="col-md-8">
							<input type="text" name="amount" onkeyup="clearNoNum(this)" id="amount" class="form-control required" />
							<span class="help-block text-danger"></span>
						</div>
					</div>
					<div class="form-group  col-md-6">
						<label class="col-sm-4 control-label">募捐周期：</label>
						<div class="col-md-8">
							<select name="raise_term" id="raise_term" class="form-control required">
							<option>--请选择--</option>
							<@select group="LOAN_raiseTerm"   />
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label">封面图片上传：</label>
						<div class="col-sm-8">
							<div class="demo">
								<div id="cover"></div>
							</div>
							<p style="color: gray;text-align: left;padding: 5px 10px;">(该图片用于标的封面显示，图片格式限于png、jpg、gif、jpeg格式，上传图片不得超过3M)</p>
						</div>
						<input type="hidden" id="imgPath" name="cover" class="required" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label">公益事件图片：</label>
						<div class="col-sm-8">
							<div class="demo">
								<div id="material"></div>
							</div>
						</div>
						<input type="hidden" id="Uploadphotos" name="event" class="required" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label">公益机构图片：</label>
						<div class="col-sm-8">
							<div class="demo">
								<div id="materiall"></div>
							</div>
						</div>
						<input type="hidden" id="Uploadphotoss" name="img_organization" class="required" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-12">
						<label class="col-sm-2 control-label">募集详情：</label>
						<div class="col-md-10">
							<textarea name="detail" style="height:420px;width:700px;" id="detail" class="required"></textarea>
							<span class="help-block text-danger"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-12">
						<label class="col-sm-2 control-label">公益机构简介：</label>
						<div class="col-md-10">
							<textarea name="organization" style="height:420px;width:700px;" id="organization" class="required"></textarea>
							<span class="help-block text-danger"></span>
						</div>
					</div>
				</div>

				
				<div class="row" style="text-align: center;
    margin: 0 auto;
    width: 715px;">
					<button type="submit" id="bcbtn"    class="btn btn-primary btn-lg pull-right">立即申请</button>
				</div>
			</div>
			</from>
</@override>

<@override name="script">
	<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
	<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
	<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
	<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript" charset="utf-8" src="${context}/asset/public/plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

	<!--以下多图上传js-->
	<script type="text/javascript" src="${context}/asset/ucenter/js/Uploadphotos1.js"></script>
	<script type="text/javascript" src="${context}/asset/ucenter/js/Uploadphotos2.js"></script>
	<script type="text/javascript">
		FormValidation.validation('#form', function(resp) {
			alert(resp.message);
		});

		//标的封面
		$('#cover').diyUpload({
			url: '${context}/upload/image.do?type=1',
			success: function(data) {
				var imgPath = $("#imgPath").val();
				if(imgPath == "") {
					$("#imgPath").val(data.absPath);
				}
			},
			error: function(err) {
				alert(err);
			},
			buttonText: '选择图片',
			chunked: true,
			// 分片大小
			chunkSize: 50000 * 1024,
			//最大上传的文件数量
			fileNumLimit: 1,
			//总文件大小
			fileSizeLimit: 500000 * 1024,
			// /单个文件大小(单位字节)
			fileSingleSizeLimit: 50000 * 1024,
			accept: {}
		});

		//标的信息
		$('#material').diyUpload({
			url: '${context}/upload/image.do?type=1',
			success: function(data) {
				var Uploadphotos = $("#Uploadphotos").val();
				if(Uploadphotos == "") {
					$("#Uploadphotos").val(data.absPath);
				} else {
					Uploadphotos += "," + data.absPath;
					$("#Uploadphotos").val(Uploadphotos);
				}
			},
			error: function(err) {
				alert(err);
			},
			buttonText: '选择图片',
			chunked: true,
			// 分片大小
			chunkSize: 50000 * 1024,
			//最大上传的文件数量
			fileNumLimit: 50,
			//总文件大小
			fileSizeLimit: 500000 * 1024,
			// /单个文件大小(单位字节)
			fileSingleSizeLimit: 50000 * 1024,
			accept: {}
		});
		//标的信息
		$('#materiall').diyUpload({
			url: '${context}/upload/image.do?type=1',
			success: function(data) {
				var Uploadphotos = $("#Uploadphotoss").val();
				if(Uploadphotos == "") {
					$("#Uploadphotoss").val(data.absPath);
				} else {
					Uploadphotos += "," + data.absPath;
					$("#Uploadphotoss").val(Uploadphotos);
				}
			},
			error: function(err) {
				alert(err);
			},
			buttonText: '选择图片',
			chunked: true,
			// 分片大小
			chunkSize: 50000 * 1024,
			//最大上传的文件数量
			fileNumLimit: 50,
			//总文件大小
			fileSizeLimit: 500000 * 1024,
			// /单个文件大小(单位字节)
			fileSingleSizeLimit: 50000 * 1024,
			accept: {}
		});
	</script>
	<script type="text/javascript">
		$("#isDayThe").on('click', function() {
			$("#deadLine,#deadDay").toggle();
		})
	</script>

	<script type="text/javascript">
		var ue = UE.getEditor('detail', {
			serverUrl: "${context}/ueditor/dispatch.do",
			imageUrlPrefix: "${context}"
		});
		var ue = UE.getEditor('organization', {
			serverUrl: "${context}/ueditor/dispatch.do",
			imageUrlPrefix: "${context}"
		});
		$(document).ready(function() {
			$('.myloanmj').addClass('active').parents().show();
			$('.submeun-14 a').addClass('active').parents().show();

		})
		function clearNoNum(obj){  
		  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
		  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
		  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
		  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数   
		  if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
		   obj.value= parseFloat(obj.value);  
		  }  
		}
	</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />