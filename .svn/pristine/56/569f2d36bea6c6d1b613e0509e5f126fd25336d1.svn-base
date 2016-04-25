package com.bluemobi.decor.portal.tags;


import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.util.BeanUtil;
import com.bluemobi.decor.service.UserService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by gaoll on 2015/3/10.
 */
public class UserTag extends SimpleTagSupport {

    private String name;

    private String role;

    //标签的处理方法，简单标签处理类只需要重写doTag方法
    public void doTag() throws JspException, IOException {
        Writer out = getJspContext().getOut();

        UserService userService = (UserService) BeanUtil.getBean("userServiceImpl");
        //List<User> list = userService.listByRole(role);
        List<User> list = null;
        StringBuffer sb = new StringBuffer();
        sb.append("<select  class='form-control' id='" + name + "' name='" + name + "' >");
        for(User user : list){
            sb.append("<option value='" + user.getId() + "'>" + user.getNickname() + "</option>");
        }
        sb.append("</select>");
        out.write(sb.toString());
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
