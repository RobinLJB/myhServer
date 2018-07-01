<@override name="head">
</@override>
<@override name="body">
<div class="r_main">
      <div class="box">
        <div class="tabtil">
       <ul><li class="on">贷款统计</li></ul>
        </div>

      <div class="boxmain2">
      <div class="biaoge" style="margin-top:0px;">
          <table class="table-small" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="9" style="text-align: left;    padding-left: 15px;">还款统计</th>
    </tr>
  <tr>
    <td align="center">总借款额</td>
    <td align="center">发布借款数</td>
    <td align="center">已还本息</td>
    <td align="center">成功借款数</td>
    <td align="center">借款总笔数统计</td>
    <td align="center">待还本息</td>
    <td align="center">正常还清笔数</td>
    <td align="center">提前还清笔数</td>
    <td align="center">未还清笔数</td>
    </tr>
  <tr>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
     <td align="center">0</td>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
    <td align="center">0</td>
    <td align="center">0</td>
    </tr>
          </table>

          </div>
          <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="4" style="text-align: left;    padding-left: 15px;">逾期统计</th>
    </tr>
  <tr>
    <td align="center">逾期本息</td>
    <td align="center">逾期次数</td>
    <td align="center">逾期罚款</td>
    <td align="center">严重逾期次数</td>
    </tr>
  <tr>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
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
	$('.submeun-11 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />