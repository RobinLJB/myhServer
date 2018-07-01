<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:overdue" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款列表</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">所有借款</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		还款列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">借款状态</label>
			<input type="text" data-toggle="table-search" data-column="6" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">申请时间</label>
			<input type="text" data-toggle="table-search" data-column="5" class="form-control" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<ul class="nav nav-tabs"     style="margin-bottom: 0px;border-bottom: 0px solid #ddd;" >
		<li class="active"><a href="${context}/admin/repay/overdue.html" aria-expanded="true">逾期期间</a></li>
		<li class=""><a href="${context}/admin/repay/repayComplete.html" aria-expanded="false">还款完成</a></li>
	</ul>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>借款编号</th>
                <th>真实姓名</th>
                <th>手机号</th>
                <th>会员状态</th>
				<th>借款金额</th>
				<th>借款周期</th>
				<th>开始逾期</th>
				<th>逾期天数</th>
				<th>逾期费用</th>
				<th>放款时间</th>
				<th>初审人员</th>
				<th>复审人员</th>
				<th>手动还款</th>
				<th>借款状态</th>
				<th>借款操作</th>
            </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#memberTable','${context}/admin/repay/overdueList/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name" },{ "data": "mobilePhone" },{ "data": "member_status" },
				{ "data": "benJin" },{ "data": "borrowDate" },{ "data": "beginTime" },{ "data": "overDays" },
				{ "data": "overdueFee" },{ "data": "secondAuditTime" },
				{ "data": "fristAdminUsername" },{ "data": "secondAdminUsername" },{ "data": "lastAdminUsername" },
				{ "data": "borrowStatus" },
			],
			"columnDefs":[
				
				{
					"targets":[3],
					"data":'member_status',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">正常客户</span>';
						}else if(data == 2){
							return '<span class="label label-warning">争议客户</span>';
						}else if(data == 3){
							return '<span class="label label-danger">黑名单</span>';
						}else if(data == 0){
							return '<span class="label label-success">等待审核</span>';
						}else{
							return '<span class="label label-success">其他状态</span>';
						}
					}
				},
				
				
				{
					"targets":[13],
					"data":'borrowStatus',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">提交审核</span>';
						}else if(data == 2){
							return '<span class="label label-success">已认领</span>';
						}else if(data == 3){
							return '<span class="label label-success">初审失败</span>';
						}else if(data == 4){
							return '<span class="label label-success">初审成功</span>';
						}else if(data == 5){
							return '<span class="label label-success">等待复审</span>';
						}else if(data == 6){
							return '<span class="label label-success">复审成功</span>';
						}else if(data == 7){
							return '<span class="label label-success">复审失败</span>';
						}else if(data == 8){
							return '<span class="label label-success">还款期间</span>';
						}else if(data == 9){
							return '<span class="label label-success">已经逾期</span>';
						}else if(data == 10){
							return '<span class="label label-success">还款完成</span>';
						}else if(data == 11){
							return '<span class="label label-success">未还完</span>';
						}
						else return  '<span class="label label-danger">其他状态</span>';
					}
				},
				{
					"targets":[14],
					"data":'id',
					"render":function(data,type,full){
						
							return	'<a class="btn btn-xs btn-default" href="${context}/admin/repay/overdueRepayDetail/'+data+'.html">还款/查看</a>';
						
						
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#memberTable');
			}
		})
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />