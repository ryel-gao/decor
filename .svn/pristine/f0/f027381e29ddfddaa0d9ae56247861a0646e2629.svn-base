/**
 * 
 */
package com.bluemobi.decor.portal.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class CommonUtils {

    /**
     * @param batName
     *            ：bat文件的全路径
     * @return String:文件上传返回的文件路径
     * @throws java.io.IOException
     */
    public static void runbat(String batName) {
        try {
            Process ps = Runtime.getRuntime().exec(batName);
            InputStream in = ps.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);// 如果你不需要看输出，这行可以注销掉
            }
            in.close();
            ps.waitFor();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("child thread done");
    }

    // 获取项目路径
    public static String getProjectPath(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        return basePath;
    }
}