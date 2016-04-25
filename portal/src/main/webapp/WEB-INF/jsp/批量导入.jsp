<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <title>批量导入</title>
    <%@ include file="inc/css.jsp" %>

    <style type="text/css">
        <!--
        body {
            margin: 0;
        }

        input {
            border: 0;
            padding: 0;
            margin: 0;
        }

        .div {
            margin: 0 auto;
            width: 100%;
            overflow: hidden;
            padding: 20px 0;
        }

        .line {
            position: relative;
            margin: 0 auto;
            width: 300px;
            text-align: left
        }

        .line span.span {
            float: left;
            padding-top: 2px;
        }

        .file {
            position: absolute;
            left: 0;
            width: 250px;
            top: 0;
            height: 28px;
            filter: alpha(opacity=0);
            opacity: 0;
            cursor: pointer
        }

        .file1 {
            float: left;
            margin-left: 8px;
            z-index: 1;
            width: 66px;
            height: 28px;
            line-height: 28px;
            background: url(static/images/liulan.gif) no-repeat 0 0;
            text-indent: -9999px;
            cursor: pointer
        }

        .rysubmit {
            font-size: 12px;
            float: left;
            margin-left: 8px;
            z-index: 1;
            width: 70px;
            height: 28px;
            line-height: 28px;
            cursor: pointer;
            border-radius: 5px;
            color: #777777;
        }

        .inputstyle {
            border: 1px solid #BEBEBE;
            width: 170px;
            float: left;
            height: 23px;
            line-height: 23px;
            background: #FFF;
            z-index: 99
        }

        #n {
            margin: 10px auto;
            width: 920px;
            border: 1px solid #CCC;
            font-size: 14px;
            line-height: 30px;
        }

        #n a {
            padding: 0 4px;
            color: #333
        }

        -->
    </style>
    <script>
        /*清除刷新页面缓存*/
        $(function () {
            clear();
        });

        /*初始化控件*/
        function clear() {
            $("#viewfile").val('');
            $("#upload").val('');
            $("#sceneIndex").val('');
            $("#goodsIndex").val('');
        }

        function check() {
            var uploadFile = document.getElementById("upload").files;
            if (uploadFile.length < 1) {
                alert("请选择一个zip上传！");
                return false;
            }
//            if (uploadFile[0].type != ("application/zip")) {
//                alert("抱歉，您上传的文件格式错误！请选择一个zip上传");
//                return false;
//            }
//            if (uploadFile[0].size > 510000000) {
//                alert("请选择小于510000000个字节(约500M)的文件上传！");
//                return false;
//            }
            uploadF();
        }

        function uploadF() {
            $("#exId").submit();
        }
        //初始化
        var sceneIndex = 0;
        var goodsIndex = 0;

        function loadCode(code) {

            if (code == 1 || code == 0) {
                var url = _basePath + "backend/goods/batchImport";
                var reqData = {
                    status: code
                };
                if (sceneIndex == 0) {
                    reqData.sceneIndex = 0;
                } else {
                    reqData.sceneIndex = sceneIndex;
                }
                if (goodsIndex == 0) {
                    reqData.goodsIndex = 0;
                } else {
                    reqData.goodsIndex = goodsIndex;
                }
                var $div = $("#messageInfo");
                $.ajax({
                    url: url,
                    type: "POST",
                    data: reqData,
                    dataType: "JSON",
                    success: function (messInfo) {
                        var $p = $("<p style='font-size: 14px;color: " + messInfo.color + ";margin-left:20px;'>" + messInfo.message + "</p>");
                        $p.appendTo($div);
                        scrollDiv();
                        if (messInfo.sceneIndex != null) {
                            sceneIndex = messInfo.sceneIndex;
                        }
                        if (messInfo.goodsIndex != null) {
                            goodsIndex = messInfo.goodsIndex;
                        }
                        bianli(messInfo.code, messInfo.color);
                    },
                    error: function () {
                        loadCode(0);
                        alert("抱歉，服务器错误");
                    }
                });
            }
        }
        function bianli(mesCode, mesColor) {
            if (mesCode == 1) {//验证code有效性是否继续遍历
                loadCode(1);
            } else if (mesCode == 0 && mesColor == 'green') {//验证code有效性是否继续遍历
                loadCode(0);
            } else {
                return false;
            }
        }

        //定位滚动条
        function scrollDiv() {
            var ex = document.getElementById("messageInfo");
            ex.scrollTop = ex.scrollHeight;
        }
    </script>
</head>

<body onload="javascript:loadCode(${info.code})">

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper" align="center">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">批量导入</h1>
            </div>
        </div>

        <form id="exId" action="backend/goods/checkZip" method="post" enctype="multipart/form-data">
            <div class="div">
                <div class="line">
    	        <span class="span">
        	    <input name="" type="text" id="viewfile"
                       onmouseout="document.getElementById('upload').style.display='none';" class="inputstyle"/>
                </span>
                    <label for="unload" onmouseover="document.getElementById('upload').style.display='block';"
                           class="file1">浏览...</label>
                    <input type="file" accept="application/zip"
                           onchange="document.getElementById('viewfile').value=this.value;this.style.display='none';"
                           class="file" id="upload" name="upload"/>
                </div>
                <input class="rysubmit" type="button" onclick="check()" value="检查并上传"/>
            </div>
        </form>
        <div id="messageInfo" align="left"
             style="width: 800px;height: 350px;border: solid;border-color: #00b7ee;overflow-y: auto; overflow-x:hidden;">
            <p style="font-size: 14px;color: ${info.color};">${info.message}</p>
        </div>
    </div>
    <%@ include file="inc/footer.jsp" %>
</div>
</body>
</html>