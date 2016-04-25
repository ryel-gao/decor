<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>资讯列表</title>
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
                <h1 class="page-header">资讯列表</h1>
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
                                    <label>文章标题：</label><input type="text" id="title" class="form-control input-sm"/>
                                </div>
                                <div class="form-group">
                                    <label>栏目：</label><select id="tagId" name="tagId" class="form-control"></select>
                                </div>
                                <div class="form-group">
                                    <button type="button" id="c_search" class="btn btn-info btn-sm">搜索</button>
                                </div>
                            </form>
                            <p></p>
                            <a href="javascript:void(0)" id="AddInfo" class="btn btn-outline btn-success btn-lg"
                               role="button">添加文章</a>
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
                                    <col class="gradeA odd"/>
                                    <col class="gradeA even"/>
                                    <col class="gradeA odd"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" onclick="$bluemobi.checkAll(this)" class="checkall"/>
                                    </th>
                                    <th>标题</th>
                                    <th>栏目</th>
                                    <th>序列号</th>
                                    <th>发布时间</th>
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
</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp" %>
</body>

<script type="text/javascript">

    var message_ = {
        v: {
            id: "message_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 页面加载时，自动加载栏目列表信息
                message_.fn.getTagList();

                message_.fn.dataTableInit();

                $("#c_search").click(function () {
                    message_.fn.search();
                });

                $("#AddInfo").click(function () {
                    // 点击跳转到添加文章界面
                    location.href = _basePath + "backend/message/add";
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    message_.fn.batchDelete(ids);
                });
            },
            //搜索
            search: function () {
                message_.v.dTable.ajax.reload();
                $(":checkbox").removeAttr("checked");
            },
            dataTableInit: function () {
                message_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/message/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "title"},
                        {"data": "tagName"},
                        {"data": "showTurn"},
                        {"data": "createTime"},
                        {"data": ""}
                    ],
                    "columnDefs": [{
                        "data": null,
                        "defaultContent": "<button type='button' title='推荐' class='btn btn-success btn-circle edit'>" + "<i class='fa fa-check-circle-o tuiJan'></i>" + "</button>" + "&nbsp;&nbsp;" + "<button type='button' title='查看' class='btn btn-primary btn-circle shows'>" + "<i class='fa fa-eye'></i>" + "</button>" + "&nbsp;&nbsp;" + "<button type='button' title='删除' class='btn btn-danger btn-circle delete'>" + "<i class='fa fa-trash-o deleteStatus'></i>" + "</button>",
                        "targets": -1
                    }],
                    "createdRow": function (row, data, index) {
                        message_.v.list.push(data);
                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");
                    },
                    rowCallback: function (row, data) {
                        //渲染样式
                        if (data.isRecommend == "yes") {
                            $('td', row).last().find(".edit").addClass("btn-danger");
                            $('td', row).last().find(".edit").attr("title", "取消推荐");
                            $('td', row).last().find(".tuiJan").removeClass().addClass("fa fa-ban");
                        } else {
                            $('td', row).last().find(".edit").addClass("btn-success");
                            $('td', row).last().find(".edit").attr("title", "推荐");
                            $('td', row).last().find(".tuiJan").removeClass().addClass("fa fa-check-circle-o");
                        }

                        //查看操作
                        $('td', row).last().find(".shows").click(function () {
                            location.href = _basePath + "backend/message/shows?id=" + data.id;
                        });

                        // 单个用户删除操作
                        $('td', row).last().find(".delete").click(function () {
                            message_.fn.deleteInfo(data.id);
                        });

                        // 推荐/取消推荐操作
                        $('td', row).last().find(".edit").click(function () {
                            var msg = "";
                            var status = "";
                            if (data.isRecommend == "yes") {
                                msg = "取消推荐";
                                status = "no";
                            } else {
                                msg = "推荐";
                                status = "yes";
                            }
                            message_.fn.changeRecommend(data.id, status, msg);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.title = $("#title").val();
                        aoData.tagId = $("#tagId").val();
                        aoData.show_turn=$("#show_turn").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            changeRecommend: function (id, status, msg) {
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/message/changeRecommend", {
                        id: id,
                        isRecommend: status
                    }, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            message_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    });
                }, "确定" + msg + "这条资讯么？", "确定");
            },
            getTagList: function () {
                $bluemobi.ajax("backend/utils/messageTag/list", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#tagId').append(content);
                    } else {
                        $bluemobi.notify("获取分类信息失败", "error");
                    }
                });
            },
            deleteInfo: function (id) {
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/message/delete", {
                        id: id
                    }, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            message_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    });
                }, "确定删除该资讯么？", "确定");
            },
            batchDelete: function (ids) {
                if (ids.length > 0) {
                    $bluemobi.optNotify(function () {
                        $bluemobi.ajax("backend/message/batchDelete", {
                            ids: JSON.stringify(ids)
                        }, function (result) {
                            if (result.status == "0") {
                                $bluemobi.notify(result.msg, "success");
                            } else {
                                $bluemobi.notify(result.msg, "error");
                            }
                            message_.v.dTable.ajax.reload();
                            $(":checkbox").removeAttr("checked");
                        })
                    }, "确认将选中项批量删除？", "确定");
                }
            }
        }
    }

    $(document).ready(function () {
        message_.fn.init();
    });

</script>

</html>