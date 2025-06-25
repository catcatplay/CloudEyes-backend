package com.nian.cloudEyes.service;

import com.nian.cloudEyes.model.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author admin
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-06-25 15:52:21
*/
public interface PictureService extends IService<Picture> {

    void upload(MultipartFile file, HttpServletRequest request);
}
