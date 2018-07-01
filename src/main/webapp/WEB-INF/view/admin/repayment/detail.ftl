<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<style>
	.detail-info {
		width: 871px;
		line-height: 20px;
		border-style: solid;
		border-width: 0 0 1px 1px;
		border-color: transparent transparent #CCC #CCC;
		margin: 10px 0 20px 0;
		box-shadow: 2px 2px 2px rgba(204,204,204,0.25);
	}
	.detail-info tbody th {
		color: #777;
		background-color: #F7F7F7;
		text-align: right;
		width: 119px;
		height: 20px;
		padding: 8px 5px;
		border-style: solid;
		border-width: 1px 1px 0 1px;
		border-color: #CCC;
	}
	.detail-info thead th {
		font-weight: 600;
		color: #FFF;
		background-color: #364150;
		height: 20px;
		padding: 8px 5px;
		border-style: solid;
		border-width: 1px 1px 0 0;
		border-color: #CCC #CCC transparent transparent;
	}
	#loanImgs{
		overflow: hidden;
		list-style: none;
		margin: 0px;
		padding: 0px;
	}
	#loanImgs li{
		float: left;
		margin-right: 10px;
	}
	#loanImgs li img{
		width:100px;
		border:1px solid #999;
	}
	.size1of4 {
	    width: 25%;
	}
</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="repayment:all" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">贷后管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/repayment/all.html">还款列表</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">还款详情</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	<table class="table detail-info">
		<thead>
			<th colspan="4">借款详情</th>
		</thead>
		
		<tbody>
		<#if loan??>
			<tr>
				<th>借款标题：</th> 
				<td>
					<a href="${context}/admin/loan/${loan.id!-1}.html">${loan.title!}</a>
				</td>
				<th>满标时间：</th>
				<td>
					${loan.fullscale_time!}
				</td>
			</tr>
			<tr>
				<th>借款账号：</th>
				<td>
					${loan.username!}
				</td>
				<th>借款用户：</th>
				<td>
					${loan.realName!}
				</td>
			</tr>
			<tr>
				<th>借款金额：</th>
				<td>
					${loan.amount!}&nbsp;元
				</td>
				<th>年利率：</th>
				<td>
					${loan.annual_rate}%
				</td>
			</tr>
			<tr>
				<th>借款期限：</th>
				<td>
					${loan.cycle}<#if loan.cycle_type == "1">个月<#else>天</#if>
				</td>
				<th>还款方式：</th>
				<td>
					<@option group="CATE_PAYMENT" key="${loan.payment_mode}" />
				</td>
			</tr>
			<#else>
			<tr>
		<th>没有借款详情</th>
		</tr>
		</#if>
		</tbody>
		
	</table>
	<table class="table detail-info">
		<thead>
			<th colspan="7">还款详情</th>
		</thead>
		
		<tbody>
			<tr>
				<td>期数</td>
			<td>还款时间</td>
			<td>应还本金</td>
			<td>应还利息</td>
			 
			<td>应还总额</td>
			<td>状态</td>
			</tr>
		
			<tr>
				<td>${repay.period}</td>
				<td>${repay.repayDate}</td>
				<td>${repay.principal}</td>
				<td>${repay.interest}</td>
				 
				<td>${repay.interest?c + repay.principal?c}</td>
				<td><#if repay.status=1>未还<#else>已还</#if></td>
			</tr>
		
		</tbody>
		
	
	</table>
	<table class="table table-bordered" style="width: 871px;">
		<thead>
			<th>用户名</th>
			<th>真实姓名/手机号</th>
			<th>本金</th>
			<th>利息</th>
			<th>总额</th>
		</thead>
		<tbody>
		<#if repayList??>
		<#list repayList as item>
			<tr>
				<td>${item.username!}</td>
				<td>${item.real_name!''}/${item.mobilePhone!}</td>
				<td>${item.principal}</td>
				<td>${item.interest}</td>
				<td>${item.principal?number+ item.interest?number}</td>
			</tr>
		</#list>
		<#else>
		没有数据
		</#if>
		</tbody>
	</table>
</div>
</@override>
<@override name="script">
<script>
	
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />