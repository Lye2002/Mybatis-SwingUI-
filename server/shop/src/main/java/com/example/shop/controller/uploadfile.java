package com.example.shop.controller;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class uploadfile {

    @PostMapping("/upload")
    public Object uploadHandler(HttpServletRequest request, String title, MultipartFile file) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("title", title);
        resultMap.put("fileName", file.getName()); // 文件名
        resultMap.put("originalFilename", file.getOriginalFilename()); // 原始名称
        resultMap.put("content-type", file.getContentType()); // 文件类型
        resultMap.put("fileSize", file.getSize() / 1024 + "K"); // 文件大小
        try {
            // 保存文件
            String uploadedFilePath = saveFile(request, file.getInputStream(), file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            resultMap.put("uploadedFilePath", uploadedFilePath); // 文件
            return resultMap;
        } catch (IOException e) {
            System.err.println("error-path: /upload/file, message: " + e.getMessage());
            return e.getMessage();
        }

    }

    /**
     * 保存上传的文件到本地服务器
     *
     * @param request HttpServletRequest
     * @param input   输入流
     * @param ext     文件扩展名
     * @return 文件路径
     * @throws IOException
     */
    public String saveFile(HttpServletRequest request, InputStream input, String ext) throws IOException {
        //String realPath = request.getServletContext().getRealPath("/upload/file/"); // 取得服务器真实路径

        //String realPath= ResourceUtils.getURL("classpath:").getPath() + "static/image/";
       // /C:/Users/Lye/Desktop/shop/target/classes/static/image/

        String realPath= System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\";
        //String realPath= System.getProperty("user.dir") + "\\src\\main\\resources\\iii\\";
        //System.out.println(realPath);
        File file = new File(realPath);
        if (!file.getParentFile().exists()) { // 目录不存在
            file.mkdirs(); // 创建多级目录
        }
        String filePath = realPath + UUID.randomUUID() + "." + ext;
        // 取的文件输出流
        OutputStream out = new FileOutputStream(filePath);
        byte[] data = new byte[2048]; // 缓冲数组2KB
        int len = 0; // 读取字节长度
        while ((len = input.read(data)) != -1) {
            out.write(data, 0, len); // 文件写入磁盘
        }
        if (input != null) {
            input.close();
        }
        out.close();
        System.out.println(filePath);

        return filePath;
    }


}