package cn.foxkiar.loongarch.entity;

import lombok.Data;

@Data
public class HostStatus {
    @Data
    public static class Cpu {
        private Double total;
        private Double used;
        private Double free;
    }
    @Data
    public static class Memory {
        private Long total;
        private Double used;
        private Double free;
    }
    @Data
    class Disk {
        private Double total;
        private Double used;
        private Double free;

        private Double read;
        private Double write;
    }
    @Data
    class Network {
        private Double up;
        private Double down;
    }
    private Cpu cpu;
    private Memory memory;
    private Disk disk;
    private Network network;
}
