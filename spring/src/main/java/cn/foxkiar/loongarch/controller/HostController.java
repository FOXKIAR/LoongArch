package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.util.Result;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.*;

import java.util.Date;
import java.util.List;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hardware {
        private CpuInfo cpuInfo;
        private List<GraphicsCard> graphicsCards;
        private List<HWDiskStore> diskStores;
        private GlobalMemory memory;
        private List<NetworkIF> networkIFs;
    }

    @GetMapping("/hardware")
    public ResponseEntity<Result<Hardware>> getDiskSpace() {
        Hardware hardware = new Hardware();
        hardware.setCpuInfo(OshiUtil.getCpuInfo());
        HardwareAbstractionLayer layer = OshiUtil.getHardware();
        hardware.setGraphicsCards(layer.getGraphicsCards());
        hardware.setDiskStores(layer.getDiskStores());
        hardware.setMemory(layer.getMemory());
        hardware.setNetworkIFs(layer.getNetworkIFs());
        return ResponseEntity.ok(Result.success(hardware));
    }
}
