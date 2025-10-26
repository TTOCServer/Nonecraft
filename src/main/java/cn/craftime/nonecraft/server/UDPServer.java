package cn.craftime.nonecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.craftime.nonecraft.config.ConfigManager;
import cn.craftime.nonecraft.geo.SimpleLocationService;
import cn.craftime.nonecraft.util.LoggingService;

public class UDPServer {
    private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);
    
    // 基岩版协议常量
    private static final byte[] MAGIC = new byte[] {
        (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x00,
        (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe,
        (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd,
        (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78
    };
    
    private static final int PACKET_UNCONNECTED_PING = 0x01;
    private static final int PACKET_UNCONNECTED_PONG = 0x1c;
    private static final int PACKET_OPEN_CONNECTION_REQUEST_1 = 0x05;
    private static final int PACKET_OPEN_CONNECTION_REPLY_1 = 0x06;
    private static final int PACKET_OPEN_CONNECTION_REQUEST_2 = 0x07;
    private static final int PACKET_OPEN_CONNECTION_REPLY_2 = 0x08;
    private static final int PACKET_CONNECTION_REQUEST = 0x09;
    private static final int PACKET_DISCONNECTION_NOTIFICATION = 0x15;
    
    private final ConfigManager configManager;
    private final SimpleLocationService locationService;
    private final LoggingService loggingService;
    private final int port;
    private DatagramSocket socket;
    private ExecutorService threadPool;
    private volatile boolean running;
    
    public UDPServer(ConfigManager configManager, SimpleLocationService locationService,
                    LoggingService loggingService, int port) {
        this.configManager = configManager;
        this.locationService = locationService;
        this.loggingService = loggingService;
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
    }
    
    public void start() {
        try {
            socket = new DatagramSocket(port);
            running = true;
            logger.info(configManager.getLang("startup.udp_started", port));
            
            // 启动接收线程
            Thread receiveThread = new Thread(this::receivePackets);
            receiveThread.setName("UDP-Server-Receive-" + port);
            receiveThread.start();
            
        } catch (SocketException e) {
            logger.error(configManager.getLang("error.server_start_failed", e.getMessage()));
        }
    }
    
    private void receivePackets() {
        byte[] buffer = new byte[4096];
        
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                threadPool.execute(() -> handlePacket(packet));
            } catch (IOException e) {
                if (running) {
                    logger.error("接收UDP数据包时出错: {}", e.getMessage());
                }
            }
        }
    }
    
    private void handlePacket(DatagramPacket packet) {
        try {
            byte[] data = packet.getData();
            int length = packet.getLength();
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            String clientIp = clientAddress.getHostAddress();
            String geoLocation = locationService.getLocation(clientIp);
            
            if (length < 1) return;
            
            int packetId = data[0] & 0xFF;
            
            switch (packetId) {
                case PACKET_UNCONNECTED_PING:
                    handleUnconnectedPing(data, length, clientAddress, clientPort, clientIp, geoLocation);
                    break;
                case PACKET_OPEN_CONNECTION_REQUEST_1:
                    handleOpenConnectionRequest1(data, length, clientAddress, clientPort, clientIp, geoLocation);
                    break;
                case PACKET_OPEN_CONNECTION_REQUEST_2:
                    handleOpenConnectionRequest2(data, length, clientAddress, clientPort, clientIp, geoLocation);
                    break;
                case PACKET_CONNECTION_REQUEST:
                    handleConnectionRequest(data, length, clientAddress, clientPort, clientIp, geoLocation);
                    break;
                default:
                    // 忽略未知包
                    break;
            }
            
        } catch (Exception e) {
            logger.error("处理UDP数据包时出错: {}", e.getMessage());
        }
    }
    
    private void handleUnconnectedPing(byte[] data, int length, InetAddress clientAddress, 
                                      int clientPort, String clientIp, String geoLocation) throws IOException {
        // 读取时间戳
        long pingTime = readLong(data, 1);
        
        // 读取客户端GUID
        long clientGuid = readLong(data, 17);
        
        // 检查MAGIC
        if (!checkMagic(data, 9)) {
            return; // MAGIC不匹配，忽略包
        }
        
        // 构建PONG响应
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(buffer);
        
        // 包ID
        output.writeByte(PACKET_UNCONNECTED_PONG);
        
        // 时间戳
        writeLong(output, pingTime);
        
        // 服务器GUID（使用固定值）
        writeLong(output, 123456789L);
        
        // MAGIC
        output.write(MAGIC);
        
        // 服务器信息字符串
        String serverInfo = buildServerInfo(clientIp, geoLocation);
        output.writeShort(serverInfo.length());
        output.write(serverInfo.getBytes(StandardCharsets.UTF_8));
        
        // 发送响应
        byte[] response = buffer.toByteArray();
        DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
        socket.send(responsePacket);
        
        loggingService.logPing(clientIp, geoLocation);
    }
    
    private void handleOpenConnectionRequest1(byte[] data, int length, InetAddress clientAddress, 
                                            int clientPort, String clientIp, String geoLocation) throws IOException {
        // 读取协议版本
        byte protocolVersion = data[17];
        
        // 构建响应
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(buffer);
        
        // 包ID
        output.writeByte(PACKET_OPEN_CONNECTION_REPLY_1);
        
        // MAGIC
        output.write(MAGIC);
        
        // 服务器GUID
        writeLong(output, 123456789L);
        
        // 安全（0=不安全）
        output.writeByte(0);
        
        // MTU大小
        output.writeShort(1400);
        
        // 发送响应
        byte[] response = buffer.toByteArray();
        DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
        socket.send(responsePacket);
        
        loggingService.logConnectionAttempt(clientIp, "基岩版玩家", geoLocation);
    }
    
    private void handleOpenConnectionRequest2(byte[] data, int length, InetAddress clientAddress, 
                                            int clientPort, String clientIp, String geoLocation) throws IOException {
        // 读取客户端地址和端口
        // 读取MTU大小
        int mtuSize = readShort(data, 25);
        
        // 读取客户端GUID
        long clientGuid = readLong(data, 27);
        
        // 构建响应
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(buffer);
        
        // 包ID
        output.writeByte(PACKET_OPEN_CONNECTION_REPLY_2);
        
        // MAGIC
        output.write(MAGIC);
        
        // 服务器GUID
        writeLong(output, 123456789L);
        
        // 客户端地址和端口
        output.write(clientAddress.getAddress());
        output.writeShort(clientPort);
        
        // MTU大小
        output.writeShort(mtuSize);
        
        // 安全（0=不安全）
        output.writeByte(0);
        
        // 发送响应
        byte[] response = buffer.toByteArray();
        DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
        socket.send(responsePacket);
        
        loggingService.logConnectionAttempt(clientIp, "基岩版玩家", geoLocation);
    }
    
    private void handleConnectionRequest(byte[] data, int length, InetAddress clientAddress,
                                       int clientPort, String clientIp, String geoLocation) throws IOException {
        // 基岩版连接请求，我们立即发送断开连接通知
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(buffer);
        
        // 包ID
        output.writeByte(PACKET_DISCONNECTION_NOTIFICATION);
        
        // 发送断开连接包
        byte[] response = buffer.toByteArray();
        DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
        socket.send(responsePacket);
        
        // 记录连接尝试
        loggingService.logConnectionAttempt(clientIp, "基岩版玩家", geoLocation);
    }
    
    private String buildServerInfo(String clientIp, String geoLocation) {
        List<String> motdLines = configManager.getStringList("motd");
        String motd1 = motdLines.size() > 0 ? motdLines.get(0) : "请不要访问我的服务器哦~";
        String motd2 = motdLines.size() > 1 ? motdLines.get(1) : "来自 %geo% 的玩家~";
        
        // 替换占位符
        motd1 = motd1.replace("%geo%", geoLocation);
        motd2 = motd2.replace("%geo%", geoLocation);
        
        // 基岩版服务器信息格式
        return String.format(
            "MCPE;%s;191;1.20.15;0;10;123456789;%s;Survival;1;19132;19133;",
            motd1 + " " + motd2,
            "Nonecraft"
        );
    }
    
    // 辅助方法：读取long
    private long readLong(byte[] data, int offset) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result |= ((long) (data[offset + i] & 0xFF)) << (8 * i);
        }
        return result;
    }
    
    // 辅助方法：写入long
    private void writeLong(DataOutputStream output, long value) throws IOException {
        for (int i = 0; i < 8; i++) {
            output.writeByte((int) (value & 0xFF));
            value >>= 8;
        }
    }
    
    // 辅助方法：读取short
    private int readShort(byte[] data, int offset) {
        return ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
    }
    
    // 辅助方法：检查MAGIC
    private boolean checkMagic(byte[] data, int offset) {
        for (int i = 0; i < MAGIC.length; i++) {
            if (data[offset + i] != MAGIC[i]) {
                return false;
            }
        }
        return true;
    }
    
    public void stop() {
        running = false;
        if (socket != null) {
            socket.close();
        }
        threadPool.shutdown();
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public int getPort() {
        return port;
    }
}