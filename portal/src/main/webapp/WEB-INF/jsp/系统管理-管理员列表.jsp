<%--
Created by IntelliJ IDEA.
User: gaoll
Date: 2015/3/3
Time: 11:58
To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>管理员列表</title>
    <%@ include file="inc/css.jsp" %>

</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">管理员列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="btn-group pull-left">
                            <a href="javascript:void(0)" id="new" class="btn btn-outline btn-primary btn-lg"
                               role="button">新增管理员
                            </a>
                        </div>
                        <div class="navbar-form pull-right">
                            <label>姓名：</label><input type="text" id="c_nickname" class="form-control input-sm">
                            <label>账号：</label><input type="text" id="c_account" class="form-control input-sm">
                            <button type="button" id="search" class="btn btn-default btn-sm">查询</button>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>账号</th>
                                    <th>权限</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
        </div>


    </div>
    <!-- /#page-wrapper -->

    <!-- Modal -->
    <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="modalLabel">操作</h4>
                </div>
                <div class="modal-body ">
                    <form id="form1" method="post" action="backend/authority/saveAdmin" class="form-horizontal"
                          role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="nickname" name="nickname" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">账号:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="account" name="account" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">密码:</label>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" id="password" name="password" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确定密码:</label>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" id="verifyPassword" name="verifyPassword"
                                       maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="hidden" id="roleId" name="roleId" value="">
                            <label class="col-sm-2 control-label">权限:</label>

                            <div class="col-sm-5">
                                <select class="form-control roleList"></select>
                            </div>
                        </div>


                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="save" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal end -->

    <!-- Modal -->
    <div class="modal fade" id="editmodal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="editmodalLabel">操作</h4>
                </div>
                <div class="modal-body ">
                    <form id="form2" method="post" action="backend/authority/saveAdmin" class="form-horizontal"
                          role="form">
                        <input type="hidden" id="id" name="id" value="">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="nickname" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">账号:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="account" maxlength="50"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">密码:</label>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="password" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确定密码:</label>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="verifyPassword" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="roleId" value="">
                            <label class="col-sm-2 control-label">权限:</label>

                            <div class="col-sm-5">
                                <select class="form-control roleList1"></select>
                            </div>
                        </div>


                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="edit" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal end -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var this_ = {
        v: {
            id: "this_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {

                $("#new").click(function () {
                    this_.fn.showModal("新增管理员");
                });

                $("#save").click(function () {
                    this_.fn.save();
                });

                $("#edit").click(function () {
                    this_.fn.editSave();
                });

                // 查询
                $("#search").click(function () {
                    this_.v.dTable.ajax.reload();
                });

                this_.fn.ajaxRoleList();
                $(".roleList").unbind("change").change(function () {
                    var roleId = $(".roleList").val();
                    $("#roleId").val(roleId);
                });
                $(".roleList1").unbind("change").change(function () {
                    var roleId = $(".roleList1").val();
                    $("#form2 :input[name=roleId]").val(roleId);
                });

                this_.fn.dataTableInit();

            },
            dataTableInit: function () {
                this_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/authority/adminList",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "nickname"},
                        {"data": "account"},
                        {"data": "role.name", "defaultContent": "无"},
                        {"data": ""}
                    ],
                    "columnDefs": [{"data": null, "defaultContent": "", "targets": -1}
                    ],
                    "createdRow": function (row, data, index) {
                        this_.v.list.push(data);

                        var optionHtml = "";
                        optionHtml +=
                                "<button type='button'  title='编辑'  class='btn btn-primary btn-circle edit'>" +
                                "<i class='fa fa-edit'></i>" +
                                "</button>";
                        $('td', row).last().html(optionHtml);
                    },
                    rowCallback: function (row, data) {
                        var items = this_.v.list;
                        $('td', row).last().find(".edit").click(function () {
                            this_.fn.edit(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.nickname = $("#c_nickname").val();
                        aoData.account = $("#c_account").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            edit: function (id) {
                this_.fn.showEditModal("修改");
                var items = this_.v.list;
                $.each(items, function (index, item) {
                    if (item.id == id) {
                        for (var key in item) {
                            var element = $("#form2 :input[name=" + key + "]")
                            if (element.length > 0) {
                                element.val(item[key]);
                            }
                            if (key == "password") {
                                $("#form2 :input[name=verifyPassword]").val(item[key]);
                            }
                            if (key == "role" && item[key]) {
                                var roleId = item[key].id;
                                $("#form2 :input[name=roleId]").val(roleId);
                                $("#form2").find(".roleList1").find("option[value='" + roleId + "']").attr("selected", true);
                            } else {
                                $("#form2").find(".roleList").find("option").eq(0).attr("selected", true);
                            }
                            $("#roleList").trigger("change");
                        }

                    }
                })
            },
            save: function () {
                if (!$('#form1').isValid()) {
                    return false;
                }
                ;
                if ($('#password').val() != $('#verifyPassword').val()) {
                    $bluemobi.notify("2次输入的密码不一致", "error");
                    return false;
                }
                $("#form1").ajaxSubmit({
                    dataType: "json",
                    success: function (result) {
                        this_.fn.responseComplete(result, true);
                    }
                })
            },
            editSave: function () {
                if (!$('#form2').isValid()) {
                    return false;
                }
                ;
                if ($('#password').val() != $('#verifyPassword').val()) {
                    $bluemobi.notify("2次输入的密码不一致", "error");
                    return false;
                }
                $("#form2").ajaxSubmit({
                    dataType: "json",
                    success: function (result) {
                        this_.fn.responseComplete(result, true);
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        this_.v.dTable.ajax.reload(null, false);
                    } else {
                        this_.v.dTable.ajax.reload();
                    }

                    $bluemobi.notify(result.msg, "success");
                    $("#modal").modal("hide");
                    $("#editmodal").modal("hide");
                } else {
                    $bluemobi.notify(result.msg, "error");
                }
            },
            showModal: function (title) {
                $("#modal").modal("show");
                $bluemobi.clearForm($("#modal"));
                if (title) {
                    $("#modal .modal-title").text(title);
                }
            },
            showEditModal: function (title) {
                $("#editmodal").modal("show");
                $bluemobi.clearForm($("#editmodal"));
                if (title) {
                    $("#modal .modal-title").text(title);
                }
            },
            ajaxRoleList: function () {
                $bluemobi.ajax("backend/authority/listRole", {}, function (result) {
                    if (result.status == "0") {
                        var html = '<option value="">无</option>';
                        for (var i = 0; i < result.data.length; i++) {
                            var role = result.data[i];
                            html += '<option value="' + role.id + '">' + role.name + '</option>';
                        }
                        $(".roleList").html(html);
                        $(".roleList1").html(html);
                    }
                })
            }
        }
    };

    $(document).ready(function () {
        this_.fn.init();
    });

</script>


</html>