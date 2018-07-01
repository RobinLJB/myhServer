<#assign context="${rc.contextPath}">
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>${webname}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link rel="stylesheet" href="${context}/asset/mobile/css/mui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${context}/asset/mobile/css/mobile-front.css"/>
    <style>
        .wrap {
            margin: 0 auto;
            max-width: 750px;
        }

        .an {
            width: 100%;
            text-align: center;
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(222, 222, 222, 0.5);
            padding-top: 0.15rem
        }
    </style>
</head>
<body>
<div class="wrap">
    <input type="hidden" id="requestCode" value="${requestCode!0}">
    <input type="hidden" id="reruestType" value="${reruestType!0}">
    <input type="hidden" id="phone" value="${phone!0}">
    <img src="${context}/asset/mobile/img/ios-guide.png" width="100%">
</div>
<div class="an">
    <img id="btns" style="width: 80%;" src="${context}/asset/mobile/img/guide-download.png" width="100%">
</div>
</body>

<script type="text/javascript" src="${context}/asset/mobile/js/mui.min.js"></script>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>

<script type="text/javascript" src="${context}/asset/mobile/js/clipboard.min.js"></script>
<script type="text/javascript">
    var a = " ${requestCode!0}"
    console.log(a)
    $("#btns").click(function () {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            var type = 1;
            mui.alert("没有安卓端");

        }

        if (isiOS == true) {
            var type = 2;
        }
        var params = {};
        params['requestCode'] = $("#requestCode").val();
        params['reruestType'] = $("#reruestType").val();
        params['phone']=$("#phone").val();
        params['type'] = type;

        console.log(type)
        $.post("generalize/mobile/type.do", params, function (data) {
            if (data.code == '0') {
//						var u = navigator.userAgent;
                var rerutype = "${reruestType!0}";
                var rerucode = "${requestCode!0}";

            } else {
                mui.alert(data.message);
            }
        }, 'json');
        console.log(1)
        window.location.href = "itms-services://?action=download-manifest&url=https://gitee.com/gitjoslyn/myh_ipa/raw/master/manifest.plist";
    });

</script>

</html>