<@override name="head">
<link href="${context}/asset/public/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css"/>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="role:detail" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<a href="#">系统管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="${context}/admin/role.html">角色管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">角色权限</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<div style="margin:10px 0;">
		<input id="btnSave" type="button" class="btn btn-success" value="保存" />
	</div>
	<div id="rightsTree">
	
	</div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jstree/jstree.min.js" type="text/javascript"></script>
<script>
	var oldRights = ${rights};
	
	function isChecked(ruleId){
		for(var i=0;i<oldRights.length;i++){
			if(oldRights[i]['id'] == ruleId)return true;
		}
		return false;
	}
	
	function parseMenu(menus){
		var list = [];
		$.each(menus,function(i,menu){
			var menuItem = {text:menu.title,li_attr:{id:menu.id}};
			var state = {};
			if(menu.subMenu.length > 0){
				menuItem.children = parseMenu(menu.subMenu);
				state.opened = true;
			}
			if(isChecked(menu.id)){
				state.selected = true;
			}
			menuItem.state = state;
			list.push(menuItem);
		});
		return list;
	}
	
	$(function(){
		var menus = parseMenu(${menus});
		$('#rightsTree').jstree({
            'plugins': ["checkbox", "types"],
            'checkbox':{"three_state":false,"cascade":"up"},
            'core': {
                "themes" : {
                    "responsive": false
                },
                'data': menus
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder icon-state-warning icon-lg"
                },
                "file" : {
                    "icon" : "fa fa-file icon-state-warning icon-lg"
                }
            }
        });
		
        function contains(arr,val){
        	for(var i=0;i<arr.length;i++){
			   if(arr[i] == val)return true;
			}
			return false;
        }

		$('#btnSave').click(function(){
			var nodes = $('#rightsTree').jstree().get_selected()
			$.ajax({
				url:'${context}/admin/role/rights/${id}.do',
				type:'post',
				data:{'rights':nodes.join(',')},
				success:function(resp){
					if(resp.code == 0){
						spark.notify(resp.message);
					}
					else spark.notify(resp.message,'error');
				}
			});
		});
	})
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />