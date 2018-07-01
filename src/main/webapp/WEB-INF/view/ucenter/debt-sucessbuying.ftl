<@override name="head">
</@override>
<@override name="body">
	<div class="r_main">
      <div class="tabtil">
        <ul><li><a href="${context}/ucenter/debtjoinbuying.html">参与购买的债权</a></li>
        <li class="on"><a>已成功购买的债权</a></li>
        </ul>
        </div>
<div class="box" >
	<div class="boxmain2">
	 <form id="searchForm" class="form-inline" action="querySucessBuyedDebt.html" >
	 <input type="hidden" name="curPage" id="pageNum"/>
	 <div class="srh">
	竞拍时间：<input type="text" name="startTime" value="" onclick="WdatePicker();"  class="form-control input-sm" /> 
	到 
	<input type="text"  name="endTime" value="" onclick="WdatePicker();"  class="form-control input-sm" /> 
	<span style="margin-left:20px;">标题：</span><input type="text" name="borrowTitle" value="" class="form-control input-sm" />
	<a href="javascript:void(0);" id="btn_search" class="btn btn-primary btn-sm">搜索</a>
	</div>
	</form>
 <div class="biaoge">
  <table class="table table-bordered table-striped">
  <tr>
    <th>借款人</th>
    <th>标题</th>
    <th>期限</th>
    <th>预期年化收益率</th>
    <th>债券期限</th>
    <th>债权金额</th>
    <th>竞拍底价</th>
    <th>竞拍价</th>
    <th>竞拍成功时间</th>
    <th>转让者</th>
    <th>操作</th>
    </tr>
  <tr>
    <td align="center">abcd</td>
    <td align="center">【工程贷】14-GCD-002</td>
    <td align="center">15个月</td>
    <td align="center">11%</td>
    <td align="center">8个月</td>
    <td align="center">3000</td>
    <td align="center">200</td>
    <td align="center">3000</td>
    <td align="center">2015-05-09</td>
    <td align="center">abcd</td>
    <td align="center"><a >查看</a></td>
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
	$('.submeun-15 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />