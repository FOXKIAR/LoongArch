package cn.foxkiar.support.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Component
public class FileInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        Path configFile = Paths.get("config", "admin.yaml");
        if (!Files.exists(configFile)) {
            log.info("未读取到配置文件，将创建默认配置：{}", configFile.toAbsolutePath());
            Files.createDirectories(configFile.getParent());
            String content = "account: admin\npassword: admin";
            Files.write(configFile, content.getBytes());
            log.info("Created is OK");
        }
    }
}