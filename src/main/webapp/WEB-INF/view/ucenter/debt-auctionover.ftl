<@override name="head">
</@override>
<@override name="body">
	 <div class="r_main">
      <div class="tabtil">
        <ul>
        <li><a href="${context}/ucenter/debttransfer.html">可以转让的借款</a></li>
        <li><a href="${context}/ucenter/debtauction.html">竞拍中的债权</a></li>
        <li class="on"><a>竞拍结束的债权</a></li>
        <li><a href="${context}/ucenter/debtfailure.html">转让失败的债权</a></li>
        </ul>
        </div>
<div class="box" >
        <div class="boxmain2">
        <form id="searchForm" action="queryAuctedDebt.html" class="form-inline">
         <input type="hidden" name="curPage" id="pageNum"/>
			<div class="srh">
			借款人：
			<input type="text" name="borrowerName" value="" class="form-control input-sm" /><label>标题：</label>
			<input type="text" name="borrowTitle" value="" class="form-control input-sm" />
			<a href="javascript:void(0);" id="btn_search" class="btn btn-primary btn-sm"> 搜索</a></div>
        </form>
        <div class="biaoge">
        <table class="table table-bordered">
		<tr>
			<th>借款人</th>
			<th>标题</th>
			<th>预期年化收益率</th>
			<th>债券期限</th>
			<th>投资金额</th>
			<th>转让金额</th>
			<th>竞拍底价</th>
			<th>竞拍最高价</th>
			<th>竞拍者</th>
			<th>结束时间</th>
			<th>状态</th>
			<th>协议书</th>
		</tr>
		  <tr> 
		    <td align="center">abcd</td>
			<td align="center"><a >【工程贷】14-GCD-002</a></td>
			<td align="center">12%</td>
		    <td align="center">6个月</td>
		    <td align="center">10000</td>
		     <td align="center">$10000</td>
		    <td align="center">$200</td>
		    <td align="center">$10000</td>
		    <td align="center">dada</td>
		    <td align="center">2016-04-25</td>
		    <td align="center">竞拍成功
			<td align="center">
		    	   <a >转让协议 </a>
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