<@override name="head">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal.css">
<link rel="stylesheet" href="${context}/asset/public/plugins/remodal/remodal-default-theme.css">
</@override>
<@override name="body">
    <div class="r_main">
    	<div class="ci-title">
            <div class="ci-title-inner">
                <h2>借款申请</h2>
                <b class="line"></b>
            </div>
        </div>
      	<div class="tabtil">
        	<ul>
				
				<li <#if status = 0>class="on"</#if>><a href="${context}/ucenter/myloan/applying.html">申请中</a></li>
				<li <#if status = 1>class="on"</#if>><a href="${context}/ucenter/myloan/first.html">初审中</a></li>
				<li <#if status = 2>class="on"</#if>><a href="${context}/ucenter/myloan/tendering.html">招标中</a></li>
				<li <#if status = 4>class="on"</#if>><a href="${context}/ucenter/myloan/repaying.html">还款中</li>
				<li <#if status = 5>class="on"</#if>><a href="${context}/ucenter/myloan/completed.html">已还完</a></li>
			</ul>
        </div>
		<div class="box" >
        <div class="boxmain2">
         <div class="biaoge">
		    <table class="table table-striped">
				<tr>
					<th>项目名称</th>
					<th>借款金额</th>
					<th>年利率</th>
					<th>期限</th>
					<th>还款方式</th>
					<th>已还期数/总期数</th>
					<th>总计本息</th>
					<th>已还本息</th>
					<#if status = 4>
					<th>待还本息</th>
					</#if>
					<th>还款明细</th>
					<th>借款协议</th> 
					<#if status = 4><th width="80">操作</th></#if>
				</tr>
			   	<tbody>
			   	<#list loanList as loan>
			   	<tr>
			    <td align="center">${loan.title}</td>
			    <td align="center"><strong>￥${loan.amount}</strong></td>
			    <td align="center">${loan.annual_rate}%</td>
			    <td align="center">${loan.cycle}<#if loan.cycle_type = "1">个月<#else>天</#if></td>
			    <td align="center">
			    <#if loan.payment_mode = "1">等额本息<#elseif loan.payment_mode = "2">按月付息<#else>到期还本付息</#if>
			    </td>
			    <td align="center">${loan.has_paied_cycle}/${loan.total_cycle}</td>
			    <td>${loan.totalAmount}</td>
			    <td>${loan.paiedAmount}</td>
			    <#if status = 4>
			    <td>${loan.remainedAmount}</td>
			    </#if>
			    <td align="center">
			    	<a onclick="showRepayment(${loan.id})" class="btn btn-success btn-xs">查看明细</a>
			    </td>
			  	<td><a class="btn btn-xs btn-info" href="${context}/ucenter/invest/contract/${loan.id}.html" target="_blank">查看</a></td>			  	
			  	<#if status = 4>
			  	<td>
			  	<button type="button" class="btn btn-xs btn-info" onclick="prepay(${loan.id})">提前还款</button> &nbsp;&nbsp; 
			  	<button type="button" class="btn btn-xs btn-info hidden" onclick="overDuePay(${loan.id})" >逾期还款</button>
			  	</td>
			  	</#if>
			  	</tr>
			  	</#list>
			  	</tbody>
		    </table>
		</div>
    </div>
</div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">还款明细</h4>
      </div>
      <div class="modal-body">
      	<p id="repaymentList2"></p>
      </div>
    </div>
  </div>
</div>

<div class="remodal" data-remodal-id="modal">
	<a data-remodal-action="close" class="remodal-close"></a>
	<div id="repaymentList"></div>
      
</div>
<div>
  	<form action="${context}/ucenter/finance/preRepay.do" id="postForm" method="post" class="form" id="repayForm">
		<input type="hidden" id="loanId" name="loanId" value="-1"> 
	</form>
	
 </div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/remodal/remodal.js"></script>
<script>
	$(document).ready(function() {
		$('.myloanhk').addClass('active').parents().show();
		$('.submeun-12 a').addClass('active').parents().show();
	});
	
	function prepay(loanId){
		$("#loanId").val(loanId);
		$("#postForm").attr("action", "${context}/ucenter/finance/preRepay.do");
		$("#postForm").submit();
	}
	
	
	function showRepayment(id) { 
		$.get('${context}/ucenter/myloan/repayment.html?loanId='+id,function(html){
			$('#repaymentList').html(html);
			//$('#myModal').modal('show');
			$('[data-remodal-id=modal]').remodal().open();
			
		});
	}
	
	
	
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />