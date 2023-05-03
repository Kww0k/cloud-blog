package com.cxcacm.file.controller;

import com.cxcacm.file.annotation.SystemLog;
import com.cxcacm.file.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class UploadController {

    private final FilesService filesService;

    @Autowired
    public UploadController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping("/upload")
    @SystemLog(businessName = "上传文件")
    public String upload(@RequestParam MultipartFile file) {
        return filesService.upload(file);
    }

    @GetMapping("/download/{uuid}")
    @SystemLog(businessName = "文件下载")
    public void download(@PathVariable String uuid, HttpServletResponse response) {
        filesService.download(uuid, response);
    }
}
