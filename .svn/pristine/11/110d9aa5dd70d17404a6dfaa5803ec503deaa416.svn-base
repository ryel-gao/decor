<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>查看关于网站</title>
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
                <h1 class="page-header">关于网站</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><span id="aboutWebTitle">标题</span></h4>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- 网站信息 -->
                        <form id="aboutForm" method="post" enctype="multipart/form-data">
                            <!-- 隐藏字段 -->
                            <input type="hidden" name="id" value="${aboutWeb.id}"/>
                            <input type="hidden" id="title" value="${aboutWeb.title}"/>

                            <table border="0" style="width: 90%; line-height: 50px; margin-left: 8%;">
                                <tr>
                                    <th style="width: 12%;">标题：</th>
                                    <td>${aboutWeb.title}</td>
                                </tr>
                                <tr>
                                    <th>内容：</th>
                                    <td>
                                        <!-- 加载编辑器的容器 -->
                                        <script id="container" name="content" type="text/plain"
                                                style="width:100%; height:150px; line-height: 0px;"></script>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <!-- /.panel-body -->

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-lg-12" style="text-align: center;">
                                <a href="javascript:void(0)" id="subWebForm" class="btn btn-primary"
                                   style="color: white;"
                                   role="button">确定</a>
                                &nbsp;&nbsp;
                                <a href="backend/aboutweb/index" class="btn btn-primary" style="color: white;"
                                   role="button">返回</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.panel -->

                <span style="display: none;" id="spqq">${aboutWeb.content}</span>

            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">
    var aboutweb_ = {
        v: {
            id: "aboutweb_"
        },
        fn: {
            init: function () {
                // 页面加载时，绑定显示的标题信息
                var title = $('#title').val();
                $('#aboutWebTitle').html(title);

                // 提交表单
                $('#subWebForm').click(function () {
                    aboutweb_.fn.save();
                });
            },
            save: function () {
                var flag = true;

                if (!$('#aboutForm').isValid()) {
                    flag = false;
                    return;
                }

                // 所有的验证通过后，执行修改操作
                if (flag) {
                    $("#aboutForm").ajaxSubmit({
                        url: _basePath + "backend/aboutweb/updateWebInfo",
                        dataType: "json",
                        success: function (result) {
                            if (result.status == 0) {
                                window.location.href = _basePath + "backend/aboutweb/index";
                            } else {
                                $bluemobi.notify(result.msg, "error");
                            }
                        }
                    });
                }
            }
        }
    }

    $(document).ready(function () {
        aboutweb_.fn.init();
    });

</script>

<!-- 配置文件 -->
<script type="text/javascript" src="ueditor1_4_3/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="ueditor1_4_3/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var editor1 = new baidu.editor.ui.Editor();
    editor1.render('container');
    editor1.ready(function () {
        this.setContent($("#spqq").html());
    });
</script>

</html>