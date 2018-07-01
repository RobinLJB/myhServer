<@override name="head">
</@override>
<@override name="body">
  <div class="r_main">
      <div class="tabtil">
        <ul><li id="lab_1"><a  href="${context}/ucenter/investlist.html">成功借出</a></li>
        <li id="lab_2"><a  href="${context}/ucenter/attractborrow.html">招标中的借款</a></li>
        <li id="lab_3"><a  href="${context}/ucenter/recycleinborrow.html">回收中的借款</a></li>
        <li id="lab_4"  class="on">已回收的借款</li>
        <li id="lab_5"><a  href="${context}/ucenter/billquery.html">回账查询</a></li>
        </ul>
        </div>
		<div class="box">
        <div class="boxmain2">
         <div class="srh">
          <form action="homeBorrowRecycleList.html" id="searchForm" class="form-inline">
			<input type="hidden" name="curPage" id="pageNum" />
			<label>关键字：</label><input id="titles" name="title" value="" type="text" class="form-control input-sm" />
			<a href="javascript:void(0);" id="search" class="btn btn-primary btn-sm"> 搜索</a>
        </form>
        </div>
        <div class="biaoge">
        <table class="table table-bordered">
	<tr>
    <th>借款人</th>
    <th>标题</th>
    <th>类型</th>
    <th>信用等级</th>
    <th>预期年化收益率</th>
    <th>期限</th>
    <th>投资金额</th>
    <th>已收金额</th>
    <th>操作</th>
    </tr>
	 <tr>
	    <td align="center">abcd</td>
		<td align="center"><a >【工程贷】14-GCD-002</a></td>
    	<td align="center">
    	信用贷款
        </td>
    	<td align="center">88分</td>
    	<td align="center">11.8%</td>
    	<td align="center">8
    	个月
    	<td align="center">1000</td>
    	<td align="center">500</td>
    	<td align="center">
    	   <a>查看详情</a>
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
	$('.myinvest').addClass('active').parents().show();
	$('.submeun-9 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />