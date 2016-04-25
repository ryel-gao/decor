$(function () {

});

var $bluemobi = {
    v: {
        ajaxOption: {method: 'get', dataType: 'json', async: true},
        notifyMethod: null
    },
    notify: function (msg, status) {
        var option = {
            position: "top center",
            autoHideDelay: 2000,
            className: status,
            arrowSize: 10
        }
        $.notify(msg, option);
    },
    optNotify: function (method,title,button) {

        if($("#notifyjs-foo-alert-option").length>0){
            return false;
        }

        if(title===undefined){
            title = '确认删除么？删除后不可恢复！';
        }
        if(button===undefined){
            button = '删除';
        }

        $.notify({
            title: title,
            button:button
        }, {
            style: 'foo',
            autoHide: false,
            clickToHide: false,
            position: "top center"
        });
        if (method != undefined) {
            $bluemobi.v.notifyMethod = method;
        }
    },
    cutText: function (sub, length,less) {
        var str = "";
        if (sub && sub.length > length) {
            str =  sub.substr(0, length);
            if(less){
                str=str+less;
                str =  '<p title="'+sub+'">' + str + '</p>';
            }
        } else {
            str =  sub;
        }

        return str;
    },
    subStrAdminNick:function(obj,str){
        if(obj.user.roleType=="admin"){
            obj.user.nickname="Décor";
        }
    },
    cutStr: function (sub, length,less) {
        var str = "";
        if (sub && sub.length > length) {
            str =  sub.substr(0, length);
            if(less){
                str=str+less;
            }
        } else {
            str =  sub;
        }

        return str;
    },
    bannerStyle:function(imageObj){
        var cwidth = document.body.clientWidth;
        var iWidth = imageObj.width;
        var iHeight = imageObj.height;
        var height = 500;
        var width = (iWidth/iHeight)*height;
        var left = 0;
        if(width>cwidth){
            left = "-"+(width-cwidth)/2
        }
        var style = 'style="position: relative;left:'+left+'px;width:'+width+'px;height:'+height+'px;"';
        return style;
    },

    ajax: function (url, data, callbackFun) {
        jQuery.ajax({
            dataType: 'json',
            url: url,
            type:"post",
            data: data,
            async: true,
            success: function (data) {
                callbackFun(data);
            }
        });
    }

};


$(document).on('click', '.notifyjs-foo-base .no', function () {
    $(this).trigger('notify-hide');
});
$(document).on('click', '.notifyjs-foo-base .yes', function () {
    if ($bluemobi.v.notifyMethod != null) {
        eval("$bluemobi.v.notifyMethod()");
    }
    $(this).trigger('notify-hide');
});


