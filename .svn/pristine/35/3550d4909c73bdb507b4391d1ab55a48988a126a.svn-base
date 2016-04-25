<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>查看系列图</title>
    <%@ include file="inc/css.jsp" %>
    <link href="static/js/plugins/dropper/jquery.fs.dropper.css" rel="stylesheet">
    <script src="static/js/plugins/dropper/jquery.fs.dropper.js"></script>
</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">查看系列图</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>系列图信息</h4>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- 系列图信息 -->
                        <table border="0" style="width: 90%; line-height: 30px; margin-left: 8%;">
                            <tr>
                                <th style="width: 12%;">&nbsp;&nbsp;&nbsp;发布者：</th>
                                <td>${series.author }</td>
                            </tr>
                            <tr>
                                <th>图片分类：</th>
                                <td>${series.sort }</td>
                            </tr>
                            <tr>
                                <th>图文介绍：</th>
                                <td>${series.info }</td>
                            </tr>
                            <!-- 场景图 -->
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图片：</th>
                                <td>
                                    <div style="float: left;" id="lastImageDiv"></div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- /.panel-body -->

                    <%-- 隐藏的图片区域 --%>
                    <div id="tempDiv" style="display:none;float: left; height: 180px;width: 200px;margin-right:6px;">
                        <img alt="" src="" style="height: 170px;width: 200px;">
                    </div>

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-lg-12" style="text-align: center;">
                                <a href="backend/series/index" class="btn btn-primary" style="color: white;"
                                   role="button">返回</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.panel -->
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">
    var series_ = {
        v: {
            id: "series_"
        },
        fn: {
            init: function () {
                //加载所属场景图
                series_.fn.getSeriesImages();
            },
            getSeriesImages: function () {
                var imgList = ${imageList};
                if (imgList.length == 0) {
                    $('#lastImageDiv').html('暂无');
                } else {
                    $.each(imgList, function (i, item) {
                        series_.fn.insertImage(item.path);
                    });
                }
            },
            insertImage: function (imgURL) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.css("display", "block");
                tempDiv.children(":first").attr("src", imgURL);
                tempDiv.insertBefore("#lastImageDiv");
            }
        }
    }

    $(document).ready(function () {
        series_.fn.init();
    });

</script>

</html>