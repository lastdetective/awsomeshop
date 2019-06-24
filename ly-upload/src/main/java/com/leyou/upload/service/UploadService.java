package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExceptiion;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {
    @Autowired
    private UploadProperties prop;
    // private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg", "image/bmp");
    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    public String uploadImage(MultipartFile file) {
        //URL url = file.getClass().getClassLoader().getResource("");
        /**
         *  File file = new File("D:\\pics\\baby.png");
         *         // 上传并且生成缩略图
         *         StorePath storePath = this.storageClient.uploadFile(
         *                 new FileInputStream(file), file.length(), "png", null);
         *         // 带分组的路径
         *         System.out.println(storePath.getFullPath());
         *         // 不带分组的路径
         *         System.out.println(storePath.getPath());
         */
        if (!prop.getAllowTypes().contains(file.getContentType())) {
            throw new LyExceptiion(ExceptionEnum.FILE_TYPE_ERROR);
        }
        // String filePrefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        //   String directory = "D:\\workspace\\study\\leyou\\upload\\";
        //  File dest = new File(directory + file.getOriginalFilename());

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new LyExceptiion(ExceptionEnum.INVALID_FILE);
            }
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            //  file.transferTo(dest);
            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            log.error("文件上传出错" + e.getMessage());
            throw new LyExceptiion(ExceptionEnum.UPLOAD_IMAGE_ERROR);
        }
    }
}
