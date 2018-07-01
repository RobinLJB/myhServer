<@override name="head">
<link href="${context}/asset/public/plugins/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<#--<style>
 .bordered.dataTable tbody th,
 table.table-bordered.dataTable tbody td {
  vertical-align:middle;
}
</style>-->
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
<input type="hidden" id="MENU_ACTIVE_ID" value="cms:banner"/>
<div class="page-content">
    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="#">内容管理</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a href="#">轮播图管理</a>
            </li>
        </ul>
        <div class="page-toolbar">
        </div>
    </div>
    <h3 class="page-title">
        轮播图列表 <h4>（最佳750*320像素，图片大小控制在100k以内）</h4>
    </h3>
    <table id="imageTable" class="table table-striped table-bordered">
        <tr>
            <th>轮播图A：</th>
            <td>
                <#if bannerImgs.path1??>
                    <ul id="loanImgs">
                        <li><img src="${bannerImgs.path1!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
            <td>
                <button type="button" id="uploadImgA" class="btn btn-primary btn-xs" data-btn-type="selectIcon">
                    <i class="fa fa-mail-forward">&nbsp;上传图片</i>
                </button>
            </td>
        </tr>
        <tr>
            <th>轮播图B：</th>
            <td>
                <#if bannerImgs.path2??>
                    <ul id="loanImgs">
                        <li><img src="${bannerImgs.path2!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
            <td>
                <button type="button" id="uploadImgB" class="btn btn-primary btn-xs" data-btn-type="selectIcon">
                    <i class="fa fa-mail-forward">&nbsp;上传图片</i>
                </button>
            </td>

        </tr>
        <tr>
            <th>轮播图C：</th>
            <td>
                <#if bannerImgs.path3??>
                    <ul id="loanImgs">
                        <li><img src="${bannerImgs.path3!}"/></li>
                    </ul>
                <#else>
                    <span class="label label-warning">无图片</span>
                </#if>
            </td>
            <td>
                <button type="button" id="uploadImgC" class="btn btn-primary btn-xs" data-btn-type="selectIcon">
                    <i class="fa fa-mail-forward">&nbsp;上传图片</i>
                </button>
            </td>
        </tr>


        </tbody>
    </table>
</div>
</@override>
<@override name="script">

<script src="${context}/asset/public/plugins/datatables/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/datatables/js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${context}/asset/public/plugins/jquery.ocupload-1.1.2.js" type="text/javascript"></script>
<script type="text/javascript">
    //调用OCUpload插件的方法
    $("#uploadImgA").upload({
        //后台传入ID图片
        action: "${context}/admin/cms/bannerImgA/upload.html",
        name: "myFile",

        onComplete: function (data) { //提交表单之后
            alert(data);
            if (data == "0") {
                spark.redirect('${context}/admin/cms/banner.html');
                alert("上传成功");
                //spark.notify(resp.message, '导入成功');//提示框，Excel导入成功
            } else {
                spark.redirect('${context}/admin/cms/banner.html');
                alert("上传失败"); //提示框，Excel导入成失败
            }
        },
        onSelect: function () {//当用户选择了一个文件后触发事件
            //当选择了文件后，关闭自动提交
            this.autoSubmit = true;
            this.submit();
        }
    });
</script>
<script type="text/javascript">
//调用OCUpload插件的方法
$("#uploadImgB").upload({
    //后台传入ID图片
    action: "${context}/admin/cms/bannerImgB/upload.html",
    name: "myFile",

    onComplete: function (data) { //提交表单之后
        alert(data);
        if (data == "0") {
            spark.redirect('${context}/admin/cms/banner.html');
            alert("上传成功");
            //spark.notify(resp.message, '导入成功');//提示框，Excel导入成功
        } else {
            spark.redirect('${context}/admin/cms/banner.html');
            alert("上传失败"); //提示框，Excel导入成失败
        }
    },
    onSelect: function () {//当用户选择了一个文件后触发事件
        //当选择了文件后，关闭自动提交
        this.autoSubmit = true;
        this.submit();
    }
});
</script>
<script type="text/javascript">
    //调用OCUpload插件的方法
    $("#uploadImgC").upload({
        //后台传入ID图片
        action: "${context}/admin/cms/bannerImgC/upload.html",
        name: "myFile",

        onComplete: function (data) { //提交表单之后
            alert(data);
            if (data == "0") {
                spark.redirect('${context}/admin/cms/banner.html');
                alert("上传成功");
                //spark.notify(resp.message, '导入成功');//提示框，Excel导入成功
            } else {
                spark.redirect('${context}/admin/cms/banner.html');
                alert("上传失败"); //提示框，Excel导入成失败
            }
        },
        onSelect: function () {//当用户选择了一个文件后触发事件
            //当选择了文件后，关闭自动提交
            this.autoSubmit = true;
            this.submit();
        }
    });
</script>
</@override>
<@layout name="/admin/layout/main.ftl" />