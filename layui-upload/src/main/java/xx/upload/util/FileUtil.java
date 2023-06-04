package xx.upload.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileUtil {
    /**
     * 日期格式胡
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 生成相对路径
     * @param date
     * @return
     */
    public static String getRelativeDir(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 文件路径 upload/groupID/年月日
        String monthStr = month < 10 ? "0" + month : "" + month;
        String dayStr = day < 10 ? "0" + day : "" + day;
        String filePath = year + monthStr + dayStr;
        return filePath;
    }
}
