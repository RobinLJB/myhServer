<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="member:index" />
<input type="hidden" id="status" value="${status}" />
<div class="page-content">
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li>
				<i class="fa fa-home"></i>
				<a href="#">用户管理</a>
				<i class="fa fa-angle-right"></i>
			</li>
			<li>
				<a href="#">会员管理</a>
			</li>
		</ul>
		<div class="page-toolbar">
		</div>
	</div>
	<h3 class="page-title">
		会员列表
	</h3>
	<form class="form-inline" role="form">
		<div class="form-group">
			<label for="control-label">真实姓名</label>
			<input type="text" data-toggle="table-search" data-column="1" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<label for="control-label">手机号</label>
			<input type="text" data-toggle="table-search" data-column="2" class="form-control" placeholder="">
		</div>
		<div class="form-group">
			<div class="controls">
                <div id="reportrange" class="pull-left dateRange" style="width:200px">
					<input type="text" data-toggle="table-search"   id="searchDateRange" data-column="10" class="form-control" placeholder="点击选择日期" style="width: 100%;" readonly>
                </div>
            </div>
		</div>
		<button type="button" class="btn btn-default">查找</button>
        <button type="button" class="btn green btn-default btn-excel">导出</button>
	</form>
	<ul class="nav nav-tabs"     style="margin-bottom: 0px;border-bottom: 0px solid #ddd;" >
		<li id="a" ><a href="${context}/admin/member.html?status=0" aria-expanded="true">注册未申请</a></li>
		<li id="b" ><a href="${context}/admin/member.html?status=1" aria-expanded="false">正常用户</a></li>
		<li id="c" ><a href="${context}/admin/member.html?status=2" aria-expanded="false">争议用户</a></li>
		<li id="d" ><a href="${context}/admin/member.html?status=3" aria-expanded="false">黑名单</a></li>
	</ul>
	<table id="memberTable" class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>会员编号</th>
                <th>真实姓名</th>
                <th>手机号</th>
				<th>QQ</th>
				<th>已放款</th>
				<th>已还款</th>
				<th>已逾期</th>
				<th>死账</th>
				<th>用户类型</th>
				<th>推荐人</th>
				<th>注册时间</th>
				<th>操作</th>

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
<script>
	$(document).ready(function() {
		var table = spark.dataTable('#memberTable','${context}/admin/member/list/${status}.json',{
			"columns": [
				{ "data": "id" },
				{ "data": "real_name" },
				{ "data": "mobilePhone" },
				{ "data": "qqno" },
				{ "data": "alreadyBorrowSum" },
				{ "data": "alreadyRepaySum" },
				{ "data": "overdueSum" },
				{ "data": "dieSum" },
				{ "data": "member_status" },
				{ "data": "refferreal_name" },
				{ "data": "create_time" },
			],
			"columnDefs":[

			{
					"targets":[8],
					"data":'member_status',
					"render":function(data,type,full){
						console.log(data)
					    if(data == 2) return  '<span class="label label-info">争议用户</span>';
						else if(data == 3) return  '<span class="label label-danger">黑名单</span>';
						else return '<span class="label label-success">正常用户</span>';
					}
				},
				{
					"targets":[9],
					"data":'refferreal_name',
					"render":function(data,type,full){
						if(data == "" || data==null){
							 return  '无推荐';
						}else{
							return data;
						}
					}
				},

				{
					"targets":[11],
					"data":'id',
					"render":function(data,type,full){
						if(data>0){return '<a class="btn btn-xs btn-default" href="${context}/admin/member/'+data+'.html">查看</a><a class="btn btn-xs btn-danger"  href="javascript:void(0);" onclick="delMember('+data+')">删除</a>';}
						else return '';
					}
				}



			],
			drawCallback:function(){
				spark.handleToggle('#memberTable');
			}
		})
		$('[data-toggle="table-search"]').change(function(){
			$('[data-toggle="table-search"]').each(function(){
				var index = parseInt($(this).attr('data-column'));
				table.columns(index).search($(this).val());
			})
			table.draw();
		});$('.btn-excel').on('click', function() {
            var url = "/admin/member/excel.do";
            var params = "&searchDateRange=" + $("#searchDateRange").val();
            window.open(encodeURI(encodeURI(url+"?"+params)));
        });
	} );

	$(function(){
		var status=$("#status").val();
		if(status==0){
			$("#a").attr("class","active");
			$("#b").removeAttr("class");
			$("#c").removeAttr("class");
			$("#d").removeAttr("class");
		}else if(status==1){
			$("#b").attr("class","active");
			$("#a").removeAttr("class");
			$("#c").removeAttr("class");
			$("#d").removeAttr("class");
		}else if(status==2){
			$("#c").attr("class","active");
			$("#a").removeAttr("class");
			$("#b").removeAttr("class");
			$("#d").removeAttr("class");
		}else if(status==3){
			$("#d").attr("class","active");
			$("#a").removeAttr("class");
			$("#b").removeAttr("class");
			$("#c").removeAttr("class");
		}
	})
	
	    function delMember(data) {
    
    
     var msg = "确定操作？\n\n请确认！"; 
	 if (confirm(msg)==true){ 
　        location.href="${context}/admin/member/delete/"+data+".html";
	 }else{ 
	  return ; 
	 } 
	 
	 
	 
	 }
	
	
</script>
<script type="text/javascript">
    $(document).ready(function (){


                $('#reportrange').daterangepicker(
                        {
                            // startDate: moment().startOf('day'),
                            //endDate: moment(),
                            //minDate: '01/01/2012',    //最小时间
                            maxDate : moment(), //最大时间
                            dateLimit : {
                                days : 90
                            }, //起止时间的最大间隔
                            showDropdowns : true,
                            showWeekNumbers : false, //是否显示第几周
                            timePicker : false, //是否显示小时和分钟
                            timePickerIncrement : 1, //时间的增量，单位为分钟
                            timePicker12Hour : false, //是否使用12小时制来显示时间
                            ranges : {
                                //'最近1小时': [moment().subtract('hours',1), moment()],
                                '今日': [moment().startOf('day'), moment()],
                                '昨日': [moment().subtract(1,'days').startOf('day'), moment().subtract(1,'days').endOf('day')],
								'最近3日': [moment().subtract(2,'days'), moment()],
                                '最近7日': [moment().subtract(6,'days'), moment()],
                                '最近30日': [moment().subtract(29,'days'), moment()]
                            },
                            opens : 'right', //日期选择框的弹出位置
                            buttonClasses : [ 'btn btn-default' ],
                            applyClass : 'btn-small btn-primary blue',
                            cancelClass : 'btn-small',
                            format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式
                            separator : ' to ',
                            locale : {
                                applyLabel : '确定',
                                cancelLabel : '取消',
                                fromLabel : '起始时间',
                                toLabel : '结束时间',
                                customRangeLabel : '自定义',
                                daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                                        '七月', '八月', '九月', '十月', '十一月', '十二月' ],
                                firstDay : 1
                            }
                        }, function(start, end, label) {//格式化日期显示框

                            $('#searchDateRange').val(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')).change();
                       });


    })
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />
