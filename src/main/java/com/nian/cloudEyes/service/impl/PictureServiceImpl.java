package com.nian.cloudEyes.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.cloudEyes.model.Picture;
import com.nian.cloudEyes.model.User;
import com.nian.cloudEyes.service.PictureService;
import com.nian.cloudEyes.mapper.PictureMapper;
import com.nian.cloudEyes.service.UserService;
import com.nian.cloudEyes.utils.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
* @author admin
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-06-25 15:52:21
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{
    @Resource
    private MinioUtil minioUtil;
    @Resource
    private UserService userService;
    @Resource
    private PictureMapper pictureMapper;
    @Override
    public void upload(MultipartFile file, HttpServletRequest request) {
        // 获取到上传的文件名
        String fileName = file.getOriginalFilename();
        User loginUser = userService.getLoginUser(request);
        String userId = loginUser.getId().toString();
        String uploadFileName = String.format("%s/%s", userId, fileName);
        // 上传文件
        minioUtil.upload(file, uploadFileName);

        // 解析图片尺寸和比例
        Integer picWidth = null;
        Integer picHeight = null;
        Double picScale = null;
        try (InputStream is = file.getInputStream()) {
            // 使用ImageIO读取图片尺寸
            BufferedImage image = ImageIO.read(is);
            if (Objects.nonNull(image)) {
                picWidth = image.getWidth();
                picHeight = image.getHeight();
                picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
            }
        } catch (IOException e) {
            throw new RuntimeException("解析图片尺寸失败", e);
        }

        // 将文件信息保存到数据库中
        Picture picture = new Picture()
                .setUrl(minioUtil.getFileUrl(uploadFileName))
                .setName(fileName)
                .setUserId(loginUser.getId())
                .setPicSize(file.getSize())
                .setPicWidth(picWidth)
                .setPicHeight(picHeight)
                .setPicScale(picScale)
                .setPicFormat(file.getContentType());
        pictureMapper.insert(picture);
    }

}




