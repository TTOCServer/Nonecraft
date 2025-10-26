package cn.craftime.nonecraft.geo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.craftime.nonecraft.config.ConfigManager;

public class SimpleLocationService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLocationService.class);
    
    private final ConfigManager configManager;
    private final Map<String, String> ip2stressMap;
    private boolean ip2stressEnabled;
    
    public SimpleLocationService(ConfigManager configManager) {
        this.configManager = configManager;
        this.ip2stressMap = new ConcurrentHashMap<>();
        initialize();
    }
    
    private void initialize() {
        ip2stressEnabled = configManager.getBoolean("ip2stress.enable", true);
        
        logger.info("地理位置服务初始化 - IP2Stress: {}", ip2stressEnabled);
        
        if (ip2stressEnabled) {
            loadIP2StressMap();
        }
        
        logger.info("地理位置服务初始化完成 - IP2Stress: {}", ip2stressEnabled);
    }
    
    private void loadIP2StressMap() {
        File ip2stressFile = new File("ip2stress.txt");
        if (!ip2stressFile.exists()) {
            logger.warn("ip2stress.txt文件不存在，将创建空文件");
            try {
                ip2stressFile.createNewFile();
                logger.info("已创建空的ip2stress.txt文件");
            } catch (IOException e) {
                logger.error("创建ip2stress.txt文件失败: {}", e.getMessage());
                ip2stressEnabled = false;
                return;
            }
        }
        
        try {
            Files.lines(Paths.get("ip2stress.txt"))
                    .filter(line -> !line.trim().isEmpty() && line.contains("="))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String ip = parts[0].trim();
                            String location = parts[1].trim();
                            ip2stressMap.put(ip, location);
                        }
                    });
            logger.info("IP2Stress映射文件加载成功，共 {} 条记录", ip2stressMap.size());
        } catch (IOException e) {
            logger.error("IP2Stress映射文件加载失败: {}", e.getMessage());
            ip2stressEnabled = false;
        }
    }
    
    public String getLocation(String ipAddress) {
        // 检查ip2stress映射
        if (ip2stressEnabled) {
            String location = ip2stressMap.get(ipAddress);
            if (location != null) {
                logger.debug("IP2Stress找到位置: {} -> {}", ipAddress, location);
                return location;
            }
        }
        
        logger.debug("未找到IP {} 的位置映射", ipAddress);
        return "未知位置";
    }
    
    public boolean isEnabled() {
        return ip2stressEnabled;
    }
    
    public void reload() {
        ip2stressMap.clear();
        initialize();
    }
    
    public void close() {
        ip2stressMap.clear();
    }
}