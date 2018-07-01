<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="article:index" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">内容管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">文章分类</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		文章分类
	</h3>
 
	<form id="categoryForm" action="${context}/admin/cms/article/category/update.do" class="form-inline" role="form">
		<@token name="CATEGORY_DETAIL" />
		<div class="form-group">
			<label for="title">分类名称:</label>
			<input type="text" class="form-control" id="name" name="name" placeholder="请输入分类名称">
		</div>		 
		<button type="submit"   class="btn btn-success">添加分类</button>
		 
	</form>	
	<br/><br/>
	<table id="categoryTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                 
                <th>分类编号</th>
				<th>分类名称</th>
				<th>操作</th>
            </tr>
        </thead>
		<tbody>
		<#if cates?exists>
			<#list cates?keys as key>
			<tr><td>${key}</td><td>${cates[key]}</td><td><a href="${context}/admin/cms/article/list/${key}.html" class="btn btn-success">文章列表</a></td></tr>
			</#list>
		</#if>
		</tbody>
    </table>
	 
 
	 
	
</div>
</@override>
<@override name="script">
 
</@override>
<@layout name="/admin/layout/main.ftl" />