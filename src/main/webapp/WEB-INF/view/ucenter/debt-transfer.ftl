<@override name="head">
</@override>
<@override name="body">
	<div class="r_main">
		<div class="tabtil">
			<ul>
				<li class="on"><a>可以转让的借款</a></li>
				<li><a href="${context}/ucenter/debtauction.html">竞拍中的债权</a></li>
				<li><a href="${context}/ucenter/debtauctionover.html">竞拍结束的债权</a></li>
				<li><a href="${context}/ucenter/debtfailure.html">转让失败的债权</a></li>
			</ul>
        </div>
		<div class="box">
		<div class="boxmain2">
		<div id="zrzq_div" class="tcbox" style="display: none;">
			<div class="tcmain">
				<p class="zqsm">
				简单的债权说明
				</p>
				<div id="debt_add" class="xzzl"></div>
			</div>
		</div>
        <form id="searchForm" class="form-inline" action="queryCanAssignmentDebt.html">
         	<input type="hidden" name="curPage" id="pageNum"/>
         <div class="srh">
        借款人：
          <input type="text" name="borrowerName" value="" class="form-control input-sm" /><span style="margin-left:20px;">标题：</span>
          <input type="text" name="borrowTitle" value="" class="form-control input-sm" />
        <a href="javascript:void(0);" id="btn_search" class="btn btn-primary btn-sm">搜索</a></div>
        </form>
	<div class="biaoge">
	<table class="table table-bordered table-striped">
		<tr>
			<th>借款人</th>
			<th>标题</th>
			<th>期限</th>
			<th>发布时间</th>
			<th>预期年化收益率</th>
			<th>债券期限</th>
			<th>投资金额</th>
			<th>已收金额</th>
			<th>待收金额</th>
			<th>状态</th>
			<th>竞拍时间</th>
			<th>操作</th>
		</tr>
		<tr>
	    <td align="center">abcd</td> 
	    <td align="center"><a >【工程贷】14-GCD-002</a></td>
	    <td align="center">8个月</td>
	    <td align="center">2016-04-25</td>
	    <td align="center">6%</td>
	    <td align="center">5个月</td>
	    <td align="center">10000</td>
	    <td align="center">5000</td>
	    <td align="center">50000</td>
	    <td align="center">
	    	--
	    </td>
	    <td align="center">
	  	--
	    </td>
	    <td align="center">
	    		<a >债权转让</a>

	    </td>
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
	$('.debt').addClass('active').parents().show();
	$('.submeun-14 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />