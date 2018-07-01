<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="app:list" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">站点信息</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	
	
	<a class="btn btn-primary" href="${context}/admin/app/publish.html"><i class="fa fa-plus"></i>&nbsp;发布版本</a>
	<table id="siteTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>build</th>
                <th>平台</th>
                <th>版本号</th>
				<th>更新说明</th>
				<th>下载地址</th>
				<th>发布时间</th>
            </tr>
        </thead>
    </table>
</div>
<div class="modal fade" data-backdrop="static" id="updateModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="exampleModalLabel">更新键值</h4>
			</div>
			<form id="updateForm" action="${context}/admin/site/option/update.do" method="post">
				<div class="modal-body">
					<div class="form-group">
						<p class="form-control-static" style="font-weight:bold;" id="keyTitle"></p>
						<input type="hidden" name="keyName" id="keyName"/>
					</div>
					<div class="form-group">
						<input type="text" name="keyValue" class="form-control" id="keyValue"/>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</form>
		</div>
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
	var table;
	$(document).ready(function() {
		table = spark.dataTable('#siteTable','${context}/admin/app/revision.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "platform" },
				{ "data": "revision" },
				{ "data": "update_remark" },
				{ "data": "download_url" },
				{ "data": "publish_time" }
			],
			"columnDefs":[
				{
					targets:[1],
					render:function(data,type,obj){
						if(obj.platform == '1'){
							return "<label class='label label-primary'>IOS</label>";
						}
						else {
							return "<label class='label label-success'>Android</label>";
						}
					}
				}
			],
			drawCallback:function(){
				
			}
		})
		
		$('#updateForm').ajaxForm({
			success:function(resp){
				spark.autoNotify(resp,true);
			}
		});
		
		$('#groupKey').change(function(){
			table.columns(0).search($(this).val()).draw();
		})
	} );
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />