<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="menu:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">菜单管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div class="note note-success">
		<h3>后台系统菜单设置</h3>
		<p>更改后重新登录生效</p>
	</div>
	<div class="row">
		<div class="col-sm-4">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption font-green-sharp">
						<span class="caption-subject">菜单权限树</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="ztree" id="menuTree"></div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption font-green-sharp">
						<i class="icon-speech font-green-sharp"></i>
						<span class="caption-subject">菜单编辑</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<a href="javascript:;" id="btnSave" class="btn btn-circle green ">
							<i class="fa fa-edit"></i>保存</a>
							<a style="margin-left:10px" class="btn btn-circle btn-default " href="javascript:;" data-toggle="dropdown" aria-expanded="false">
							更多操作<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu pull-right">
								<li>
									<a href="javascript:;" id="addSubNode">
									<i class="fa fa-plus"></i>添加子菜单</a>
								</li>
								<li>
									<a href="javascript:;" id="addRootNode">
									<i class="fa fa-plus"></i>添加根菜单</a>
								</li>
								<li>
									<a href="javascript:;" id="deleteNode">
									<i class="fa fa-trash-o"></i>删除</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<form id="updateForm" action="${context}/admin/system/menu/edit.do" method="post">
						<input type="hidden" name="id" />
						<input type="hidden" name="parentId" />
						<div class="form-group form-md-line-input">
							<input type="text" name="title" class="form-control required" />
							<label>菜单名称</label>
						</div>
						<div class="form-group form-md-line-input">
							<input type="text" name="name" class="form-control required" />
							<label>权限标识</label>
						</div>
						<div class="form-group form-md-line-input">
							<select class="form-control required" name="type">
								<option value="1">用户菜单</option>
								<option value="2">权限菜单</option>
							</select>
							<label>菜单类型</label>
							<span class="help-block">权限菜单仅做权限控制，不显示在菜单树中</span>
						</div>
						<div class="form-group form-md-line-input">
							<input type="text" name="url" class="form-control" />
							<label>菜单链接</label>
						</div>
						<div class="form-group form-md-line-input">
							<input type="text" name="icon" class="form-control" />
							<label>菜单图标</label>
						</div>
						<div class="form-group form-md-line-input">
							<input type="text" name="sort" class="form-control required number" />
							<label>排序</label>
							<span class="help-block">同级菜单，序号越小越靠前</span>
						</div>
						<div class="form-group form-md-radios">
							<label>状态</label>
							<div class="md-radio-inline">
								<div class="md-radio">
									<input type="radio" value="1" id="radio1" name="status" class="md-radiobtn" checked>
									<label for="radio1">
									<span></span>
									<span class="check"></span>
									<span class="box"></span>
									显示</label>
								</div>
								<div class="md-radio">
									<input type="radio" value="2" id="radio2" name="status" class="md-radiobtn">
									<label for="radio2">
									<span></span>
									<span class="check"></span>
									<span class="box"></span>
									隐藏</label>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/zTree/js/jquery.ztree.all.min.js" type="text/javascript"></script>
<script>
	var setting = {
		data: {
			key: {
				name:"title",
				title:"",
				children: "subMenu",
				url:"",
				icon:""
			}
		},
		callback:{
			onClick:function zTreeOnClick(event, treeId, treeNode) {
				console.log(treeNode);
				$('#updateForm [name=id]').val(treeNode.id);
				$('#updateForm [name=parentId]').val(treeNode.parentId);
				$('#updateForm [name=title]').val(treeNode.title);
				$('#updateForm [name=name]').val(treeNode.name);
				$('#updateForm [name=sort]').val(treeNode.sort);
				$('#updateForm [name=url]').val(treeNode.url);
				$('#updateForm [name=icon]').val(treeNode.icon);
				$('#updateForm [name=type]').val(treeNode.type);
				$('label[for=radio'+treeNode.type+']').click();

			}
		}
	};
	
	function getSelectedNode(){
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
		var nodes = treeObj.getSelectedNodes();
		return nodes[0];
	}

	$(document).ready(function(){
		$.ajax({
			url:'${context}/admin/system/menu/list.do',
			success:function(data){
				console.log(data);
				$.fn.zTree.init($("#menuTree"), setting, data);
			}
		});
		$('#btnSave').click(function(){
			$('#updateForm').ajaxSubmit({
				success:function(resp){
					spark.autoNotify(resp,false);
				}
			});
		})
		$('#deleteNode').click(function(){
			var node = getSelectedNode();
			var treeObj = $.fn.zTree.getZTreeObj("menuTree");
			if(node == null){
				spark.alert('请先选择菜单!');
			}
			else{
				spark.confirm('确定要删除该菜单及其所有子菜单吗？',function(){
					$.ajax({
						type:'post',
						url:'${context}/admin/system/menu/delete.do',
						data:{id:node.id},
						success:function(resp){
							treeObj.removeNode(node);
							spark.autoNotify(resp,false);
						}
					})
				})
			}
		});

		$('#addSubNode').click(function(){
			var treeObj = $.fn.zTree.getZTreeObj("menuTree");
			var parentNode = getSelectedNode();
			if(parentNode !=null){
				var newNode = {parentId:parentNode.id,title:"子菜单",name:"",sort:255,status:1,type:1};
				newNode = treeObj.addNodes(parentNode, newNode);
			}
			else{
				spark.alert('请先选择父菜单!');
			}
		});
		$('#addRootNode').click(function(){
			var treeObj = $.fn.zTree.getZTreeObj("menuTree");
			var newNode = {title:"根菜单",sort:255,status:1,type:1};
			newNode = treeObj.addNodes(null, newNode);
		});
	});
	
</SCRIPT>
</@override>
<@layout name="/admin/layout/main.ftl" />