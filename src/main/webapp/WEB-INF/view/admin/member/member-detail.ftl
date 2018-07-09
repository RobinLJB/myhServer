<@override name="body">
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
<input type="hidden" id="MENU_ACTIVE_ID" value="member:index"/>
<input type="hidden" id="displayStatus" value="${displayStatus}"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <a href="#">系统管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="${context}/admin/member.html">会员管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">会员详情</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <div style="margin-top: 20px;padding: 0 20px;">
        <!-- Nav tabs -->
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation" class="active">
                <a href="#basic" aria-controls="basic" role="tab" data-toggle="tab">基本信息</a>
            </li>
            <li role="presentation">
                <a href="#borrowDetail" aria-controls="finance" role="tab" data-toggle="tab">借款记录</a>
            </li>

            <li role="presentation">
                <a href="#financelist" aria-controls="financelist" role="tab" data-toggle="tab">资金记录</a>
            </li>

            <li role="presentation">
                <a href="#comicatelist" class="comicatelists" aria-controls="financelist" role="tab" data-toggle="tab">通讯录</a>
            </li>
            <li>
                <button type="button" class="btn green btn-default btn-excel">通讯录导出</button>
            </li>
        </ul>


        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="basic">


                <table class="table detail-info">
                    <thead>
                    <th colspan="4" class="active">会员注册信息</th>
                    </thead>
                    <tbody>

                    <tr>
                        <th>会员编号</th>
                        <td>${basicMap.id!}</td>
                        <th>唯一标示</th>
                        <td>${basicMap.memberNo!}</td>
                    </tr>

                    <tr>
                        <th>手机号</th>
                        <td>${basicMap.mobilePhone!}</td>
                        <th>注册时间</th>
                        <td>${basicMap.create_time!}</td>
                    </tr>


                    </tbody>
                </table>

                <table class="table detail-info">
                    <thead>
                    <th colspan="4" class="active">基本信息</th>
                    </thead>
                    <tbody>
                        <#if iphoneMap??>
                        <tr>
                            <th>苹果型号</th>
                            <td>${iphoneMap.type!}</td>
                            <th>内存容量</th>
                            <td>${iphoneMap.storage!}G</td>
                        </tr>
                        </#if>
                    <tr>
                        <th>学历</th>
                        <td><#if infoMap??>${infoMap.xueli!}<#else>没有输入</#if></td>
                    <#--<th>紧急电话</th>
                    <td><#if infoMap??>${infoMap.phone!}<#else>没有输入</#if></td>-->
                    </tr>
                    </tbody>
                </table>


                <table class="table detail-info">
                    <thead>
                    <th colspan="4" class="active">实名认证信息</th>
                    </thead>
                    <tbody>
                        <#if identityMap??>
                        <tr>
                            <th>真实姓名</th>
                            <td><#if identityMap??>${identityMap.real_name!}<#else>没有输入</#if></td>
                            <th>身份证号</th>
                            <td><#if identityMap??>${identityMap.card_no!}<#else>没有输入</#if></td>
                        </tr>


                        <tr>
                            <th>身份证照片A：</th>
                            <td>
                                <#if identityMap.card_imgA??>
                                    <ul id="loanImgs">
                                        <li><img src="${identityMap .card_imgA!}"/></li>
                                    </ul>
                                <#else>
                                    <span class="label label-warning">无图片</span>
                                </#if>
                            </td>
                            <th>身份证照片B：</th>
                            <td>
                                <#if identityMap.card_imgB??>
                                    <ul id="loanImgs">
                                        <li><img src="${identityMap .card_imgB!}"/></li>
                                    </ul>
                                <#else>
                                    <span class="label label-warning">无图片</span>
                                </#if>
                            </td>

                        </tr>
                        <tr>
                            <th>手持身份证照片：</th>
                            <td>
                                <#if identityMap.handle_img??>
                                    <ul id="loanImgs">
                                        <li><img src="${identityMap .handle_img!}"/></li>
                                    </ul>
                                <#else>
                                    <span class="label label-warning">无图片</span>
                                </#if>
                            </td>
                            <#if iphoneMap??>
                                <#if iphoneMap.iphone_imgurl??>
                                    <th>iphone认证：</th>
                                    <td>
                                        <#if iphoneMap.iphone_imgurl??>
                                            <ul id="loanImgs">
                                                <li><img src="${iphoneMap.iphone_imgurl!} "/></li>
                                                <#if (iphoneMap.serialNum)??>
						                         	<li>序列号：${iphoneMap.serialNum!}</li><br/>
						                         </#if>
						                         <#if (iphoneMap.imeiNum)??>
						                       		<li>IMEI：${iphoneMap.imeiNum!}</li>
						                       	</#if>
                                            </ul>
                                        <#else>
                                            <span class="label label-warning">无图片</span>
                                        </#if>
                                    </td>
                                </#if></#if>
                        </tr>
                        <tr>
                            <#if iphoneMap??>
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
                            </#if>
                        </tr>
                        </#if>
                    </tbody>
                </table>

                <table class="table detail-info">
                    <thead>
                    <th colspan="4" class="active">借款资质信息</th>
                    </thead>
                    <tbody>
                    <tr>
                        <th>运营商信息</th>
                        <td><#if member.taskId??><#if member.callDetail??><a target="_blank"
                                                                             href="${context}/admin/phone/detail/${member.id?c}.html">查看本地报告</a>
                        <#else>
                            <a id="createReport">生成本地报告</a>
                        </#if>

                        </#if>&nbsp;&nbsp;&nbsp;
                            <#if member.taskId??><a
                                    href="https://portal.shujumohe.com/nolayout/customerReport/${member.taskId}"
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
                    <th colspan="4" class="active">推广信息</th>
                    </thead>
                    <tbody>

                    <tr>
                        <th>邀请人数</th>
                        <td class="blue12 left">
                        ${basicMap.invateSum!0}
                        </td>
                        <th>剩余佣金</th>
                        <td class="f66 leftp200">
                        ${basicMap.commisionSum!0}
                        </td>
                    </tr>
                    </tbody>
                </table>


                <form name="basicForm" id="basicForm" action="${context}/admin/member/update/basic/${basicMap.id!0}.do"
                      class="form-horizontal" role="form" style="margin-top:20px;">
                    <div class="form-body">


                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="mobilePhone" id="mobilePhone"
                                       value="${basicMap.mobilePhone!}">
                            </div>
                            <span class="btn btn-danger">请谨慎修改手机号码，可能导致审核，放款等操作失败</span>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">修改会员状态
                            </label>
                            <div class="col-md-6">
                                <label class="checkbox-inline">
                                    <input type="radio" name="member_status" value="0" <#if basicMap.member_status=="0">
                                           checked </#if>>注册未申请
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="member_status" value="1" <#if basicMap.member_status=="1">
                                           checked </#if>>正常用户
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="member_status" value="2" <#if basicMap.member_status=="2">
                                           checked </#if>>争议客户
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="member_status" value="3" <#if basicMap.member_status=="3">
                                           checked </#if>>黑名单
                                </label>
                            </div>
                        </div>


                        <div class="form-actions">
                            <div class="row">
                                <div class="col-md-offset-3 col-md-9">
                                    <button type="submit" class="btn btn-info">修改</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div role="tabpanel" class="tab-pane active" id="financelist">
                <table id="financelistTab" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>借款编号</th>
                        <th>手机号</th>
                        <th>真实姓名</th>
                        <th>操作金额</th>
                        <th>操作时间</th>
                        <th>IP</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                </table>
            </div>


            <div role="tabpanel" class="tab-pane" id="borrowDetail">
                <table id="borrowDetailTab" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>借款编号</th>
                        <th>本金</th>
                        <th>初次天数</th>
                        <th>续期天数</th>
                        <th>总费用</th>
                        <th>放款时间</th>
                        <th>续期次数</th>
                        <th>实际还款</th>
                        <th>借款状态</th>
                    </tr>
                    </thead>
                </table>
            </div>


            <div role="tabpanel" class="tab-pane" id="comicatelist">
                <table id="comicatelistTab" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>手机号</th>
                    </tr>
                    </thead>
                    <#if linkman??>
                        <#list linkman as l>
                            <tr>
                                <td>${l.name!}</td>
                                <td>${l.tel!}</td>
                            </tr>
                        </#list>
                    </#if>

                </table>
            </div>

        </div>
    </div>
</div>
</@override>
<@override name="script">
<script src="${context}/asset/public/plugins/jquery-validation/js/jquery.validate.min.js"
        type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery-validation/js/localization/messages_zh.js"
        type="text/javascript"></script>
<script src="${context}/asset/public/plugins/validation.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.form.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script>
    $(function () {
        FormValidation.validation('#basicForm', function (resp) {
            if (resp.code == 0) {
                spark.notify(resp.message);
            }
            else spark.notify(resp.message, 'error');
        });

        var displayStatus = $("#displayStatus").val();
        if (displayStatus == 4) {
            $('.comicatelists').trigger("click");
        }


    })


    $(document).ready(function () {
        var table = spark.dataTable('#financelistTab', '${context}/admin/member/fundList/${basicMap.id}.json', {
            "columns": [
                {"data": "borrowId"},
                {"data": "mobilePhone"},
                {"data": "real_name"},
                {"data": "amount"},
                {"data": "occurTime"},
                {"data": "ip"},
                {"data": "remark"},
            ],

            drawCallback: function () {
                spark.handleToggle('#financelistTab');
            }
        })
    });

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

    $(document).ready(function () {
        var table = spark.dataTable('#borrowDetailTab', '${context}/admin/member/borrowList/${basicMap.id}.json', {
            "columns": [
                {"data": "id"},
                {"data": "benJin"},
                {"data": "borrowDate"},
                {"data": "addBorrowDay"},
                {"data": "totalFee"},
                {"data": "realLoanTime"},
                {"data": "overdueTimes"},
                {"data": "finalRepayTime"},
                {"data": "borrowStatus"},
            ],
            "columnDefs": [


                {
                    "targets": [8],
                    "data": 'borrowStatus',
                    "render": function (data, type, full) {
                        if (data == 1) {
                            return '<span class="label label-success">提交审核</span>';
                        } else if (data == 2) {
                            return '<span class="label label-success">已认领</span>';
                        } else if (data == 3) {
                            return '<span class="label label-danger">初审失败</span>';
                        } else if (data == 4) {
                            return '<span class="label label-success">初审成功</span>';
                        } else if (data == 5) {
                            return '<span class="label label-success">等待复审</span>';
                        } else if (data == 6) {
                            return '<span class="label label-success">复审成功</span>';
                        } else if (data == 7) {
                            return '<span class="label label-danger">复审失败</span>';
                        } else if (data == 8) {
                            return '<span class="label label-success">还款期间</span>';
                        } else if (data == 9) {
                            return '<span class="label label-danger">已经逾期</span>';
                        } else if (data == 10) {
                            return '<span class="label label-success">还款完成</span>';
                        } else if (data == 11) {
                            return '<span class="label label-success">未还完</span>';
                        } else if (data == 12) {
                            return '<span class="label label-danger">已取消</span>';
                        }
                        else return '<span class="label label-danger">其他状态</span>';
                    }
                }


            ],
            drawCallback: function () {
                spark.handleToggle('#borrowDetailTab');
            }
        })
    });


</script>
<script>
    $(document).ready(function () {
        $('.btn-excel').on('click', function () {
            var url = "/admin/member/address/list/excel.do";
            var params = "&searchDateRange=" +${member.id?c};
            window.open(encodeURI(encodeURI(url + "?" + params)));
        });
    })
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />