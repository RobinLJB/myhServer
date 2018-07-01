<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:relation"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="#">用户管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">用户推荐关系</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <h3 class="page-title">
        推荐列表
    </h3>
    <form class="form-inline" role="form">
        <!--<div class="form-group">
            <label for="control-label">用户名</label>
            <input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
        </div>-->
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
    <table id="memberTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>会员编号</th>
            <th>邀请码</th>
            <th>真实姓名</th>
            <th>手机号</th>
            <th>推荐数量</th>
            <th>推荐佣金</th>
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
    $(document).ready(function () {
        var table = spark.dataTable('#memberTable', '${context}/admin/member/relation/list.json', {
            "columns": [
                {"data": "id"},
                {"data": "memberNo"},
                {"data": "real_name"},
                {"data": "mobilePhone"},
                {"data": "invateSum"},
                {"data": "commisionSum"},
            ],
            "columnDefs": [
                {
                    "targets": [6],
                    "data": 'id',
                    "render": function (data, type, full) {
                        /*if (data > 0) {
                            return '<a class="btn btn-xs btn-default" href="${context}/admin/member/mrefferMember/' + data + '.html">查看</a>';
                        }*/
                        if (){}return '<a class="btn btn-xs btn-default" href="${context}/admin/member/mrefferMember/' + data + '.html">认领</a>';
                        /*else return '';*/
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
        })
    });
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />