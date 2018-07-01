<@override name="head">
</@override>
<@override name="body">
<div class="r_main">
      <div class="box">
      <div class="tabtil">
       <ul><li class="on">理财统计</li></ul></h2>
        </div>
       <div class="boxmain2">
      <div class="biaoge" style="margin-top:0px;">
    <table class="table table-bordered">
  <tr>
    <th colspan="3">回报统计</th>
    </tr>
  <tr>
    <td align="center">已赚利息</td>
    <td align="center">奖励收入总额</td>
    <td align="center">已赚逾期罚息</td>
    </tr>
  <tr>
    <td align="center">￥0.00</td>
    <td align="center">￥0.00</td>
    <td align="center">￥0.00</td>
    </tr>
          </table>

          </div>
          <div class="biaoge">
          <table class="table table-bordered">
  <tr>
    <th colspan="6">个人理财统计</th>
    </tr>
  <tr>
    <td align="center">总借出金额</td>
    <td align="center">总借出笔数</td>
    <td align="center">已回收本息</td>
    <td align="center">已回收笔数</td>
    <td align="center">待回收本息</td>
    <td align="center">待回收笔数</td>
    </tr>
  <tr>
    <td align="center">￥0.00</td>
    <td align="center">0</td>
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
	$('.myinvest').addClass('active').parents().show();
	$('.submeun-8 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />