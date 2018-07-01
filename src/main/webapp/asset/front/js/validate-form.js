jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	var mobile =/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");

//登录验证
var loginValidation = function(redirect) {
	var form2 = $('#loginForm');
	var error2 = $('.alert-danger', form2);
	var success2 = $('.alert-success', form2);

	form2.validate({
	   errorElement: 'span', //default input error message container
       errorClass: 'help-block help-block-error', // default input error message class
       focusInvalid: true, // do not focus the last invalid input
       ignore: "",  // validate all fields including form hidden input
		rules : {
			username : {
				minlength : 2,
				required : true
			},
			password : {
				minlength : 6,
				required : true
			},
			checkcode : {
				required : true
			}
		},

		invalidHandler : function(event, validator) { //display error alert on form submit              
			success2.hide();
			error2.show();
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

		unhighlight : function(element) { // revert the change done by hightlight

		},

		success : function(label, element) {
			var icon = $(element).parent('.input-icon').children('i');
			$(element).closest('.form-group').removeClass('has-error')
					.addClass('has-success'); // set success class to the control group
			icon.removeClass("fa-warning").addClass("fa-check");
		},

		submitHandler : function(form) {
			success2.show();
			error2.hide();
			$.ajax({
				url:'login.do',
				type:'post',
				data:form2.serialize(),
				success:function(data){
					if(data.code == 0){
						window.location.href= redirect;	
					}else{
						alert(data.message);
					}
				}
			});
		}
	});
}

//注册验证
var registerValidate = function(){
	var register = $('#registerForm');
	var error = $('.alert-danger', register);
	var success = $('.alert-success', register);

	register.validate({
		errorElement : 'span', //default input error message container
		errorClass : 'help-block help-block-error', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "", // validate all fields including form hidden input
		rules : {
			username : {
				minlength : 2,
				maxlength : 12,
				required : true,
				remote:{
					url:'checkname.do',
					type:'post',
					data:{
						'username' : function(){
							return $.trim($('#name').val());
						}
					}
				}
			},
			password : {
				minlength : 6,
				maxlength : 12,
				required : true
			},
			passrepeat : {
				equalTo: "#password",
				required : true
			},
			checkcode : {
				required : true
			},
			phone:{
				mobile:true,
				required : true
			},
			phonecode:{
				required:true
			},
			refferee:{
				required : false,
				remote:{
					url:'checkname.do',
					type:'post',
					data:{
						'refferee' : function(){
							return $.trim($('#refferee').val());
						}
					}
				}
			}
		},
		messages:{
			username:{
				remote:"该用户名已存在!"
			},
			refferee:{
				remote:"该推荐人不存在!"
			}
		},
		invalidHandler : function(event, validator) { //display error alert on form submit              
			success.hide();
			error.show();
		},

		errorPlacement : function(error, element) { // render error placement for each input type
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

		unhighlight : function(element) { // revert the change done by hightlight

		},

		success : function(label, element) {
			var icon = $(element).parent('.input-icon').children('i');
			$(element).closest('.form-group').removeClass('has-error')
					.addClass('has-success'); // set success class to the control group
			icon.removeClass("fa-warning").addClass("fa-check");
		},

		submitHandler : function(form) {
			success.show();
			error.hide();
			$.ajax({
				url:'register.do',
				type:'post',
				data:register.serialize(),
				success:function(data){
					if(data.message != 'success'){
						alert(data.message);
					}else{
						alert('注册成功!');
						window.location.href = 'login.html';
					}
				}
			});
		}
	});
};
// 用户注册发送手机验证码
$('#getphonecode').click(function() {
	if (!$('#mobile').val()) return;
	$.ajax({
		url : 'sendCheckCode.do',
		type : 'post',
		data : {
			phone : $('#mobile').val()
		},
		success : function(data) {
         if(data.success != 1){
        	 alert('短信发送失败!');
         }
		}
	});
});