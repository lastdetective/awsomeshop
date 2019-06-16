package com.leyou.upload.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExceptiion;
import lombok.extern.slf4j.Slf4j;
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
public class UploadService {
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg", "image/bmp");

    public String uploadImage(MultipartFile file) {
        //URL url = file.getClass().getClassLoader().getResource("");
        if (!suffixes.contains(file.getContentType())) {
            throw new LyExceptiion(ExceptionEnum.FILE_TYPE_ERROR);
        }
        String directory = "D:\\workspace\\study\\leyou\\upload\\";
        File dest = new File(directory + file.getOriginalFilename());
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new LyExceptiion(ExceptionEnum.IAVALID_FILE);
            }
            file.transferTo(dest);
            return "http://iamge.awsomeshop.club/" + file.getOriginalFilename();
        } catch (IOException e) {
            log.error("文件上传出错" + e.getMessage());
            throw new LyExceptiion(ExceptionEnum.UPLOAD_IMAGE_ERROR);
        }
    }
}
