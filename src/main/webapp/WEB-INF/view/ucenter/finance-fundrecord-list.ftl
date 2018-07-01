<table class="table table-bordered table-striped table-small" style="font-size:12px;">
	<thead>
		<tr>
			<th width="140px;">时间</th>
			<th width="100px;">操作类型</th>
			<th>资金变动</th>
			<th>备注</th>
			<th>可用余额</th>
			<th>冻结金额</th>
			<th>待收金额</th>
	 
		</tr>
	</thead>
	<tbody>
		<#list page.items as fund>
			<tr>
				<td align="center">${fund.add_time!}</td>
				<td align="center">${fund.fundMode!}</td>
				<td align="center">
					<#if fund.type = "1">
					<span class="text-success">+ ${fund.amount!}</span>
					<#elseif fund.type = "2">
					<span class="text-danger">- ${fund.amount!}</span>
					<#else>
					<span class="text-default">${fund.amount!}</span>
					</#if>
				</td>
				<td align="left" width="95px;" >${fund.remark!}</td>
				<td align="center">${fund.usableSum!}</td>
				<td align="center">${fund.freezeSum!}</td>
				<td align="center">${fund.dueinSum!}</td>
			 
			</tr>
		<#else>
		<tr><td colspan="7" align="center">暂无信息</td></tr>
		</#list>
	</tbody>
</table>
<@pagination curPage="${page.currentPage}" click="changePage" pageSize="${page.pageSize}" totalRecord="${page.totalRecord}"/>
