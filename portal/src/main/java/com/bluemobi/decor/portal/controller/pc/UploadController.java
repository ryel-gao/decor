package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传
 */
@Controller
@RequestMapping(value = "/pc/upload")
public class UploadController extends CommonController {

    @Autowired
    private UploadImageService uploadImageService;


    @RequestMapping(value = "/uploadImageToQiniu")
    @ResponseBody
    public Map<String,Object> saveImage(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(required = false) MultipartFile file) {
        String image = "";
        String thumbnailImage = "";
        if (file != null) {
            image = UploadUtils.uploadMultipartFile(file);
            thumbnailImage = uploadImageService.uploadThumbImage2Qiniu(file);
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("image",image);
        map.put("thumbnailImage",thumbnailImage);
        return map;
    }



}