package cn.craftime.nonecraft.util;

import cn.craftime.nonecraft.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    
    private final ConfigManager configManager;
    private final ReentrantLock lock;
    private PrintWriter logWriter;
    private String currentLogFile;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat logDateFormat;
    
    private boolean logPing;
    private boolean logConnections;
    private boolean logGeo;
    
    public LoggingService(ConfigManager configManager) {
        this.configManager = configManager;
        this.lock = new ReentrantLock();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initialize();
    }
    
    private void initialize() {
        logPing = configManager.getBoolean("log.log-ping", true);
        logConnections = configManager.getBoolean("log.log-connections", true);
        logGeo = configManager.getBoolean("log.log-geo", true);
        
        // 创建日志目录
        File logDir = new File("log");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        
        // 初始化日志文件
        updateLogFile();
    }
    
    private void updateLogFile() {
        String today = dateFormat.format(new Date());
        String newLogFile = "log/" + today + ".log";
        
        if (!newLogFile.equals(currentLogFile)) {
            lock.lock();
            try {
                if (logWriter != null) {
                    logWriter.close();
                }
                
                currentLogFile = newLogFile;
                logWriter = new PrintWriter(new FileWriter(currentLogFile, true), true);
                
            } catch (IOException e) {
                logger.error("无法创建日志文件: {}", e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }
    
    public void logPing(String ipAddress, String geoLocation) {
        if (!logPing) return;
        
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String message = String.format("[%s] PING请求来自: %s", timestamp, ipAddress);
        
        if (logGeo && geoLocation != null && !geoLocation.equals("未知位置")) {
            message += String.format(" [位置: %s]", geoLocation);
        }
        
        writeToLog(message);
        logger.info(configManager.getLang("log.ping_received", ipAddress));
    }
    
    public void logConnectionAttempt(String ipAddress, String playerName, String geoLocation) {
        if (!logConnections) return;
        
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String message = String.format("[%s] 连接尝试来自: %s", timestamp, ipAddress);
        
        if (playerName != null && !playerName.isEmpty()) {
            message += String.format(" [玩家: %s]", playerName);
        }
        
        if (logGeo && geoLocation != null && !geoLocation.equals("未知位置")) {
            message += String.format(" [位置: %s]", geoLocation);
        }
        
        writeToLog(message);
        logger.info(configManager.getLang("log.connection_attempt", ipAddress));
        
        if (playerName != null && !playerName.isEmpty()) {
            logger.info(configManager.getLang("log.player_join_attempt", playerName));
        }
        
        if (logGeo && geoLocation != null && !geoLocation.equals("未知位置")) {
            logger.info(configManager.getLang("log.geo_location", geoLocation));
        }
    }
    
    public void logGeoLocation(String ipAddress, String geoLocation) {
        if (!logGeo) return;
        
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String message = String.format("[%s] 地理位置查询: %s -> %s", timestamp, ipAddress, geoLocation);
        
        writeToLog(message);
    }
    
    public void logInfo(String message) {
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String logMessage = String.format("[%s] INFO: %s", timestamp, message);
        
        writeToLog(logMessage);
        logger.info(message);
    }
    
    public void logWarning(String message) {
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String logMessage = String.format("[%s] WARNING: %s", timestamp, message);
        
        writeToLog(logMessage);
        logger.warn(message);
    }
    
    public void logError(String message) {
        updateLogFile();
        String timestamp = logDateFormat.format(new Date());
        String logMessage = String.format("[%s] ERROR: %s", timestamp, message);
        
        writeToLog(logMessage);
        logger.error(message);
    }
    
    private void writeToLog(String message) {
        lock.lock();
        try {
            if (logWriter != null) {
                logWriter.println(message);
                logWriter.flush();
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void reload() {
        lock.lock();
        try {
            logPing = configManager.getBoolean("log.log-ping", true);
            logConnections = configManager.getBoolean("log.log-connections", true);
            logGeo = configManager.getBoolean("log.log-geo", true);
            
            if (logWriter != null) {
                logWriter.close();
                logWriter = null;
            }
            
            updateLogFile();
            
        } finally {
            lock.unlock();
        }
    }
    
    public void close() {
        lock.lock();
        try {
            if (logWriter != null) {
                logWriter.close();
                logWriter = null;
            }
        } finally {
            lock.unlock();
        }
    }
}