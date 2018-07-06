<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="activity:index"/>
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
    <h3 class="page-title">
    		计量用户
    </h3>
	<#if (role !=26) >
    <form class="form-inline" role="form">
        <a href="${context}/admin/extension/create/group/-1.html" class="btn btn-success">添加计量用户</a>
        <div class="form-group">
            <label for="control-label">推广名称</label>
            <input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
        </div>
        <button type="button" class="btn btn-default">查找</button>
    </form>
    </#if>
    <table id="memberTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>推广编号</th>
            <th>推广名称</th>
            <th>KEY</th>
            <th>邀请总数量</th>
            <th>苹果端数量</th>
            <th>安卓端数量</th>
            <th>已借款人数</th>
            <th>放款总额</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="https://cdn.bootcss.com/clipboard.js/1.7.1/clipboard.min.js"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        var table = spark.dataTable('#memberTable', '${context}/admin/extension/jiliang/list.json', {
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "onlyKey"},
                {"data": "invitesum"},
                {"data": "ios_invite_sum"},
                {"data": "android_invite_sum"},
                {"data": "successBorrowMemberSum"},
                {"data": "successBorrowSum"},
            ],
            "columnDefs": [


                {
                    "targets": [8],
                    "data": 'id',
                    "render": function (data, type, full) {
                       if (${role}!=26){
                        return '<div class="dropdown"><a href="#" class="nodeco" data-toggle="dropdown">操作&nbsp;<span class="caret"></span><ul class="dropdown-menu"><li class="active"><a  class="a" data-clipboard-text="' + full.url + '" >复制链接</a></li><li class="active"><a href="${context}/admin/extension/create/group/' + data + '.html">编辑</a></li><li><a  data-toggle="ajax-link" data-tip="删除后不能恢复，确定继续吗？" data-href="${context}/admin/extension/group/delete/' + data + '.do">删除</a></li><li><a href="${context}/admin/extension/jiliangDetail/' + data + '.html">计量明细</a></li></ul></a></div>';
                   	   }else{
                   		return '<div class="dropdown"><a href="#" class="nodeco" data-toggle="dropdown">操作&nbsp;<span class="caret"></span><ul class="dropdown-menu"><li class="active"><a  class="a" data-clipboard-text="' + full.url + '" >复制链接</a></li><li><a href="${context}/admin/extension/jiliangDetail/' + data + '.html">计量明细</a></li></ul></a></div>';
                   	}
                    }
                }
            ],
            drawCallback: function () {
                spark.handleToggle('#memberTable');
            }
        })
        $('[data-toggle="table-search"]').change(function () {
            $('[data-toggle="table-search"]').each(function () {
                var index = parseInt($(this).attr('data-column'));
                table.columns(index).search($(this).val());
            })
            table.draw();
        });


        var clipboard1 = new Clipboard('.a');
        clipboard1.on('success', function (e) {
            console.log(e);
            alert("复制成功！")
        });
        clipboard1.on('error', function (e) {
            console.log(e);
            alert("复制失败！请手动复制")
        });

    });


</script>

</@override>
<@layout name="/admin/layout/main.ftl" />