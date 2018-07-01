<@override name="head">
<link href="${context}/asset/public/plugins/area/css/select2.css" rel="stylesheet"/>
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<style>
.form-group .control-label{width:200px!important}
.form-group .form-control{margin-top:0px;}
</style>
</@override>
<@override name="body">

	<div class="alert alert-success">
		<h3>添加银行卡</h3>
	</div>
	<form class="form-horizontal"   id="from" method="post"    action="bindBank.do"> 
 
			
		<div class="" id="verify" >
			<div class="form-group">
				<label class="control-label col-md-3">开户行:</label>
				  <div class="col-md-4">
					<div class="shortcut-card noinfo mr15" id="selectedBank" data-code="" style="">
                        <i class="banklogo-zggsyh jLogo banklogo"></i>
                        <i class="icon-shortcut"></i>
                    </div>
				  </div>
				  <input type="hidden"  id="bankCode"   name="bank_code" value="ICBC"  />
				 <a href="javascript:;" onclick="showRepayment(1)" class="link">重新选择</a> 
				 
			</div>
			<div class="form-group">
			<label class="control-label col-md-3"></label>
				<div class="col-md-4">
					<table class="table-bordered  tab-quota">
						<tbody>
						<tr><th>单笔限额(元)</th><th>每日限额(元)</th></tr>
						<tr class="table-data"><td>50000</td><td>500000</td></tr>
						</tbody>
					</table>
				</div>	
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">姓名:</label>
				  <div class="col-md-4">
						<input type="text"   class="form-control "  value="${user.realName!}" readonly />
				  </div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">身份证号码:</label>
				  <div class="col-md-4">
						<input type="text"   class="form-control required" value="${user.identNo!}" readonly />
				  </div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">储蓄卡卡号:</label>
				  <div class="col-md-4">
						<input type="text"   class="form-control required" name="bank_account_no" value=""  />
						<input type="hidden"   class="form-control required" name="card_attribute" value="C"  />
				  </div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">开户地区:</label>
				  <div class="col-md-2">
						<select id="loc_province" style="width:120px;" name="province" class="required"></select>
				  </div>
				  <div class="col-md-2">
				  <select id="loc_city" style="width:120px; margin-left: 10px" class="required" name="city"></select>
				  </div>
				  <!---
				  <div class="col-md-2">
				  <select id="loc_town" style="width:120px;margin-left: 10px" name="town"></select>
				  </div>
				  -->
				   
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3">银行预留手机:</label>
				<div class="col-md-4">
					<input type="text"  name="phone" id="phone"  class="form-control required"   value="${user.mobilePhone!}" readonly  />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">手机验证码:</label>
				<div class="col-md-4">
					<input type="text" id="sncode" class="form-control required" name="sncode" value="" />
				</div>
				<div class="col-md-2">
					<button id="getCheckCode" type="button" class="btn btn-default">获取验证码</button>
				</div>
			</div>
		 
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				 
				<button  type="submit" id="submit" class="btn btn-primary"  >确定</button>
			</div>
			 
		</div>	
	</form>	
<div class="remodal" data-remodal-id="modal">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="repaymentList">
		<div style="position: fixed; left: 0px; top: 0px; z-index: 998; width: 1908px; height: 1061px; opacity: 0.6; background-color: rgb(0, 0, 0);"></div>
		<div class="dialog-main" style=" position: fixed;top: 21.774px;z-index: 999;width: 775px;margin-left: -387px;">              
		<div class="dialog-tip" style="display: none"></div>
		<div class="dialog-hd"><h2>选择银行</h2><span class="line"></span></div>              
		<div class="dialog-bd">
			<div class="banklist-box" id="secureBankList">
				<ul class="banklist-ul clearfix mt10">
							<li class="banklist-li selected" data-code="ICBC" data-one="5" data-day="5">
								<p class="hd"><i class="banklogo banklogo-zggsyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额5万</p>
							</li>
							<li class="banklist-li " data-code="CCB" data-one="5" data-day="50">
								<p class="hd"><i class="banklogo banklogo-zgjsyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额50万</p>
							</li>
							<li class="banklist-li" data-code="ABC" data-one="50000" data-day="100000">
								<p class="hd"><i class="banklogo banklogo-zgnyyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额10万</p>
							</li>
							<li class="banklist-li" data-code="BOC" data-one="50000" data-day="500000">
								<p class="hd"><i class="banklogo banklogo-zgyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额50万</p>
							</li>
							<li class="banklist-li" data-code="CMB" data-one="50000" data-day="2000000">
								<p class="hd"><i class="banklogo banklogo-zsyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额10万，日限额200万</p>
							</li>
							<li class="banklist-li" data-code="CITIC" data-one="50000" data-day="500000">
								<p class="hd"><i class="banklogo banklogo-zxyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额50万</p>
							</li>
							<li class="banklist-li" data-code="CIB" data-one="50000" data-day="50000">
								<p class="hd"><i class="banklogo banklogo-xyyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额5万</p>
							</li>
							<li class="banklist-li" data-code="CMBC"  data-one="50000" data-day="2000000">
								<p class="hd"><i class="banklogo banklogo-zgmsyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额200万</p>
							</li>
							<li class="banklist-li" data-code="CEB"  data-one="50000" data-day="3000000">
								<p class="hd"><i class="banklogo banklogo-zggdyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额300万</p>
							</li>
							<li class="banklist-li" data-code="SZPAB"  data-one="50000" data-day="3000000">
								<p class="hd"><i class="banklogo banklogo-payh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额300万</p>
							</li>
							<li class="banklist-li" data-code="GDB" data-one="50000" data-day="1000000">
								<p class="hd"><i class="banklogo banklogo-gfyh"  ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额100万</p>
							</li>
							<li class="banklist-li" data-code="BOS" data-one="5000" data-day="50000">
								<p class="hd"><i class="banklogo banklogo-shyh"  ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额0.5万，日限额5万</p>
							</li>
							 <li class="banklist-li" data-code="COMM" data-one="50000" data-day="500000">
								<p class="hd"><i class="banklogo banklogo-jtyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额50万</p>
							</li>
							<li class="banklist-li" data-code="PSBC"  data-one="5000" data-day="5000">
								<p class="hd"><i class="banklogo banklogo-zgyzcxyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额0.5万，日限额0.5万</p>
							</li>
							<li class="banklist-li" data-code="SPDB" data-one="50000" data-day="50000">
								<p class="hd"><i class="banklogo banklogo-pfyh" ></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额5万</p>
							</li>
				</ul>
			</div>
		</div>
		<div class="dialog-bt">
			<a href="javascript:;" class="btn btn-success" id="btn-select">确定</a>
			<a href="javascript:;" data-remodal-action="close"  class="btn btn-warning">取消</a>
		</div>
		</div>	
	
	</div>
</div>	
</@override> 
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/asset/public/plugins/area/js/area.js"></script>
<script type="text/javascript" src="${context}/asset/public/plugins/area/js/location.js"></script>
<script src="${context}/asset/public/plugins/area/js/select2.js"></script>
<script src="${context}/asset/public/plugins/area/js/select2_locale_zh-CN.js"></script>
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
	$(function(){
		FormValidation.validation('#from',function(resp){
			if(resp.code == 0){
					spark.alert(resp.message,"success");
					window.location.href="${context}/ucenter/safety/index.html";
				}
				else {
					spark.alert(resp.message,"error");
			}
		});	
	
	
		$('.submeun-4').addClass('selected').parents().show();
		$(".banklist-li").click(function(){
			$(this).addClass('selected').siblings().removeClass('selected');
		});
		$("#btn-select").click(function(){
			$("#selectedBank .jLogo").attr('class',$("#secureBankList .selected .banklogo").attr('class')+" jLogo");
			$(".table-data   td:eq(0)").html($(".banklist-ul .selected").attr("data-one"));
			$(".table-data   td:eq(1)").html($(".banklist-ul .selected").attr("data-day"));
			$("#bankCode").val($(".banklist-ul .selected").attr("data-code"));
			$('[data-remodal-id=modal]').remodal().close();
		});
		
			//发送验证码
	$('#getCheckCode').on('click',function(){
 
		$(this).attr('disabled',true);		 
		$.ajax({
			url:"${context}/ucenter/sendMbCode.do",
			data:{verify:"BINDBACK"},
			type:"post",
			dataType:"json",
			success:function(res){
				if(res.code == 0){
					startTick();
				}
				else {
					spark.alert(res.message,"error");
					$('#getCheckCode').attr('disabled',false);
				}
			},
			error:function(){
				$(this).attr('disabled',false);
			}
		});
	});
	function startTick(){
		window.tick = 60;
		window.clock = setInterval(function(){
			if(window.tick >0){
				$('#getCheckCode').text('剩余时间('+window.tick+'s)');
				window.tick--;
			}
			else {
				$('#getCheckCode').text('获取验证码');
				$('#getCheckCode').attr('disabled',false);
				clearInterval(window.clock);
			}
		},1000);
	}
	})
	function showRepayment(id){
		$('[data-remodal-id=modal]').remodal().open();
	}
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />