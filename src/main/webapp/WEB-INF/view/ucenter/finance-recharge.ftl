<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<style>
.form-group .control-label{width:200px!important}
.form-group .form-control{margin-top:10px;}
</style>
</@override>
<@override name="body">
	<div class="nymain">
  	<div class="wdzh">
    <div class="r_main">
		<div class="ci-title">
            <div class="ci-title-inner">
                <h2>充值</h2>
                <b class="line"></b>
            </div>
        </div>
        <div class="rech-opts2">
		<form class="form-horizontal" id="from" method="post"  action="${context}/ucenter/finance/recharge/redirect.do">
		<div class="rech-opts">
			<div class="rech-items nom-items">
                <h3>账户余额：</h3>
                <div class="rech-row"><span class="fcorange">￥${finance.usableBalance!0}</span><span>元</span></div>
            </div>
			<div class="rech-items nom-items">
               <h3>充值金额：</h3>
               <div class="pay-monetary">
                    <input type="text" name="amount" id="amountOnlineBank" class="i-text" autocomplete="off" placeholder="请输入充值金额" maxlength="10"><span class="symbol">元</span>
                </div>
            </div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4" style="margin-top: 10px;">
				<button  type="submit" id="submit" class="btn-primary5">登录新浪支付充值</button>
			</div>
			 
		</div>	
	</form>	
	
	</div>
	<div class="rech-prompt mt50">
         <h3 class="ci-cont-hd">温馨提示</h3>
            <p>1. 充值完全免费，资金将由新浪支付进行第三方托管<br>2. 网上银行充值无需本人银行卡<br>3. 当日充值的资金，不能当日提取<br>4. 每日的充值限额依据各银行限额为准</p>
        </div>
    </div>
  </div>   
</div>
<div class="remodal" data-remodal-id="modal">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="repaymentList">
		<div style="position: fixed; left: 0px; top: 0px; z-index: 998; width: 1908px; height: 1061px; opacity: 0.6; background-color: rgb(0, 0, 0);"></div>
		<div class="dialog-main" style="    position: fixed;top: 21.774px;z-index: 999;width: 775px;margin-left: -387px;">              
		<div class="dialog-tip" style="display: none"></div>
		<div class="dialog-hd"><h2>选择银行</h2><span class="line"></span></div>              
		<div class="dialog-bd">
			<div class="banklist-box" id="secureBankList">
				<ul class="banklist-ul clearfix mt10">
							<li class="banklist-li selected" data-code="ICBC" data-one="50000" data-day="50000">
								<p class="hd"><i class="banklogo banklogo-zggsyh"></i><i class="icon-gg-s"></i></p>
								<p class="bd">单笔限额5万，日限额5万</p>
							</li>
							<li class="banklist-li " data-code="CCB" data-one="50000" data-day="500000">
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
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
  $(document).ready(function() {
  $('.mymoney').addClass('active').parents().show();
  $('.submeun-1 a').addClass('active').parents().show();
  <#if memberInfo??>
		location.href = "${context}/ucenter/safety/realname.html";
	</#if>
  })

</script>
<script>
    $("#submit").click(function(){
         var amount = parseFloat($('#amountOnlineBank').val());
        if(isNaN(amount)|| amount<=0){
            spark.alert('请输入正确的充值金额，必须为大于0的数字','error');
            return false;
        }
    })
	$(function(){
		/*FormValidation.validation('#from',function(resp){
			alert(resp.message);
		});*/	
	
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
	})
	function showRepayment(id){
		$('[data-remodal-id=modal]').remodal().open();
	}
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />