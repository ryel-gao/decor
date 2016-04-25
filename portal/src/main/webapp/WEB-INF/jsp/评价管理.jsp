<%--
Created by IntelliJ IDEA.
User: gaoll
Date: 2015/3/3
Time: 11:58
To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp"%>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>评论管理</title>
    <%@ include file="inc/css.jsp"%>

</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp"%>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">评论管理</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="navbar-form pull-left">
                            <!-- <label>编号：</label><input type="text" id="cname" class="form-control input-sm" placeholder="请输入编号">-->
                            <label>用户名：</label><input type="text" id="username" class="form-control input-sm">
                            <button type="button" id="c_search" class="btn btn-default btn-sm">搜索</button>
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
                                    <th>用户昵称</th>
                                    <th>评论时间</th>
                                    <th>评论对象</th>
                                    <th>图片名称</th>
                                    <th>评论内容</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>

                    </div>
                    <!-- /.panel-body -->

                </div>
                <!-- /.panel -->
            </div>
        </div>
        <!-- 详细内容 -->
        <div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">评价内容</h4>
                    </div>
                    <div class="modal-body" id="content"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp"%>
</body>

<script type="text/javascript">

    var comment_ = {
        v: {
            id: "comment_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                // 查询
                $("#c_search").click(function () {
                    comment_.fn.search();
                });
                comment_.fn.dataTableInit();
            },
            search: function () {
                comment_.v.dTable.ajax.reload();
                $(":checkbox").removeAttr("checked");
            },
            dataTableInit: function () {
                comment_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching":false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/comment/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "user.account"},
                        {"data": "createTime"},
                        {"data": "objectStr"},
                        {"data": "objectName"},
                        {"data": "content"},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {"data": null,"defaultContent":"","targets": -1}
                    ],
                    "createdRow": function (row, data, index) {
                        comment_.v.list.push(data);
                        var content ="";
                        if(data.content.length>6){
                            content +="<a class='show' style='cursor:pointer;'>" + data.content.substr(0,6)+"..." +  "</a>";
                        }else{
                            content +="<a class='show' style='cursor:pointer;'>" + data.content +  "</a>";
                        }
                        $('td', row).eq(4).html(content);

                        var optionHtml = "";
                        optionHtml +=
                                "<button type='button'  title='删除' class='btn btn-danger btn-circle deletes'>" +
                                "<i class='fa fa-bitbucket'></i>" +
                                "</button>";

                        $('td', row).last().html(optionHtml);
                    },
                    rowCallback: function (row, data) {
                        var items = comment_.v.list;
                        $('td', row).last().find(".deletes").click(function () {
                            comment_.fn.deletes(data.id);
                        });
                        $('td', row).eq(4).find(".show").click(function () {
                            $("#content").html(data.content);
                            comment_.fn.show();
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.username = $("#username").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            deletes:function(id){
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/comment/delete", {id: id}, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            $("#dataTables").DataTable().ajax.reload(null,false);
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    })
                });
            },
            show:function(){
                $("#deleteConfirm").modal("show");
            }
        }
    }

    $(document).ready(function () {
        comment_.fn.init();
    });

</script>


</html>