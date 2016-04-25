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
    <title>图片风格分类管理</title>
    <%@ include file="inc/css.jsp" %>

</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">风格标签管理</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="btn-group pull-left">
                            <a href="javascript:void(0)" id="new" class="btn btn-outline btn-primary btn-lg"
                               role="button">新增</a>
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
                                    <th>标签名称</th>
                                    <th>标签英文名称</th>
                                    <th>图片数</th>
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
    <div class="modal fade" id="styleTagModal" tabindex="-1" role="dialog" aria-labelledby="styleTagModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="styleTagModalLabel">操作</h4>
                </div>
                <div class="modal-body ">
                    <form id="styleTagForm" method="post" action="backend/styleTag/save" class="form-horizontal"
                          role="form">
                        <input type="hidden" id="id" name="id" value="">
                        <input type="hidden" id="level" name="level" value="">

                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">名称:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name" name="name" maxlength="50"
                                       data-rule="required" placeholder="请输入名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">英文:</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="englishName" name="englishName"
                                       maxlength="50"
                                       data-rule="required" placeholder="请输入英文名称">
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="save" class="btn btn-primary">保存</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- Modal end -->

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var styletag_ = {
        v: {
            id: "styletag_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {

                $("#new").click(function () {
                    styletag_.fn.showModal("新增");
                })

                $("#save").click(function () {
                    styletag_.fn.save();
                })

                // 查询
                $("#c_search").click(function () {
                    styletag_.v.dTable.ajax.reload();
                })

                styletag_.fn.dataTableInit();
            },
            dataTableInit: function () {
                styletag_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/styleTag/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "englishName"},
                        {"data": "num"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        styletag_.v.list.push(data);

                        var option = "<button type='button'  title='编辑' class='btn btn-primary btn-circle edit'>" +
                                "<i class='fa fa-edit'></i>" +
                                "</button>"
                                + "&nbsp;" +
                                "<button type='button'  title='删除' class='btn btn-danger btn-circle delete'>" +
                                "<i class='fa fa-bitbucket'></i>" +
                                "</button>";
                        $('td', row).last().html(option);
                    },
                    rowCallback: function (row, data) {
                        var items = styletag_.v.list;
                        $('td', row).last().find(".edit").click(function () {
                            styletag_.fn.edit(data.id);
                        });
                        $('td', row).last().find(".delete").click(function () {
                            styletag_.fn.deleteRow(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            deleteRow: function (id) {
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/styleTag/delete", {id: id}, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            styletag_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    })
                });
            },
            edit: function (id) {
                styletag_.fn.showModal("修改");
                var items = styletag_.v.list;
                $.each(items, function (index, item) {
                    if (item.id == id) {
                        for (var key in item) {
                            var element = $("#styleTagForm :input[name=" + key + "]")
                            if (element.length > 0) {
                                element.val(item[key]);
                            }
                        }
                    }
                })
            },
            save: function () {
                if (!$('#styleTagForm').isValid()) {
                    return false;
                }
                ;
                $("#level").val(1);
                $("#styleTagForm").ajaxSubmit({
                    dataType: "json",
                    success: function (result) {
                        styletag_.fn.responseComplete(result, true);
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        styletag_.v.dTable.ajax.reload(null, false);
                    } else {
                        styletag_.v.dTable.ajax.reload();
                    }

                    $bluemobi.notify(result.msg, "success");
                    $("#styleTagModal").modal("hide");
                } else {
                    $bluemobi.notify(result.msg, "error");
                }
            },
            showModal: function (title) {
                $("#styleTagModal").modal("show");
                $bluemobi.clearForm($("#styleTagModal"));
                if (title) {
                    $("#styleTagModal .modal-title").text(title);
                }
            }
        }
    }

    $(document).ready(function () {
        styletag_.fn.init();
    });

</script>


</html>