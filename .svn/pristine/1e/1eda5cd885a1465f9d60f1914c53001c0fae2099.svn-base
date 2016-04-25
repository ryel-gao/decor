package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.FileBo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by wangbin on 2014/12/7.
 */
public interface UploadImageService {

    public FileBo saveImage(MultipartFile file);

    public void uploadImage(MultipartFile file, String... thumbSizes);
    public String uploadThumbImage(MultipartFile file);


    public String uploadFile(MultipartFile file);

//    public Image uploadImage(MultipartFile file);


    // 上传缩略图到七牛
    public String uploadThumbImage2Qiniu(MultipartFile file);
    public String uploadThumbImage2Qiniu(File file);



}
