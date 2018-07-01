<#assign context="${rc.contextPath}">
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>${webname}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css" />
        <link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css" />
    </head>
    <body>
        <header class="mui-bar mui-bar-nav">
            <a class="mui-icon mui-icon-left-nav mui-pull-left" data-href="${context}/mobile/home.html"></a>
            <h1 class="mui-title">申请贷款</h1>
        </header>
        <input type="hidden" value="{borrowStatus!0}">
        <div class="mui-content">
            <div class="mui-content-padded applyloan">
            	<!--身份信息已确认-->
                <div class="apply">
                    <div class="applyleft mui-text-center">
                        <a href="${context}/mobile/borrow/identity.html"></a>
                            <img class="sh_img"  src="${context}/asset/mobile/img/applyimg1-2.png" width="100%">
                        <img src="${context}/asset/mobile/img/xuxians.png">
                    </div>
                    <div class="applyright">
                        <h4 class="active">身份信息已确认</h4>
                    </div>
                </div>
                <!--初审中-->
                <div class="apply" id="check_item1">
                    <input type="hidden" id="borrowId" value="${borrowId!0}">
                    <div class="applyleft mui-text-center">
                        <img id="iImg0" class="check_img" src="${context}/asset/mobile/img/applyimg1-3.png" width="100%">
                        <!--<img class="sh_img" class="" src="${context}/asset/mobile/img/applyimg1-2.png" width="100%">-->
                        <img src="${context}/asset/mobile/img/xuxians.png">
                    </div>
                    <div class="applyright">
                        <h4 id="text0_1" class="active ">初审中</h4>
                        <p id="text0_2" class="">预计30分钟内告诉您结果</p>
                    </div>
                </div>
                <!--银行卡-->
                <div class="apply" id="check_item2">
                    <div class="applyleft mui-text-center">
                        <img id="iImg2" class="check_img" src="${context}/asset/mobile/img/applyimg1-1.png" width="100%">
                        <img src="${context}/asset/mobile/img/xuxians.png">
                    </div>
                    <div class="applyright">
                            <h4 id="text1_1">确认银行卡</h4>
                            <p id="text1_2">请确认收款银行卡</p>
							                            
                        </span>
                    </div>
                </div>
                <!--复审中-->
                <div class="apply" id="check_item3">
                    <div class="applyleft mui-text-center">
                        <a href="${context}/mobile/borrow/identity.html"></a>
                        <img id="iImg2" class="check_img" src="${context}/asset/mobile/img/applyimg1-1.png" width="100%">
                        <img src="${context}/asset/mobile/img/xuxians.png">
                    </div>
                    <div class="applyright">
                       <h4 id="text2_1">等待复审</h4>
                       <p id="text2_2">请您耐心等待</p>
                    </div>
                </div>
                <!--放款成功-->
                <div class="apply" style="height: inherit;" id="check_item4">
                    <div class="applyleft mui-text-center">
                        <img id="iImg3" class="check_img" src="${context}/asset/mobile/img/applyimg1-1.png" width="100%">
                    </div>
                    <div class="applyright">
                            <h4 id="bor_over">等待放款</h4>
                            <p>放款完成后，注意查收到账！</p>
                    </div>
                </div>
            </div>
            <div class="mui-content-padded" style="padding: 2px 0px 20px;">
                <span type="button" class="mui-btn mui-btn-primary mui-btn-block applyloan-btn" id="bingBankCard" style="display: none;">认证银行卡</span>
                <button type="button" class="mui-btn mui-btn-primary mui-btn-block applyloan-btn" id="btn_check">等待初审</button>
            </div>
        </div>
    </body>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
    <script type="text/javascript">
        $(function() {
            mui('body').on('tap','a', function() {
            var url = this.getAttribute("data-href");
            window.location.href=url;
            });
			//一进页面就检查状态
			checkCode();
        })


        $("#bingBankCard").click(function(){
            window.location="${context}/mobile/borrow/bindBankCard.html";
        });

        $("#completeIdentity").click(function(){
        	
            var borrowId=$("#borrowId").val();
            var params = {};
            params['borrowId'] = borrowId;
            params['compareId'] = 5;
            $.post("${context}/mobile/borrow/checkBorrowStatus.do", params, function(data) {
                if(data.code == 0) {
                    window.location="${context}/mobile/borrow/identity.html?borrowId="+borrowId;
                } else {
                    mui.alert(data.message);
                }
            }, 'json') ; 

        })

        //按钮返回首页
        $("#btn_check").click(function(){
            window.location="${context}/mobile/borrow/borrowPath.html";
        })
       

        //定时器
        var borrowId=$("#borrowId").val();
        var params = {};
        params['borrowId'] = borrowId;
        var timer=setInterval(function(){
			checkCode()
        },3000)
		function checkCode(){
			$.ajax({
				type:"post",
				url:"${context}/mobile/borrow/getBorrowStatus.do?id="+borrowId,
				async:true,
				data:params,
				dataType:'json',
				success:function(data){
					console.log(data.data)
					if (data.code == '0') {
					    //初审中
					    if (data.data == '1' || data.data == '2') {
					    	$('#btn_check').html('等待初审')
					    }
					    //初审失败
					    if (data.data == '3') {
					        $('#check_item1').find('h4').addClass('active').html('初审失败').end().find('p').remove();
					        $('#btn_check').html('初审失败');
					        clearInterval(timer);
					    }
					    //初审成功，认证银行卡
					    if (data.data >= '4') {
					        //认证银行卡
					        $('#check_item1').find('.check_img').eq(0).attr('src','${context}/asset/mobile/img/applyimg1-2.png').addClass('sh_img');
					        $('#check_item1').find('h4').addClass('active').html('初审成功').end().find('p').remove();
					        $('#bingBankCard').show();
					        $('#btn_check').hide();
					        $('#check_item2').find('h4:eq(0)').addClass('active')
					        //银行卡认证完
					        if(data.data > '4'){
					        	$('#check_item2').find('.check_img').attr('src','${context}/asset/mobile/img/applyimg1-2.png').addClass('sh_img');
					        	$('#check_item2').find('h4:eq(0)').html('银行卡已确认').end().find('p').addClass('active').html(${bankCardNo!});
					        	$('#bingBankCard').hide()
					        	//等待复审
					        	if(data.data >= '5'){
						        	$('#text2_1').addClass('active');
						        	$('#btn_check').show().html('等待复审');
						        	//复审成功等待放款
						        	if(data.data >= '6'){
						        		$('#check_item3').find('.check_img').attr('src','${context}/asset/mobile/img/applyimg1-2.png').addClass('sh_img');
							        	$('#text2_1,#btn_check').html('复审成功')
							        }
						        	//复审失败
						        	if(data.data == '7'){
							        	$('#text2_1,#btn_check').html('复审失败');
							        	clearInterval(timer);
							        }
						        	//放款成功
						        	if(data.data == '8'){
						        		$('#check_item4').find('.check_img').attr('src','${context}/asset/mobile/img/applyimg1-2.png').addClass('sh_img');
						        		$('#bor_over').addClass('active').html('放款成功');
						        		$('#btn_check').html('放款成功');
						        		clearInterval(timer);
						        	}
						        }
					        	
					        }
					    }
					} else {
					    mui.alert(data.message)
					}
					//img 1-3
			$('.sh_img:last').parents('.apply').next().find('img').eq(0).attr('src','${context}/asset/mobile/img/applyimg1-3.png')
				}
			});
		}

    </script>
    </html>