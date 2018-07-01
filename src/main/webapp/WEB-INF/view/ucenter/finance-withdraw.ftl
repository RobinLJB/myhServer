<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
</@override>
<@override name="body">
    <div class="nymain">
    <div class="wdzh">
   
    <div class="r_main">
        <div class="ci-title">
            <div class="ci-title-inner">
                <h2>提现</h2>
                <b class="line"></b>
            </div>
        </div>
		<div class="rech-opts2">
            <form action="${context}/ucenter/finance/withdraw/redirect.do" method="post">
            <div class="rech-items nom-items ">
                <h3>可提金额：</h3>
                <div class="rech-row"><span class="hot">￥${finance.usableBalance!0}</span></div>
            </div>
            <div class="rech-items nom-items symbol-modelWithdraw">
                <h3>提现金额：</h3>
                <div class="sum-input2">
                    <input type="hidden" id="withdrawTimes" value="0">
                    <input id="amount" name="amount" autocomplete="off" type="text" class="i-text" maxlength="10">
                    <span class="symbol">￥</span>
                </div>
            </div>
            <!--
            <div class="rech-items nom-items mt10">
                <h3>到账时间：</h3>
                <div id="timeDesc" style="height:42px;line-height:42px">预计2小时</div>
            </div>
            <div class="rech-items mt10">
                <h3>交易密码：</h3>
                <div class="sum-input2">
                    <input type="password" style="width:0;height:0;float:right;visibility: hidden;">
                    <input id="tradePassword" type="password" autocomplete="off" placeholder="请输入六位数字的交易密码" class="i-text" maxlength="20">
                </div>
            </div>-->
            <div class="rech-items nom-items" style="display:none; width: 300px">
                <div class="err-tip">您今天还可提现#num#次，每人每天最多可提现5次</div>
            </div>
            <div class="rech-submit mt10">
                <button id="doSubmit" class="btn-1" type="submit">提交</button>
            </div>
            </form>
        </div>
		
		<div class="rech-prompt">
            <h3 class="ci-cont-hd">温馨提示</h3>
            <p>
            1. 单笔提现最高50万元，单日累计提现金额上限为200万元，每日最多可提现10次<br>
            2. 当日充值的资金，不能当日提取<br>
            </p>
        </div>
    </div>
  </div>   
</div>
</@override>
<@override name="script">
<script>
  
    $("#doSubmit").click(function(){
         var amount = parseFloat($('#amount').val());
        if(isNaN(amount)|| amount<=0){
            spark.alert('请输入正确的提现金额，必须为大于0的数字','error');
            return false;
        }
    })
	function jumpUrl(obj){
       window.location.href=obj;
    }
    
    $("#btn_submit").click(function(){   
        if($("#dealMoney").val()=="" || $("#money_tip").text() != ""){
			return false;
        }
	});
</script>
<script>
	$(document).ready(function() {
	$('.mymoney').addClass('active').parents().show();
	$('.submeun-2 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />