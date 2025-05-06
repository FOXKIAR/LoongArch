package cn.foxkiar.loongarch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec("C:\\Users\\foxkiar\\Develop\\python-3.13.3\\python.exe C:\\Users\\foxkiar\\Develop\\Projects\\test\\test.py");
            
            // 获取命令的输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            System.out.println(line);
            }
            
            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
            } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            }
    }

}
