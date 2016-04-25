<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>系列图列表</title>
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
                <h1 class="page-header">系列图列表</h1>
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
                                    <label style="width:33%;">分类：</label><select id="tagList" name="tagList"
                                                                                 style="width: 67%;"
                                                                                 class="form-control"></select>
                                </div>
                                <div class="form-group">
                                    <button type="button" id="c_search" class="btn btn-info btn-sm">搜索</button>
                                </div>
                            </form>
                            <p></p>
                            <a href="javascript:void(0)" id="addSeries" class="btn btn-outline btn-primary btn-lg"
                               role="button">添加系列图
                            </a>
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
                                    <th>系列图描述</th>
                                    <th>发布者</th>
                                    <th>分类</th>
                                    <th>创建时间</th>
                                    <th>点击</th>
                                    <th>收藏</th>
                                    <th>点赞</th>
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

    var series_ = {
        v: {
            id: "series_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 页面加载时，自动加载分类信息
                series_.fn.getTagList();

                series_.fn.dataTableInit();

                $("#c_search").click(function () {
                    series_.fn.search();
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    series_.fn.batchDeleteUser(ids);
                });

                $("#addSeries").click(function () {
                    window.location.href = "backend/series/addSeriesUI";
                });
            },
            //搜索
            search: function () {
                series_.v.dTable.ajax.reload();
                $(":checkbox").removeAttr("checked");
            },
            dataTableInit: function () {
                series_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/series/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "id"},
                        {"data": "id"},
                        {"data": "info"},
                        {"data": "author"},
                        {"data": "sort"},
                        {"data": "createTime"},
                        {"data": "seeNum"},
                        {"data": "collectionNum"},
                        {"data": "praiseNum"},
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
                        $('td', row).eq(2).html($bluemobi.cutText(data.info, 12, '...'));

                        //查看操作
                        $('td', row).last().find(".shows").click(function () {
                            location.href = _basePath + "backend/series/shows?id=" + data.id;
                        });

                        // 单个用户删除操作
                        $('td', row).last().find(".delete").click(function () {
                            series_.fn.deleteUser(data.id);
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
                    $bluemobi.ajax("backend/series/delete", {
                        id: id
                    }, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            series_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    });
                }, "确定删除该系列图么？", "确定");
            },
            batchDeleteUser: function (ids) {
                if (ids.length > 0) {
                    $bluemobi.optNotify(function () {
                        $bluemobi.ajax("backend/series/batchDelete", {
                            ids: JSON.stringify(ids)
                        }, function (result) {
                            if (result.status == "0") {
                                $bluemobi.notify(result.msg, "success");
                            } else {
                                $bluemobi.notify(result.msg, "error");
                            }
                            series_.v.dTable.ajax.reload();
                            $(":checkbox").removeAttr("checked");
                        })
                    }, "确认将选中项批量删除？", "确定");
                }
            },
            getTagList: function () {
                $bluemobi.ajax("backend/utils/seriesTag/list", null, function (result) {
                    if (null != result) {
                        // 获取返回的分类列表信息，并循环绑定到label中
                        var content = "<option value=''>全部</option>";
                        jQuery.each(result, function (i, item) {
                            content += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $('#tagList').append(content);
                    } else {
                        $bluemobi.notify("获取分类信息失败", "error");
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        series_.fn.init();
    });

</script>

</html>