<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp" %>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>关于网站</title>
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
                        <div>
                            <form class="form-inline">
                                <div class="form-group">
                                    <label>文章标题：</label><input type="text" id="title" class="form-control input-sm"/>
                                </div>
                                <div class="form-group">
                                    <button type="button" id="c_search" class="btn btn-info btn-sm">搜索</button>
                                </div>
                            </form>
                        </div>
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
                                    <th>标题</th>
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

    var aboutweb_ = {
        v: {
            id: "aboutweb_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                aboutweb_.fn.dataTableInit();

                $("#c_search").click(function () {
                    aboutweb_.fn.search();
                });
            },
            //搜索
            search: function () {
                aboutweb_.v.dTable.ajax.reload();
                $(":checkbox").removeAttr("checked");
            },
            dataTableInit: function () {
                aboutweb_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/aboutweb/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "title"},
                        {"data": ""}
                    ],
                    "columnDefs": [{
                        "data": null,
                        "defaultContent": "<button type='button' title='查看' class='btn btn-primary btn-circle shows'>" + "<i class='fa fa-eye'></i>" + "</button>",
                        "targets": -1
                    }],
                    "createdRow": function (row, data, index) {
                        aboutweb_.v.list.push(data);
                    },
                    rowCallback: function (row, data) {
                        //查看操作
                        $('td', row).last().find(".shows").click(function () {
                            location.href = _basePath + "backend/aboutweb/shows?id=" + data.id;
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.title = $("#title").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            }
        }
    }

    $(document).ready(function () {
        aboutweb_.fn.init();
    });

</script>

</html>