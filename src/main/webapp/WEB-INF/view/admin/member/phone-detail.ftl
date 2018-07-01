<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>秒易花-后台</title>
    <link href="/asset/public/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        .static-info {
            font-family: 微软雅黑;
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<script type="text/javascript" src="/asset/public/plugins/vue.js"></script>
<body>
<div class="row col-md-12 ">
    <div><strong><h3>运营商信息</h3></strong></div>
</div>
<div class="row col-md-12" id="phone-detail-example">
    <div v-if="carrier == null" class="alert alert-warning">运营商未认证</div>

    <div v-else class="static-info">
        <div class="col-md-12"><span style="font-size: 20px">个人信息</span>
            <div class="col-md-12 ">
                <div class="row static-info col-md-12">
                    <div class="col-md-4">
                        <div class="col-md-6">用户姓名：</div>
                        <div class="col-md-6">{{carrier.real_name}}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="col-md-6">手机号：</div>
                        <div class="col-md-6">{{carrier.user_mobile}}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="col-md-6">身份证号：</div>
                        <div class="col-md-6">{{carrier.identity_code}}</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="col-md-6">归属地：</div>
                        <div class="col-md-6">{{carrier.channel_src}}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="col-md-6">运营商：</div>
                        <div class="col-md-6">{{carrier.channel_attr}}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="col-md-6">开户日期：</div>
                        <div class="col-md-6">{{carrier.created_time}}</div>
                    </div>
                </div>
            </div>
        </div>
        </br>
        <div class="col-md-12">
            <div><span style="font-size: 20px">账户信息</span></div>
            <div class="col-md-12 container">
                <div v-if="carrier.task_data.account_info!=null">
                    <div class="row static-info col-md-12">
                        <div class="col-md-3">
                            <div class="col-md-6"> 账户余额:</div>
                            <div class="col-md-6">{{carrier.task_data.account_info.account_balance/100}}元</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">账户星级:</div>
                            <div class="col-md-6">
                                {{carrier.task_data.account_info.credit_level}}
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">账户状态:</div>
                            <div class="col-md-6"> {{carrier.task_data.account_info.mobile_status}}</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">网龄:</div>
                            <div class="col-md-6">{{carrier.task_data.account_info.net_age/12}}年
                            </div>
                        </div>
                    </div>

                </div>
                <div v-if="carrier.user_portrait!=null" class="row static-info col-md-12">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="col-md-6">统计起始：</div>
                            <div class="col-md-6">{{carrier.user_portrait.silent_days.start_day}}</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">统计终止：</div>
                            <div class="col-md-6">{{carrier.user_portrait.silent_days.end_day}}</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">最大静默天数:</div>
                            <div class="col-md-6">{{carrier.user_portrait.silent_days.max_interval}}天</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">月均静默天数：</div>
                            <div class="col-md-6">{{carrier.user_portrait.silent_days.monthly_avg_days}}天</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="col-md-6">互通联系人:</div>
                            <div class="col-md-6">{{carrier.user_portrait.both_call_cnt}}</div>
                        </div>
                        <div class="col-md-3">
                            <div class="col-md-6">主要地区:</div>
                            <div class="col-md-6">{{carrier.user_portrait.contacts_distribution.area}}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </br>
        <div class="col-md-12"><span style="font-size: 20px">通话记录</span>
            <div v-if="carrier.task_data.call_info == null" class="alert alert-warning">无</div>
            <div v-else>
                <div v-for="item in carrier.task_data.call_info">
                    <table class="table table-bordered table-hover text-center">
                        <thead>
                        <tr>
                        <tr>
                            <th class="text-center">通话地点</th>
                            <th class="text-center">呼叫类型</th>
                            <th class="text-center">对方号码</th>
                            <th class="text-center">通话时长(秒)</th>
                            <th class="text-center">通话类型</th>
                            <th class="text-center">费用小计(分)</th>
                        </tr>
                        </tr>
                        </thead>
                        <tbody v-for="item_record in item.call_record">
                        <tr>
                            <td>{{item_record.call_address}}</td>
                            <td>{{item_record.call_type_name}}</td>
                            <td>{{item_record.call_other_number | showLinkName}}</td>
                            <td>{{item_record.call_time}}</td>
                            <td>{{item_record.call_land_type}}</td>
                            <td>{{item_record.call_cost}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        </br>
        <div class="col-md-12"><span style="font-size: 20px">短信记录</span>
            <div v-if="carrier.task_data.sms_info == null" class="alert alert-warning">无</div>
            <div v-else>
                <div v-for="item in carrier.task_data.sms_info">
                <table class="table table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <th class="text-center">起始时间</th>
                        <th class="text-center">发送方式</th>
                        <th class="text-center">对方号码</th>
                        <th class="text-center">信息类型</th>
                        <th class="text-center">短信地区</th>
                        <th class="text-center">费用小计(分)</th>
                    </tr>
                    </thead>
                    <tbody v-for="item_record in item.sms_record">
                    <tr>
                        <td>{{item_record.msg_start_time}}</td>
                        <td>{{item_record.msg_type}}</td>
                        <td>{{item_record.msg_other_num}}</td>
                        <td>{{item_record.msg_channel}}</td>
                        <td>{{item_record.msg_address}}</td>
                        <td>{{item_record.msg_cost}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>
</body>
<script>
    //axios
    var example = new Vue({
        el: '#phone-detail-example',
        data: {
            carrier:${detail}
        }
    })
</script>
</html>

