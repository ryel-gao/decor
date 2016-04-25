<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>画板作品</title>
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
                <h1 class="page-header">画板作品</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div>
                            <form class="form-inline">
                                <div class="form-group">
                                    <label>ID：</label><input type="text" id="ID" class="form-control input-sm"/>
                                </div>
                                <div class="form-group">
                                    <label>发布者：</label><input type="text" id="author" class="form-control input-sm"/>
                                </div>
                                <div class="form-group">
                                    <button type="button" id="c_search" class="btn btn-info btn-sm">搜索</button>
                                </div>
                            </form>
                            <p></p>
                            <a href="javascript:void(0)" id="batchDel" class="btn btn-outline btn-danger btn-lg"
                               role="button">批量删除</a>
                        </div>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover" id="dataTables">
                                <colgroup>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd" style="width: 25%;"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$bluemobi.checkAll(this)" class="checkall"/>
                                    </th>
                                    <th>ID</th>
                                    <th>发布账户</th>
                                    <th>发布者</th>
                                    <th>创建时间</th>
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
            <!-- Modal -->
            <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="modalLabel">操作</h4>
                        </div>
                        <div class="modal-body ">
                            <form id="form1" method="post" action="backend/user/save" class="form-horizontal"
                                  role="form">

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">发布者:</label>

                                    <div class="col-sm-5">
                                        <input type="text" style="background-color: #ffffff" class="form-control" id="nickname" readonly/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">发布者账号:</label>

                                    <div class="col-sm-5">
                                        <input type="text" style="background-color: #ffffff" class="form-control" id="account" readonly/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">图片:</label>

                                    <div class="col-sm-5">
                                        <img id="img" alt="" src="" style="height: 170px;width: 200px;">
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
            <!-- /#page-wrapper -->
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

                this_.fn.dataTableInit();

                $("#c_search").click(function () {
                    this_.fn.search();
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    this_.fn.batchDeleteUser(ids);
                });
            },
            //搜索
            search: function () {
                this_.v.dTable.ajax.reload();
                $(":checkbox").removeAttr("checked");
            },
            dataTableInit: function () {
                this_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/drawBoard/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "id"},
                        {"data": "user.account"},
                        {"data": "user.nickname"},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [{
                        "data": null,
                        "defaultContent": "<button type='button' title='查看' class='btn btn-primary btn-circle shows'>" + "<i class='fa fa-eye'></i>" + "</button>" + "&nbsp;&nbsp;" + "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" + "<i class='fa fa-trash-o'></i>" + "</button>",
                        "targets": -1
                    }],
                    "createdRow": function (row, data) {
                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");
                    },
                    rowCallback: function (row, data) {

                        //查看操作
                        $('td', row).last().find(".shows").click(function () {
                            this_.fn.showModal("查看画板作品");
                            $("#nickname").val(data.user.nickname);
                            $("#account").val(data.user.account);
                            $("#img").prop('src', data.image);
                        });

                        // 单个用户删除操作
                        $('td', row).last().find(".delete").click(function () {
                            this_.fn.deleteUser(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.id = $("#ID").val();
                        aoData.author = $("#author").val();
                        aoData.sort = $("#tagList").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            deleteUser: function (id) {
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/drawBoard/delete", {
                        id: id
                    }, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            this_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    });
                }, "确定删除该画板么？", "确定");
            },
            batchDeleteUser: function (ids) {
                if (ids.length > 0) {
                    $bluemobi.optNotify(function () {
                        $bluemobi.ajax("backend/drawBoard/deletes", {
                            ids: JSON.stringify(ids)
                        }, function (result) {
                            if (result.status == "0") {
                                $bluemobi.notify(result.msg, "success");
                            } else {
                                $bluemobi.notify(result.msg, "error");
                            }
                            this_.v.dTable.ajax.reload();
                            $(":checkbox").removeAttr("checked");
                        })
                    }, "确认将选中项批量删除？", "确定");
                }
            },
            showModal: function (title) {
                $("#modal").modal("show");
                $bluemobi.clearForm($("#modal"));
                if (title) {
                    $("#modal .modal-title").text(title);
                }
            }
        }
    }

    $(document).ready(function () {
        this_.fn.init();
    });

</script>

</html>