package xx.editor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    /**
     * 根据日期得到年/月/日/的相对路径
     *
     * @param date
     * @return
     */
    public static String getRelativeDir(Date date) {
        String year = format(date, "yyyy");
        String month = format(date, "MM");
        String day = format(date, "dd");
        String dir = year + "/" + month + "/" + day + "/";
        return dir;
    }

    /**
     * 格式化日日期
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static final String format(Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(formatPattern).format(date);
    }


}
