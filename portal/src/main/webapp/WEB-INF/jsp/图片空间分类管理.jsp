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
    <title>空间标签管理</title>
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
                <h1 class="page-header">空间标签管理</h1>
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
                                    <th>图标</th>
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
    <div class="modal fade" id="spaceTagModal" tabindex="-1" role="dialog" aria-labelledby="spaceTagModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="spaceTagModalLabel">操作</h4>
                </div>
                <div class="modal-body ">
                    <form id="spaceTagForm" method="post" action="backend/spaceTag/save" class="form-horizontal"
                          role="form">
                        <input type="hidden" id="id" name="id" value="">

                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">标签名</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name" name="name" maxlength="50"
                                       data-rule="required" placeholder="请输入名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">英文名</label>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="englishName" name="englishName"
                                       maxlength="50"
                                       data-rule="required" placeholder="请输入英文名称">
                            </div>
                        </div>
                        <%--封面图片--%>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">封面图:</label>

                            <div class="col-sm-10"  style="padding-left: 30px;padding-top: 30px;">
                                <div class="headImage form-group img_tooltip" >
                                    <div class="col-sm-5" style="padding-left: 0px;">
                                        <input type="hidden" name="imagePath" value="" class="image">
                                        <div class="image_show" style="display: none">
                                        </div>
                                        <div class="image_handle" data-toggle="tooltip" data-placement="top"
                                             title="" data-original-title="建议上传宽480px高320px的图片">
                                            <div class="dropped"></div>
                                        </div>
                                    </div>
                                    <a href="javascript:void(0)" id="removeImg" class="removeImg btn btn-info"
                                       role="button">删除图片</a>
                                </div>
                            </div>
                        </div>
                        <%--小图片--%>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Logo:</label>

                            <div class="col-sm-10" style="padding-left: 30px;">
                                <div class="headImage form-group img_tooltip">
                                    <div class="col-sm-5" style="padding-left: 0px;">
                                        <input type="hidden" class="image" name="logoPath" value="">

                                        <div class="image_show" style="display: none">
                                        </div>
                                        <div class="image_handle" data-toggle="tooltip" data-placement="top"
                                             title="" data-original-title="建议上传宽480px高320px的图片">
                                            <div class="dropped"></div>
                                        </div>
                                    </div>
                                    <a href="javascript:void(0)" class="removeImg btn btn-info"
                                       role="button">删除图片</a>
                                </div>
                            </div>
                        </div>
                    </form>
                    <input type="hidden" value="" id="headImagePath" name="image"/>
                    <input type="hidden" value="" id="LogoImagePath" name="logo"/>
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
    var headImage = document.getElementById("headImagePath").value;
    var LogoImage = document.getElementById("LogoImagePath").value;

    var spacetag_ = {
        v: {
            id: "spacetag_",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {


                $("#new").click(function () {
                    spacetag_.fn.showModal("新增");
                })

                $("#save").click(function () {
                    spacetag_.fn.save();
                })

                // 查询
                $("#c_search").click(function () {
                    spacetag_.v.dTable.ajax.reload();
                })

                spacetag_.fn.dataTableInit();

                spacetag_.fn.viewImage(headImage,0);
                spacetag_.fn.viewImage(LogoImage,1);
                spacetag_.fn.dropperInit();
                $(".removeImg").click(function (_this) {
                    spacetag_.fn.clearImageView($(this));
                });


            },
            dropperInit: function () {
                $(" .dropped").eq(0).dropper({
                    postKey: "file",
                    action: "backend/utils/saveImage",
                    postData: {thumbSizes: '480x320'},
                    label: "把封面图拖拽到此处"
                }).on("fileComplete.dropper", spacetag_.fn.onFileComplete)
                        .on("fileError.dropper", spacetag_.fn.onFileError);
                $(" .dropped").eq(1).dropper({
                    postKey: "file",
                    action: "backend/utils/saveImage",
                    postData: {thumbSizes: '480x320'},
                    label: "把Logo图拖拽到此处"
                }).on("fileComplete.dropper", spacetag_.fn.onFileCompleteTo)
                        .on("fileError.dropper", spacetag_.fn.onFileError);
            },
            onFileError: function (e, file, error) {
                $bluemobi.notify(error, "error");
            },
            onFileComplete: function (e, file, response) {
                if (response != '' && response != null) {
                    spacetag_.fn.viewImage(response, 0);
                } else {
                    $bluemobi.notify("抱歉上传失败", "error");
                }
            },
            onFileCompleteTo: function (e, file, response) {
                if (response != '' && response != null) {
                    spacetag_.fn.viewImage(response, 1);
                } else {
                    $bluemobi.notify("抱歉上传失败", "error");
                }
            },
            viewImage: function (image, index) {
                if (image) {
                    $(".headImage").eq(index).find(".dropper-input").val("");
                    $(".headImage").eq(index).find(".image_handle").hide();
                    $(".headImage").eq(index).find(".image_show").show();
                    $(".image").eq(index).val(image);
                    $(".headImage").eq(index).find(".image_show").html("<img src='" + image + "' class='img-responsive' >");
                }
            },
            clearImageView: function (th) {
                th.parent().val("");
                th.parent().find(".image_show").html("");
                th.parent().find(".image_handle").show();
                th.parent().find(".dropper-input").val("");
            },
          /*  clearImageViewAndDelete: function () {
                var path = $("#ssimgid").prop("src");
                $.ajax({
                            url: "backend/utils/deleteImage",
                            data: {
                                path: path
                            }
                        }
                );
                $("#image").val("");
                $("#headImage").find(".image_show").html("");
                $("#headImage").find(".image_handle").show();
                $("#headImage").find(".dropper-input").val("");
            },*/

            dataTableInit: function () {
                spacetag_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": {
                        "url": "backend/spaceTag/list",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "name"},
                        {"data": "englishName"},
                        {"data": "num"},
                        {"data": "image"},
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
                        spacetag_.v.list.push(data);
                        //var path = "";
                        //if(data.image&&data.image.path){
                        //    path = data.image.path;
                        //}
                        var img = '<img src="' + data.image + '" width="30" height="30">';
                        $('td', row).eq(2).html(img);

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
                        var items = spacetag_.v.list;
                        $('td', row).last().find(".edit").click(function () {
                            spacetag_.fn.edit(data.id);
                        });
                        $('td', row).last().find(".delete").click(function () {
                            spacetag_.fn.deleteRow(data.id);
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
                    $bluemobi.ajax("backend/spaceTag/delete", {id: id}, function (result) {
                        if (result.status == "0") {
                            $bluemobi.notify(result.msg, "success");
                            spacetag_.v.dTable.ajax.reload();
                        } else {
                            $bluemobi.notify(result.msg, "error");
                        }
                    })
                });
            },
            edit: function (id) {
                spacetag_.fn.showModal("修改");
                var items = spacetag_.v.list;
                $.each(items, function (index, item) {
                    if (item.id == id) {
                        for (var key in item) {
                            var element = $("#spaceTagForm :input[name=" + key + "]")
                            if (element.length > 0) {
                                element.val(item[key]);
                            }
                            if (key == "image") {
                                spacetag_.fn.viewImage(item[key],0);
                            }
                            if (key == "picImage") {
                                spacetag_.fn.viewImage(item[key],1);
                            }
                        }
                    }
                })
            },
            save: function () {
                if (!$('#spaceTagForm').isValid()) {
                    return false;
                }
                ;
                $("#level").val(1);
                $("#spaceTagForm").ajaxSubmit({
                    dataType: "json",
                    success: function (result) {
                        spacetag_.fn.responseComplete(result, true);
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        spacetag_.v.dTable.ajax.reload(null, false);
                    } else {
                        spacetag_.v.dTable.ajax.reload();
                    }

                    $bluemobi.notify(result.msg, "success");
                    $("#spaceTagModal").modal("hide");
                } else {
                    $bluemobi.notify(result.msg, "error");
                }
            },
            showModal: function (title) {
                $("#spaceTagModal").modal("show");
                $bluemobi.clearForm($("#spaceTagModal"));
                spacetag_.fn.clearImageView($(".remove"));
                if (title) {
                    $("#spaceTagModal .modal-title").text(title);
                }
            }
        }
    }

    $(document).ready(function () {
        spacetag_.fn.init();
    });

</script>


</html>