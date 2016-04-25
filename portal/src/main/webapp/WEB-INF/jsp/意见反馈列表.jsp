<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <%@ include file="inc/meta.jsp"%>
  <meta name="description" content="">
  <meta name="author" content="">
  <title>意见反馈列表</title>
  <%@ include file="inc/css.jsp"%>
  <link href="static/js/plugins/dropper/jquery.fs.dropper.css" rel="stylesheet">
  <script src="static/js/plugins/dropper/jquery.fs.dropper.js"></script>
  <script  src="static/js/plugins/My97DatePicker/WdatePicker.js"> </script>

</head>

<body>

<div id="posts" class="wrapper">

  <%@ include file="inc/nav.jsp"%>

  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">意见反馈列表</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>

    <div class="row">
      <div class="col-lg-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="navbar-form pull-left">
              <label>编号：</label><input type="text" id="id" class="form-control input-sm">
              <label>用户名：</label><input type="text" id="username" class="form-control input-sm">
              <button type="button" id="c_search" class="btn btn-default btn-sm">查询</button>
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
                  <th>ID</th>
                  <th>用户名</th>
                  <th>评论内容</th>
                  <th>时间</th>
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
    <!-- 详细内容 -->
    <div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">详细内容</h4>
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

  var feedback_ = {
    v: {
      id: "feedback_",
      list: [],
      dTable: null
    },
    fn: {
      init: function () {

        feedback_.fn.dataTableInit();

        $("#c_search").click(function () {
          feedback_.fn.search();
        });
      },
      //查询
      search: function () {
        feedback_.v.dTable.ajax.reload();
        $(":checkbox").removeAttr("checked");
      },
      dataTableInit: function () {
        feedback_.v.dTable = $bluemobi.dataTable($('#dataTables'), {
          "processing": true,
          "serverSide": true,
          "searching":false,
          "ordering": false,
          "ajax": {
            "url": "backend/feedback/list",
            "type": "POST"
          },
          "columns": [
            {"data": "id"},
            {"data": "user.account"},
            {"data": "content"},
            {"data": "createTime"},
	        {"data": ""}
          ],
          "columnDefs": [
            {"data": null,"defaultContent":"","targets": -1}
          ],
          "createdRow": function (row, data, index) {
            feedback_.v.list.push(data);
            var content ="";
            if(data.content.length>8){
              content +="<a class='show' style='cursor:pointer;'>" + data.content.substr(0,8)+"..." +  "</a>";
            }else{
              content +="<a class='show' style='cursor:pointer;'>" + data.content +  "</a>";

            }
            $('td', row).eq(2).html(content);
            var optionHtml = "";
            optionHtml +=
                    "<button type='button'  title='删除' class='btn btn-danger btn-circle deletes'>" +
                    "<i class='fa fa-bitbucket'></i>" +
                    "</button>";

            $('td', row).last().html(optionHtml);
          },
          rowCallback: function (row, data) {
            var items = feedback_.v.list;
            $('td', row).last().find(".deletes").click(function () {
              feedback_.fn.deletes(data.id);
            });
            $('td', row).eq(2).find(".show").click(function () {
              $("#content").html(data.content);
              feedback_.fn.show();
            });
          },
          "fnServerParams": function (aoData) {
            aoData.id = $("#id").val();
            aoData.username = $("#username").val();
          },
          "fnDrawCallback": function (row) {
            $bluemobi.uiform();
          }
        });
      },
      deletes:function(id){
        $bluemobi.optNotify(function () {
          $bluemobi.ajax("backend/feedback/delete", {id: id}, function (result) {
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
    feedback_.fn.init();
  });

</script>


</html>