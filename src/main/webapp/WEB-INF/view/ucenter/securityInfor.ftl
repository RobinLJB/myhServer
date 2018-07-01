<@override name="head">
 <link rel="stylesheet" type="text/css" href="${context}/asset/ucenter/css/security.css">
  <style type="text/css">
      #bank .row{
        margin-bottom: 10px;
      }
      #bank .checkbox input[type=checkbox],
       #bank .checkbox-inline input[type=checkbox], 
       #bank .radio input[type=radio], 
       #bank .radio-inline input[type=radio] {
          margin-top: 14px !important;
      }
      </style>
</@override>
<@override name="body">
	  <ul class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">实名认证</a></li>
	    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">修改登录密码</a></li>
	    <li role="presentation"><a href="#transaction" aria-controls="profile" role="tab" data-toggle="tab">修改交易密码</a></li>
	    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">手机绑定</a></li>
	    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">通知设置</a></li>
	    <li id="banksetting" role="presentation"><a  href="#bank" aria-controls="bank" role="tab" data-toggle="tab">银行卡设置</a></li>
	  </ul>
	  <div class="tab-content">
	  <!-- 真实姓名审核 -->
	    <div role="tabpanel" class="tab-pane active" id="home">
	        <form id="realForm" class="form-horizontal" style="margin-top:100px;">
	             <#if realMap?? && realMap.status == 0 && realMap.realName??>
	             <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-8">
	                   <p id="realtip" class="form-control-static">管理员正在审核，请耐心等待.</p>
	                 </div>
	                 </div>
	             </#if>
	          <div class="form-body">
				  <div class="form-group">
				    <label for="username" class="col-sm-2 control-label">真实姓名</label>
				    <div class="col-sm-8">
				        <div class="input-icon right">
						<i class="fa"></i>
				        <input type="text" class="form-control" id="username" name="username" <#if realMap?? && realMap.realName??>value="${realMap.realName!}" readonly </#if>placeholder="真实姓名">
				        </div>
				    </div>
				  </div>
				   <div class="form-group">
				    <label for="idcard" class="col-sm-2 control-label">身份证号码</label>
				    <div class="col-sm-8">
				      <div class="input-icon right">
						<i class="fa"></i>
				        <input type="text" class="form-control" id="idcard" name="idnumber" <#if realMap?? && realMap.idcardnumber??>value="${realMap.idcardnumber!}" readonly</#if>  placeholder="身份证号码">
				      </div>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-8">
				      <button type="submit" id="btn-real" class="btn btn-primary" <#if realMap?? && realMap.realName??>disabled</#if> >提交</button>
				    </div>
				  </div>
			   </div>
		    </form>
	    </div>
	    <!-- 修改密码 -->
	    <div role="tabpanel" class="tab-pane" id="profile">
	        <form id="updateForm" class="form-horizontal" style="margin-top:100px;">
	             <div class="form-group">
				    <label for="oldpassword" class="col-sm-2 control-label">原密码</label>
				    <div class="col-sm-8">		      
				        <input type="password" class="form-control" id="oldpassword" name="oldpassword" placeholder="原密码">
				    </div>
				 </div>
				 <div class="form-group">
				    <label for="newpass" class="col-sm-2 control-label">新密码</label>
				    <div class="col-sm-8">
				      <input type="password" class="form-control" id="newpass" name="newpass" placeholder="新密码">	
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="newpassagain" class="col-sm-2 control-label">重新输入新密码</label>
				    <div class="col-sm-8">
				      <input type="password" class="form-control" id="newpassagain" name="newpassagain" placeholder="重新输入新密码">	
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-8">
				      <button type="submit" id="btn-update" class="btn btn-primary">提交</button>
				    </div>
				  </div>
	        </form>
	    </div>
	    <!-- 修改交易密码 -->
	    <div class="tab-pane" id="transaction">
	        <form id="updatetranForm" class="form-horizontal" style="margin-top:100px;">
	             <div class="form-group">
				    <label for="oldtranpassword" class="col-sm-2 control-label">原交易密码</label>
				    <div class="col-sm-8">		      
				        <input type="password" class="form-control" id="oldtranpassword" name="oldtranpassword" placeholder="原交易密码">
				    </div>
				 </div>
				 <div class="form-group">
				    <label for="newtranpass" class="col-sm-2 control-label">新交易密码</label>
				    <div class="col-sm-8">
				      <input type="password" class="form-control" id="newtranpass" name="newtranpass" placeholder="新交易密码">	
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="newtranpassagain" class="col-sm-2 control-label">重新输入新密码</label>
				    <div class="col-sm-8">
				      <input type="password" class="form-control" id="newtranpassagain" name="newtranpassagain" placeholder="重新输入新密码">	
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-8">
				      <button type="submit" id="btn-tran-update" class="btn btn-primary">提交</button>
				    </div>
				  </div>
	        </form>
	    </div>
	    <!-- 手机绑定 -->
	    <div role="tabpanel" class="tab-pane" id="messages">        
	    </div>
	    <!-- 通知设置 -->
	    <div role="tabpanel" class="tab-pane" id="settings">
	    </div>
	    <!-- 银行卡设置 -->
	    <div role="tabpanel" class="tab-pane" id="bank">
	        <div class="alert alert-warning" style="margin-top:20px;">
				温馨提示: 绑定银行卡后可以提现，并且最多可以绑定两张银行卡！暂不支持<b>信用卡</b>绑定!!!
			</div>
			<table class="table">
				<tr>
				<td style="width:150px;" align="left">真实姓名：<br /></td>
				<td width="83%">
				<span class="txt" id="cardUserName1">
				    <#if realMap?? && realMap.realName??>${realMap.realName}</#if> 
				</span>
				</td>
				</tr>
				<tr>
				<td align="left" style="vertical-align: top;">开户行:</td></td>
				<td>
				  <!-- <input type="text" class="inp188" id="bankName1" /> -->
				   <div class="row">
				    <label class="radio-inline col-md-4">
					  <input type="radio" name="bank" value="中国建设银行" checked> <img src="${context}/asset/ucenter/img/bank/1.gif" />	  
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国农业银行"> <img src="${context}/asset/ucenter/img/bank/2.gif" />
					</label>
					</div>
					<div class="row">
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="招商银行"> <img src="${context}/asset/ucenter/img/bank/3.gif" />
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国民生银行"> <img src="${context}/asset/ucenter/img/bank/4.gif" />
					</label>
				  </div>
				  <div class="row">
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国工商银行"> <img src="${context}/asset/ucenter/img/bank/5.gif" />
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国光大银行"> <img src="${context}/asset/ucenter/img/bank/6.gif" />
					</label>
				  </div>
				  <div class="row">
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="交通银行"> <img src="${context}/asset/ucenter/img/bank/7.gif" />
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="华夏银行"> <img src="${context}/asset/ucenter/img/bank/8.gif" />
					</label>
				  </div>
				  <div class="row">
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国银行"> <img src="${context}/asset/ucenter/img/bank/9.gif" />
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中国邮政"> <img src="${context}/asset/ucenter/img/bank/10.gif" />
					</label>
				  </div>
				   <div class="row">
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank"  value="中兴实业银行"> <img src="${context}/asset/ucenter/img/bank/11.gif" />
					</label>
					<label class="radio-inline col-md-4">
					  <input type="radio" name="bank" value="农村信用社"> <img src="${context}/asset/ucenter/img/bank/12.gif" />
					</label>
				  </div>
				   <div class="row">	 
				       <label class="radio-inline col-md-4" style="line-height: 37px;">
					    <input type="radio" name="bank" id="chose">其它
					   </label>
					   <div class="col-md-4 pr10">
					        <input type="text" id="other" class="form-control" placeholder="其他银行" readonly/>
					   </div>		
				   </div> 
				</tr>
				<tr>
					<td align="left">支行：<br /></td>
					<td> 
					 <form class="form-inline">
					   <select id="s_province" class="form-control" name="s_province"></select>  
   					   <select id="s_city" class="form-control" name="s_city" ></select>  
      				   <select id="s_county" class="form-control" name="s_county"></select> 
					   <input type="text" class="form-control" id="subBankName1" />	
					   <input type="hidden" id="show" />
					  </form>	 
					</td>
				</tr>
				<tr>
					<td align="left">卡号：<br /></td>
					<td>
					<input type="text" class="form-control" id="bankCard1" style="width:90%;"/>				
				</td>
				</tr>
				<tr>
					<td align="right">&nbsp;<br /></td>
					<td style="padding-top:10px;">
					<input class="btn btn-primary btn-sm" id="addbank" type="button" data-toggle="button" data-loading-text="正在提交..." value="提交"/>
					</td>
				</tr>
			</table>
			<!-- 已绑定银行卡列表  -->
			<table class="table table-striped table-bordered">
			    <thead>
			      <tr>
			         <th>序号</th>
			         <th>用户名</th>
			         <th>开户行</th>
			         <th>支行</th>
			         <th>卡号</th>
			         <th>申请时间</th>
			         <th>审核状态</th>
			         <th>审核意见</th>
			         <th>操作</th>
			      </tr>
			    </thead>
			    <tbody id="bankbody">
			    </tbody>
			</table>
	    </div>
    </div>
</@override>
<@override name="script">
  <script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"></script>
  <script src="${context}/asset/public/plugins/jquery-validation/js/additional-methods.min.js"></script>
  <script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
  <script src="${context}/asset/ucenter/js/area.js"></script>
  <script src="${context}/asset/ucenter/js/security.js"></script>
</@override>
<@layout name="/ucenter/layout/ucenter-base.ftl" />