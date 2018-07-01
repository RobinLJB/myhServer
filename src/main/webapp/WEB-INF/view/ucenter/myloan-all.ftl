<@override name="head">
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
				<li <#if status = -1>class="on"</#if>><a href="${context}/ucenter/myloan/all.html">全部</a></li>
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
		    <table class="table table-bordered">
				<tr>
					<th>项目名称</th>
					<th>借款金额</th>
					<th>年利率</th>
					<th>期限</th>
					<th>还款方式</th>
					<th>发布时间</th>
					<th>状态</th>
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
			    <td align="center">${loan.publish_time}</td>
			    <td align="center">
			    <#if loan.status = "1">
			    初审中
			    <#elseif loan.status = "2">
			    招标中
			    <#elseif loan.status = "3">
			    复审中
			    <#elseif loan.status = "4">
			    还款中
			    <#elseif loan.status = "5">
			    已还完
			    <#elseif loan.status = "6">
			    已流标
			    </#if>
			    </td>
			  	</tr>
			  	</#list>
			  	</tbody>
		    </table>
		</div>
    </div>
</div>
</div>
</@override>
<@override name="script">
 <script>
	$(document).ready(function() {
	$('.myloanhk').addClass('active').parents().show();
	$('.submeun-12 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />