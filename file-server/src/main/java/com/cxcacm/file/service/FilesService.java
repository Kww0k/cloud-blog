package com.cxcacm.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.file.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * (Files)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 00:36:12
 */
public interface FilesService extends IService<Files> {

    String upload(MultipartFile file);

    void download(String uuid, HttpServletResponse response);
}
