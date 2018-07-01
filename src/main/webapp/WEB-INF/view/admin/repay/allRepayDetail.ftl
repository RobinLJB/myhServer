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
<input type="hidden" id="MENU_ACTIVE_ID" value="borrow:alllist"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="#">借款管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">借款列表</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">手动全额还款</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>

    <div class="alert alert-danger" style="margin-top:20px;">
        <p>请谨慎操作，全额还款或者部分还款不可逆</p>
    </div>


    <table class="table detail-info">
        <thead>
        <th colspan="4">身份信息</th>
        </thead>
        <tbody>


        <tr class="active">
            <th>真实姓名</th>
            <td>${member.realName!}</td>
            <th>身份证号</th>
            <td>${member.identNo!}</td>
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
        <tr>
            <th>身份证照片A：</th>
        <td>
            <#if cardMap??>
                <#if cardMap.card_imgA??>
                    <ul id="loanImgs">
                        <li><img src="${cardMap.card_imgA!} "/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
                <th>身份证照片B：</th>
            <td>
                <#if cardMap.card_imgB??>
                    <ul id="loanImgs">
                        <li><img src="${cardMap.card_imgB!} "/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
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
                            <li><img src="${iphoneMap.iphone_imgurl!} "/></li>
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

        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="8">借款信息</th>
        </thead>
        <tbody>

        <tbody>
        <tr class="active">
            <th>借款金额</th>
            <td>${borrowMap.benJin!} &nbsp; 元</td>
            <th>未还总额</th>
            <td>${unrepaySum!} &nbsp; 元</td>
            <th>借款期限</th>
            <td>${borrowMap.borrowDate!}  &nbsp; 天</td>
        </tr>
        <tr class="active">
            <th>打款时间</th>
            <td><span class="label label-danger">${borrowMap.realLoanTime!}</span></td>
            <th>还款日期</th>
            <td><span class="label label-danger">${borrowMap.appointmentTime!}</span></td>
            <th>当前日期</th>
            <td><span class="label label-danger">${nowdate}</span></td>
        </tr>
        </tbody>
    </table>

    <#--<table class="table detail-info">
        <thead>
        <th colspan="4">银行卡信息</th>
        </thead>
        <tbody>

        <tbody>
        <tr class="active">
            <th>银行卡号</th>
            <td><#if bankMap??>${bankMap.cardNo!}<#else> 还未认证</#if></td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>-->


    <table class="table detail-info">
        <thead>
        <th colspan="4">审核信息</th>
        </thead>
        <tbody>

        <tbody>
        <tr>
            <th>审核意见</th>
            <td><#if reviewMap??>${reviewMap.frist_review!}<#else><#if member.memberStatus==1>自动审核<#else>
                没有审核</#if></#if></td>
            <th>初审时间</th>
            <td><#if reviewMap??>${reviewMap.frist_audit_time!}<#else>${borrowMap.fristSubmitTime!}</#if></td>
        </tr>

        <tr>
            <th>复审意见</th>
            <td><#if reviewMap??>${reviewMap.second_review!}<#else><#if member.memberStatus==1>自动审核<#else>
                没有审核</#if></#if></td>
            <th>复审时间</th>
            <td><#if reviewMap??>${reviewMap.second_audit_time!}<#else>${borrowMap.secondAuditTime!}</#if></td>
        </tr>

        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="8">部分还款记录</th>
        </thead>
        <thead>
        <tr>
            <th>还款序号</th>
            <th>还款总额</th>
            <th>剩余本金</th>
            <th>剩余管理费</th>
            <th>还款意见</th>
            <th>操作人员</th>
            <th>还款时间</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#if apartList??>
                <#list apartList as lists>
                <tr>
                    <td>${lists.id!}</td>
                    <td>${lists.amount!}</td>
                    <td><span class="label label-danger">${lists.remainBenjin!}</span></td>
                    <td><span class="label label-danger">${lists.remainFee!0}</span></td>
                    <td>${lists.review!}</td>
                    <td>${lists.adminid!}</td>
                    <td>${lists.repayTime!}</td>
                </tr>
                </#list>


            <#else>
            <tr>
                <th colspan="8">没有部分还款记录</th>
            </tr>
            </#if>
        </tr>
        </tbody>
    </table>


    <table class="table detail-info">
        <thead>
        <th colspan="8">续期记录</th>
        </thead>
        <thead>
        <tr>
            <th>续期序号</th>
            <th>续期天数</th>
            <th>续期费用</th>
            <#--<th>扣款卡号</th>-->
            <th>扣款时间</th>
            <th>扣款地址</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#if renewalList??>
                <#list renewalList as lists>
                <tr>
                    <td>${lists.id!}</td>
                    <td>${lists.renewalDays!0}</td>
                    <td><span class="label label-danger">${lists.renewalFee!}</span></td>
                    <#--<td><span class="label label-danger">${lists.bankCardNo!0}</span></td>-->
                    <td>${lists.renewalTime!}</td>
                    <td>${lists.renewalIp!}</td>
                </tr>
                </#list>


            <#else>
            <tr>
                <th colspan="8">没有续期记录</th>
            </tr>
            </#if>
        </tr>
        </tbody>
    </table>
    <table class="table detail-info">
        <thead>
        <th colspan="8">逾期记录</th>
        </thead>
        <thead>
        <tr>
            <th style="background: #364150;">逾期编号</th>
            <th style="background: #364150;">逾期费用</th>
            <th style="background: #364150;">逾期天数</th>
            <th style="background: #364150;">开始逾期日期</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#if overdueMap??>

            <tr>
                <td>${overdueMap.id}</td>
                <td>${overdueMap.overdueFee!0}</td>
                <td>${overdueMap.overDays!}</td>
                <td>${overdueMap.beginTime!}</td>
            </tr>

            <#else>
            <tr>
                <th colspan="4" style="text-align:center;">没有逾期记录</th>
            </tr>
            </#if>
        </tr>
        </tbody>
    </table>

    <table class="table detail-info">
        <thead>
        <th colspan="4">借款信息</th>
        </thead>
        <tbody>
        <tr class="active">
            <th>待还借款金额</th>
            <td id="remainBenjin" value="${remainBenjin!}">${remainBenjin!}元</td>

        </tr>

        <tr class="active">
            <th>待还逾期费</th>
            <td id="overdueFee" value="${overdueFee!}">${overdueFee!}元</td>
        </tr>

        <tr class="active">
            <th>还款方式</th>
            <td>
                <label><input name="repayType" type="radio" value="1"/>部分还款 </label> &nbsp;
                <label><input name="repayType" type="radio" value="2"/>全额还款 </label>
            </td>
        </tr>

        <tr class="active">
            <th>请输入收到的还款金额</th>
            <td><label><input id="amount" type="text" value=""/></label></td>
        </tr>
        <tr>
            <th>手动还款备注</th>
            <td colspan="3">
                <textarea id="auditOpinion" class="form-control" placeholder="请输入还款备注"></textarea>
            </td>
        </tr>

        </tbody>
    </table>

    <button class="btn btn-danger" type="button" onclick="audit(10)">确认还款</button>


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
		var cmsg=status==10?'手动还款':'';
    	if(confirm('确定要'+cmsg+'?')){
        var param = {};
	        var auditOpinion = $('#auditOpinion').val();
	        if (auditOpinion == null || auditOpinion.length == 0) {
	            alert("还款备注为必填");
	            $('#auditOpinion').focus();
	            return;
	        }
	        var repayType = $("input[name='repayType']:checked").val();
	        var realamount = $('#amount').val();
	        var remainBenjin = $('#remainBenjin').attr("value");
	       /* var managerFee = $('#managerFee').attr("value");*/
	        var overdueFee = $('overdueFee').attr("value");
	        if (isNaN(realamount)) {
	            alert("请输入数字");
	            $('#amount').focus();
	            return;
	        }
	        if (!$("input[name='repayType']").is(":checked")) {
	            alert("请选择还款方式");
	            return;
	        }
	
	        param['id'] = ${borrowMap.id};
	        param['auditOpinion'] = $('#auditOpinion').val();
	        param['repayType'] = repayType;
	        param['realamount'] = realamount;
	        param['remainBenjin'] = remainBenjin;
	        param['overdueFee'] = overdueFee;
	        $.post('${context}/admin/repay/manualFullRepay.do', param, function (resp) {
	            if (resp.code == 0) {
	                spark.notify(resp.message);
	                spark.redirect('${context}/admin/repay/alllist.html');
	            } else {
	                spark.notify(resp.message, 'error');
	            }
	        }, "json");
        }
    }


    $('#audit').click(function () {
        var bid = $("#bid").val();
        /*var bankid = $("#bankid").val();*/

        var benjin = $("input[name=benjin]:checked").val();
        var borrowDay = $("input[name=borrowDay]:checked").val();
        var borrowStatus = $("input[name=borrowStatus]:checked").val();
        alert(bid + "----" + status);
        var $btn = $(this).button('loading');
        $.ajax({
            url: '${context}/admin/borrow/secondAudit/updateStatus.do',
            type: 'post',
            dataType: "json",
            data: {bid: bid, benjin: benjin, borrowDate: borrowDay, status: borrowStatus, bankid: bankid},
            success: function (data) {

                if (data.code == 0) {
                    spark.notify('操作成功', 'success');
                    location.href = "${context}/admin/borrow/forFristAudit.html";
                } else {
                    spark.notify('操作失败', 'error');
                }

            }
        });
    });
</script>

</@override>
<@layout name="/admin/layout/main.ftl" />
