package tmall.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * DateUtil这个日期工具类主要是用于java.util.Date类与java.sql.Timestamp 类的互相转换
 */
public class DateUtil {

    public static Timestamp date2time(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Date time2date(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

}
