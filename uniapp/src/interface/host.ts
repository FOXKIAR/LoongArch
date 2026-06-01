export class HostInfo {
    hostname: string;
    system: string;
    cpu: string;
    gpu: string;
    startTime: string;
    uptime: number;
}

export interface Cpu {
    used: number; // 使用率
    free: number; // 空闲率
}

export interface Disk {
    name: string; // 磁盘名称
    readBytes: number;
    writeBytes: number;
}

export interface Memory {
    total: number; // 总内存
    used: number; // 使用量
    free: number; // 空闲量
}

export interface Network {
    name: string; // 网卡名称
    upBytes: number;
    downBytes: number;
}

export interface Hardware {
    cpu: Cpu;
    disks: Disk[];
    diskTotal: number;
    diskUsed: number;
    diskFree: number;
    memory: Memory;
    networks: Network[];
}

export class AreaCharts {
    categories: string[]; // X轴显示的名称
    series: {
        name: string, // 图例名称
        data: string[] | number[] // 数据列表，长度应和 categories 一致
    }[];

    constructor() {
        this.categories = [];
        this.series = [
            {name: "", data: []},
            {name: "", data: []}
        ];
    }
}

export class GaugeCharts {
    categories: {value: number, color: string}[];
    series: {name: string, data: number}[];

    constructor() {
        this.categories = [
            {"value":0.2,"color":"#2fc25b"},
            {"value":0.8,"color":"#1890ff"},
            {"value":1,"color":"#f04864"}
        ];
        this.series = [
            {
                name: "使用率",
                data: 0.10
            }
        ];
    }
}

export class PieCharts {
    series: {data: {name: string, value: number}[]}[];

    constructor() {
        this.series = [{
            data: [
                {name: "使用量", value: 0},
                {name: "空闲量", value: 0}
            ]
        }]
    }
}