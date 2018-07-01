<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal-default-theme.css">
<style>
	td{text-align: center;}
	#d_debt table tr > td.inpt{padding:0;height: 100%;}
	#d_debt table td>input{width: 100%;height: 100%; border: 0; text-align:center; }
</style>
</@override>
<@override name="body">
 	<div class="r_main">
      <div class="tabtil">
        <ul>
        	<li id="lab_2" <#if status = 2>class="on"</#if> ><a href="${context}/ucenter/invest/tendering.html">招标中</a></li>
			<li id="lab_2" <#if status=3>class="on"</#if> ><a  href="${context}/ucenter/invest/fullScale.html">满标</a></li>
        	<li id="lab_3" <#if status=4>class="on"</#if> ><a  href="${context}/ucenter/invest/repaying.html">回款中</a></li>
        	<li id="lab_4" <#if status=5>class="on"</#if> ><a  href="${context}/ucenter/invest/completed.html">已还清</a></li>
        </ul>
        </div>
		<div class="box">
        <div class="boxmain2">
        <div class="biaoge" >
		  <table class="table table-bordered">
		  	<tr>
		    	<th>项目名称</th>
		    	<th>投标金额</th>
		    	<th>应收利息</th>
		    	<th>年利率</th>
		    	<th>期限</th>
		    	<#if status = 4>
		    	<th>已还期数/总期数</th>
		    	</#if>
		    	<th>已收本金</th>
		    	<th>已收利息</th>
				<#if (status>3) >
		 		<th>还款明细</th>
		 		<th>借款协议</th>
				</#if>
		 		<#if status = 4>
				<th>债权转让</th>
				</#if>
		   	</tr>
			<#if investList??>
			<#list investList as invest>
			<tr> 
				<td><a target="_blank" href="${context}/loan/${invest.loan_id!}.html">${invest.title!}</a></td>
				<td class="amount">${invest.amount!}</td>
				<td class="interest">${invest.interest!}</td>
				<td>${invest.annual_rate!0}%</td>
				<td>${invest.cycle!}<#if invest.cycle_type = "1">个月<#else>天</#if></td>
				<#if status = 4>
				<td>${invest.paied_cycle!}/${invest.repayCycle!}</td>
				</#if>
				<td class="paied_principal">${invest.paied_principal!0}</td>
				<td class="paied_interest">${invest.paied_interest!0}</td>
				<#if ( status > 3 ) > 
				<td><a class="btn btn-xs btn-info" onclick="showRepayment(${invest.id!})">查看</a></td>
				<td><a class="btn btn-xs btn-info" href="${context}/ucenter/invest/contract/${invest.loan_id}.html" target="_blank">查看</a></td>
				</#if>
				<#if status = 4>
				<td>
				<!--   状态：1：等待审核，2：正在募集，3：等待复审，4：已完成，5：失败 6：转让标卖完  7: 超出时间每人购买    -->
					<#if invest.debtStatus == '1'> 
					债权转让等待审核
					<#elseif invest.debtStatus == '2'> 
					债权转让正在募集
					<#elseif invest.debtStatus == '3'> 
					债权转让等待复审
					<#elseif invest.debtStatus == '4'> 
					债权转让已完成
					<#elseif invest.debtStatus == '5'>
					债权转让已流标
					<#elseif invest.debtStatus == '6'>
					债权转让转让标卖完
					<#elseif invest.debtStatus == '7'>
					债权转让超出时间
					<#else>
					<button type="button" class="btn btn-xs btn-info btnApplyDebt" 
						invest_id="${invest.id!}">申请债权转让</button>
					</#if> 
				</td>
				</#if>
			</tr>
			 
			</#list>
			<#else>
			<tr> <td>暂无数据	</td></tr>
			</#if>
		</table>
       </div>
    </div>
</div>
</div>
<div class="remodal" data-remodal-id="modal">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="repaymentList"></div>
</div>

<div class="remodal" data-remodal-id="debt-md">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="d_debt" style="margin:35px 10px;">
	<form method="post" 
	   action="${context}/ucenter/invest/doDebt.do">
	<table class="table table-bordered">
	<tr><td>债权总金额</td><td id="lSum"></td></tr>
	<tr><td>债权转让标题</td><td class="inpt"><input type="text" id="debtTitle" /></td></tr>
	<tr><td>转让价格(元)</td><td class="inpt"><input type="text" onkeyup="clearNoNum(this)" id="amount" /></td></tr>
	<tr><td>招标天数(天)</td><td class="inpt"><input type="text" id="raise_time" /></td></tr>
	<tr><td>转让原因</td><td class="inpt"><input type="text" id="remark" /></td></tr>
	</table>
	<input type="hidden" id="investId" />
	<button type="button" id="btnDebtOk" class="btn btn-xs btn-info" style="padding:5px 15px;" >确定</button>
	</form>
	</div>
</div>

</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
function clearNoNum(obj){  
  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数   
  if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
   obj.value= parseFloat(obj.value);  
  }  
}
	$(document).ready(function() {
		$('.myinvest').addClass('active').parents().show();
		$('.submeun-9 a').addClass('active').parents().show();
	});
	
	$("#btnDebtOk").click(function(){
		var params = {};
		params['investId']=$("#investId").val();
		params['debtTitle']=$("#debtTitle").val();
		params['amount']=$("#amount").val();
		params['raise_time']=$("#raise_time").val();
		params['remark']=$("#remark").val();
		$.post("${context}/ucenter/invest/doDebt.do", params, function(data){
			if(data.code != "0"){
				alert(data.message);
			}else{
				alert("已提交到后台，请等待审核");
				$('[data-remodal-id=debt-md]').remodal().close();
				location.reload();
			}
		}, 'json');
	});
	
	$(".btnApplyDebt").click(function(){
		$('[data-remodal-id=debt-md]').remodal().open();
		var invest_id = $(this).attr("invest_id");
		var lSum = parseFloat($(this).parent().siblings(".amount").html()) + 
			parseFloat($(this).parent().siblings(".interest").html()) -
			parseFloat($(this).parent().siblings(".paied_principal").html()) -
			parseFloat($(this).parent().siblings(".paied_interest").html()) ;
		$("#lSum").html(lSum.toFixed(2) + "元"); 
		$("#investId").val(invest_id);
	});
	
	function showRepayment(id) { 
		$.get('${context}/ucenter/invest/repayment.html?investId='+id,function(html){
			$('#repaymentList').html(html);
			$('[data-remodal-id=modal]').remodal().open();
		});
	}
	
	
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />