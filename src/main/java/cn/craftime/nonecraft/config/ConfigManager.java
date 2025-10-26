package cn.craftime.nonecraft.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    
    private Map<String, Object> config;
    private Map<String, String> language;
    private final String configPath;
    private final String langPath;
    
    public ConfigManager() {
        this.configPath = "config.yml";
        this.langPath = "lang/";
        loadConfig();
        loadLanguage();
    }
    
    @SuppressWarnings("unchecked")
    private void loadConfig() {
        try {
            File configFile = new File(configPath);
            if (!configFile.exists()) {
                // 创建默认配置文件
                createDefaultConfig();
            }
            
            Yaml yaml = new Yaml();
            try (InputStream inputStream = Files.newInputStream(Paths.get(configPath))) {
                config = yaml.loadAs(inputStream, Map.class);
            }
            
            logger.info("配置文件加载成功");
        } catch (Exception e) {
            logger.error("配置文件加载失败: {}", e.getMessage());
            config = new HashMap<>();
        }
    }
    
    private void createDefaultConfig() throws IOException {
        // 复制默认配置文件
        try (InputStream defaultConfig = getClass().getClassLoader().getResourceAsStream("default-config.yml")) {
            if (defaultConfig != null) {
                Files.copy(defaultConfig, Paths.get(configPath));
            } else {
                // 创建基本配置文件
                String defaultConfigContent =
                    "# Nonecraft配置文件\n" +
                    "lang: zh_cn\n" +
                    "tcp:\n" +
                    "  - 25565\n" +
                    "udp:\n" +
                    "  - 19132\n" +
                    "log:\n" +
                    "  log-ping: true\n" +
                    "  log-connections: true\n" +
                    "  log-geo: true\n" +
                    "ip2stress:\n" +
                    "  enable: false\n" +
                    "proxy-protocol:\n" +
                    "  enable: false\n" +
                    "motd:\n" +
                    "  - \"请不要访问我的服务器哦~\"\n" +
                    "  - \"qwq\"\n" +
                    "refuse:\n" +
                    "  - \" %player% ：\"\n" +
                    "  - \"你想看到什么？你在颠覆什么？你在影射什么？\"\n" +
                    "  - \"想对我的服务器动手？\"\n" +
                    "  - \"没门！\"\n" +
                    "  - \"——我们伟大的腐竹\"\n" +
                    "  - \"——%time%\"\n";
                
                Files.write(Paths.get(configPath), defaultConfigContent.getBytes());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadLanguage() {
        String lang = getString("lang", "zh_cn");
        String langFile = langPath + lang + ".yml";
        
        try {
            Yaml yaml = new Yaml();
            try (InputStream inputStream = Files.newInputStream(Paths.get(langFile))) {
                language = yaml.loadAs(inputStream, Map.class);
            }
            logger.info("语言文件加载成功: {}", lang);
        } catch (Exception e) {
            logger.error("语言文件加载失败: {}", e.getMessage());
            language = new HashMap<>();
        }
    }
    
    // 配置获取方法
    public String getString(String key, String defaultValue) {
        return getNestedValue(key, String.class, defaultValue);
    }
    
    public int getInt(String key, int defaultValue) {
        return getNestedValue(key, Integer.class, defaultValue);
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        return getNestedValue(key, Boolean.class, defaultValue);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        Object value = getNestedValue(key, Object.class, null);
        if (value instanceof List) {
            return (List<String>) value;
        }
        return new ArrayList<>();
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> getIntList(String key) {
        Object value = getNestedValue(key, Object.class, null);
        if (value instanceof List) {
            return (List<Integer>) value;
        }
        return new ArrayList<>();
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getNestedValue(String key, Class<T> type, T defaultValue) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = config;
        
        for (int i = 0; i < keys.length - 1; i++) {
            Object value = current.get(keys[i]);
            if (value instanceof Map) {
                current = (Map<String, Object>) value;
            } else {
                return defaultValue;
            }
        }
        
        Object value = current.get(keys[keys.length - 1]);
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        return defaultValue;
    }
    
    // 语言获取方法
    public String getLang(String key, Object... args) {
        String value = getNestedLangValue(key);
        if (value != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                value = value.replace("{" + i + "}", String.valueOf(args[i]));
            }
        }
        return value != null ? value : key;
    }
    
    @SuppressWarnings("unchecked")
    private String getNestedLangValue(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = (Map<String, Object>) (Map<?, ?>) language;
        
        for (int i = 0; i < keys.length - 1; i++) {
            Object value = current.get(keys[i]);
            if (value instanceof Map) {
                current = (Map<String, Object>) value;
            } else {
                return null;
            }
        }
        
        Object value = current.get(keys[keys.length - 1]);
        return value instanceof String ? (String) value : null;
    }
    
    // 重新加载配置
    public void reload() {
        loadConfig();
        loadLanguage();
    }
}