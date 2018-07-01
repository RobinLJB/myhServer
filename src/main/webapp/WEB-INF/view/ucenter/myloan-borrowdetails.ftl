<@override name="head">
</@override>
<@override name="body">
    <div class="r_main">
      <div class="tabtil">
        <ul>
				<li><a href="${context}/ucenter/successborrow.html">成功借款</a></li>
				<li><a href="${context}/ucenter/payingborrow.html">正在还款的借款</a></li>
				<li><a href="${context}/ucenter/refunddetails.html">还款明细账</li>
				<li class="on">借款明细账</a></li>
				<li><a href="${context}/ucenter/finishborrow.html">已还完的借款</a></li>
			</ul>
        </div>
   
		<div class="box" >
        <div class="boxmain2">
         <div class="srh">
          <form action="queryBorrowInvestorInfo.do" id="searchForm" class="form-inline">
        投资者：
          <input type="text" class="form-control input-sm" id="investor" name="investor" value=""/>
          <a href="javascript:void(0)" class="btn btn-primary btn-sm" id="btn_search"> 搜索</a> 
          <input type="hidden" name="curPage" id="pageNum" />
          </form>
          </div>
         <div class="biaoge">
    <table class="table table-bordered">
		<tr>
		<th>投资者</th>
		<th>借入总额</th>
		<th>还款总额</th>
		<th>已还本金</th>
		<th>已还利息</th>
		<th>待还本金</th>
		<th>待还利息</th>
		</tr>

	   <tr>
	    <td align="center">abcd</td>
	    <td align="center"><strong>￥15000</strong></td>
	    <td align="center">￥15000</td>
	    <td align="center">￥15000</td>
	    <td align="center">￥50</td>
	    <td align="center">￥0.00</td>
	    <td align="center">￥0.00</td>
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