jQuery.validator.addMethod("idnumber", function(value, element) {
	var length = value.length;
	var idnumber =/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	return this.optional(element) || idnumber.test(value);
}, "身份证号码格式错误");

var realForm = $('#realForm'),
    errorReal = $('.alert-danger', realForm),
	successReal = $('.alert-success', realForm);

   realForm.validate({
	   errorElement: 'span', //default input error message container
       errorClass: 'help-block help-block-error', // default input error message class
       focusInvalid: true, // do not focus the last invalid input
       ignore: "",  // validate all fields including form hidden input
	   rules : {
			username : {
				minlength : 2,
				required : true
			},
			idnumber : {
                idnumber : true,
				minlength : 15,
				required : true
			}
		},

		invalidHandler : function(event, validator) { //display error alert on form submit              
			successReal.hide();
			errorReal.show();
		},
		errorPlacement : function(error, element) { 
			var icon = $(element).parent('.input-icon').children('i');
			icon.removeClass('fa-check').addClass("fa-warning");
			icon.attr("data-original-title", error.text()).tooltip({
				'container' : 'body'
			});

			//
			if (element.parent(".input-group").size() > 0) {
				error.insertAfter(element.parent(".input-group"));
			} else if (element.attr("data-error-container")) {
				error.appendTo(element.attr("data-error-container"));
			} else if (element.parents('.radio-list').size() > 0) {
				error.appendTo(element.parents('.radio-list').attr(
						"data-error-container"));
			} else if (element.parents('.radio-inline').size() > 0) {
				error.appendTo(element.parents('.radio-inline').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-list').size() > 0) {
				error.appendTo(element.parents('.checkbox-list').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-inline').size() > 0) {
				error.appendTo(element.parents('.checkbox-inline').attr(
						"data-error-container"));
			} else {
				error.insertAfter(element); // for other inputs, just perform default behavior
			}
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.form-group').removeClass("has-success")
					.addClass('has-error'); // set error class to the control group   
		},
		success : function(label, element) {
			var icon = $(element).parent('.input-icon').children('i');
			$(element).closest('.form-group').removeClass('has-error')
					.addClass('has-success'); // set success class to the control group
			icon.removeClass("fa-warning").addClass("fa-check");
		},

		submitHandler : function(form) {
			successReal.show();
			errorReal.hide();
			 $('#btn-real').button('loading');
			$.ajax({
				url:'realnamecheck.do',
				type:'post',
				data:realForm.serialize(),
				success:function(data){
					if(data.code == 0){
						 alert('操作成功');
						 $('#username').prop('readonly',true);
						 $('#idcard').prop('readonly',true);
					}else{
						  alert(data.message);
						  $('#btn-real').button('reset');
					}
				}
			});
		}
	});
	
	/*
	 * 修改用户密码  
	 */
var updateForm = $('#updateForm'),
    errorUpdate = $('.alert-danger', updateForm),
	successUpdate = $('.alert-success', updateForm);

   updateForm.validate({
	   errorElement: 'span', //default input error message container
       errorClass: 'help-block help-block-error', // default input error message class
       focusInvalid: true, // do not focus the last invalid input
       ignore: "",  // validate all fields including form hidden input
	   rules : {
			oldpassword : {
				required : true
			},
			newpass : {
				minlength : 6,
				required : true
			},
			newpassagain : {
			    equalTo: "#newpass",
			    minlength : 6,
				required : true
			}
		},

		invalidHandler : function(event, validator) { //display error alert on form submit              
			successUpdate.hide();
			errorUpdate.show();
		},
		errorPlacement : function(error, element) { 
			var icon = $(element).parent('.input-icon').children('i');
			icon.removeClass('fa-check').addClass("fa-warning");
			icon.attr("data-original-title", error.text()).tooltip({
				'container' : 'body'
			});

			//
			if (element.parent(".input-group").size() > 0) {
				error.insertAfter(element.parent(".input-group"));
			} else if (element.attr("data-error-container")) {
				error.appendTo(element.attr("data-error-container"));
			} else if (element.parents('.radio-list').size() > 0) {
				error.appendTo(element.parents('.radio-list').attr(
						"data-error-container"));
			} else if (element.parents('.radio-inline').size() > 0) {
				error.appendTo(element.parents('.radio-inline').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-list').size() > 0) {
				error.appendTo(element.parents('.checkbox-list').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-inline').size() > 0) {
				error.appendTo(element.parents('.checkbox-inline').attr(
						"data-error-container"));
			} else {
				error.insertAfter(element); // for other inputs, just perform default behavior
			}
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.form-group').removeClass("has-success")
					.addClass('has-error'); // set error class to the control group   
		},
		success : function(label, element) {
			var icon = $(element).parent('.input-icon').children('i');
			$(element).closest('.form-group').removeClass('has-error')
					.addClass('has-success'); // set success class to the control group
			icon.removeClass("fa-warning").addClass("fa-check");
		},

		submitHandler : function(form) {
			successUpdate.show();
			errorUpdate.hide();
			 $('#btn-upate').button('loading');
			$.ajax({
				url:'updatepassword.do',
				type:'post',
				data:updateForm.serialize(),
				success:function(data){
					if(data.code == 0){
						 alert('操作成功');
						 $('#username').prop('readonly',true).prop('disable',true);
						 $('#idcard').prop('readonly',true).prop('disable',true);
						 location.href = "login.html";
					}else{
						  alert(data.message);
						  $('#btn-update').button('reset');
					}
				}
			});
		}
	});
	
/*
 * 修改用户交易密码  
 */
var updatetranForm = $('#updatetranForm'),
    errortranUpdate = $('.alert-danger', updatetranForm),
	successtranUpdate = $('.alert-success', updatetranForm);

   updatetranForm.validate({
	   errorElement: 'span', 
       errorClass: 'help-block help-block-error', 
       focusInvalid: true, 
       ignore: "", 
	   rules : {
			oldtranpassword : {
				required : true
			},
			newtranpass : {
				minlength : 6,
				required : true
			},
			newtranpassagain : {
			    equalTo: "#newtranpass",
			    minlength : 6,
				required : true
			}
		},

		invalidHandler : function(event, validator) { //display error alert on form submit              
			successtranUpdate.hide();
			errortranUpdate.show();
		},
		errorPlacement : function(error, element) { 
			var icon = $(element).parent('.input-icon').children('i');
			icon.removeClass('fa-check').addClass("fa-warning");
			icon.attr("data-original-title", error.text()).tooltip({
				'container' : 'body'
			});

			//
			if (element.parent(".input-group").size() > 0) {
				error.insertAfter(element.parent(".input-group"));
			} else if (element.attr("data-error-container")) {
				error.appendTo(element.attr("data-error-container"));
			} else if (element.parents('.radio-list').size() > 0) {
				error.appendTo(element.parents('.radio-list').attr(
						"data-error-container"));
			} else if (element.parents('.radio-inline').size() > 0) {
				error.appendTo(element.parents('.radio-inline').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-list').size() > 0) {
				error.appendTo(element.parents('.checkbox-list').attr(
						"data-error-container"));
			} else if (element.parents('.checkbox-inline').size() > 0) {
				error.appendTo(element.parents('.checkbox-inline').attr(
						"data-error-container"));
			} else {
				error.insertAfter(element); // for other inputs, just perform default behavior
			}
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.form-group').removeClass("has-success")
					.addClass('has-error'); // set error class to the control group   
		},
		success : function(label, element) {
			var icon = $(element).parent('.input-icon').children('i');
			$(element).closest('.form-group').removeClass('has-error')
					.addClass('has-success'); // set success class to the control group
			icon.removeClass("fa-warning").addClass("fa-check");
		},

		submitHandler : function(form) {
			successtranUpdate.show();
			errortranUpdate.hide();
			 $('#btn-tran-update').button('loading');
			$.ajax({
				url:'updatetranspassword.do',
				type:'post',
				data:updatetranForm.serialize(),
				success:function(data){
					if(data.code == 0){
						 alert('操作成功');
						 $('#username').prop('readonly',true).prop('disable',true);
						 $('#idcard').prop('readonly',true).prop('disable',true);
					}else{
						  alert(data.message);
						  $('#btn-tran-update').button('reset');
					}
				}
			});
		}
	});
//银行卡设置
;(function($){$.fn.bankInput=function(options){var defaults={min:10,max:25,deimiter:" ",onlyNumber:true,copy:true};var opts=$.extend({},defaults,options);var obj=$(this);obj.css({imeMode:"Disabled",borderWidth:"1px",color:"#000",fontFamly:"Times New Roman"}).attr("maxlength",opts.max);if(obj.val()!=""){obj.val(obj.val().replace(/\s/g,"").replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter))}obj.bind("keyup",function(event){if(opts.onlyNumber){if(!(event.keyCode>=48&&event.keyCode<=57)){this.value=this.value.replace(/\D/g,"")}}this.value=this.value.replace(/\s/g,"").replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter)}).bind("dragenter",function(){return false}).bind("onpaste",function(){return !clipboardData.getData("text").match(/\D/)}).bind("blur",function(){this.value=this.value.replace(/\s/g,"").replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter);if(this.value.length<opts.min){alertMsg.warn("最少输入"+opts.min+"位账号信息！");obj.focus()}})};$.fn.bankList=function(options){var defaults={deimiter:" "};var opts=$.extend({},defaults,options);return this.each(function(){$(this).text($(this).text().replace(/\s/g,"").replace(/(\d{4})(?=\d)/g,"$1"+opts.deimiter))})}})(jQuery);
$('#bankCard1').bankInput();
_init_area();
$('#s_county').change(function(){
	$('#show').val($('#s_province').val()+$('#s_city').val()+$('#s_county').val());
});
$('input[name="bank"]').click(function(){
		if($('#chose').prop('checked')){
			$('#other').removeAttr('readonly');
		}else{
			$('#other').attr('readonly','readonly').val('');
		}
	});
	$('#other').blur(function(){
		$('#chose').val($(this).val());
	});
	
//获取绑定银行卡列表
function getBanklist(){
  $.ajax({
     url:'banklist.do',
     type:'post',
     success:function(data){
       var htmlTmp = ""; 
       if(data instanceof Array){
          for(var i = 0;i < data.length; i++){
            var checkstatus = data[i].cardStatus == 1?  '通过' : '未通过';
            var opera = checkstatus == 1? '---' : '<a  class="delete" href="javascript:" data-href="bank/delete/'+data[i].id+'.do">撤销</a>';
            var remark = data[i].remark? data[i].remark : "";
             htmlTmp += "<tr><td>"+(1+i)+"</td><td>"+data[i].cardUserName+"</td><td>"+data[i].bankName+"</td><td>"+data[i].branchBankName+"</td><td>"+data[i].cardNo+"</td><td>"+data[i].commitTime+"</td><td>"+checkstatus+"</td><td>"+remark+"</td><td>"+opera+"</td></tr>";
          }
       }
       $('#bankbody').html(htmlTmp);
     }
  });
}

$('#banksetting').on('click',getBanklist);

//添加银行卡
var bankform = {};
$('#addbank').click(function(){
   if($('#chose').is(':checked')){
      bankform["bankname"] = $('#other').val();
   }else{
 	  bankform["bankname"] = $('input[name="bank"]:checked').val();
   }
   bankform["bankaddress"] = $('#show').val()+$('#subBankName1').val();
   bankform["banknum"] = $('#bankCard1').val();
   $.ajax({
      url:'addbank.do',
      data:bankform,
      type:'post',
      success :function(data){
        if(data.code == 0){
           alert('操作成功');
           $('#bankCard1').val('');
           $('#banksetting').trigger('click');
        }else{
           alert('操作失败');
        }
      }
   });
});

//撤销银行卡
$('#bankbody').on('click','.delete',function(e){
    e.preventDefault();
    $.ajax({
      url:$(this).data('href'),
      type:'post',
      success:function(data){
        if(data.code == 0){
           alert('操作成功');
            $('#banksetting').trigger('click');
        }else{
           alert('操作失败');
        }
      }
    });
});