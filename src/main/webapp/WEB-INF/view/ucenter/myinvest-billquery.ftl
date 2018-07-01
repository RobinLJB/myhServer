<@override name="head">
</@override>
<@override name="body">
 <div class="r_main">
      <div class="tabtil">
        <ul><li id="lab_1"><a  href="${context}/ucenter/investlist.html">成功借出</a></li>
        <li id="lab_2"><a  href="${context}/ucenter/attractborrow.html">招标中的借款</a></li>
        <li id="lab_3"><a  href="${context}/ucenter/recycleinborrow.html">回收中的借款</a></li>
        <li id="lab_4"><a  href="${context}/ucenter/endrecycleinborrow.html">已回收的借款</a></li>
        <li id="lab_5" class="on">回账查询</li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
        <div class="srh"><strong class="blue">个人贷款：</strong></div>
        <div class="biaoge" style="margin-top:0px;">
<table class="table table-bordered">
  <tr>
    <th>&nbsp;</th>
    <th>未来一个月</th>
    <th>未来三个月</th>
    <th>未来一年</th>
    <th>全部</th>
    </tr>
  <tr>
    <td align="center">待收本息</td>
    <td align="center" id="allForPIOneMonth">￥0.00</td>
    <td align="center" id="allForPIThreeMonth">￥0.00</td>
    <td align="center" id="allForPIYear">￥0.00</td>
    <td align="center" id="allForPI">￥0.00</td>
    </tr>
    </table>
          <p>&nbsp;</p>
          </div>
         <div class="srh">
        <form action="homeBorrowBackAcountList.html" id="searchForm" class="form-inline">
	        <input type="hidden" name="curPage" id="pageNum" />
	        发布时间：<input type="text" id="publishTimeStart"  name="publishTimeStart" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        到 
	        <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	        <label>关键字：</label><input id="titles" name="title" value="" type="text" class="form-control input-sm" />
			<a href="javascript:void(0);" id="search" class="btn btn-primary btn-sm"> 搜索</a>
        </form>
        </div>
        <div class="biaoge" id="content">
        <table class="table table-bordered">
	<tr>
    <th>借款者</th>
    <th>标题</th>
    <th>借款类型</th>
    <th>预期年化收益率</th>
    <th>期限</th>
    <th>投资金额</th>
    <th>已收金额</th>
    <th>待收金额</th>
    <th>查看协议</th>
    </tr>
  
	 <tr>
	    <td align="center">abc</td>
		<td align="center"><a>【工程贷】14-GCD-002</a></td>
    	<td align="center">
    	理财项目
        </td>
    	<td align="center">8.2%</td>
    	<td align="center">5
    	个月
    	</td>
    	<td align="center">1500</td>
    	<td align="center">1000</td>
    	<td align="center">500</td>
    	<td align="center">
    	<a >查看协议</a>
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