package xx.location.util;

import org.lionsoul.ip2region.xdb.Searcher;

import java.util.Objects;

public class AddressUtil {
    /**
     * 根据IP地址查询登录来源
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        try {
            // 获取当前记录地址位置的文件
            String dbPath = Objects.requireNonNull(AddressUtil.class.getResource("/data/ip2region.xdb")).getPath();
            //创建查询对象
            Searcher searcher = Searcher.newWithFileOnly(dbPath);
            //开始查询
            return searcher.searchByStr(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认返回空字符串
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getCityInfo("68.174.252.207"));
    }
}
