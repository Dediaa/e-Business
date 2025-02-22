package com.ch.ebusiness.controller.common;

import com.ch.ebusiness.entity.result.AjaxResult;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            //指定oss保存文件路径 dkd-images/2024/06/18/文件名
            String objectName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/";
            //上传文件，返回文件信息
            FileInfo fileInfo = fileStorageService.of(file)
                    .setPath(objectName)
                    .upload();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", fileInfo.getUrl());
            ajax.put("fileName", fileInfo.getUrl());
            ajax.put("newFileName", fileInfo.getUrl());
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
