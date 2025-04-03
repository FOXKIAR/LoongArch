package cn.foxkiar.loongarch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostInfo {
    private String hostname;
    private String system;
    private String cpu;
    private String gpu;
    private Date startTime;
    private Long uptime;
}
