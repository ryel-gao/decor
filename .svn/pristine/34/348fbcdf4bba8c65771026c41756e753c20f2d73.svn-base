package com.bluemobi.decor.service.impl;



import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.core.Configue;
import com.bluemobi.decor.entity.FileBo;
import com.bluemobi.decor.service.UploadImageService;
import com.bluemobi.decor.service.abs.AbstractUploadService;
import com.bluemobi.decor.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangbin on 2014/12/7.
 */
@Service
@Transactional(readOnly = true)
public class UploadImageServiceImpl extends AbstractUploadService implements UploadImageService {

    @Override
    public FileBo saveImage(MultipartFile file) {
        if (!FileUtil.isImage(file.getOriginalFilename())) {
            GeneralExceptionHandler.handle("文件格式不正确，请上传图片!");
        }
        FileBo result = null;
        try {
            result = super.save(file);
        } catch (IOException e) {
            GeneralExceptionHandler.handle("上传失败,服务器繁忙!");
        }
        return result;
    }

    @Override
    public void uploadImage(MultipartFile file, String... thumbSizes) {
        try {
            FileBo fileBo = saveImage(file);

            //处理大于500k的jpg图片
            Map<String,Long> imgInfo = ImageUtil.getImgInfo(fileBo.getFile());
            Long imgSize = imgInfo.get("size");
            if(imgSize>512000&&fileBo.getExt().equals(".jpg")){
                String destImage = fileBo.getForeName() + "_" + "compact" + fileBo.getExt();
                ImageUtil.compactImage(fileBo.getFile(), Configue.getUploadPath(), destImage);
                imgInfo = ImageUtil.getImgInfo(Configue.getUploadPath()+destImage);
            }

            //存储宽和高
            Map<String,Long> attrMap = new HashMap<String, Long>();
            attrMap.put("width",imgInfo.get("width"));
            attrMap.put("height",imgInfo.get("height"));

            if (thumbSizes != null && thumbSizes.length > 0) {
                Map<String, String> thumb = new HashMap<String, String>();
                for (String size : thumbSizes) {
                    String regex = "(\\d+)x(\\d+)";
                    Pattern p = Pattern.compile(regex, Pattern.DOTALL);
                    Matcher m = p.matcher(size);
                    String destImage = fileBo.getForeName() + "_" + size + fileBo.getExt();
                    if (m.find()) {
                        int width = Integer.parseInt(m.group(1));// 宽
                        int height = Integer.parseInt(m.group(2));// 高
                        ImageUtil.thumbImage(fileBo.getFile(),Configue.getUploadPath(), destImage, width, height);
                    }
                    thumb.put(size, destImage);
                }
            }
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
    }



    @Override
    public String uploadFile(MultipartFile file) {
        try {
            FileBo fileBo = super.save(file);
            if(fileBo != null){
                return fileBo.getPath();
            }
            return null;
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
        return null;
    }

    // 上传图片的缩略图到七牛
    @Override
    public String uploadThumbImage2Qiniu(MultipartFile file) {
        try {
            // 图片上传为本地缩略图
            String thumbImagePath = uploadThumbImage(file);

            // 本地缩略图上传到七牛
            File thumbFile = new File(thumbImagePath);
            String thumbImage7Path = UploadUtils.uploadFile(thumbFile);

            // 返回七牛所路途
            return thumbImage7Path;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadThumbImage(MultipartFile file) {
        try {
            FileBo fileBo = saveImage(file);
            String destImage = fileBo.getForeName() + "_" + "compact" + fileBo.getExt();
            ImageUtil.compactImage(fileBo.getFile(), Configue.getUploadPath(), destImage);
            ImageUtil.getImgInfo(Configue.getUploadPath()+destImage);
            return Configue.getUploadPath()+destImage;
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
        return null;
    }

    // 上传图片的缩略图到七牛
    @Override
    public String uploadThumbImage2Qiniu(File file) {
        try {
            // 图片上传为本地缩略图
            String thumbImagePath = uploadThumbImage(file);

            // 本地缩略图上传到七牛
            File thumbFile = new File(thumbImagePath);
            String thumbImage7Path = UploadUtils.uploadFile(thumbFile);

            // 返回七牛所路途
            return thumbImage7Path;
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
        return null;
    }

    public String uploadThumbImage(File file) {
        try {
            String uploadPath = UploadUtil.getImagesUpladPath(); //生成日期目录 image/2014/7/21/
            String fileName = String.valueOf(System.currentTimeMillis());
            String foreName = uploadPath+fileName;   //文件名称 image/2014/7/21/221144144554
            String origFileName = file.getName(); //原始名称,如aa.jpg
            String ext =  FileUtil.getFileExt(origFileName); //后缀，如jpg
            String destImage = foreName + "_" + "compact" + ext;
            ImageUtil.compactImage(file, Configue.getUploadPath(), destImage);
            ImageUtil.getImgInfo(Configue.getUploadPath()+destImage);
            return Configue.getUploadPath()+destImage;
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
        return null;
    }



}
