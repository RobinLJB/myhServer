<@override name="head">
	<link href="${context}/asset/public/css/jquery.Jcrop.min.css" rel="stylesheet" type="text/css">
	<link href="${context}/asset/public/plugins/layer-v2.4/skin/layer.css" rel="stylesheet" type="text/css">
	<style>
		#imgPreview {
			height: 100px;
			margin-bottom: 10px;
		}
		#headImg{width:100%}
		.btn-upload {
			position: relative;
		}
		
		.btn-upload input[type="file"] {
			position: absolute;
			opacity: 0;
			left: 0;
			top: 0;
			right: 0;
			bottom: 0;
			width: 100%;
			margin: 0;
			padding: 0;
			cursor: pointer;
		}
		
		.menu {
			border-right: none;
		}
	</style>
</@override>

<@override name="body">

	<div class="box">

		<div class="zhuye-zijin" style="height: 140px;    display: table;    padding: 0 10px 0 25px;">
			<div class="pic" >
			<#if  Session.MEMBER.avatar??&&Session.MEMBER.avatar!="">
             <img class="pointer" id="headImg" src="${context}${Session.MEMBER.avatar!}"   title="点击更换头像" />
            <#else>
				<img class="pointer" id="headImg" src="${context}/asset/ucenter/img/default-user.png" width="100" height="100" title="点击更换头像" />
            </#if>

			</div>
			<div class="zijin-right">
				<div class="zijin-title-left">

					<span class="userName">${Session.MEMBER.username!}</span>&nbsp;&nbsp;
					<div class="zijin-ico">
						<a href="${context}/ucenter/finance/sinapage.html" target="_blank" title="登录新浪托管账户"><span class="ico-realname"></span></a>
						<#if Session.MEMBER.mobilePhone??>
							<a href="${context}/ucenter/safety/mobile.html" title="绑定手机"><span class="ico-phone"></span></a>
							<#else>
								<a href="boundcellphone.html" title="绑定手机">
									<span class="ico-phone-null"></span></a>
						</#if>

						<#if Session.MEMBER.email??>
							<a href="javascript:void(0);" title="绑定邮箱"><span class="ico-email"></span></a>
							<#else>
								<a href="${context}/ucenter/safety/email.html" title="绑定邮箱"><span class="ico-email-null"></span></a>
						</#if>
						<div style="float: right;padding-left: 18px;line-height: 30px;">
							<a style="color: white;background: #a3cdeb;display: inline-block;height:31px;width:50px;border-radius: 8px;" href="${context}/ucenter/logout.html"><span class="uses">退出</span></a>

						</div>
					</div>

				</div>

			</div>
			<div class="zijin-right1">

				<a class="ancz" href="${context}/ucenter/finance/recharge.html" target="_self" style="margin-right:23px;">充值</a>
				<a class="antx" href="${context}/ucenter/finance/withdraw.html" target="_self">提&nbsp;&nbsp;现</a>

			</div>
		</div>
		<div class="HOME">
			<div class="zijin-right-content">
				<div class="ASSET">
					<span class="money">${finance.total?string("#,##0.00")}</span>
					<p>总资产（元）</p>
				</div>
				<div class="mymoney">
					<div class="avalible count">
						<span class="money">${finance.usableBalance?string("#,##0.00")}</span>
						<p>可用金额（元）</p>
					</div>

					<div class="count">
						<span class="money">${finance.freezeBalance?string("#,##0.00")}</span>
						<p>冻结金额（元）</p>
					</div>
					<div class="count">
						<span class="money">${finance.dueinSum?string("#,##0.00")}</span>
						<p>待收金额（元）</p>
					</div>

				</div>

			</div>
			<#if (user.merits> 0) >
				<div class="chart" style="float: left;width: 34%;margin-top: 59px;">
					<img style="vertical-align: middle;height: 154px;" src="${context}/asset/front/images/gxz.png" />
					<p style="text-align: center;font-size: 22px;color: #391d0a;">第${user.merits!0}枚</p>
				</div>
			</#if>
			<!--	<div class="chart" id="pieChart" ></div>-->
		</div>
		<!-- 收益统计 -->
		<div role="tabpanel" class="tab-pane active" id="investment">
			<div class="navli" style="clear:both;">
				<a href="#investment" style="color: black;">近3个月内的收益统计</a>
			</div>
			<div id="main" style="width: 800px;height:400px;"></div>

		</div>
		<!-- 我的投资 -->
		<div role="tabpanel" class="tab-pane active" id="investment">
			<div class="navli" style="clear:both;">
				<a href="#investment" style="color: black;">最近待收款</a>
			</div>
			<table class="table table-bordered" align="center">
				<thead>
					<tr>
						<th>项目名称</th>
						<th>待还金额</th>
						<th>本金利息</th>
						<th>期数</th>
						<th>还款日期</th>
					</tr>
				</thead>
				<tbody>
					<#if investlist?? && (investlist?size> 0)>
						<#list investlist as item>
							<tr>
								<td>${item.title!}</td>
								<td>${(item.principal?number+item.interest?number)!}</td>
								<td>${item.principal!}/${item.interest!}</td>
								<td>${item.repayPeriod!}</td>
								<td>${item.repayDate!}</td>
							</tr>
						</#list>
						<#else>
							<tr>
								<td colspan=5>暂无数据</td>
							</tr>
					</#if>
				</tbody>
			</table>
			<!----
			<div class="navli" style="clear:both;">
                <a href="#investment" style="color: black;">最近待还款</a>		
		  	</div>
				<table class="table-tongji" >

					<tr class="table-title"><td>总借出金额</td><td>总借出笔数</td><td>已回收本息</td><td>待回收本息</td><td>待回收笔数</td></tr>
					<tr>
					<td>￥0.00</td>
					<td>￥0.00</td>
					<td>￥0.00</td>
					<td>￥0.00</td>
					<td>￥0.00</td>
					</tr>
				</table>
			---->
		</div>

	</div>

	<!-- 头像上传模态框 -->
	<div class="modal fade" id="headerModal" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document" style="background-color:#fff;">
        <div class="modal-header">
         <div class="modal-content">
         <div class="js-fileapi-wrapper upload-btn" id="choose"> 
            <input name="files" type="file" />         
          </div> 
        </div>
       
      <div class="modal-body">   
      <div id="images">          
      <p style="margin-top: 40px;">
      </p>
      <table class="table table-striped">
         <tbody>
           <tr>
              <td><div id="img2" ></div></td>
              <td>预览:<div id="img3"></div></td>                 
           </tr>
        </tbody>
      </table>
      </div>
      </div>
      
      <div class="modal-footer">
           <button id="btn" class="btn btn-default">上传</button>
      </div>
     </div> 
   </div>
   </div>

</@override>
<@override name="script">
	<script type="text/javascript"  src="${context}/asset/public/plugins/layer-v2.4/layer.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/echarts.min.js"></script>
	<script type="text/javascript" src="${context}/asset/public/plugins/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));

		// 指定图表的配置项和数据
		option = {
			title: {

			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['利息收益', '存钱罐收益']
			},
			toolbox: {
				show: false,
				feature: {
					dataZoom: {
						yAxisIndex: 'none'
					},
					dataView: {
						readOnly: false
					},
					magicType: {
						type: ['line', 'bar']
					},
					restore: {},
					saveAsImage: {}
				}
			},
			xAxis: {
				type: 'category',
				boundaryGap: false,
				data: ${dateList}
			},
			yAxis: {
				type: 'value',
				min: 0,
				axisLabel: {
					formatter: '{value} 元'
				}
			},
			series: [
			{
				name: '利息收益',
				type: 'line',
				data: ${interestData}
			},
			{
				name: '存钱罐收益',
				type: 'line',
				data: ${savingPotData}
			}]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		
$('#headImg').on('click', function() { 
      layer.open( { 
      type: 2, 
      title: '上传头像',
      shadeClose: true,
      shade: 0.8,
      area: ['600px', '490px'],
      content: '${context}/ucenter/updateHead.html' //iframe的url
    });
});     
	</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />