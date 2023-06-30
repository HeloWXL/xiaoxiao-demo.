package xx.transform.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class OpenccUtil {

    public static void main(String[] args) {
        isChinese();
    }


    /**
     * 转为简体 toSimple
     */
    public static void toSimple(){
        String original = "聲明：我是一個java程序員";
        String result = ZhConverterUtil.toSimple(original);
        System.out.println(result);
        // 声明：我是一个java程序员
    }


    /**
     * 转为繁体 toTraditional
     */
    public static void toTraditional(){
        String original = "声明：我是一个java程序员";
        String result = ZhConverterUtil.toTraditional(original);
        System.out.println(result);
        // 聲明：我是一個java程序員
    }

    /**
     * 是否为简体 isSimple
     */
    public static void checkSimple(){
        System.out.println(ZhConverterUtil.isSimple("員"));
        System.out.println(ZhConverterUtil.isSimple("员"));
        System.out.println(ZhConverterUtil.isSimple("程序員"));
        //  false , true ,false
    }

    /**
     * 是否包含简体 containsSimple
     */
    public static void isContainSimple(){
        System.out.println(ZhConverterUtil.containsSimple("員"));
        System.out.println(ZhConverterUtil.containsSimple("员"));
        System.out.println(ZhConverterUtil.containsSimple("程序員"));
        //
    }

    /**
     * 是否为繁体 isTraditional
     */
    public static void checkTraditional(){
        System.out.println(ZhConverterUtil.isTraditional("員"));
        System.out.println(ZhConverterUtil.isTraditional("员"));
        System.out.println(ZhConverterUtil.isTraditional("程序員"));
        //
    }

    /**
     * 是否包含繁体 containsTraditional
     */
    public static void isContainTraditional(){
        System.out.println(ZhConverterUtil.containsTraditional("員"));
        System.out.println(ZhConverterUtil.containsTraditional("员"));
        System.out.println(ZhConverterUtil.containsTraditional("程序員"));
        //
    }


    /**
     * 返回包含的简体列表
     */
    public static void getSimpleList(){
        System.out.println(ZhConverterUtil.simpleList("員"));
        System.out.println(ZhConverterUtil.simpleList("员"));
        System.out.println(ZhConverterUtil.simpleList("程序員"));
        //
    }


    /**
     * 是否包含繁体 containsTraditional
     */
    public static void getTraditionalList(){
        System.out.println(ZhConverterUtil.traditionalList("員"));
        System.out.println(ZhConverterUtil.traditionalList("员"));
        System.out.println(ZhConverterUtil.traditionalList("程序員"));
        //
    }


    /**
     * 单个字符是否为中文 \  是否全部为中文
     */
    public static void isChinese(){
        System.out.println(ZhConverterUtil.isChinese("員"));
        System.out.println(ZhConverterUtil.isChinese("员"));
        System.out.println(ZhConverterUtil.isChinese("程序員"));
        System.out.println(ZhConverterUtil.isChinese(""));
        // true ,true ,true,false
    }

    /**
     * 字符串中是否包含中文
     */
    public static void containsChinese(){
        System.out.println(ZhConverterUtil.containsChinese("聲明：我是一個java程序員"));
        System.out.println(ZhConverterUtil.containsChinese("abc"));
        System.out.println(ZhConverterUtil.containsChinese("123"));
        System.out.println(ZhConverterUtil.containsChinese(""));
        System.out.println(ZhConverterUtil.containsChinese(null));
        // true, false,false,false,false
    }

}
