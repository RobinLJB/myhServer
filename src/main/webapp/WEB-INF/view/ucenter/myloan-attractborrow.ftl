<@override name="head">
</@override>
<@override name="body">
 <div class="r_main">
      <div class="tabtil">
        <ul>
        <li><a  href="${context}/ucenter/auditborrow.html">审核中的借款</a></li>
        <li class="on">招标中的借款</li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
        <div class="srh">
          <form action="homeBorrowAuditList.do" id="searchForm" class="form-inline">
	        发布时间：<input type="text" id="publishTimeStart"  name="publishTimeStart" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        到 
	        <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	        <span style="margin-left:10px;">关键字：</span><input id="titles" name="title" value="" type="text" maxlength="200" class="form-control input-sm" />
	        <a href="javascript:void(0);" id="search" class="btn btn-sm btn-primary"> 搜索</a>
        </form>
          </div>
         <div class="biaoge">
             <table class="table table-bordered">
  <tr>
    <th>标题</th>
    <th>类型</th>
    <th>还款方式</th>
    <th>金额（元）</th>
    <th>预期年化收益率</th>
    <th>期限</th>
    <th>发布时间</th>
    <th>进度</th>
    <th>状态</th>
    </tr>
	 <tr> 
		<td align="center"><a>【工程贷】14-GCD-002</a></td>
    	<td align="center">信用贷款 </td>
    	<td align="center">等额本息</td>
    	<td align="center">10000</td>
    	<td align="center">5%</td>
    	<td align="center">6个月</td>
    	<td align="center">2016-04-25</td>
    	<td align="center">52%</td>
    	<td align="center">  还款中</td>
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
	$('.submeun-12 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />