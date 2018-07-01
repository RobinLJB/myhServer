<@override name="head">
</@override>
<@override name="body">
    <div class="r_main">
    	<div class="ci-title">
            <div class="ci-title-inner">
                <h2>投资列表</h2>
                <b class="line"></b>
            </div>
        </div>
        <div class="tabtil">
            <ul>
                <li id="lab_2" <#if status = 2>class="on"</#if> ><a href="${context}/ucenter/invest/tendering.html">招标中</a></li>
                <li id="lab_3" <#if status=4>class="on"</#if> ><a  href="${context}/ucenter/invest/repaying.html">回款中</a></li>
                <li id="lab_4" <#if status=5>class="on"</#if> ><a  href="${context}/ucenter/invest/completed.html">已还清</a></li>
            </ul>
        </div>
		<div class="box">
        <div class="boxmain2">
        <div class="biaoge">
        <table class="table table-bordered">
    		<tr>
    		<th>项目名称</th>
            <th>借款金额</th>
            <th>年利率</th>
    		<th>还款方式</th>
            <th>期限</th>
    		<th>投标金额</th>
    		<th>投资时间</th>
    		<th>项目进度</th>
		</tr>
    	<#if investList??>
    	<#list  investList as list>
    	<tr>
    		<td align="center"><a>${list.title!}</a></td>
        	<td align="center">${list.loanAmount!}</td>
        	<td align="center">${list.annual_rate!}%</td>
            <td align="center">${list.payment_mode!}</td>
        	<td align="center">${list.cycle !}<#if list.cycle_type=="1">个月<#else>天</#if>
        	</td>
            <td align="center">${list.amount}</td>
        	<td align="center">${list.invest_time!}</td>
        	<td align="center">
        	   ${list.progress_scale!0}%
        	</td>
        </tr>
        </#list >
    	<#else>
    	<tr><td align="center">暂无数据</td></tr>
    	</#if>
    </table>
<@pagination curPage="${page.currentPage}" click="changePage" pageSize="${page.pageSize}" totalRecord="${page.totalRecord}"/>
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
	
	$('#bt_searchaa').click(function(){
	    changePage();
	});

  	function changePage(curPage,pageSize){
  		var param =  {};
  		param["curPage"] = curPage||1;
  		if(pageSize){
  			param["pageSize"] = pageSize;
  		}
		param["startTime"]=$("#startTime").val();
		param["endTime"]=$("#endTime").val();
		param["type"]=$("#type").val();
		$.post("queryFundrecordList.html",param,function(data){
			$("#fundRecord").html(data);
		});
	};

</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />