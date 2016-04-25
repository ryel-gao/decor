<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="inc/meta.jsp"%>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>消息提醒</title>
    <%@ include file="inc/css.jsp"%>

</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp"%>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">消息提醒列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
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
                                    <th>账号</th>
                                    <th>用户昵称</th>
                                    <th>时间</th>
                                    <th>提醒内容</th>
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
                        <h4 class="modal-title" id="myModalLabel">提醒内容</h4>
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
                    comment_.v.dTable.ajax.reload();
                });
                comment_.fn.dataTableInit();
            },
            dataTableInit: function () {
                comment_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching":false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/reminding/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "user.account"},
                        {"data": "user.nickname"},
                        {"data": "createTime"},
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
                        $('td', row).eq(3).html(content);

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
                        $('td', row).eq(3).find(".show").click(function () {
                            $("#content").html(data.content);
                            comment_.fn.show();
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.cname = $("#cname").val();
                        aoData.tname = $("#tname").val();
                        aoData.uname = $("#uname").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            deletes:function(id){
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/reminding/delete", {id: id}, function (result) {
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