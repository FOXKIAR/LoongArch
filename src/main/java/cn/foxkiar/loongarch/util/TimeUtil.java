package cn.foxkiar.loongarch.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum TimeUtil {
    SECOND(1000, "秒"), // 以毫秒为基数
    MINUTE(SECOND.millis * 60, "分钟"),
    HOUR(MINUTE.millis * 60, "小时"),
    DAY(HOUR.millis * 24, "天"),
    MONTH(DAY.millis * 30, "个月"),
    YEAR(MONTH.millis * 12, "年");
    private final long millis;
    private final String type;

    public static String formatTimestamp(long timestamp) {
        if (timestamp < 1000) {
            throw new IllegalArgumentException("时间戳不能小于一秒");
        }
        Map<TimeUtil, Long> timeMap = new HashMap<>();
        List<TimeUtil> list = Arrays.asList(TimeUtil.values());
        Collections.reverse(list);
        for (TimeUtil unit : list) {
            if (timestamp >= unit.millis) {
                timeMap.put(unit, timestamp / unit.millis);
                timestamp %= unit.millis; // 取余，继续计算下一个单位
            }
        }
        StringBuilder result = new StringBuilder();
        for (TimeUtil unit : list) {
            if (timeMap.containsKey(unit)) {
                result.append(timeMap.get(unit)).append(unit.getType().toLowerCase()).append(" ");
            }
        }
        return result.toString().trim();
    }
}
