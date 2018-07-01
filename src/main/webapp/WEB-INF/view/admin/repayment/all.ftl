<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${context}/asset/public/plugins/jquery-treeview/css/jquery.treeview.css" /> 
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="repayment:all" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">借款管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">还款列表</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>还款列表</h3>
		<p></p>
	</div>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="title">借款人</label>
			<input type="text" class="form-control" data-toggle="table-search" data-column="1"   placeholder="申请人">
		</div>
		<div class="form-group">
			<label for="title">手机号</label>
			<input type="text" class="form-control" data-toggle="table-search"  data-column="2" placeholder="手机号">
		</div>	
				
		<button type="button" id="search" class="btn btn-default">查找</button>
	</form>
	<table id="loanTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>借款编号</th>
                <th>借款人(真实姓名)</th>
				<th>手机号</th>
                <th>应还本金</th>
				<th>应还利息</th>
				<th>剩余本金</th>
				<th>剩余利息</th>
				<th>总期数</th>
				<th>应还日期</th>
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
		var table = spark.dataTable('#loanTable','${context}/admin/repayment/list/-1.json',{
		  //向服务器传额外的参数
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "mobilePhone" },
				{ "data": "principal" },
				{ "data": "interest" },
				{ "data": "principalBalance" },
				{ "data": "interestBalance" },
				{ "data": "period" },
				{ "data": "repayDate" },
				{ "data": "status" },
				{ "data": "id" }
			],
			"columnDefs":[
				{
					"targets":1,
					"render":function(data,type,row){
						return row.username+"/"+row.realName;
					}
				},
				{
					"targets":9,
					"render":function(data,type,row){
						if(row.status == "1"){
							return "<span class='label label-danger'>未还</span>";
						}
						else return "<span class='label label-success'>已还</span>";
					}
				},
				{
					"targets":10,
					"render":function(data,type,row){
						return  "<a class='btn btn-xs btn-default' href='${context}/admin/repayment/"+row.id+".html'>查看详情</a>";
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