export const auto = -1;

export const timeUnit = (() => {
    const second = 1000,
        minute = 60 * second,
        hour = 60 * minute,
        day = 24 * hour,
        month = 30 * day,
        year = 12 * month;

    return {year, month, day, hour, minute, second};
})();

export function formatTime(time: number, unit: number = -1): string {
    let result = "";
    for (let current in timeUnit) {
        if (time > timeUnit[current]) {
            result += Math.floor(time / timeUnit[current]) + ((unit) => {
                switch (unit) {
                    case "second": return "秒";
                    case "minute": return "分钟";
                    case "hour": return "个小时";
                    case "day": return "天";
                    case "month": return "个月";
                    case "year": return "年";
                }
            })(current);
            time %= timeUnit[current];
        }
        if (timeUnit[current] == unit)
            return result;
    }
    return result;
}