package cn.foxkiar.loongarch.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
@AllArgsConstructor
public enum SizeUtil {
    KB(1024),
    MB(KB.size * 1024),
    GB(MB.size * 1024),
    TB(GB.size * 1024);
    private final long size;

    public static String formatSize(long size) {
        String result = size + "B";
        if (size < 1024)
            return result;
        for (SizeUtil util : SizeUtil.values()) {
            if (size >= util.size) {
                result = new DecimalFormat("#.0").format((float) size / util.size) + " " + util.name();
            } else {
                break;
            }
        }
        return result;
    }
}

