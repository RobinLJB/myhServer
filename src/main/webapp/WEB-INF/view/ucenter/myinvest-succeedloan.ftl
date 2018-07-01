<@override name="head">
</@override>
<@override name="body">
 <div class="r_main">
      <div class="tabtil">
         <ul><li id="lab_1"     class="on">成功借出</li>
        <li id="lab_2"><a  href="${context}/ucenter/attractborrow.html">招标中的借款</a></li>
        <li id="lab_3"><a  href="${context}/ucenter/recycleinborrow.html">回收中的借款</a></li>
        <li id="lab_4"><a  href="${context}/ucenter/endrecycleinborrow.html">已回收的借款</a></li>
        <li id="lab_5"><a  href="${context}/ucenter/billquery.html">回账查询</a></li>
        </ul>
        </div>
<div class="box">
        <div class="boxmain2">
         <div class="srh">
          <form action="homeBorrowInvestList.html" id="searchForm" class="form-inline">
			
	        发布时间：<input type="text" id="publishTimeStart"  name="publishTimeStart" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
	        到 
	        <input type="text" id="publishTimeEnd" name="publishTimeEnd" value="" class="form-control input-sm" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	        <span style="margin-left:20px;">关键字：</span><input id="titles" name="title" value="" type="text" maxlength="200" class="form-control input-sm" />
	        <a href="javascript:void(0);" id="search" class="btn btn-primary btn-sm"> 搜索</a>
        </form>
        </div>
        <div class="biaoge" >
		  <table class="table table-bordered">
		  <tr>
		    <th>借款人</th>
		    <th>标题</th>
		    <th>类型</th>
		    <th>还款方式</th>
		    <th>我的投标金额（元）</th>
		    <th>预期年化收益率</th>
		    <th>期限</th>
		    <th>发布时间</th>
		 <th>协议书</th>
		    </tr>
		  	
			 <tr> 
			    <td align="center">abcd</td>
				<td align="left"><a href="<c:url value='/financeDetail.html?id=138'/>" target="_blank">【工程贷】14-GCD-002</a></td>
		    	<td align="center">
		    	   实验贷
		        </td>
		    	<td align="center">
		    	先息后本
		    	</td>
		    	<td align="center">100</td>
		    	<td align="center">11.8%</td>
		    	<td align="center">8
		    	个月
		    	</td> 
		    	<td align="center">2016-04-25</td>
		    	
		    	<td align="center"><a>查看协议</a></td> 
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