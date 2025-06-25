package com.nian.cloudEyes.controller;

import com.nian.cloudEyes.annotation.AuthCheck;
import com.nian.cloudEyes.common.BaseResponse;
import com.nian.cloudEyes.common.ResultUtils;
import com.nian.cloudEyes.service.PictureService;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/file/minio")
public class PictureController {
    @Resource
    private PictureService pictureService;

    /**
     * 上传文件
     */
    @AuthCheck
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public BaseResponse<?> uploadReport(@NotNull @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        pictureService.upload(file, request);
        return ResultUtils.success();
    }

}