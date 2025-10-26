package cn.craftime.nonecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.craftime.nonecraft.config.ConfigManager;
import cn.craftime.nonecraft.geo.SimpleLocationService;
import cn.craftime.nonecraft.proxy.ProxyProtocolV2;
import cn.craftime.nonecraft.util.LoggingService;

public class TCPServer {
    private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
    
    private final ConfigManager configManager;
    private final SimpleLocationService locationService;
    private final LoggingService loggingService;
    private final int port;
    private final boolean proxyProtocolEnabled;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private volatile boolean running;
    
    public TCPServer(ConfigManager configManager, SimpleLocationService locationService,
                    LoggingService loggingService, int port) {
        this.configManager = configManager;
        this.locationService = locationService;
        this.loggingService = loggingService;
        this.port = port;
        this.proxyProtocolEnabled = configManager.getBoolean("proxy-protocol.enable", false);
        this.threadPool = Executors.newCachedThreadPool();
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            logger.info(configManager.getLang("startup.tcp_started", port));
            
            // 启动接收线程
            Thread acceptThread = new Thread(this::acceptConnections);
            acceptThread.setName("TCP-Server-Accept-" + port);
            acceptThread.start();
            
        } catch (IOException e) {
            logger.error(configManager.getLang("error.server_start_failed", e.getMessage()));
        }
    }
    
    private void acceptConnections() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                threadPool.execute(() -> handleConnection(socket));
            } catch (IOException e) {
                if (running) {
                    logger.error("接受TCP连接时出错: {}", e.getMessage());
                }
            }
        }
    }
    
    private void handleConnection(Socket socket) {
        String clientIp = socket.getInetAddress().getHostAddress();
        String geoLocation = locationService.getLocation(clientIp);
        
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            
            // 检查Proxy Protocol V2头部
            if (proxyProtocolEnabled) {
                ProxyProtocolV2.ProxyInfo proxyInfo = ProxyProtocolV2.parseProxyHeader(input);
                if (proxyInfo != null) {
                    // 使用Proxy Protocol提供的真实客户端IP
                    clientIp = proxyInfo.getSourceAddress().getHostAddress();
                    geoLocation = locationService.getLocation(clientIp);
                    logger.debug("Proxy Protocol V2检测到真实客户端IP: {}", clientIp);
                }
            }
            
            // 读取包长度
            int packetLength = MinecraftProtocol.readVarInt(input);
            if (packetLength <= 0) {
                socket.close();
                return;
            }
            
            // 读取包ID
            int packetId = MinecraftProtocol.readVarInt(input);
            
            if (packetId == MinecraftProtocol.HANDSHAKE_PACKET) {
                handleHandshake(input, output, clientIp, geoLocation);
            } else {
                // 未知包，关闭连接
                socket.close();
            }
            
        } catch (IOException e) {
            // 连接可能已关闭，正常处理
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // 忽略关闭异常
            }
        }
    }
    
    private void handleHandshake(DataInputStream input, DataOutputStream output, 
                               String clientIp, String geoLocation) throws IOException {
        // 读取协议版本
        int protocolVersion = MinecraftProtocol.readVarInt(input);
        // 读取服务器地址
        String serverAddress = MinecraftProtocol.readString(input);
        // 读取服务器端口
        int serverPort = input.readUnsignedShort();
        // 读取下一状态（1=状态，2=登录）
        int nextState = MinecraftProtocol.readVarInt(input);
        
        if (nextState == 1) {
            // 状态请求
            handleStatusRequest(input, output, clientIp, geoLocation);
        } else if (nextState == 2) {
            // 登录请求
            handleLoginRequest(input, output, clientIp, geoLocation);
        }
    }
    
    private void handleStatusRequest(DataInputStream input, DataOutputStream output, 
                                   String clientIp, String geoLocation) throws IOException {
        // 读取状态请求包（包ID=0）
        int packetLength = MinecraftProtocol.readVarInt(input);
        int packetId = MinecraftProtocol.readVarInt(input);
        
        if (packetId != MinecraftProtocol.STATUS_REQUEST_PACKET) {
            return;
        }
        
        // 构建MOTD
        List<String> motdLines = configManager.getStringList("motd");
        String motd1 = motdLines.size() > 0 ? motdLines.get(0) : "请不要访问我的服务器哦~";
        String motd2 = motdLines.size() > 1 ? motdLines.get(1) : "来自 %geo% 的玩家~";
        
        // 替换占位符
        motd1 = motd1.replace("%geo%", geoLocation);
        motd2 = motd2.replace("%geo%", geoLocation);
        
        String statusResponse = MinecraftProtocol.buildStatusResponse(motd1, motd2);
        
        // 发送状态响应
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream tempOutput = new DataOutputStream(buffer);
        MinecraftProtocol.writeString(tempOutput, statusResponse);
        
        MinecraftProtocol.sendPacket(output, MinecraftProtocol.STATUS_RESPONSE_PACKET, buffer.toByteArray());
        
        // 等待PING请求
        handlePingRequest(input, output, clientIp, geoLocation);
        
        loggingService.logPing(clientIp, geoLocation);
    }
    
    private void handlePingRequest(DataInputStream input, DataOutputStream output,
                                 String clientIp, String geoLocation) throws IOException {
        try {
            // 读取PING包长度和ID
            int pingLength = MinecraftProtocol.readVarInt(input);
            int pingId = MinecraftProtocol.readVarInt(input);
            
            if (pingId == MinecraftProtocol.PING_REQUEST_PACKET) {
                // 读取PING时间戳
                long pingTime = input.readLong();
                
                // 发送PING响应
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                DataOutputStream tempOutput = new DataOutputStream(buffer);
                tempOutput.writeLong(pingTime);
                
                MinecraftProtocol.sendPacket(output, MinecraftProtocol.PING_RESPONSE_PACKET, buffer.toByteArray());
            }
        } catch (IOException e) {
            // PING请求可能超时或失败，正常处理
        }
    }
    
    private void handleLoginRequest(DataInputStream input, DataOutputStream output,
                                  String clientIp, String geoLocation) throws IOException {
        // 读取登录开始包（包ID=0）
        int packetLength = MinecraftProtocol.readVarInt(input);
        int packetId = MinecraftProtocol.readVarInt(input);
        
        if (packetId != MinecraftProtocol.LOGIN_START_PACKET) {
            return;
        }
        
        // 读取玩家名
        String playerName = MinecraftProtocol.readString(input);
        
        // 构建拒绝消息
        List<String> refuseLines = configManager.getStringList("refuse");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        
        String[] processedLines = new String[refuseLines.size()];
        for (int i = 0; i < refuseLines.size(); i++) {
            processedLines[i] = refuseLines.get(i)
                .replace("%geo%", geoLocation)
                .replace("%player%", playerName)
                .replace("%time%", currentTime);
        }
        
        String disconnectMessage = MinecraftProtocol.buildMultiLineDisconnectMessage(processedLines);
        
        // 发送断开连接包
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream tempOutput = new DataOutputStream(buffer);
        MinecraftProtocol.writeString(tempOutput, disconnectMessage);
        
        MinecraftProtocol.sendPacket(output, MinecraftProtocol.DISCONNECT_PACKET, buffer.toByteArray());
        
        loggingService.logConnectionAttempt(clientIp, playerName, geoLocation);
    }
    
    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("关闭TCP服务器时出错: {}", e.getMessage());
        }
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public int getPort() {
        return port;
    }
}