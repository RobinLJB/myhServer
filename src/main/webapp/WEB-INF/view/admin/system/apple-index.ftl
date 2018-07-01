<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:forIphoneAudit"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="${context}/admin/borrow/forIphoneAudit.html">管理列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${context}/admin/borrow/forIphoneAudit.html">苹果账号管理</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <h3 class="page-title">
        苹果账户列表
    </h3>
    <form class="form-inline" role="form">
        <div class="form-group">
            <label for="control-label">icloud账号</label>
            <input type="text" data-toggle="table-search" data-column="3" class="form-control" placeholder="">
        </div>

        <button type="button" class="btn btn-default">查找</button>
    </form>
    <button type="button" id="uploadExcel" class="btn btn-primary" data-btn-type="selectIcon">
        <i class="fa fa-mail-forward">&nbsp;上传Excel</i>
    </button>

    <table id="memberTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>姓名</th>
            <th>手机号</th>
            <th>身份证号</th>
            <th>账号</th>
            <th>密码</th>
            <th>使用状态</th>
            <th>操作</th>
            <!--<th>后面加操作</th>-->
        </tr>
        </thead>
    </table>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.ocupload-1.1.2.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        var table = spark.dataTable('#memberTable', '${context}/admin/apple/excel/list.json', {
            "columns": [
                {"data": "real_name"},
                {"data": "mobile_phone"},
                {"data": "ident_no"},
                {"data": "apple_id"},
                {"data": "apple_pass"},
                {"data": "status"},
            ],
            "columnDefs": [
                {
                    "targets": [5],
                    "data": 'status',
                    "render": function (data, type, full) {
                        if (data == 1) {
                            return '<span class="label  label-danger">已使用</span>';
                        }
                        else return '<span class="label label-success">未使用</span>';
                    }
                }, {
                    "targets": [6],
                    "data": 'apple_id',
                    "render": function (data, type, full) {
                            return '<a class="btn btn-xs btn-danger" href="${context}/admin/apple/excel/delete/' + data + '.html">删除</a>';
                    }
                }],
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
<script type="text/javascript">
    //调用OCUpload插件的方法
    $("#uploadExcel").upload({
        action: "${context}/admin/apple/excel/upload.html", //表单提交的地址
        name: "myFile",
        onComplete: function (data) { //提交表单之后
            if (data == "0") {
                alert("导入成功");
                spark.redirect('${context}/admin/apple/excel/show.html');
                spark.notify(resp.message, '导入成功');//提示框，Excel导入成功
            } else {
                spark.redirect('${context}/admin/apple/excel/show.html');
                spark.notify(resp.message, '导入失败'); //提示框，Excel导入成失败
            }
        },
        onSelect: function () {//当用户选择了一个文件后触发事件
            //当选择了文件后，关闭自动提交
            this.autoSubmit = false;
            //校验上传的文件名是否满足后缀为.xls或.xlsx
            var regex = /^.*\.(?:xls|xlsx)$/i;
            //this.filename()返回当前选择的文件名称 (ps：我使用这个方法没好使，自己写了一个获取文件的名的方法) $("[name = '"+this.name()+"']").val())
            //alert(this.filename());
            if (regex.test($("[name = '" + this.name() + "']").val())) {
                //通过校验
                this.submit();
            } else {
                alert("请选择正确的excel文件？"); //错误提示框，文件格式不正确，必须以.xls或.xlsx结尾
            }
        }
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {


        $('#reportrange').daterangepicker(
                {
                    // startDate: moment().startOf('day'),
                    //endDate: moment(),
                    //minDate: '01/01/2012',    //最小时间
                    maxDate: moment(), //最大时间
                    dateLimit: {
                        days: 90
                    }, //起止时间的最大间隔
                    showDropdowns: true,
                    showWeekNumbers: false, //是否显示第几周
                    timePicker: false, //是否显示小时和分钟
                    timePickerIncrement: 1, //时间的增量，单位为分钟
                    timePicker12Hour: false, //是否使用12小时制来显示时间
                    ranges: {
                        //'最近1小时': [moment().subtract('hours',1), moment()],
                        '今日': [moment().startOf('day'), moment()],
                        '昨日': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                        '最近3日': [moment().subtract(2, 'days'), moment()],
                        '最近7日': [moment().subtract(6, 'days'), moment()],
                        '最近30日': [moment().subtract(29, 'days'), moment()]
                    },
                    opens: 'right', //日期选择框的弹出位置
                    buttonClasses: ['btn btn-default'],
                    applyClass: 'btn-small btn-primary blue',
                    cancelClass: 'btn-small',
                    format: 'YYYY-MM-DD', //控件中from和to 显示的日期格式
                    separator: ' to ',
                    locale: {
                        applyLabel: '确定',
                        cancelLabel: '取消',
                        fromLabel: '起始时间',
                        toLabel: '结束时间',
                        customRangeLabel: '自定义',
                        daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                            '七月', '八月', '九月', '十月', '十一月', '十二月'],
                        firstDay: 1
                    }
                }, function (start, end, label) {//格式化日期显示框

                    $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')).change();
                });


    })
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />