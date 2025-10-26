package cn.craftime.nonecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MinecraftProtocol {
    
    // 包ID常量
    public static final int HANDSHAKE_PACKET = 0x00;
    public static final int STATUS_REQUEST_PACKET = 0x00;
    public static final int STATUS_RESPONSE_PACKET = 0x00;
    public static final int PING_REQUEST_PACKET = 0x01;
    public static final int PING_RESPONSE_PACKET = 0x01;
    public static final int LOGIN_START_PACKET = 0x00;
    public static final int DISCONNECT_PACKET = 0x00;
    
    // 读取VarInt
    public static int readVarInt(DataInputStream input) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;
        
        while (true) {
            currentByte = input.readByte();
            value |= (currentByte & 0x7F) << position;
            
            if ((currentByte & 0x80) == 0) break;
            
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt太大");
        }
        
        return value;
    }
    
    // 写入VarInt
    public static void writeVarInt(DataOutputStream output, int value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                output.writeByte(value);
                return;
            }
            
            output.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }
    
    // 读取字符串
    public static String readString(DataInputStream input) throws IOException {
        int length = readVarInt(input);
        byte[] stringBytes = new byte[length];
        input.readFully(stringBytes);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }
    
    // 写入字符串
    public static void writeString(DataOutputStream output, String value) throws IOException {
        byte[] stringBytes = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(output, stringBytes.length);
        output.write(stringBytes);
    }
    
    // 发送包
    public static void sendPacket(DataOutputStream output, int packetId, byte[] data) throws IOException {
        ByteArrayOutputStream packetBuffer = new ByteArrayOutputStream();
        DataOutputStream packetOutput = new DataOutputStream(packetBuffer);
        
        writeVarInt(packetOutput, packetId);
        packetOutput.write(data);
        
        byte[] packetData = packetBuffer.toByteArray();
        
        ByteArrayOutputStream lengthBuffer = new ByteArrayOutputStream();
        DataOutputStream lengthOutput = new DataOutputStream(lengthBuffer);
        writeVarInt(lengthOutput, packetData.length);
        
        output.write(lengthBuffer.toByteArray());
        output.write(packetData);
        output.flush();
    }
    
    // 构建状态响应JSON
    public static String buildStatusResponse(String motd1, String motd2) {
        return String.format(
            "{\"version\":{\"name\":\"1.20.1\",\"protocol\":763},\"players\":{\"max\":0,\"online\":0,\"sample\":[]},\"description\":{\"text\":\"%s\\n%s\"},\"favicon\":\"data:image/png;base64,\"}",
            escapeJson(motd1), escapeJson(motd2)
        );
    }
    
    // 构建断开连接消息JSON
    public static String buildDisconnectMessage(String message) {
        return String.format("{\"text\":\"%s\"}", escapeJson(message));
    }
    
    // 构建多行断开连接消息
    public static String buildMultiLineDisconnectMessage(String[] lines) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                message.append("\\n");
            }
            message.append(escapeJson(lines[i]));
        }
        return String.format("{\"text\":\"%s\"}", message.toString());
    }
    
    // JSON转义
    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f");
    }
}