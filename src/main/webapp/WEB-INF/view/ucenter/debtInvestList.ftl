<@override name="head">
</@override>
<@override name="body">
   <div id="page-content-wrapper">
    <div class="r_main">
      	<div class="ci-title">
            <div class="ci-title-inner">
                <h2>债权投资列表</h2>
                <b class="line"></b>
            </div>
        </div>
   		
		<div class="box" >
        <div class="boxmain2">
         <div class="biaoge">
		    <table class="table table-bordered">
				<tr>
					<th>债权标题</th>
					<th>转让价格</th>
					<th>投资金额</th>
					<th>年利率</th>
					<th>还款方式</th>
					<th>发布时间</th>
					<th>状态</th>
				</tr>
			   	<tbody>
			   	<#if list??>
			   	<#list list as l>
			   	<tr>
			    <td align="center">${l.debtTitle!}</td>
			    <td align="center"><strong>￥${l.amount!}</strong></td>
			    <td align="center"><strong>￥${l.investAmount!}</strong></td>
			    <td align="center">${l.annual_rate}%</td>
			    <td align="center">
			    	<#if l.payment_mode == "1">等额本息
						<#elseif l.payment_mode=="2">按月付息 到期还本
						<#elseif l.payment_mode=="3">到期还本付息
					</#if>
			    </td>
			    <td align="center">${l.add_time}</td>
			    <td align="center"> 
			    	<#if l.status == "0">审核失败
						<#elseif l.status=="1">等待审核
						<#elseif l.status=="2">正在募集
						<#elseif l.status=="3">等待复审
						<#elseif l.status=="4">已完成
						<#elseif l.status=="5">已流标
					</#if>
			    </td>
			  	</tr>
			   	</#list>
			   	</#if>
			   	
			  	</tbody>
		    </table>
		</div>
    </div>
</div>
</div>
			</div>
		</div>
	</div>

</@override>
<@override name="script">
 <script>
	$(document).ready(function() {
	$('.myloanall').addClass('active').parents().show();
	$('.submeun-11-2 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />