package com.cxcacm.file.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.file.entity.Files;
import com.cxcacm.file.mapper.FilesMapper;
import com.cxcacm.file.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * (Files)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 00:36:12
 */
@Service("filesService")
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.port}")
    private String port;

    @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        if (size > 1024 * 1024) {
            return "上传失败，文件过大";
        }
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists())
            uploadParentFile.mkdirs();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        LambdaQueryWrapper<Files> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Files::getName, originalFilename);
        Files findFile = baseMapper.selectOne(wrapper);
        if (findFile != null) {
            return findFile.getUrl();
        } else  {
            try {
                file.transferTo(new File(fileUploadPath + "/" +uuid + "." + type));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Files files = new Files();
            files.setName(originalFilename);
            files.setType(type);
            files.setSize(size / 1024);
            files.setUrl("http://localhost:" + port + "/file/download/" + uuid + "." + type);
            baseMapper.insert(files);
            return "http://localhost:" + port + "/file/download/" + uuid + "." + type;
        }

    }

    @Override
    public void download(String uuid, HttpServletResponse response) {
        File file = new File(fileUploadPath + "/" + uuid);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(uuid, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            outputStream.write(FileUtil.readBytes(file));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
