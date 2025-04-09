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
        private Long used;
        private Long free;
    }
    @Data
    public static class Disk {
        private Long total;
        private Long used;
        private Long free;

        private Long read;
        private Long write;
    }
    @Data
    public static class Network {
        private Long up;
        private Long down;
    }
    private Cpu cpu;
    private Memory memory;
    private Disk disk;
    private Network network;
}
