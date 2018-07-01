<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">用户管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/member.html">会员管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">推荐列表</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div style="margin-top: 20px;padding: 0 20px;">
		
		
		
		
		
			
		
			
			
			<div role="tabpanel" class="tab-pane" id="borrowDetail">
				<table id="borrowDetailTab" class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>会员编号</th>
							<th>真实姓名</th>
							<th>手机号</th>
							<th>注册时间</th>
							<th>用户状态</th>
							<th>借款次数</th>
						</tr>
					</thead>
				</table>
			</div>
			
			
		
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
	
	
	
	$(document).ready(function() {
		var table = spark.dataTable('#borrowDetailTab','${context}/admin/member/relation/${basicMap.id}.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "create_time" },
				{ "data": "member_status" },
				{ "data": "successBorrowTimes" },
			],
			"columnDefs":[
				
				
				{
					"targets":[4],
					"data":'member_status',
					"render":function(data,type,full){
						if(data == 1){
							return '<span class="label label-success">正常用户</span>';
						}else if(data == 2){
							return '<span class="label label-success">争议用户</span>';
						}else if(data == 3){
							return '<span class="label label-success">黑名单</span>';
						}else if(data == 0){
							return '<span class="label label-success">待审用户</span>';
						}else{
								return '<span class="label label-danger">其他状态</span>';
							}
				}
				
				}
				
			],			
			drawCallback:function(){
				spark.handleToggle('#borrowDetailTab');
			}
		})
	} );
	
	
	
	
	
</script>

</@override>
<@layout name="/admin/layout/main.ftl" />







