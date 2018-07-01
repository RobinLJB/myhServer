<@override name="head">
</@override>
<@override name="body">
   <div class="r_main">
		<div class="tabtil">
			<ul>
				<li class="on">成功借款</li>
				<li><a href="${context}/ucenter/payingborrow.html">正在还款的借款</a></li>
				<li><a href="${context}/ucenter/refunddetails.html">还款明细账</a></li>
				<li><a href="${context}/ucenter/borrowdetails.html">借款明细账</a></li>
				<li><a href="${context}/ucenter/finishborrow.html">已还完的借款</a></li>
			</ul>
        </div>
		<div class="box">
        <div class="boxmain2">
           <div class="srh">
           <form action="queryMySuccessBorrowList.html" id="searchForm" class="form-inline">
        <label>发布时间：</label><input type="text" id="startTime" name="startTime" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        到 
        <input type="text" id="endTime" value="" name="endTime" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        <span style="margin-left:5px;">关键字：</span><input type="text" class="form-control input-sm" name="title" value="" id="title_s" />
        <a href="javascript:void(0)" class="btn btn-sm btn-primary" id="btn_search">搜索</a> 
        <a href="javascript:void(0)" class="btn btn-sm btn-default" onclick="excelout()">导出</a>
		</div>
        <input type="hidden" name="curPage" id="pageNum" />
        </form>
        <div class="biaoge">
	<table  class="table table-bordered">
   <tr>
    <th>标题</th>
    <th>借款类型</th>
    <th>借款金额</th>
    <th>预期年化收益率</th>
    <th>还款期限</th>
    <th>借款时间</th>
    <th>应还本息</th>
    <th>已还本息</th>
    <th>未还本息</th>
    <th>状态</th>
    </tr>
    <tr>
	    <td align="center"><a>【工程贷】14-GCD-002</a></td>
	    <td align="center">医设贷
		</td>
	    <td align="center">10000元</td>
	    <td align="center">4%</td>
	    <td align="center">8
	      个月
	    </td>
	    <td align="center">2016-04-25</td>
	    <td align="center">2016-04-25</td>
	    <td align="center">￥54</td>
	    <td align="center">￥85</td>
	    <td align="center">已还款</td>
	</tr>
    </table>
    </div>
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