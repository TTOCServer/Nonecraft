package cn.craftime.nonecraft.proxy;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ProxyProtocolV2 {
    // Proxy Protocol V2 常量
    private static final byte[] SIGNATURE = {
        (byte) 0x0D, (byte) 0x0A, (byte) 0x0D, (byte) 0x0A,
        (byte) 0x00, (byte) 0x0D, (byte) 0x0A, (byte) 0x51,
        (byte) 0x55, (byte) 0x49, (byte) 0x54, (byte) 0x0A
    };
    
    // 版本和命令
    private static final byte VERSION_COMMAND_LOCAL = 0x20;  // 版本2 + 本地命令
    private static final byte VERSION_COMMAND_PROXY = 0x21;  // 版本2 + 代理命令
    
    // 地址族和传输协议
    private static final byte FAMILY_UNSPEC = 0x00;
    private static final byte FAMILY_INET = 0x10;    // IPv4
    private static final byte FAMILY_INET6 = 0x20;   // IPv6
    private static final byte FAMILY_UNIX = 0x30;    // UNIX
    
    private static final byte TRANSPORT_STREAM = 0x01;  // TCP
    private static final byte TRANSPORT_DGRAM = 0x02;   // UDP
    
    /**
     * 解析Proxy Protocol V2头部
     */
    public static ProxyInfo parseProxyHeader(DataInputStream input) throws IOException {
        // 检查签名
        byte[] signature = new byte[12];
        input.readFully(signature);
        
        if (!isValidSignature(signature)) {
            return null; // 不是Proxy Protocol V2
        }
        
        // 读取版本和命令
        byte versionCommand = input.readByte();
        if ((versionCommand & 0xF0) != 0x20) {
            return null; // 不是版本2
        }
        
        boolean isProxy = (versionCommand & 0x0F) == 0x01;
        if (!isProxy) {
            return null; // 本地命令，不包含地址信息
        }
        
        // 读取地址族和传输协议
        byte familyTransport = input.readByte();
        int addressFamily = (familyTransport & 0xF0) >> 4;
        int transport = familyTransport & 0x0F;
        
        // 读取地址长度
        int addressLength = input.readUnsignedShort();
        
        // 根据地址族解析地址信息
        switch (addressFamily) {
            case 0x01: // IPv4
                return parseIPv4Address(input, transport);
            case 0x02: // IPv6
                return parseIPv6Address(input, transport);
            case 0x03: // UNIX
                return parseUnixAddress(input, addressLength);
            default:
                return null; // 不支持的地址族
        }
    }
    
    /**
     * 检查签名是否有效
     */
    private static boolean isValidSignature(byte[] signature) {
        if (signature.length != SIGNATURE.length) {
            return false;
        }
        
        for (int i = 0; i < SIGNATURE.length; i++) {
            if (signature[i] != SIGNATURE[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 解析IPv4地址信息
     */
    private static ProxyInfo parseIPv4Address(DataInputStream input, int transport) throws IOException {
        // 读取源地址和端口
        byte[] srcAddr = new byte[4];
        input.readFully(srcAddr);
        int srcPort = input.readUnsignedShort();
        
        // 读取目标地址和端口
        byte[] dstAddr = new byte[4];
        input.readFully(dstAddr);
        int dstPort = input.readUnsignedShort();
        
        try {
            InetAddress sourceAddress = Inet4Address.getByAddress(srcAddr);
            InetAddress destAddress = Inet4Address.getByAddress(dstAddr);
            
            return new ProxyInfo(sourceAddress, srcPort, destAddress, dstPort, transport);
        } catch (UnknownHostException e) {
            return null;
        }
    }
    
    /**
     * 解析IPv6地址信息
     */
    private static ProxyInfo parseIPv6Address(DataInputStream input, int transport) throws IOException {
        // 读取源地址和端口
        byte[] srcAddr = new byte[16];
        input.readFully(srcAddr);
        int srcPort = input.readUnsignedShort();
        
        // 读取目标地址和端口
        byte[] dstAddr = new byte[16];
        input.readFully(dstAddr);
        int dstPort = input.readUnsignedShort();
        
        try {
            InetAddress sourceAddress = Inet6Address.getByAddress(srcAddr);
            InetAddress destAddress = Inet6Address.getByAddress(dstAddr);
            
            return new ProxyInfo(sourceAddress, srcPort, destAddress, dstPort, transport);
        } catch (UnknownHostException e) {
            return null;
        }
    }
    
    /**
     * 解析UNIX地址信息
     */
    private static ProxyInfo parseUnixAddress(DataInputStream input, int addressLength) throws IOException {
        // 读取源地址
        byte[] srcAddr = new byte[108];
        input.readFully(srcAddr);
        
        // 读取目标地址
        byte[] dstAddr = new byte[108];
        input.readFully(dstAddr);
        
        String sourcePath = new String(srcAddr, StandardCharsets.US_ASCII).trim();
        String destPath = new String(dstAddr, StandardCharsets.US_ASCII).trim();
        
        return new ProxyInfo(sourcePath, destPath);
    }
    
    /**
     * Proxy Protocol信息类
     */
    public static class ProxyInfo {
        private final InetAddress sourceAddress;
        private final int sourcePort;
        private final InetAddress destAddress;
        private final int destPort;
        private final int transport;
        private final String sourcePath;
        private final String destPath;
        private final boolean isUnix;
        
        public ProxyInfo(InetAddress sourceAddress, int sourcePort, 
                        InetAddress destAddress, int destPort, int transport) {
            this.sourceAddress = sourceAddress;
            this.sourcePort = sourcePort;
            this.destAddress = destAddress;
            this.destPort = destPort;
            this.transport = transport;
            this.sourcePath = null;
            this.destPath = null;
            this.isUnix = false;
        }
        
        public ProxyInfo(String sourcePath, String destPath) {
            this.sourceAddress = null;
            this.sourcePort = 0;
            this.destAddress = null;
            this.destPort = 0;
            this.transport = 0;
            this.sourcePath = sourcePath;
            this.destPath = destPath;
            this.isUnix = true;
        }
        
        public InetAddress getSourceAddress() {
            return sourceAddress;
        }
        
        public int getSourcePort() {
            return sourcePort;
        }
        
        public InetAddress getDestAddress() {
            return destAddress;
        }
        
        public int getDestPort() {
            return destPort;
        }
        
        public int getTransport() {
            return transport;
        }
        
        public String getSourcePath() {
            return sourcePath;
        }
        
        public String getDestPath() {
            return destPath;
        }
        
        public boolean isUnix() {
            return isUnix;
        }
        
        @Override
        public String toString() {
            if (isUnix) {
                return String.format("UNIX: %s -> %s", sourcePath, destPath);
            } else {
                return String.format("%s:%d -> %s:%d (transport: %d)", 
                    sourceAddress.getHostAddress(), sourcePort,
                    destAddress.getHostAddress(), destPort, transport);
            }
        }
    }
}