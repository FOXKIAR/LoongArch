package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.HostInfo;
import cn.foxkiar.loongarch.entity.HostStatus;
import cn.foxkiar.loongarch.util.Result;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/host")
public class HostController {
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

    @GetMapping("/status")
    public ResponseEntity<Result<HostStatus>> getHostStatus() {
        HostStatus hostStatus = new HostStatus();
        hostStatus.setCpu(getCpu());
        hostStatus.setMemory(getMemory());
        hostStatus.setNetwork(getNetwork());
        hostStatus.setDisk(getDisk());
        return ResponseEntity.ok(Result.success(hostStatus));
    }

    private static HostStatus.Cpu getCpu() {
        HostStatus.Cpu cpu = new HostStatus.Cpu();
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        cpu.setTotal(cpuInfo.getToTal());
        cpu.setFree(cpuInfo.getFree());
        cpu.setUsed(cpuInfo.getUsed());
        return cpu;
    }

    private static HostStatus.Memory getMemory() {
        HostStatus.Memory memory = new HostStatus.Memory();
        GlobalMemory globalMemory = OshiUtil.getMemory();
        long total = globalMemory.getTotal(), free = globalMemory.getAvailable();
        memory.setTotal(total);
        memory.setFree(free);
        memory.setUsed(total - free);
        return memory;
    }

    private static HostStatus.Disk getDisk() {
        HostStatus.Disk disk = new HostStatus.Disk();
        List<HWDiskStore> diskStoreList = OshiUtil.getDiskStores();
        long read = 0, write = 0, total = 0, free = 0;
        for (HWDiskStore diskStore : diskStoreList) {
            read += diskStore.getReadBytes() / diskStore.getReads();
            write += diskStore.getWriteBytes() / diskStore.getWrites();
        }
        File file = new File("/");
        if (file.exists()) {
            total = file.getTotalSpace();
            free = file.getFreeSpace();
        }
        disk.setRead(read);
        disk.setWrite(write);
        disk.setTotal(total);
        disk.setFree(free);
        disk.setUsed(total - free);
        return disk;
    }

    private static HostStatus.Network getNetwork() {
        HostStatus.Network network = new HostStatus.Network();
        List<NetworkIF> networkIFList = OshiUtil.getNetworkIFs();
        long up = 0L, down = 0L, upTemp, downTemp;
        for (NetworkIF networkIF : networkIFList) {
            downTemp = networkIF.getBytesRecv();
            upTemp = networkIF.getBytesSent();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            } finally {
                networkIF.updateAttributes();
            }
            down += (networkIF.getBytesRecv() - downTemp) * 8 / 1024;
            up += (networkIF.getBytesSent() - upTemp) * 8 / 1024;
        }
        network.setUp(up);
        network.setDown(down);
        return network;
    }
}
