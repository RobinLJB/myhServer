<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="person:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">营销管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">平台活动</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<ul class="nav nav-tabs"     style="margin-bottom: 0px;border-bottom: 0px solid #ddd;" >
		<li class=""><a href="${context}/admin/extension/subPersonA/${mid}.html" aria-expanded="false">已提交申请</a></li>
		<li class="active"><a href="${context}/admin/extension/subPersonB/${mid}.html" aria-expanded="true">已放款的</a></li>
		<li class=""><a href="${context}/admin/extension/subPersonC/${mid}.html" aria-expanded="false">已拒绝</a></li>
	</ul>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>借款编号</th>
                <th>真实姓名</th>
				<th>手机号码</th>
				<th>申请日期</th>
				<th>申请额度</th>
				<th>申请天数</th>
				<th>审核状态</th>
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
		var table = spark.dataTable('#memberTable','${context}/admin/extension/subPersonB/list/${mid}.json',{
			"columns": [
				{ "data": "bid" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "fristSubmitTime" },
				{ "data": "benJin" },
				{ "data": "borrowDate" },
				{ "data": "borrowStatus" },
				
				
			],
			"columnDefs":[
				{
					"targets":[6],
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
						}else if(data == 12){
							return '<span class="label label-success">已取消</span>';
						}
						else return  '<span class="label label-danger">其他状态</span>';
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
		});
		
	});
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />