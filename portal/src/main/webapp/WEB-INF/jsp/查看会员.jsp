<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>查看会员</title>
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
                <h1 class="page-header">查看会员</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>会员资料</h4>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- 会员信息 -->
                        <form id="userShowInfo" action="backend/user/updateUserInfo" method="post">
                            <!-- 隐藏字段 -->
                            <input type="hidden" id="userId" name="id" value="${user.id }"/>
                            <input type="hidden" id="provinceId" value="${provinceId }"/>
                            <input type="hidden" id="cityId" value="${cityId }"/>

                            <table border="0" style="width: 90%; line-height: 40px; margin-left: 8%;">
                                <tr>
                                    <th style="width: 12%;">&nbsp;账号：</th>
                                    <td>${user.account }</td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>呢称：</th>
                                    <td><input type="text" class="form-control" id="nickname" name="nickname" maxlength="20" style="width: 50%;" value="${user.nickname }"/></td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>密码：</th>
                                    <td><input type="password" class="form-control" id="password" name="password" data-rule="密码: required; !digits; length[6~15]" maxlength="15" data-msg-digits="{0}不能使用纯数字" data-msg-length="{0}请输入6~15位" style="width: 50%;" value="${user.password }"/></td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>确认密码：</th>
                                    <td><input type="password" class="form-control" id="quepassword" name="quepassword" data-rule="确认密码: match[password]" style="width: 50%;" value="${user.password }"/></td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>性别：</th>
                                    <td>
                                        <input id="nan" type="radio" name="sex" <c:if test="${user.sex == 0}">checked</c:if> value="0" />
                                        <label for="nan">男</label> &nbsp;&nbsp;
                                        <input id="nv" type="radio" name="sex" <c:if test="${user.sex == 1}">checked</c:if> value="1" />
                                        <label for="nv">女</label> &nbsp;&nbsp;
                                        <input id="bm" type="radio" name="sex" <c:if test="${user.sex == 2}">checked</c:if> value="2" />
                                        <label for="bm">保密</label>
                                    </td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>联系方式：</th>
                                    <td><input type="text" class="form-control" id="mobile" name="mobile" maxlength="11" data-rule="required;integer;" style="width: 50%;" value="${user.mobile }"/></td>
                                </tr>
                                <tr>
                                    <th><span style="color: red;">*</span>所在城市：</th>
                                    <td>
                                        <select id="provinceList" name="provinceList" style="margin-top: 5px; width: 20%; float: left;" class="form-control"></select>
                                        <select id="cityList" name="cityList" style="margin-top: 5px; width: 20%; float: left; margin-left: 10px;" class="form-control"></select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>&nbsp;个人主页：</th>
                                    <td>${user.website }</td>
                                </tr>
                                <tr>
                                    <th>&nbsp;个人简介：</th>
                                    <td>${user.info }</td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <!-- /.panel-body -->

                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-lg-12" style="text-align: center;">
                                <a href="javascript:void(0)" id="save" class="btn btn-primary" style="color: white;" role="button">确定</a>
                                &nbsp;&nbsp;
                                <a href="backend/user/index" class="btn btn-primary" style="color: white;" role="button">返回</a>
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
    var user_ = {
        v: {
            id: "user_"
        },
        fn: {
            init: function () {
                // 获取当前会员的省份ID
                var provinceId = $("#provinceId").val();
                // 页面加载时，自动加载省份信息
                user_.fn.getProvince();
                // 页面加载时，自动加载城市信息
                user_.fn.getCity(provinceId);

                // 当省份更改时，自动加载对应的城市信息
                $('#provinceList').change(function() {
                    var id = $('#provinceList').val();
                    user_.fn.getCity(id);
                });

                // 点击确定按钮，执行表单提交操作
                $('#save').click(function() {
                    user_.fn.updateInfo();
                });
            },
            updateInfo : function() {
                var flag = true;

                if (!$('#userShowInfo').isValid()) {
                    flag = false;
                    return;
                }

                if (flag) {
                    $("#userShowInfo").ajaxSubmit({
                        dataType: "json",
                        data: {
                            "cityId": $("#cityList").val()
                        },
                        success: function (result) {
                            if (null != result) {
                                $bluemobi.notify(result.msg, "success");
                            } else {
                                $bluemobi.notify(result.msg, "error");
                            }
                        }
                    });
                }
            },
            getProvince: function () {
                $("#provinceList").find("option").remove();
                $bluemobi.ajax("backend/utils/province/list", null, function (result) {
                    if (null != result) {
                        // 获取返回的省份列表信息，并循环绑定到label中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option  value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#provinceList').append(content);
                        var id = $("#provinceId").val();
                        $("#provinceList option").each(function () {
                            if ($(this).val() == id) {
                                $(this).attr("selected", true);
                            }
                        });
                    } else {
                        $bluemobi.notify("获取省份信息失败", "error");
                    }
                });
            },
            getCity: function (provinceId) {
                $("#cityList").find("option").remove();
                $bluemobi.ajax("backend/utils/city/list", {
                    provinceId: provinceId
                }, function (result) {
                    if (null != result) {
                        // 获取返回的城市列表信息，并循环绑定到label中
                        var content = "";
                        jQuery.each(result, function (i, item) {
                            content += "<option  value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#cityList').append(content);
                        var id = $("#cityId").val();
                        $("#cityList option").each(function () {
                            if ($(this).val() == id) {
                                $(this).attr("selected", true);
                            }
                        });
                    } else {
                        $bluemobi.notify("获取城市信息失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        user_.fn.init();
    });

</script>

</html>