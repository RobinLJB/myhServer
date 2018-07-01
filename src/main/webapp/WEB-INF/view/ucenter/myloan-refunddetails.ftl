<@override name="head">
</@override>
<@override name="body">
    <div class="r_main">
		<div class="tabtil">
		<ul>
				<li><a href="${context}/ucenter/successborrow.html"></a>成功借款</li>
				<li><a href="${context}/ucenter/payingborrow.html">正在还款的借款</li>
				<li class="on">还款明细账</a></li>
				<li><a href="${context}/ucenter/borrowdetails.html">借款明细账</a></li>
				<li><a href="${context}/ucenter/finishborrow.html">已还完的借款</a></li>
			</ul>
        </div>
	<div class="box" >
        <div class="boxmain2">
        <div class="srh">
        <form action="queryAllDetails.html" id="searchForm" class="form-inline">
        发布时间：<input type="text" id="startTime" name="startTime" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        到 
        <input type="text" id="endTime" value="" name="endTime" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
        <span style="margin-left:20px;">关键字：</span><input type="text" class="form-control input-sm" name="title" value="" id="title" />
        <a href="javascript:void(0)" class="btn btn-primary btn-sm" id="btn_search">搜索</a> 
        <a href="javascript:void(0);" class="btn btn-default btn-sm" onclick="excels()">导出</a>
        <input type="hidden" name="curPage" id="pageNum" />
        </form>
        </div>
        <div class="biaoge">
		<table class="table table-bordered">
			<tr>
				<th>标题</th>
				<th>第几期</th>
				<th>应还款日期</th>
				<th>实际还款日期</th>
				<th>本期应还本息</th>
				<th>利息</th>
				<th>逾期罚款</th>
				<th>逾期天数</th>
				<th>还款状态</th>
				<th>操作</th>
			</tr>
		<tr> 
	    <td align="center"><a >【工程贷】14-GCD-002</a></td>
	    <td align="center">二</td>
	    <td align="center">2016-04-25</td>
	    <td align="center">2016-04-25</td>
	    <td align="center">￥50 </td>
	    <td align="center">￥32</td>
	    <td align="center">￥48</td>
	    <td align="center">25</td>
	    <td align="center">
	       未偿还
	    </td>
	    <td align="center">
	   <a >还款</a>
	    </td>
	  </tr>
    </table>      
    </div>
          
    <span id="my_pay_"></span>  
         
    </div>
</div>

    </div>
</@override>
<@override name="script">
  <script>
	$(document).ready(function() {
	$('.myloan').addClass('active').parents().show();
	$('.submeun-13 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />