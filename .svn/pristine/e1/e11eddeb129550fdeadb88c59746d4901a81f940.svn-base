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
    <title>权限列表</title>
    <%@ include file="inc/css.jsp"%>

</head>

<body>

<div id="posts" class="wrapper">

    <%@ include file="inc/nav.jsp"%>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">权限列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="btn-group pull-left">
                            <a href="javascript:void(0)" id="new" class="btn btn-outline btn-primary btn-lg"
                               role="button">新增权限
                            </a>
                        </div>
                        <div class="navbar-form pull-right">
                            <label>权限名称：</label><input type="text" id="c_name" class="form-control input-sm" >
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
                                    <th>权限名称</th>
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
                    <form id="form1" method="post" action="backend/user/save" class="form-horizontal" role="form">
                        <input type="hidden" id="id" name="id" value="">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">权限名称:</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name" name="name" maxlength="50"
                                       data-rule="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">权限列表:</label>
                            <div class="col-sm-5">
                                <div class="panel panel-default">
                                    <div class="list-group">

                                    </div>
                                </div>
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

</div>
<!-- /#wrapper -->

<%@ include file="inc/footer.jsp"%>
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

                $("#new").click(function(){
                    this_.fn.showModal("新增权限");
                });

                $("#save").click(function () {
                    this_.fn.save();
                });

                // 查询
                $("#search").click(function () {
                    this_.v.dTable.ajax.reload();
                });

                this_.fn.dataTableInit();
                this_.fn.loadResource();

            },
            dataTableInit: function () {
                this_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching":false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/authority/roleList",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": ""}
                    ],
                    "columnDefs": [{"data": null,"defaultContent":"","targets": -1}
                    ],
                    "createdRow": function (row, data, index) {
                        this_.v.list.push(data);

                        var optionHtml = "";
                        optionHtml +=
                                "<button type='button'  title='编辑'  class='btn btn-primary btn-circle edit'>" +
                                "<i class='fa fa-edit'></i>" +
                                "</button>";
                        optionHtml +=
                                "&nbsp;" +
                                "<button type='button'  title='删除'  class='btn btn-danger btn-circle delete'>" +
                                "<i class='fa fa-bitbucket'></i>" +
                                "</button>";
                        $('td', row).last().html(optionHtml);
                    },
                    rowCallback: function (row, data) {
                        var items = this_.v.list;
                        $('td', row).last().find(".edit").click(function () {
                            this_.fn.edit(data.id);
                        });
                        $('td', row).last().find(".delete").click(function () {
                            this_.fn.delete(data.id);
                        });
                    },
                    "fnServerParams": function (aoData) {
                        aoData.name = $("#c_name").val();
                    },
                    "fnDrawCallback": function (row) {
                        $bluemobi.uiform();
                    }
                });
            },
            edit: function (id) {
                this_.fn.showModal("修改");
                var items = this_.v.list;
                $.each(items, function (index, item) {
                    if (item.id == id) {
                        for (var key in item) {
                            var element = $("#form1 :input[name=" + key + "]")
                            if (element.length > 0) {
                                element.val(item[key]);
                            }
                            if (key == "resourceList") {
                                if(item[key]&&item[key].length>0){
                                    for(var i=0;i<item[key].length;i++){
                                        var resource = item[key][i];
                                        var resourceId = resource.id;
                                        $("[resourceid='"+resourceId+"']").removeClass("fa-circle-o").addClass("fa-check-circle-o");
                                    }
                                }

                            }
                        }
                    }
                })
            },
            save: function () {
                if(!$('#form1').isValid()) {
                    return false;
                };

                var id = $("#id").val();

                var resourceIds =  this_.fn.selectedResource();
                if(resourceIds==""){
                    $bluemobi.notify("请选择权限", "error");
                    return false;
                }

                var data = {id:id,name:$("#name").val(),resourceIds:resourceIds};
                $bluemobi.ajax("backend/authority/saveRole",data,function(result){
                    this_.fn.responseComplete(result);
                },{method:'post'})
            },
            delete:function(id){
                $bluemobi.optNotify(function () {
                    $bluemobi.ajax("backend/authority/deleteRole", {roleId: id}, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            this_.v.dTable.ajax.reload(null, false);
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    })
                });
            },
            responseComplete: function (result,action) {
                if (result.status == "0") {
                    this_.v.list = [];
                    if(action){
                        this_.v.dTable.ajax.reload(null, false);
                    }else{
                        this_.v.dTable.ajax.reload();
                    }

                    $bluemobi.notify(result.msg, "success");
                    $("#modal").modal("hide");
                } else {
                    $bluemobi.notify(result.msg, "error");
                }
            },
            showModal: function (title) {
                $("#modal").modal("show");
                $bluemobi.clearForm($("#modal"));
                $(".list-group").find("i").each(function(){
                    var i = $(this);
                    i.removeClass("fa-check-circle-o").addClass("fa-circle-o");
                });
                if (title) {
                    $("#modal .modal-title").text(title);
                }
            },
            // 加载资源列表
            loadResource:function(){
                $bluemobi.ajax("backend/authority/resourceList",{},function(result){
                    if (result.status == "0") {
                        var html = "";
                        for(var i=0;i<result.data.length;i++){
                            var resource = result.data[i];
                            html+=' <a class="list-group-item">\
                            <i class="fa fa-circle-o fa-fw"  resourceid='+resource.id+'></i>'+resource.name+'\
                            </a>';
                        }
                        $(".list-group").html(html);

                        // 添加点击事件
                        $(".list-group").find("a").unbind("click").click(function(){
                            var i = $(this).find("i");
                            if(i.hasClass("fa-circle-o")){
                                i.removeClass("fa-circle-o").addClass("fa-check-circle-o");
                            }else{
                                i.removeClass("fa-check-circle-o").addClass("fa-circle-o");
                            }
                        });
                    } else {
                        $bluemobi.notify(result.msg, "error");
                    }
                })
            },
            selectedResource:function(){
                var resourceIds = "";
                $(".list-group").find(".fa-check-circle-o").each(function(index){
                    if(index!=0){
                        resourceIds+=",";
                    }
                    resourceIds+=$(this).attr("resourceid");
                });
                return resourceIds;
            }
        }
    };

    $(document).ready(function () {
        this_.fn.init();
    });

</script>


</html>