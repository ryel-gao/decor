<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="static/js/jquery-1.11.0.js"></script>
<style>
    .logo{margin: 14px 10px 14px 10px!important;}
    .header_r a{margin-left:20px!important;}
</style>
<div class="header">
    <div class="content clearfix">
        <a class="logo fl" href="#"><img src="static/pc/images/logo.png"/></a>

        <div class="menu fl">
            <ul class="clearfix">
                <li><a href="pc/forward/to?type=scene"><fmt:message key="info.tuku"/></a></li>
                <li><a href="pc/forward/to?type=goods"><fmt:message key="info.shangpin"/></a></li>
                <li><a href="pc/forward/to?type=user"><fmt:message key="info.shejishi"/></a></li>
                <li><a href="pc/forward/to?type=message"><fmt:message key="info.zixun"/></a></li>
            </ul>
        </div>
        <div class="header_r fr">
            <div class="search_wrap fl">
                <div class="search rel">
                    <i class="iicon search-icon abs"></i><input class="search_text" style="width: 100px;float: right;"
                                                                type="text" value="${name}"/>

                    <div class="select_wrap abs">
                        <div class="select_input rel">
                            <i class="iicon search-select abs"></i>
                            <span class="select-text"><fmt:message key="info.changjingtu"/></span>
                            <input type="hidden" id="search_type" value="${type}"/>
                            <select id="typeSelect">
                                <option><fmt:message key="info.changjingtu"/></option>
                                <option><fmt:message key="info.shangpintu"/></option>
                                <option><fmt:message key="info.xilietu"/></option>
                                <option><fmt:message key="info.shejishi"/></option>
                                <option><fmt:message key="info.zixun"/></option>
                            </select>
                        </div>
                    </div>
                </div>
                <button class="search_btn"><fmt:message key="info.sousuo"/></button>
                <a id="alink" href="pc/forward/to?type=scene" class="search_a"></a>

            </div>
            <input type="hidden" id="sessionUserId" value="${session_pc_user.id}"/>
            <c:if test="${session_pc_user != null}">
                <div class="user fl" style="cursor: pointer;">
                    <a href='pc/user/detailHomePage'><img src="${session_pc_user.headImage}" width="36" height="36"><span>${session_pc_user.shortNickname}</span></a>
                    <span onclick="login.out()"><fmt:message key="info.tuichu"/></span>
                </div>
            </c:if>
            <c:if test="${session_pc_user == null}">
                <a onclick="loginPopup.showDlg()" style="margin:0 0 0 9px;"><fmt:message key="info.denglu"/></a>
                <a onclick="registerPopup.showDlg()" style="margin:0 0 0 9px;"><fmt:message key="info.zhuce"/></a>
            </c:if>
            <%
                HttpSession s = request.getSession();
            %>
            <input type="hidden" id="langua" value="<%=s.getAttribute("langua") %>"/>
            <%--<select></select>--%>
            <select onchange="lu()" id="lua" style="background: #fff;padding: 2px 1px 4px 3px;margin-left: 7px;">
                <option value="zh_CN">中文</option>
                <option value="en_US">English</option>
            </select>
        </div>
    </div>
</div>
<script>
    $(function init() {
        var langua = $("#langua").val();
        $("#lua").val(langua);
    });
    function lu() {
        var lua = $("#lua").val();
        var url = _basePath;
        if (lua == 'en_US') {
            url += "us"
        }
        if (lua == 'zh_CN') {
            url += "chinese"
        }
        var data = {
            "locale": lua
        };
        $.ajax({
            url: url,
            type: "get",
            data: data,
            async: false
        });
        window.location.href = "index?langua=" + lua;
    }
</script>


