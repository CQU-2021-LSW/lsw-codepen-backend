package com.lowt.codepenlowt.controller;

import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RequestMapping("upload")
@RestController
public class FileUploadController {

    /**
     * 用户管理 -> 业务层
     */
    @Autowired
    private TableUserService tableUserService;

    /**
     * 文件上传根目录(在Spring的application.yml的配置文件中配置):<br>
     * web:
     * upload-path: （jar包所在目录）/resources/static/
     */

    @Value("${img.upload-path}")
    private String webUploadPath;

    @Value("${img.upload-server-ip}")
    public String imgFileServerIp;

    @Value("${img.upload-server-port}")
    public String imgFileServerPort;

    /**
     * 基于用户标识的头像上传
     *
     * @param file   图片
     * @param userId 用户标识
     * @return
     */
    @PostMapping(value = "/imgUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R imgUpload(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) throws IOException {
        if (file != null) {
            if (Objects.requireNonNull(file.getContentType()).contains("image")) {
                if (file.getSize() == 0) {
                    return R.error("文件大小异常");
                }
                if (file.getOriginalFilename() != null) {
                    // 存入服务器
                    String[] suffixStr = file.getOriginalFilename().split("\\.");
                    String suffix = suffixStr[suffixStr.length - 1];
                    File newFile = new File(webUploadPath + userId + "." + suffix);
                    file.transferTo(newFile);
//                    // Linux下有问题
//                    // 给用户存入URL
//                    // 获得本机Ip（获取的是服务器的Ip）
//                    InetAddress inetAddress = InetAddress.getLocalHost();
//                    String ip = inetAddress.getHostAddress();
                    // 返回保存的url，根据url可以进行文件查看或者下载
                    String fileDownloadUrl = imgFileServerIp + ":" + imgFileServerPort + "/img/photo/" + userId + "." + suffix;
                    //在这里把路径url存到数据库
                    tableUserService.updateUserPhoto(fileDownloadUrl, userId);
                    //返回保存的url
                    return R.ok();
                }
            }
            return R.error("文件不为图片类型");
        }
        return R.error("文件为空");

        /*
        System.err.println("文件是否为空 ： " + file.isEmpty());
        System.err.println("文件的大小为 ：" + file.getSize() + "字节");
        System.err.println("文件的媒体类型为 ： " + file.getContentType());
        System.err.println("文件的名字： " + file.getName());
        System.err.println("文件的originName为： " + file.getOriginalFilename());
        */
    }
}
