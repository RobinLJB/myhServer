<@override name="head">
</@override>
<@override name="body">
   <div class="r_main">
      <div class="box">
      <div class="tabtil">
       <ul><li class="on">自动投标</li></ul></h2>
        </div>
      <div class="boxmain2">
      <div class="biaoge2" style="margin-top:0px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left"> 设置我的自动投标工具</th>
    </tr>
  <tr>
    <td align="right"><p>自动投标状态：</p></td>
    <td><p id="statusText" style="text-align: left">关闭状态</p></td>
  </tr>
  <tr>
    <td align="right"><p>您的账户余额：</p></td>
    <td>0.00元
    	<input type="hidden" value="" id="usableSum" name="usableSum" />
    </td>
  </tr>
  <tr>
    <td align="right"><p>每次投标金额：<span class="fred">*</span></p></td>
    <td><input type="text" class="inp100x" id="bidAmount" maxlength="20"  value=""/>
      元</td>
  </tr>
  <tr>
    <td align="right"><p>利率范围：<span class="fred">*</span></p></td>
    <td><input type="text" class="inp100x" id="rateStart" maxlength="20"  value=""/>
      % 至
        <input type="text" class="inp100x" id="rateEnd" maxlength="20"  value=""/>
%</td>
  </tr>
  <tr>
    <td align="right"><p>借款期限：</p></td>
    <td>
    <select>
     <option>1天</option>
 	 <option>15天</option>
  	 <option>25天</option>
  	 <option>1个月</option>
  	 <option>2个月</option>
 	 <option>3个月</option>
  	 <option>4个月</option>
  	 <option>5个月</option>
    </select>
       至
  <select>
     <option>1天</option>
 	 <option>15天</option>
  	 <option>25天</option>
  	 <option>1个月</option>
  	 <option>2个月</option>
 	 <option>3个月</option>
  	 <option>4个月</option>
  	 <option>5个月</option>
    </select></td>
  </tr>
  <tr>
    <td align="right"><p>信用等级范围：</p></td>
    <td>
   <select>
     <option>HR</option>
 	 <option>E</option>
  	 <option>D</option>
  	 <option>C</option>
  	 <option>B</option>
 	 <option>A</option>
  	 <option>AA</option>
    </select>   至
     <select>
     <option>HR</option>
 	 <option>E</option>
  	 <option>D</option>
  	 <option>C</option>
  	 <option>B</option>
 	 <option>A</option>
  	 <option>AA</option>
    </select> </td>
  </tr>
  <tr>
    <td align="right"><p>账户保留金额：<span class="fred">*</span></p></td>
    <td><input type="text" class="inp100x" id="remandAmount" maxlength="20"  value=""/>
元</td>
  </tr>
  <input type="checkbox" checked  style="display:none;" name="checkList.id" value="3"/>

  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top: 10px;">
    <button type="button" id="setbtn" class="btn btn-success btn-sm">关闭自动投标</button>
    <input type="button" value="保存设置" id="savebtn" class="btn btn-default btn-sm" />
    </td>
  </tr>
</table>
</div>
<p class="tips" style="margin-top:15px;text-align: left"><strong>自动投标工具说明</strong><br/>
1、贷款进入招标中十五分钟后，才会启动自动投标。<br/>

2、投标进度达到95%时停止自动投标。若投标后投标进度超过95%，则按照投标进度达到95%的金额向下取50的倍数金额值投标。<br/>

3、单笔投标金额若超过该标贷款总额的20%，则按照20%比例的金额向下取50的倍数金额值投标。<br/>

4、满足自动投标规则的金额小于设定的每次投标金额，也会进行自动投标。<br/>

5、贷款用户在获得贷款时会自动关闭自动投标，以避免借款被用作自动投标资金。<br/>

6、投标排序规则如下：<br/>
<span style="padding-left: 30px;">a）投标序列按照开启自动投标的时间先后进行排序。</span><br/>
<span style="padding-left: 30px;">b）每个用户每个标仅自动投标一次，投标后，排到队尾。</span><br/>
<span style="padding-left: 30px;">c）轮到用户投标时没有符合用户条件的标，也视为投标一次，重新排队。</span></p>
</div>
</div>
</div>
</@override>
<@override name="script">
 <script>
	$(document).ready(function() {
	$('.myinvest').addClass('active').parents().show();
	$('.submeun-10 a').addClass('active').parents().show();
	})
</script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />