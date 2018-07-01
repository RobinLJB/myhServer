/**前端通用处理 **/
var spark = function(){
	var basePath  = '';
	return {
    	init:function(context){
    		basePath = context;
    		$.ajaxSetup({
                beforeSend:function(xhr){
                    spark.blockUI();
                },
                complete:function(xhr,ts){
                    spark.unblockUI();
                }
            });
    	},
		/*
		type：success成功，warning警告，error错误
		message：提示内容
		spark.alert(message,type);
		*/
		alert:function(message,type) {
			if(type==""){
				swal({
					title:message,
					confirmButtonText:"确定" 
				});	
			}else{
				swal({
					title:message,
					type: type,
					confirmButtonText:"确定" 
				 
				});	
			} 
		},
		/**
		type：success成功，warning警告，error错误
		message：提示内容
		fun:回调函数
		*/
		confirm:function(title,message,fun) {
				swal({
						title: message,
						text:message,
						showCancelButton: true,
						confirmButtonText:"确定",
						cancelButtonText: "取消",
						closeOnConfirm: false ,
						showLoaderOnConfirm: true,
						html: true
					}, 
					function(){
						fun(); 
					});
		},
    	blockUI:function(options) {
		    options = $.extend(true, {}, options);
		    var html = '';
		    if (options.message) {
		        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><img src="' +basePath+ '/asset/admin/img/loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;' + (options.message ? options.message : '正在加载...') + '</span></div>';
		    } else if (options.textOnly) {
		        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><span>&nbsp;&nbsp;' + (options.message ? options.message : '正在加载...') + '</span></div>';
		    } else {
		        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><img src="' + basePath + '/asset/admin/img/loading-spinner-grey.gif" align=""></div>';
		    }

		    if (options.target) { // element blocking
		        var el = $(options.target);
		        if (el.height() <= ($(window).height())) {
		            options.cenrerY = true;
		        }
		        el.block({
		            message: html,
		            baseZ: options.zIndex ? options.zIndex : 1000,
		            centerY: options.cenrerY !== undefined ? options.cenrerY : false,
		            css: {
		                top: '10%',
		                border: '0',
		                padding: '0',
		                backgroundColor: 'none'
		            },
		            overlayCSS: {
		                backgroundColor: options.overlayColor ? options.overlayColor : '#555',
		                opacity: options.boxed ? 0.05 : 0.1,
		                cursor: 'wait'
		            }
		        });
		    } else { // page blocking
		        $.blockUI({
		            message: html,
		            baseZ: options.zIndex ? options.zIndex : 1000,
		            css: {
		                border: '0',
		                padding: '0',
		                backgroundColor: 'none'
		            },
		            overlayCSS: {
		                backgroundColor: options.overlayColor ? options.overlayColor : '#555',
		                opacity: options.boxed ? 0.05 : 0.1,
		                cursor: 'wait'
		            }
		        });
		    }
		},
		unblockUI:function(target) {
		    if (target) {
		        $(target).unblock({
		            onUnblock: function() {
		                $(target).css('position', '');
		                $(target).css('zoom', '');
		            }
		        });
		    } else {
		        $.unblockUI();
		    }
		},
		handleToggle:function(dom){
				console.log('handleToggle');
				dom = dom || document;
				$("[data-toggle]",dom).each(function(){
				var toggle = $(this).attr('data-toggle');
				if(toggle == 'datetime-picker'){ //日期选择
					console.log(this);
					var minView = $(this).attr('data-minview')||2;
					var format = $(this).attr('data-format')||"yyyy-mm-dd";
					$(this).datetimepicker({
						format: format,//日期格式
						language:'zh-CN',//语言
						minView:minView,
						autoclose:true
					});
				}
				else if(toggle == 'validate' && this.tagName == 'FORM'){
					var position = $(this).attr('data-postion') || "centerRight";
					$(this).validationEngine("attach",{ 
						promptPosition:position,
						scroll:true,
						showOneMessage:true
					});
				}
				else if(toggle=='ajax-link'){
					$(this).on('click',function(){
						var $this = $(this);
						var tip = $(this).attr('data-tip') || '确定要继续吗？';
						if(tip){
							spark.confirm(tip,function(){
								$.getJSON($this.attr('data-href'),function(res){
									if(res.code == 0){
										spark.alert(res.message);
										var reload = $this.attr('data-reload') || true;
										if( reload == true){
											setTimeout(function(){
												location.reload();
											},2000);
										}
									}
									else spark.alert(res.message,'error');
								});
							});
						}
						else{
							$.getJSON($this.attr('data-href'),function(res){
								if(res.errcode == 0){
									spark.alert(res.errmsg);
									if($this.attr('data-reload') == 'true'){
										setTimeout(function(){
											location.reload();
										},2000);
									}
								}
								else spark.alert(res.errmsg,'error');
							});
						}
						return false;
					})
				}
				else if(toggle=='remote-dialog'){
					$(this).click(function(){
						BootDialog.showFrame($(this).attr('data-title'),$(this).attr('data-href'),400);
					});
				}
			});
		},		
		dataTable:function(el,url,option){
			if($.fn.dataTable){
				$.extend($.fn.dataTable.defaults, {
					ordering:false,
					searching:true,
					language: {
						"sProcessing": "处理中...",
						"sLengthMenu": "每页 _MENU_ 项记录",
						"sZeroRecords": "没有记录",
						"sInfo": "第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
						"sInfoEmpty": "显示第 0 至 0 项记录，共 0 项",
						"sInfoFiltered": "(由 _MAX_ 项记录过滤)",
						"sInfoPostFix": "",
						"sSearch": "搜索:",
						"sUrl": "",
						"sEmptyTable": "表中数据为空",
						"sLoadingRecords": "载入中...",
						"sInfoThousands": ",",
						"oPaginate": {
							"sFirst": "首页",
							"sPrevious": "上页",
							"sNext": "下页",
							"sLast": "末页"
						},
						"oAria": {
							"sSortAscending": ": 以升序排列此列",
							"sSortDescending": ": 以降序排列此列"
						}
					}
				});
			}
			$.extend(option, {
				ajax:function (data, callback, settings) {
					if(option.before && typeof option.before == 'function'){
						data = option.before(data);
					}
					spark.blockUI()
					$.ajax({
						url : url,
						data : data,
						type : 'post',
						dataType : 'json',
						success : function(result) {
							spark.blockUI();
							callback(result.dataTable);
						},
						error : function(msg) {
							spark.unblockUI();
							spark.alert('加载数据失败','error');
						}
					});
				},
				"serverSide":true
			});
			return $(el).DataTable(option);
		},
		validation:function(el) {
            var form = $(el);
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);
            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success.hide();
                    error.show();
                },

                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
					$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                },
                submitHandler: function (form) {
                    success.show();
                    error.hide();
                }
            });
		},
		validationWithIcon:function(el) {
        	// for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation
            var form = $(el);
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);

            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success.hide();
                    error.show();
                },

                errorPlacement: function (error, element) { // render error placement for each input type
                    var icon = $(element).parent('.input-icon').children('i');
                    icon.removeClass('fa-check').addClass("fa-warning");  
                    icon.attr("data-original-title", error.text()).tooltip({'container': 'body'});
                },

                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    
                },

                success: function (label, element) {
                    var icon = $(element).parent('.input-icon').children('i');
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                    icon.removeClass("fa-warning").addClass("fa-check");
                },

                submitHandler: function (form) {
                    success.show();
                    error.hide();
                    form[0].submit(); // submit the form
                }
            });
		}
    }
}();