<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">全部的借款</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		借款列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="title">借款人</label>
			<input type="text" class="form-control" data-toggle="table-search" data-column="1"   placeholder="申请人">
		</div>
		<div class="form-group">
			<label for="title">真实姓名</label>
			<input type="text" class="form-control" data-toggle="table-search"  data-column="2" placeholder="真实姓名">
		</div>			
		<button type="button" id="search" class="btn btn-default">查找</button>
	</form>
	<table id="loanTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>借款编号</th>
                <th>借款人</th>
				<th>真实姓名</th>
				<th>手机号</th>
                <th>借款金额</th>
				<th>年利率</th>
				<th>借款期限</th>
				<th>发布时间</th>
				<th>状态</th>
				<th>操作</th>
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
	    var index = 0;
		var table = spark.dataTable('#loanTable','${context}/admin/loan/list.json',{
		  //向服务器传额外的参数
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "realName" },
				{ "data": "mobilePhone" },
				{ "data": "amount" },
				{ "data": "annual_rate" },
				{ "data": "cycle" },
				{ "data": "publish_time" },
				{ "data": "status" },
				{ "data": "id" }
			],
			"columnDefs":[
				{
					"targets":5,
					"render":function(data,type,row){
						return row.annual_rate+"%";
					}
				},
				{
					"targets":6,
					"render":function(data,type,row){
						return spark.sprintf("<span>%s&nbsp;%s</span>",row.cycle,row.cycle_type==1?"月":"天");
					}
				},
				{
					"targets":[8],
					"data":'status',
					"render":function(data,type,full){
						switch(data){
						case  "0" : return '<span class="label label-warning">等待资料</span>';break;
						case  "1" : return '<span class="label label-warning">初审中</span>';break;
						case  "2" : return '<span class="label label-primary">正在招标中</span>';break;
						case  "3" : return '<span class="label label-warning">等待复审</span>';break;
						case  "4" : return '<span class="label label-success">还款中</span>';break;
						case  "5" : return '<span class="label label-success">已还完</span>';break;
						case  "6" : return '<span class="label label-danger">已流标</span>';break;
						case  "10" : return '<span class="label label-danger">已流标</span>';break;
						}
					}
				},
				{
					"targets":[9],
					"data":'id',
					"render":function(data,type,full){
						switch(full.status){
							case  "0" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "1" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a><a class="btn btn-xs btn-default" href="${context}/admin/loan/edit/'+data+'.html">编辑</a>';break;
							case  "2" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "3" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "4" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "5" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "6" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;
							case  "10" : return '<a class="btn btn-xs btn-default" href="${context}/admin/loan/'+data+'.html">查看</a>';break;							
						}
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#loanTable');
			},
		});
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