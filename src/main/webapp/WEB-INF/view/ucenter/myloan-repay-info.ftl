<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal-default-theme.css">
<style>
	#repayAmt{
		font-size: 20px;
    	font-weight: bold;
    	color: #0a8c0a;
	}
	#detail{
		color: #666;
    	font-size: 16px;
	}
</style>
</@override>
<@override name="body">
<div class="r_main">
	<div class="ci-title">
        <div class="ci-title-inner">
            <h2>还款列表</h2>
            <b class="line"></b>
        </div>
    </div>
    <div class="box">
        <div class="boxmain2">
	        <div class="biaoge">
		        <table class="table table-bordered">
			        <tr>
			          	<th>项目名称</th>
						<th>借款金额</th>
						<th>年利率</th>
						<th>期限</th>
						<th>还款方式</th>
						<th>总计本息</th>
						<th>已还期数/总期数</th>
						<th>本期应还</th>
						<th>还款日期</th>
						<th>逾期天数</th>
						<th>逾期罚息</th>
						<th>借款协议</th>
						<th>操作</th>
			        </tr>
			        <tbody>
			          <#list loanList as loan>
			          <tr>
			          <td align="center">${loan.title}</td>
			          <td align="center"><strong>￥${loan.amount}</strong></td>
			          <td align="center">${loan.annual_rate}%</td>
			          <td align="center">${loan.cycle}<#if loan.cycle_type = "1">个月<#else>天</#if></td>
			          <td align="center">
						<@option group="CATE_PAYMENT" key="${loan.payment_mode}" />
			          </td>
			          <td align="center">${loan.totalAmount!}</td>
			          <td align="center">${loan.has_paied_cycle}/${loan.total_cycle}</td>
			          <td align="center">${loan.repayAmount!}</td>
			          <td align="center">${loan.repayDate!}</td>
			          <td>${loan.overdue_day!}</td>
			          <td>${loan.overdue_fee!}</td>
			          <td><a class="btn btn-xs btn-info" href="${context}/ucenter/invest/contract/${loan.id}.html" target="_blank">查看</a></td>
			          <td align="center"><a onclick="repay(${loan.repayId!})" class="btn btn-success btn-xs">还款</a></td>
			          </tr>
			          </#list>
			        </tbody>
		        </table>
		    </div>
	    </div>
	</div>
</div>
<div class="remodal" data-remodal-id="modal">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="repaymentDetail" style="text-align:left;">
		<form action="${context}/ucenter/finance/repay.do" method="post" class="form" id="repayForm">
			<input type="hidden" id="repayId" name="repayId" />
			<div class="form-group">
				<label>本期实际应还本息</label>
				<p><span id="repayAmt"></span><span id="detail"></span></p>
			</div>
			<div class="form-group">
				<button class="btn btn-danger" type="submit">确定还款</button>
			</div>
		</form>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery.form.js"></script>
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
  	$(document).ready(function() {
  		$('.myloanhk').addClass('active').parents().show();
  		$('.submeun-13 a').addClass('active').parents().show();
  		/*$('#repayForm').ajaxForm({
  			success:function(resp){
  				$('[data-remodal-id=modal]').remodal().close();
  				if(resp.code == 0){
  					alert("还款成功");
  					location.reload();
  				}
  				else alert("还款失败，"+resp.message);
  			}
  		});*/
  	})

  	function repay(id){
		$.getJSON('${context}/ucenter/finance/repayment/detail.do?id='+id,function(resp){
			$('#repayId').val(id);
			$('#repayAmt').html("￥"+(resp.principal+resp.interest).toFixed(2));
			$('#detail').html("（其中本金：￥"+resp.principal.toFixed(2)+"，利息：￥"+resp.interest.toFixed(2)+"）");
			$('[data-remodal-id=modal]').remodal().open();

		});
	}
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />