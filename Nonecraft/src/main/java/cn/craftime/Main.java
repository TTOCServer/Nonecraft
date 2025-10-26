package cn.craftime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.craftime.nonecraft.config.ConfigManager;
import cn.craftime.nonecraft.geo.SimpleLocationService;
import cn.craftime.nonecraft.server.TCPServer;
import cn.craftime.nonecraft.server.UDPServer;
import cn.craftime.nonecraft.util.LoggingService;
import cn.craftime.nonecraft.util.ResourceManager;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    private static ConfigManager configManager;
    private static SimpleLocationService locationService;
    private static LoggingService loggingService;
    private static List<TCPServer> tcpServers = new ArrayList<>();
    private static List<UDPServer> udpServers = new ArrayList<>();
    private static volatile boolean running = true;
    
    public static void main(String[] args) {
        try {
            // 初始化服务
            initializeServices();
            
            // 启动服务器
            startServers();
            
            // 添加关闭钩子
            addShutdownHook();
            
            // 启动控制台命令处理
            startConsoleCommandHandler();
            
        } catch (Exception e) {
            logger.error("Nonecraft启动失败: {}", e.getMessage());
            System.exit(1);
        }
    }
    
    private static void initializeServices() {
        logger.info("正在初始化Nonecraft...");
        
        // 提取资源文件到运行目录
        ResourceManager.extractResources();
        
        // 检查必要的资源文件是否存在
        if (!ResourceManager.checkRequiredResources()) {
            logger.warn("部分必要的资源文件缺失，地理位置服务可能无法正常工作");
        }
        
        // 初始化配置管理器
        configManager = new ConfigManager();
        loggingService = new LoggingService(configManager);
        locationService = new SimpleLocationService(configManager);
        
        loggingService.logInfo("Nonecraft服务初始化完成");
    }
    
    private static void startServers() {
        logger.info("正在启动服务器...");
        
        // 启动TCP服务器
        List<Integer> tcpPorts = configManager.getIntList("tcp");
        for (int port : tcpPorts) {
            try {
                TCPServer tcpServer = new TCPServer(configManager, locationService, loggingService, port);
                tcpServer.start();
                tcpServers.add(tcpServer);
                loggingService.logInfo("TCP服务器已在端口 " + port + " 启动");
            } catch (Exception e) {
                loggingService.logError("TCP服务器在端口 " + port + " 启动失败: " + e.getMessage());
            }
        }
        
        // 启动UDP服务器
        List<Integer> udpPorts = configManager.getIntList("udp");
        for (int port : udpPorts) {
            try {
                UDPServer udpServer = new UDPServer(configManager, locationService, loggingService, port);
                udpServer.start();
                udpServers.add(udpServer);
                loggingService.logInfo("UDP服务器已在端口 " + port + " 启动");
            } catch (Exception e) {
                loggingService.logError("UDP服务器在端口 " + port + " 启动失败: " + e.getMessage());
            }
        }
        
        logger.info("Nonecraft服务器已准备就绪");
        loggingService.logInfo("Nonecraft服务器已准备就绪");
    }
    
    private static void startConsoleCommandHandler() {
        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            
            while (running) {
                try {
                    System.out.print("> ");
                    if (scanner.hasNextLine()) {
                        String command = scanner.nextLine().trim().toLowerCase();
                        
                        switch (command) {
                            case "reload":
                            case "rl":
                                reloadConfig();
                                break;
                            case "stop":
                            case "exit":
                            case "quit":
                                shutdown();
                                break;
                            case "status":
                                showStatus();
                                break;
                            case "help":
                                showHelp();
                                break;
                            case "":
                                // 空命令，忽略
                                break;
                            default:
                                System.out.println("未知命令。输入 'help' 查看可用命令。");
                                break;
                        }
                    }
                } catch (Exception e) {
                    logger.error("控制台命令处理错误: {}", e.getMessage());
                }
            }
            
            scanner.close();
        });
        
        consoleThread.setName("Console-Command-Handler");
        consoleThread.setDaemon(true);
        consoleThread.start();
    }
    
    private static void reloadConfig() {
        logger.info("重新加载配置...");
        
        // 重新加载配置
        configManager.reload();
        locationService.reload();
        loggingService.reload();
        
        // 停止所有服务器
        stopAllServers();
        
        // 清空服务器列表
        tcpServers.clear();
        udpServers.clear();
        
        // 重新启动服务器
        startServers();
        
        logger.info("配置重新加载完成");
        System.out.println("配置已重新加载");
    }
    
    private static void showStatus() {
        System.out.println("=== Nonecraft 服务器状态 ===");
        System.out.println("TCP服务器数量: " + tcpServers.size());
        for (TCPServer server : tcpServers) {
            System.out.println("  - 端口 " + server.getPort() + ": " +
                             (server.isRunning() ? "运行中" : "已停止"));
        }
        
        System.out.println("UDP服务器数量: " + udpServers.size());
        for (UDPServer server : udpServers) {
            System.out.println("  - 端口 " + server.getPort() + ": " +
                             (server.isRunning() ? "运行中" : "已停止"));
        }
        
        System.out.println("IP2Stress映射: " + (locationService.isEnabled() ? "已启用" : "未启用"));
    }
    
    private static void showHelp() {
        System.out.println("=== Nonecraft 命令帮助 ===");
        System.out.println("reload, rl  - 重新加载配置文件");
        System.out.println("status      - 显示服务器状态");
        System.out.println("stop, exit  - 停止服务器");
        System.out.println("help        - 显示此帮助信息");
    }
    
    private static void stopAllServers() {
        logger.info("正在停止所有服务器...");
        
        for (TCPServer server : tcpServers) {
            server.stop();
        }
        
        for (UDPServer server : udpServers) {
            server.stop();
        }
        
        loggingService.logInfo("所有服务器已停止");
    }
    
    private static void shutdown() {
        logger.info("正在关闭Nonecraft...");
        running = false;
        
        stopAllServers();
        
        // 关闭服务
        if (locationService != null) {
            locationService.close();
        }
        
        if (loggingService != null) {
            loggingService.close();
        }
        
        logger.info("Nonecraft已关闭");
        System.exit(0);
    }
    
    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("收到关闭信号，正在清理资源...");
            running = false;
            stopAllServers();
            
            if (locationService != null) {
                locationService.close();
            }
            
            if (loggingService != null) {
                loggingService.close();
            }
        }));
    }
}