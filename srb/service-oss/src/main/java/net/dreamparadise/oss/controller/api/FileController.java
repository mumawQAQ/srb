package net.dreamparadise.oss.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dreamparadise.common.exception.BusinessException;
import net.dreamparadise.common.result.R;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api("aws文件管理")
@RestController
@RequestMapping("/api/oss/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("module") String module){
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url",url);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR,e);
        }
    }
    @ApiOperation("文件删除")
    @DeleteMapping("/remove")
    public R remove(@RequestParam("url") String url){
        fileService.removeFile(url);
        return R.ok().message("删除成功");
    }
}
