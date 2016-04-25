<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>查看商品图</title>
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
                <h1 class="page-header">查看商品图</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>商品图信息</h4>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- 系列图信息 -->
                        <table border="0" style="width: 90%; line-height: 30px; margin-left: 3%;">
                            <tr>
                                <th style="width: 12%;">&nbsp;&nbsp;&nbsp;商品名称：</th>
                                <td>${goods.name}</td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;商品分类：</th>
                                <td>${goods.kindTagName}</td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;风格标签：</th>
                                <td>
                                    <c:forEach items="${goods.styleTagName}" var="stName">
                                        ${stName}&nbsp;&nbsp;
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;空间标签：</th>
                                <td>
                                    <c:forEach items="${goods.spaceTagName}" var="spName">
                                        ${spName}&nbsp;&nbsp;
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价格($)：</th>
                                <td>${goods.price}</td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;尺寸：</th>
                                <td><textarea class="form-control"
                                              style="width: 419px; height: 113px;background-color: #F6F6F6;resize:none;"
                                              disabled>${goods.size}</textarea></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td><h4 style=""><span style="display: none;"></span></h4></td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;商品材质：</th>
                                <td><textarea class="form-control"
                                              style="width: 419px; height: 113px;background-color: #F6F6F6;resize:none;"
                                              disabled>${goods.texture}</textarea></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td><h4 style=""><span style="display: none;"></span></h4></td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;商品描述：</th>
                                <td><textarea class="form-control"
                                              style="width: 419px; height: 113px;background-color: #F6F6F6;resize:none;"
                                              disabled>${goods.info}</textarea></td>
                            </tr>
                            <tr>
                                <th></th>
                                <td><h4 style=""><span style="display: none;"></span></h4></td>
                            </tr>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;购买链接：</th>
                                <td><a href="${goods.link}" target="_blank">${goods.link}</a></td>
                            </tr>
                            <!-- 商品图 -->
                            <tr id="trh">
                                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图片：
                                    <!-- 图片显示处理 -->
                                    <div id="bh"></div>
                                </th>
                                <td>
                                    <!-- 图片显示处理 -->
                                    <div id="gd"></div>
                                    <div style="float: left;" id="lastImageDiv"></div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- /.panel-body -->

                    <!-- goodsImgModal -->
                    <div class="modal fade" id="goodsImgModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <h4 class="modal-title"></h4>
                                </div>
                                <div class="modal-body ">
                                    <form method="post" action="#" class="form-horizontal"
                                          role="form">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label"></label>

                                            <div>
                                                <img id="goodsImg" alt="" src="" style="height: 340px;width: 400px;">
                                            </div>
                                        </div>
                                    </form>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- goodsImgModal end -->

                    <%-- 隐藏的图片区域 --%>
                    <div id="tempDiv" style="display:none;float: left; height: 180px;width: 200px;margin-right:6px;">
                        <img alt="" src="" style="height: 170px;width: 200px;cursor: pointer;"
                             onclick="showBigPic(this)">

                        <div style="display:none;background-color: #ff0000;color: #ffffff;border-radius: 5px;height: 25px;width:60px;line-height: 25px;float: left;text-align: center;margin-top: -25px;z-index: 1;position:relative;">
                            封面图
                        </div>
                        <div style="display:none;background-color: #0066cc;color: #ffffff;border-radius: 5px;height: 25px;width:60px;line-height: 25px;float: right;text-align: center;margin-top: -25px;z-index: 1;position:relative;">
                            素材图
                        </div>
                    </div>

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-lg-12" style="text-align: center;">
                                <a href="backend/goods/index" class="btn btn-primary" style="color: white;"
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
    var this_ = {
        v: {
            id: "this_"
        },
        fn: {
            init: function () {
                //加载所属商品图
                this_.fn.getGoodsImages();
            },
            getGoodsImages: function () {
                var imgList = ${imageList};
                if (imgList.length == 0) {
                    $('#lastImageDiv').html('暂无');
                } else {
                    $.each(imgList, function (i, item) {
                        this_.fn.insertImage(item.path, item.isCover, item.isMaterial);
                    });
                }
            },
            insertImage: function (imgURL, isCover, isMaterial) {
                var tempDiv = $("#tempDiv").clone();
                tempDiv.css("display", "block");
                tempDiv.children(":first").prop("src", imgURL);
                tempDiv.children(":first").next().css("display", "none");
                tempDiv.children(":first").next().next().css("display", "none");
                if (isCover == 1) {
                    tempDiv.children(":first").next().css("display", "block");
                }
                if (isMaterial == 1) {
                    tempDiv.children(":first").next().next().css("display", "block");
                }
                tempDiv.insertBefore("#lastImageDiv");
            },
            showGoodsImg: function (title) {
                $("#goodsImgModal").modal("show");
                $bluemobi.clearForm($("#goodsImgModal"));
                if (title) {
                    $("#goodsImgModal .modal-title").text(title);
                }
            }

        }
    }

    $(document).ready(function () {
        this_.fn.init();
        //图片显示处理
        var trHeight = document.getElementById('trh').clientHeight;
        document.getElementById("bh").style.height = (trHeight - 30) + "px";
        document.getElementById("bh").style.width = "108px";
        if (trHeight - 30 != 0) {
            document.getElementById("gd").style.height = (15) + "px";
        }
    });

    function showBigPic(img) {
        var src = img.src;
        document.getElementById("goodsImg").src = src;
        this_.fn.showGoodsImg();
    }

</script>

</html>