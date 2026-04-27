package cn.foxkiar.support.controller;

import cn.foxkiar.support.entity.Result;
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
        private List<String> gpu;
        private Date startTime;
        private Long uptime;
    }

    @GetMapping("/info")
    public ResponseEntity<Result> getHostInfo() {
        HostInfo hostInfo = new HostInfo();
        hostInfo.setCpu(OshiUtil.getCpuInfo().getCpuModel().split("\n")[0]);
        OsInfo osInfo = SystemUtil.getOsInfo();
        hostInfo.setSystem(osInfo.getName() + " " + osInfo.getArch() + " " + osInfo.getVersion());
        hostInfo.setHostname(SystemUtil.getHostInfo().getName());
        hostInfo.setGpu(OshiUtil.getHardware().getGraphicsCards().stream().
                // 根据 GraphicsCard 对象的 name 成员变量重新生成 List
                map(GraphicsCard::getName).collect(Collectors.toList()));
        long uptimeSeconds = OshiUtil.getOs().getSystemUptime();
        hostInfo.setStartTime(new Date(System.currentTimeMillis() - uptimeSeconds * 1000));
        hostInfo.setUptime(uptimeSeconds);
        return ResponseEntity.ok(Result.success(hostInfo));
    }

    @Data
    public static class Cpu {
        private Integer total;
        private Double used;
        private Double free;

        public Cpu(CpuInfo cpuInfo) {
            this.total = cpuInfo.getCpuNum();
            this.used = cpuInfo.getUsed();
            this.free = cpuInfo.getFree();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiskSpace {
        private Long total;
        private Long used;
        private Long free;
    }

    @Data
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
    public static class Load {
        private Cpu cpu;
        private DiskSpace diskSpace;
        private Memory memory;
    }

    @GetMapping("/load")
    public ResponseEntity<Result> getLoadUsage() {
        Load load = new Load();
        load.setCpu(new Cpu(OshiUtil.getCpuInfo()));
        long diskTotal = 0, diskFree = 0;
        for (File root : File.listRoots()) {
            diskTotal += root.getTotalSpace();
            diskFree += root.getFreeSpace();
        }
        load.setDiskSpace(new DiskSpace(diskTotal, diskTotal - diskFree, diskFree));
        load.setMemory(new Memory(OshiUtil.getHardware().getMemory()));
        return ResponseEntity.ok(Result.success(load));
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
    public static class Network {
        private String name;
        private Long upBytes;
        private Long downBytes;
    }

    @Data

    public static class Hardware {
        private List<Disk> disks;
        private List<Network> networks;
    }

    @GetMapping("/hardware")
    public ResponseEntity<Result> getHardwareUsage() {
        Hardware hardware = new Hardware();
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
        return ResponseEntity.ok(Result.success(hardware));
    }
}
