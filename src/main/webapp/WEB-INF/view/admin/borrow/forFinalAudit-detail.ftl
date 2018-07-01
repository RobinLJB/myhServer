<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${context}/asset/public/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
      rel="stylesheet" type="text/css">
<link href="${context}/asset/admin/css/processor_bar.css" rel="stylesheet">
<style>
    .detail-info {
        width: 871px;
        line-height: 20px;
        border-style: solid;
        border-width: 0 0 1px 1px;
        border-color: transparent transparent #CCC #CCC;
        margin: 10px 0 20px 0;
        box-shadow: 2px 2px 2px rgba(204, 204, 204, 0.25);
    }

    .detail-info tbody th {
        color: #777;
        background-color: #F7F7F7;
        text-align: right;
        width: 119px;
        height: 20px;
        padding: 8px 5px;
        border-style: solid;
        border-width: 1px 1px 0 1px;
        border-color: #CCC;
    }

    .detail-info thead th {
        font-weight: 600;
        color: #FFF;
        background-color: #364150;
        height: 20px;
        padding: 8px 5px;
        border-style: solid;
        border-width: 1px 1px 0 0;
        border-color: #CCC #CCC transparent transparent;
    }

    #loanImgs {
        overflow: hidden;
        list-style: none;
        margin: 0px;
        padding: 0px;
    }

    #loanImgs li {
        float: left;
        margin-right: 10px;
    }

    #loanImgs li img {
        width: 100px;
        border: 1px solid #999;
    }

</style>
</@override>
<@override name="body">
<input type="hidden" id="MENU_ACTIVE_ID" value="loan:borrow"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="#">借款管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">放款审核列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">放款审核详情</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <#if borrowMap.borrowStatus = "10">
        <div class="alert alert-info" style="margin-top:20px;">
            <p class="bg-info">该借款已经还完</p>
        </div>
    <#else>
        <div id="stepItems">
            <ul class="processor_bar grid_line">
                <li data-id="2" class="step grid_item size1of5">
                    <h4>1 初审中</h4>
                </li>
                <li data-id="5" class="step grid_item size1of5">
                    <h4>2复审中</h4>
                </li>
                <li data-id="8" class="step grid_item size1of5">
                    <h4>3 还款中</h4>
                </li>
                <li data-id="9" class="no_extra step grid_item size1of5">
                    <h4>4 逾期中</h4>
                </li>
                <li data-id="10" class="no_extra step grid_item size1of5">
                    <h4>5 已完成</h4>
                </li>

            </ul>
        </div>
    </#if>

    <table class="table detail-info">
        <thead>
        <th colspan="4">借款人信息</th>
        </thead>
        <tbody>
        <tr>
            <th>姓名</th>
            <td>${member.realName!}</td>
            <th>身份证</th>
            <td>${member.identNo!}</td>
        </tr>
        <tr>
            <th>手机号</th>
            <td>
            ${member.mobilePhone!}
            </td>
            <th>注册时间</th>
            <td>
            ${member.createTime!}
            </td>

        </tr>
        <tr>
            <th>所属地区</th>
            <td>
            ${areaMap.area!}
            </td>
            <th>会员性别</th>
            <td>
            ${areaMap.sex!}
            </td>

        </tr>
        <tr>
            <th>手机使用地址</th>
            <td colspan="3">
            ${cardMap.true_address!}
            </td>	
        </tr>
        <tr>
            <th>会员年龄</th>
            <td>
            ${areaMap.age!}
            </td>
            <th>通讯录</th>
            <td>
            ${member.createTime!}
            </td>
        </tr>
        <tr>
            <th>苹果型号</th>
            <td>${iphoneMap.type!}</td>
            <th>内存容量</th>
            <td>${iphoneMap.storage!}G</td>
        </tr>
        <tr>
            <th>会员状态</th>
            <td>
                <#if member.memberStatus ==0>
                    <span class="label label-info">注册未申请</span>
                <#elseif member.memberStatus ==1>
                    <span class="label label-success">正常用户</span>
                <#elseif member.memberStatus ==2>
                    <span class="label label-warning">争议用户</span>
                <#elseif member.memberStatus ==3>
                    <span class="label label-danger">黑名单</span>
                </#if>
            </td>
            <th>成功借款次数</th>
            <td>
            ${member.successBorrowTimes!0}
            </td>
        </tr>
        <tr>
            <th>邀请人数</th>
            <td class="blue12 left">
            ${member.invateSum!0}
            </td>
            <th>剩余佣金</th>
            <td class="f66 leftp200">
            ${member.commisionSum!0}
            </td>
        </tr>
        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="4">借款人认证信息</th>
        </thead>
        <tbody>
        <tr>
            <th>身份证照片A：</th>
            <td>
                <#if cardMap.card_imgA??>
                    <ul id="loanImgs">
                        <li><img src="${cardMap.card_imgA!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
            <th>身份证照片B：</th>
            <td>
                <#if cardMap.card_imgB??>
                    <ul id="loanImgs">
                        <li><img src="${cardMap.card_imgB!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>

        </tr>
        <tr>
            <th>手持身份证照片：</th>
            <td>
                <#if cardMap.handle_img??>
                    <ul id="loanImgs">
                        <li><img src="${cardMap.handle_img!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
            <#if iphoneMap.iphone_imgurl??>
                <th>iphone认证：</th>
                <td>
                    <#if iphoneMap.iphone_imgurl??>
                        <ul id="loanImgs">
                         <#if iphoneMap.iphone_imgurl!=''>
                         <li><img src="${iphoneMap.iphone_imgurl!} "/></li>
                         <#else>
                         	<li>序列号：${iphoneMap.serialNum!}</li><br/>
                       		<li>IMEI：${iphoneMap.imeiNum!}</li>
                         </#if>
                        </ul>
                    <#else>
                        <span class="label label-warning">无图片</span>
                    </#if>
                </td>
            </#if>
        </tr>
        <tr>
            <#if iphoneMap.icloud_imgurl??>
                <th>icloud认证：</th>
                <td>
                    <#if iphoneMap.icloud_imgurl??>
                        <ul id="loanImgs">
                            <li><img src="${iphoneMap.icloud_imgurl!} "/></li>
                        </ul>
                    <#else>
                        <span class="label label-warning">无图片</span>
                    </#if>
                </td>
            </#if>
        </tr>
        <tr>
            <th>运营商信息</th>
            <td><#if member.taskId??><#if member.callDetail??><a target="_blank" href="${context}/admin/phone/detail/${member.id?c}.html">查看本地报告</a>
            <#else>
                <a id="createReport">生成本地报告</a>
            </#if>

            </#if>&nbsp;&nbsp;&nbsp;
                <#if member.taskId??><a href="https://portal.shujumohe.com/nolayout/customerReport/${member.taskId}"
                                        target="_blank">官方运营商报告</a><#else>没有官方运营商报告</#if></td>
            <th>通讯录</th>
            <td>
                <a href="${context}/admin/member/${member.id?c}.html?displayStatus=4">查看通讯录</a> <#--${linkmansize.linkmansize!}-->
            </td>
        </tr>
        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="4">银行卡信息</th>
        </thead>
        <tbody>
        <tr>
            <th>银行卡号</th>
            <td><#if bankMap??>${bankMap.cardNo!}<#else>等待输入</#if></td>
            <th>银行开户行</th>
            <td><#if bankMap??>${bankMap.bank_name!}<#else>等待输入</#if></td>
        </tr>
        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="4">审核信息</th>
        </thead>
        <tbody>

        <tbody>
        <tr>
            <th>初审意见</th>
            <td>
                <#if reviewMap??>  ${reviewMap.frist_review!}  </#if>
            </td>
            <th>初审时间</th>
            <td>
                <#if reviewMap??>  ${reviewMap.frist_audit_time!}  </#if>
            </td>

        </tr>
            <#if borrowMap.borrowStatus=="6" || borrowMap.borrowStatus=="7">
            <tr>
                <th>复审意见</th>
                <td>
                    <#if reviewMap??>  ${reviewMap.second_review!}  </#if>
                </td>
                <th>复审时间</th>
                <td>
                    <#if reviewMap??>  ${reviewMap.second_audit_time!}  </#if>
                </td>

            </tr>
            </#if>
        <tr>
            <th>苹果账号</th>
            <td>
                <#if iphoneMap.icloudId??>  ${iphoneMap.icloudId!}  </#if>
            </td>
            <th>苹果密码</th>
            <td>
                <#if iphoneMap.icloudPass??>  ${iphoneMap.icloudPass!}  </#if>
            </td>

        </tr>
        </tbody>
    </table>
    <table class="table detail-info">
        <thead>
        <th colspan="8">借款信息</th>
        </thead>
        <tbody>
        <tr>
            <th>借款编号</th>
            <td>${borrowMap.borrowNo!}</td>
        </tr>
        <tr>
            <th>本金</th>
            <td>${borrowMap.benJin!}元</td>
            <th>借款天数</th>
            <td>${borrowMap.borrowDate!}天</td>
        </tr>
        <tr>
            <th>服务费</th>
            <td>${borrowMap.serviceFee!}元</td>
            <th>到账金额</th>
            <td>${paidFee}元</td>
        </tr>
    </table>
    <div class="radio">苹果二次密码 &nbsp;
        <label>
            <input type="text" id="secondIcloudPass" name="secondIcloudPass" size="20" maxlength="20">
        </label>
    </div>
    <button class="btn btn-success" type="button" onclick="audit(8)">终审通过</button>
    <button class="btn btn-error" type="button" onclick="audit(7)">终审不通过</button>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(function () {
        $('#createReport').click(function () {
            console.log("1")
            $.ajax({
                url: "${context}/admin/borrow/create/report.do",
                type: "post",
                data:{
                    taskId: "${member.taskId}",
                    memberId:"${member.id}"
                },

                dataType: "json",
                success: function (res) {
                    //  console.log(data);
                    if(res.code == 0){
                        alert("本地报告生成成功");
                        window.location.reload();
                    }
                    else {
                        alert("本地报告生成失败");
                    }
                }
            });
        });
    });

    function audit(status) {
       var msg = "确定操作？\n\n请确认！"; 
	 if (confirm(msg)==true){ 
	 
	 }else{ 
	  return ; 
	 } 
	 
        var param = {};
        param['id'] = ${borrowMap.id};
        param['benjin'] = ${borrowMap.benJin};
        param['borrowDay'] = "7";
        /*param['opinion'] = $('#auditOpinion').val();*/
        param['status'] = status;
        param['memberId'] =${borrowMap.member_id};
        param['iphoneId'] =${iphoneMap.id};
        param['secondIcloudPass'] = $('#secondIcloudPass').val();
        param['serviceAmount'] =${borrowMap.serviceFee}
                $.post('${context}/admin/borrow/finalAudit/updateStatus.do', param, function (resp) {

                    if (resp.code == 0) {
                        spark.notify('终审通过');
                        spark.redirect('${context}/admin/borrow/loanBorrow.html');
                    } else if (resp.code == -2) {
                        spark.notify(resp.message, 'error');
                        spark.redirect('${context}/admin/borrow/loanBorrow.html');
                    } else {
                        spark.notify(resp.message, 'error');
                        spark.redirect('${context}/admin/borrow/loanBorrow.html');
                    }


                });
    }


</script>

</@override>
<@layout name="/admin/layout/main.ftl" />
