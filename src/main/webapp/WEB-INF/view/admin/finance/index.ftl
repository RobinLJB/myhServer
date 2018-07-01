<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="finance:userFinance" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">财务管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">资金管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		用户资金概况
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">用户名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="3" class="form-control" placeholder="">
		</div>
		<button type="button" class="btn btn-default">查找</button>
	</form>
	<table id="roleTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>账户编号</th>
                <th>用户名</th>
                <th>真实姓名</th>
				<th>手机号</th>
				<th>可用金额</th>
				<th>冻结金额</th>
				<th>操作</th>
            </tr>
        </thead>
    </table>
</div>
<div id="modal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">用户资金变更</h4>
			</div>
			<form id="updateForm" action="${context}/admin/finance/update.do" method="post">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">用户名</label>
						<p class="form-control-static" style="font-weight:bold;" id="userName"></p>
						<input type="hidden" id="uid" name="uid" value="" />
					</div>
					<div class="form-group">
						<label class="control-label">变动资金</label>
						<input type="text" name="amount" class="form-control" id="amount"/>
						<span class="help-block text-warning">资金为负数时表示扣除</span>
					</div>
					<div class="form-group">
						<div>
							<label class="radio-inline">
							  <input type="radio" checked name="account" value="1" />RMB账户
							</label>
							<label class="radio-inline">
							  <input type="radio" name="account" value="2" />FB账户
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">备注</label>
						<textarea name="remark" class="form-control"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">提交</button>
				</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#roleTable','${context}/admin/funds/member/list.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "username" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "usable_balance" },
				{ "data": "freeze_balance" }
			],
			"columnDefs":[
				{
					"targets":[6],
					"render":function(data,type,full){
						return '<a class="btn btn-xs btn-primary" href="${context}/admin/finance/record/'+full.id+'.html">资金明细</a>';
					}
				}
			],
			drawCallback:function(){
				spark.handleToggle('#roleTable');
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
	$('#updateForm').ajaxForm({
		success:function(resp){
			spark.autoNotify(resp,true);
		}
	});
	function update(uid,username){
		$('#userName').text(username);
		$('#uid').val(uid);
		$('#updateForm').attr('action',"${context}/admin/finance/update/"+uid+".do");
		$('#modal').modal('show');
	}
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />