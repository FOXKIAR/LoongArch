export class HostInfo {
    hostname: string;
    system: string;
    cpu: string;
    gpu: string;
    startTime: string;
    uptime: number;
}

export class AreaCharts {
    categories: string[]; // X轴显示的名称
    series: {
        name: string, // 图例名称
        data: string[] | number[] // 数据列表，长度应和 categories 一致
    }[];

    constructor() {
        this.categories = [];
        this.series = [];
    }
}