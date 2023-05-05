package xx.upload.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    /**
     * 响应前端是否成功标识
     */
    private boolean flag;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    public Result() {
    }

    public Result(boolean flag, String msg, Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public Result(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    /**
     * 响应成功
     */
    public static Result success(String msg, Object data) {
        return new Result(true,msg,data);
    }


    public static Result success(Object data) {
        return new Result(true,"success",data);
    }

    /**
     * 响应成功
     */
    public static Result success(String msg) {
        return new Result(true,msg);
    }

    /**
     * 响应失败
     */
    public static Result fail(String msg) {
        return new Result(false,msg,null);
    }
}
