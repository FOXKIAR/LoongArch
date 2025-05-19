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
import oshi.hardware.*;

import java.io.File;
import java.util.*;
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
    public static class Cpu {
        private Double used;
        private Double free;

        public Cpu(Double used) {
            this.used = used;
            this.free = 100 - used;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Disk {
        private String name;
        private Long readBytes;
        private Long writeBytes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Memory {
        private Long total;
        private Long used;
        private Long free;

        public Memory(GlobalMemory memory) {
            this.total = memory.getTotal();
            this.free = memory.getAvailable();
            this.used = this.total - this.free;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Network {
        private String name;
        private Long upBytes;
        private Long downBytes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hardware {
        private Cpu cpu;
        private List<Disk> disks;
        private Long diskTotal;
        private Long diskUsed;
        private Long diskFree;
        private Memory memory;
        private List<Network> networks;
    }

    @GetMapping("/hardware")
    public ResponseEntity<Result<Hardware>> getDiskSpace() {
        Hardware hardware = new Hardware();
        hardware.setCpu(new Cpu(OshiUtil.getCpuInfo().getUsed()));
        HardwareAbstractionLayer layer = OshiUtil.getHardware();
        List<Disk> disks = new ArrayList<>();
        for (HWDiskStore diskStore : layer.getDiskStores()) {
            Disk disk = new Disk();
            disk.setName(diskStore.getName());
            disk.setReadBytes(diskStore.getReadBytes());
            disk.setWriteBytes(diskStore.getWriteBytes());
            disks.add(disk);
        }
        hardware.setDisks(disks);

        List<Network> networks = new ArrayList<>();
        for (NetworkIF networkIF : layer.getNetworkIFs()) {
            Network network = new Network();
            network.setName(networkIF.getName());
            network.setUpBytes(networkIF.getBytesSent());
            network.setDownBytes(networkIF.getBytesRecv());
            networks.add(network);
        }
        hardware.setNetworks(networks);

        long total = 0, free = 0;
        for (File root : File.listRoots()) {
            total += root.getTotalSpace();
            free += root.getFreeSpace();
        }
        hardware.setDiskTotal(total);
        hardware.setDiskFree(free);
        hardware.setDiskUsed(total - free);

        hardware.setMemory(new Memory(layer.getMemory()));
        return ResponseEntity.ok(Result.success(hardware));
    }
}
