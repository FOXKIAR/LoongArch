package cn.foxkiar.support.utils;

import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

@Log4j2
public class ConfigUtil {
    private static final File defaultConfigFile = new File("./config.yaml");

    public static Map<String, Object> readConfig() throws IOException {
        if (defaultConfigFile.createNewFile())
            writeDefaultConfigFile();
        Yaml yaml = new Yaml();
        InputStream inputStream = Files.newInputStream(defaultConfigFile.toPath());
        return yaml.load(inputStream);
    }

    private static void writeDefaultConfigFile() throws IOException {
        log.info("未检测到配置文件：{}", defaultConfigFile.toPath());
        String content = "defaultPassword: 123456";
        Files.write(defaultConfigFile.toPath(), content.getBytes());
        log.info("已生成默认配置");
    }
}
