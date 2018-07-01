<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="statistics:daily"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="${context}/admin/borrow/forFristAudit.html">统计管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${context}/admin/borrow/forFristAudit.html">每日统计</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <h3 class="page-title">
        统计列表
    </h3>
    <form class="form-inline" role="form">

        <div class="form-group">
            <div class="controls">
                <div id="reportrange" class="pull-left dateRange" style="width:200px">
                    <input type="text" data-toggle="table-search" id="searchDateRange" data-column="12"
                           class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-default">查找</button>
    </form>
    <table id="memberTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>统计编号</th>
            <th>每日新增</th>
            <th>借款次数</th>
            <th>放款总额</th>
            <th>续期次数</th>
            <th>续期费用</th>
            <th>产生佣金</th>
            <th>逾期次数</th>
            <th>逾期总额</th>
            <th>还款总额</th>
            <th>申请次数</th>
            <th>拒绝次数</th>
            <th>统计日期</th>
        </tr>
        </thead>
        <tfoot>
        <tr class="tfoot">
            <td>总计</td>
            <td class="member_register"></td>
            <td class="borrow_times"></td>
            <td class="borrow_amount"></td>
            <td class="renewal_times"></td>
            <td class="renewal_amount"></td>
            <td class="commision_amount"></td>
            <td class="overdue_count"></td>
            <td class="overdue_amount"></td>
            <td class="repay_amount"></td>
            <td class="apply_count"></td>
            <td class="rejuse_count"></td>
            <td class="add_time"></td>
        </tr>
        </tfoot>
    </table>

</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script src="${context}/asset/public/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        var datas = [];
        var table = spark.dataTable('#memberTable', '${context}/admin/daily/list.json', {
            "columns": [
                {"data": "id"},
                {"data": "member_register"},
                {"data": "borrow_times"},
                {"data": "borrow_amount"},
                {"data": "renewal_times"},
                {"data": "renewal_amount"},
                {"data": "commision_amount"},
                {"data": "overdue_count"},
                {"data": "overdue_amount"},
                {"data": "repay_amount"},
                {"data": "apply_count"},
                {"data": "rejuse_count"},
                {"data": "add_time"},
            ],

            /*var a=dataTable.compute("sum(member_register)",null),*/
            /*fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                datas.push(aData);
            },
            fnDrawCallback: function () {
                var counts = {
                    "member_register": 0,
                    "borrow_times": 0,
                    "borrow_amount": 0,
                    "renewal_times": 0,
                    "renewal_amount": 0,
                    "commision_amount": 0,
                    "overdue_count": 0,
                    "overdue_amount": 0,
                    "repay_amount": 0,
                    "apply_count": 0,
                    "rejuse_count": 0,

                };
                //先清空
                for(var k3 in counts){
                    $(".dataTables_scrollFootInner ."+k3).text('');
                }
                if(!datas.length){
                    return ;
                }
                for(var i=0;i<datas.length;i++){
                    var data=datas[i];
                    for(var k1 in counts){
                        counts[k1]+=parseInt(data[k1]);
                    }
                }
            },*/
            rowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                datas.push(aData);
                /* alert(nRow[0]);
                 alert(nRow[1]);
                 alert(aData[0]);
                alert(aData[1]);
                 alert(datas);*/
            }, "columnDefs": [],
            drawCallback: function () {
                spark.handleToggle('#memberTable');
                var counts = {
                    "member_register": 0,
                    "borrow_times": 0,
                    "borrow_amount": 0,
                    "renewal_times": 0,
                    "renewal_amount": 0,
                    "commision_amount": 0,
                    "overdue_count": 0,
                    "overdue_amount": 0,
                    "repay_amount": 0,
                    "apply_count": 0,
                    "rejuse_count": 0,
                };

                $(".dataTables_scrollFootInner ."+"member_register").text('');

                if (!datas.length) {
                    return;
                }
                var member_register = 0;
                var borrow_times = 0;
                var borrow_amount = 0;
                var renewal_times = 0;
                var renewal_amount = 0;
                var commision_amount = 0;
                var overdue_count = 0;
                var overdue_amount = 0;
                var repay_amount = 0;
                var apply_count = 0;
                var rejuse_count = 0;
                for (var i = 0; i <=datas.length - 1; i++) {
                    var data = datas[i];
                    console.log(data[0]);
                    var memberRegister = data.member_register;
                    var borrowTimes = data.borrow_times;
                    var borrowAmount = data.borrow_amount;
                    var renewalTimes = data.renewal_times;
                    var renewalAmount = data.renewal_amount;
                    var commisionAmount = data.commision_amount;
                    var overdueCount = data.overdue_count;
                    var overdueAmount = data.overdue_amount;
                    var repayAmount = data.repay_amount;
                    var applyCount = data.apply_count;
                    var rejuseCount = data.rejuse_count;
                    member_register = parseInt(memberRegister) + parseInt(member_register);
                    borrow_times = parseInt(borrow_times) + parseInt(borrowTimes);
                    borrow_amount = parseInt(borrow_amount) + parseInt(borrowAmount);
                    renewal_times = parseInt(renewal_times) + parseInt(renewalTimes);
                    renewal_amount = parseInt(renewalAmount) + parseInt(renewal_amount);
                    commision_amount = parseInt(commisionAmount) + parseInt(commision_amount);
                    overdue_count = parseInt(overdueCount) + parseInt(overdue_count);
                    overdue_amount = parseInt(overdueAmount) + parseInt(overdue_amount);
                    repay_amount = parseInt(repayAmount) + parseInt(repay_amount);
                    apply_count = parseInt(applyCount) + parseInt(apply_count);
                    rejuse_count = parseInt(rejuseCount) + parseInt(rejuse_count);

                   console.log(member_register);
                    console.log(borrow_times);
                    console.log(borrow_amount);
                    console.log(renewal_times);
                    console.log(renewal_amount);
                    console.log(commision_amount);
                    console.log(overdue_count);
                    console.log(overdue_amount);
                    console.log(repay_amount);
                    console.log(apply_count);
                    console.log(rejuse_count);

                }
                $('.member_register').html(member_register);
                $('.borrow_times').html(borrow_times);
                $('.borrow_amount').html(borrow_amount);
                $('.renewal_times').html(renewal_times);
                $('.renewal_amount').html(renewal_amount);
                $('.commision_amount').html(commision_amount);
                $('.overdue_amount').html(overdue_amount);
                $('.overdue_count').html(overdue_count);
                $('.repay_amount').html(repay_amount);
                $('.apply_count').html(apply_count);
                $('.rejuse_count').html(rejuse_count);
                //清空数据
                datas=[];
            }

        });
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