package cn.craftime.nonecraft.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceManager {
    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);
    private static final String[] REQUIRED_RESOURCES = {
        "lang/zh_cn.yml",
        "lang/en_us.yml"
    };

    /**
     * 提取所有必要的资源文件到运行目录
     */
    public static void extractResources() {
        logger.info("开始提取资源文件...");
        
        // 创建运行目录下的lang文件夹
        File langDir = new File("lang");
        if (!langDir.exists()) {
            langDir.mkdirs();
            logger.info("创建语言文件夹: {}", langDir.getAbsolutePath());
        }
        
        boolean allSuccess = true;
        for (String resourcePath : REQUIRED_RESOURCES) {
            if (!extractResource(resourcePath)) {
                allSuccess = false;
            }
        }
        
        if (allSuccess) {
            logger.info("所有资源文件提取完成");
        } else {
            logger.warn("部分资源文件提取失败，请检查文件完整性");
        }
    }

    /**
     * 提取单个资源文件
     */
    private static boolean extractResource(String resourcePath) {
        File targetFile = new File(resourcePath);
        
        // 如果目标文件已存在且大小正常，则跳过提取
        if (targetFile.exists() && targetFile.length() > 0) {
            logger.debug("资源文件已存在: {} ({} bytes)", resourcePath, targetFile.length());
            return true;
        }
        
        try (InputStream inputStream = ResourceManager.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                logger.error("在JAR包中找不到资源文件: {}", resourcePath);
                return false;
            }
            
            // 确保目标文件的父目录存在
            File parentDir = targetFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // 复制文件
            try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            
            logger.info("成功提取资源文件: {} ({} bytes)", resourcePath, targetFile.length());
            return true;
            
        } catch (IOException e) {
            logger.error("提取资源文件失败: {}", resourcePath, e);
            return false;
        }
    }

    /**
     * 检查必要的资源文件是否存在
     */
    public static boolean checkRequiredResources() {
        for (String resourcePath : REQUIRED_RESOURCES) {
            File file = new File(resourcePath);
            if (!file.exists() || file.length() == 0) {
                logger.warn("必要的资源文件缺失或为空: {}", resourcePath);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取运行目录的绝对路径
     */
    public static String getWorkingDirectory() {
        try {
            return new File(".").getCanonicalPath();
        } catch (IOException e) {
            return System.getProperty("user.dir");
        }
    }
}