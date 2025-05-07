package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.util.Result;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/host")
public class HostController {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostInfo {
        private String hostname;
        private String system;
        private String cpu;
        private String gpu;
        private Date startTime;
        private Long uptime;
    }
    @GetMapping("/info")
    public ResponseEntity<Result<HostInfo>> getHostInfo() {
        HostInfo hostInfo = new HostInfo();
        // 获取 cpuModel 中的第一行
        hostInfo.setCpu(OshiUtil.getCpuInfo().getCpuModel().split("\n")[0]);
        OsInfo osInfo = SystemUtil.getOsInfo();
        hostInfo.setSystem(osInfo.getName() + " " + osInfo.getArch() + " " + osInfo.getVersion());
        hostInfo.setHostname(SystemUtil.getHostInfo().getName());
        hostInfo.setGpu(OshiUtil.getHardware().getGraphicsCards().stream().
                // 根据 GraphicsCard 对象的 name 成员变量重新生成 List
                map(GraphicsCard::getName).collect(Collectors.toList()).toString());
        long uptimeSeconds = OshiUtil.getOs().getSystemUptime();
        hostInfo.setStartTime(new Date(System.currentTimeMillis() - uptimeSeconds * 1000));
        hostInfo.setUptime(uptimeSeconds);
        return ResponseEntity.ok(Result.success(hostInfo));
    }

    @GetMapping("/disk/info")
    public ResponseEntity<Result<List<HWDiskStore>>> getDiskSpace() {
        return ResponseEntity.ok(Result.success(OshiUtil.getDiskStores()));
    }

    @GetMapping("/disk/space")
    public ResponseEntity<Result<Map<String, Long>>> getDisk() {
        Map<String, Long> data = new HashMap<>();
        long total = 0, free = 0;
        for (File root : File.listRoots()) {
            total += root.getTotalSpace();
            free += root.getFreeSpace();
        }
        data.put("total", total);
        data.put("free", free);
        data.put("used", total - free);
        return ResponseEntity.ok(Result.success(data));
    }

    @GetMapping("/memory/info")
    public ResponseEntity<Result<GlobalMemory>> getMemory() {
        return ResponseEntity.ok(Result.success(OshiUtil.getMemory()));
    }

    @GetMapping("/network/info")
    public ResponseEntity<Result<List<NetworkIF>>> getNetwork() {
        return ResponseEntity.ok(Result.success(OshiUtil.getNetworkIFs()));
    }
}
