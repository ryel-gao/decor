<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@ include file="common/meta.jsp"%>
    <%@ include file="common/css.jsp"%>
    <title><fmt:message key="info.shouye"/></title>
    <link href="static/pc/css/common.css" rel="stylesheet" type="text/css">
    <link href="static/pc/css/all.css" rel="stylesheet" type="text/css">
    <meta property="qc:admins" content="25322572076211456375" />
</head>

<body>
<div class="wrapper">
   
    <%@ include file="common/header.jsp" %>
    
    <div class="cantainer">
        <!--Banner-->
        <div class="ck-slide">
            <ul class="ck-slide-wrapper">

            </ul>
            <a href="javascript:;" class="ctrl-slide iicon ck-prev"><fmt:message key="info.shangyizhang"/></a> <a href="javascript:;" class="ctrl-slide iicon ck-next"><fmt:message key="info.xiayizhang"/></a>
        </div>
        <div class="content">
            <div class="product">
                <ul class="product-list clearfix">

                </ul>
            </div>
            <div class="pro_show mb100">
                <ul>


                </ul>
            </div>
        </div>
    </div>


    <!-- footer -->
    <%@ include file="common/footer.jsp"%>

</div>
<!--右侧悬浮-->
<%@ include file="common/other.jsp"%>

<script type="text/javascript" src="static/pc/js/slide.min.js"></script>
<script type="text/javascript" src="static/pc/js/slide.js"></script>
<script type="text/javascript" src="static/pc/js/base.js"></script>
<script>
    $(function(){
        ajaxAd(); // 加载广告
        ajaxRecommendScene(); // 加载推荐的场景
        ajaxRecommendMessage(); // 加载推荐的咨询
    });

    // 加载广告
    function ajaxAd(){
        $bluemobi.ajax("pc/homepage/ajaxAd",{},function(result){
            if (result.status == "0") {
                var html = '';
                for(var i=0;i<result.data.length;i++){
                    html += '<li><a target="_blank" href="'+result.data[i].link+'"><img src="'+result.data[i].image+'" alt=""></a></li>';
                }
                $(".ck-slide-wrapper").html(html);
                $('.ck-slide').ckSlide({
                    autoPlay: true
                });
            }
        });
    }

    // 加载推荐的场景
    function ajaxRecommendScene(){
        $bluemobi.ajax("pc/homepage/ajaxRecommendScene",{},function(result){
            if (result.status == "0") {
                var html = '';
                for(var i=0;i<result.data.length;i++){
                    var scene = result.data[i];
                    var collectClass = "i-collect"; // 收藏按钮样式
                    var praiseClass = "i-praise"; // 点赞按钮样式
                    var showName = "";

                    if(scene.isCollection && scene.isCollection == "yes"){
                        collectClass = "i-collect2";
                    }
                    if(scene.isPraise && scene.isPraise == "yes"){
                        praiseClass = "i-praise2";
                    }
                    if(scene.user.roleType == "admin"){
                        showName = 'Décor';
                    }else{
                        showName = '<a href="javascript:void(0)" onclick="toUserInfo('+scene.user.id+')">'+scene.user.nickname+'</a>';
                    }
                    $bluemobi.subStrAdminNick(eval(scene),"Décor");
                    html+='<li sceneid='+scene.id+'><div class="pro_img">\
                    <a href="pc/scene/detail?sceneId='+scene.id+'"><img src="'+scene.image+'"/></a>\
                    <div class="opera abs tr">\
                    <div class="stick"></div>\
                    <span class="i-look"><i class="iicon"></i>'+scene.seeNum+'</span>\
                    <span class="'+collectClass+' _collect"><i class="iicon"></i><span class="collectionNum" style="margin: 0 0">'+scene.collectionNum+'</span></span>\
                    <span class="'+praiseClass+' _praise"><i class="iicon"></i><span class="praiseNum" style="margin: 0 0">'+scene.praiseNum+'</span></span>\
                    </div>\
                    </div>\
                    <div class="pro_txt clearfix">\
                    <span class="fl">'+scene.name+'</span>\
                    <span class="fr">by：'+showName+'</span>\
                    </div>\
                    </li>';
                }
                $(".product-list").html(html);

                //图片文字滑动效果
                $(".pro_img").hover(function(){
                    $(this).find(".opera").stop().animate({"padding":"10px 0","height":"20px"},200)
                },function(){
                    $(this).find(".opera").stop().animate({"padding":"0px","height":"0px"},200)
                });

                // 绑定收藏、点赞的点击事件
                $(".product-list").find("li").each(function(){
                    var sceneId = $(this).attr("sceneid");
                    $(this).find("._collect").unbind("click").click(function(){
                        var currCollectBtn = $(this);
                        if(currCollectBtn.hasClass("i-collect")){
                            collectDlg.show(sceneId,"scene",function(){
                                currCollectBtn.addClass("i-collect2").removeClass("i-collect");
                                var collectionNum = currCollectBtn.find(".collectionNum").text();
                                currCollectBtn.find(".collectionNum").text(collectionNum*1+1);
                            });
                        }else if(currCollectBtn.hasClass("i-collect2")){
                            collectDlg.cancelCollect(sceneId,"scene",function(){
                                currCollectBtn.addClass("i-collect").removeClass("i-collect2");
                                var collectionNum = currCollectBtn.find(".collectionNum").text();
                                collectionNum = collectionNum*1 -1;
                                if(collectionNum<=0){
                                    collectionNum=0;
                                }
                                currCollectBtn.find(".collectionNum").text(collectionNum);
                            });
                        }
                    });
                    $(this).find("._praise").unbind("click").click(function(){
                        praiseOrCancelPraise($("#sessionUserId").val(),sceneId,$(this));
                    });
                });


            }
        });
    }

    // 跳转到用户界面
    function toUserInfo(userId){
        location.href = "pc/user/detailPage?userId="+userId;
    }

    // 点赞/取消点赞
    function praiseOrCancelPraise(userId,objectId,$obj){
        if(userId==""){
            loginPopup.showDlg();
            return false;
        }
        if($obj.hasClass("i-praise")){ // 点赞
            $bluemobi.ajax("pc/praise/praise",{userId:userId,objectId:objectId,objectType:"scene"},function(result){
                if (result.status == "0") {
                    $obj.removeClass("i-praise").addClass("i-praise2");
                    var praiseNum = $obj.find(".praiseNum").text();
                    $obj.find(".praiseNum").text(praiseNum*1+1);
                    $bluemobi.notify(result.msg,"success");
                }
            });
        }else if($obj.hasClass("i-praise2")){ // 取消点赞
            $bluemobi.ajax("pc/praise/cancelPraise",{userId:userId,objectId:objectId,objectType:"scene"},function(result){
                if (result.status == "0") {
                    $obj.removeClass("i-praise2").addClass("i-praise");
                    var praiseNum = $obj.find(".praiseNum").text();
                    if(praiseNum<=0){
                        praiseNum =0;
                    }else{
                        praiseNum = praiseNum-1;
                    }
                    $obj.find(".praiseNum").text(praiseNum);
                    $bluemobi.notify(result.msg,"success");
                }
            });
        }

    }

    // 查询推荐的咨询
    function ajaxRecommendMessage(){
        var userId = $("#sessionUserId").val();
        $bluemobi.ajax("pc/homepage/ajaxRecommendMessage",{userId:userId},function(result){
            if (result.status == "0") {
                var html = '';
                for(var i=0;i<result.data.length;i++){
                    var message = result.data[i];
                    var collect;
                    if(userId!=''&&message.ifCollect=='1'){
                        collect="<fmt:message key="info.quxiaoshoucang"/>"
                    }else if(userId!=''&&message.ifCollect=='0'){
                        collect="<fmt:message key="info.shoucangzixun"/>"
                    }else if(userId==''){
                        collect="<fmt:message key="info.shoucang"/>"
                    }
                    if(message.collectionNum<0){
                        message.collectionNum=0
                    }
                    if(i%2==0){
                        html+='<li>\
                        <div class="showTxt">\
                        <h3 class="t-title">'+message.title+'</h3>\
                         <a href="pc/message/detail?messageId='+message.id+'"><p>'+message.subContent+'</p><a/>\
                        <a class="butn" messageId='+message.id+'><i class="iicon collect"></i><span >'+collect+'</span></span>（<span>'+message.collectionNum+'</span>）</a>\
                        </div>\
                        <div class="showImg">\
                         <a href="pc/message/detail?messageId='+message.id+'"><img src="'+message.image+'"/><img src="'+message.intro_image+'"/><a/>\
                        </div>\
                        </li>';
                    }else{
                        html+='<li>\
                        <div class="showTxt changTxt">\
                        <h3 class="t-title">'+message.title+'</h3>\
                         <a href="pc/message/detail?messageId='+message.id+'"><p>'+message.subContent+'</p><a/>\
                        <a class="butn" messageId='+message.id+' ><i class="iicon collect"></i><span >'+collect+'</span>（<span>'+message.collectionNum+'</span>）</a>\
                        </div>\
                        <div class="showImg changImg">\
                         <a href="pc/message/detail?messageId='+message.id+'"><img src="'+message.image+'"/><img src="'+message.intro_image+'"/><a/>\
                        </div>\
                        </li>';
                    }
                }
                $(".pro_show").find("ul").html(html);
                $(".pro_show").find(".showTxt").find("img").remove();
                $(".showTxt ").find(".butn").unbind("click").click(function(){
                        var messageId=$(this).attr("messageId")
                        var userId = $("#sessionUserId").val();
                        var addOrDel=false;
                       var Num=$(this).find("span").eq(1).text()*1;
                    var spanOne=$(this).find("span").eq(0);
                    var spanTwo=$(this).find("span").eq(1);
                      var thisObj=$(this);
                     // 用户未登录，则弹出未登录提示框
                    if(userId==""){
                        loginPopup.showDlg();
                        return false;
                    }
                    //判断资讯收藏状态
                    if(spanOne.text()=="<fmt:message key="info.shoucangzixun"/>") {
                        $bluemobi.ajax("pc/message/saveCollectionMessage", {
                            userId: userId,
                            messageId: messageId
                        }, function (result) {
                            if (result.status == "0") {
                                spanOne.text("<fmt:message key="info.quxiaoshoucang"/>");
                                $bluemobi.notify(result.msg, "success");
                            }
                            addOrDel=true;
                            $bluemobi.ajax("pc/message/ajaxCollectionNumAdd", {messageId: messageId ,addOrDel:addOrDel},
                                    function (result) {
                                        if (result.status == "0") {
                                            $bluemobi.notify(result.msg, "success");
                                            spanTwo.text(result.data);
                                        }
                                    });
                        });
                    }else if(spanOne.text()=="<fmt:message key="info.quxiaoshoucang"/>") {
                        $bluemobi.ajax("pc/message/cancelledMessage", {
                            userId: userId,
                            messageId: messageId
                        }, function (result) {
                            if (result.status == "0") {
                               spanOne.text("<fmt:message key="info.shoucangzixun"/>");
                                $bluemobi.notify(result.msg, "success");
                            }
                           //收藏量增加
                       $bluemobi.ajax("pc/message/ajaxCollectionNumAdd", {messageId: messageId ,addOrDel:addOrDel},
                                    function (result) {
                                        if (result.status == "0") {
                                            $bluemobi.notify(result.msg, "success");
                                            spanTwo.text(result.data);
                                        }
                                    });
                        });
                    }
                })
            }
        });
    }
</script>
</body>
</html>