package xx.rabbitmq.chat.entity;

import lombok.Data;

import java.util.Date;


/**
 * (ChatMsg)表实体类
 *
 * @author halo-king
 * @since 2021-10-10 01:09:20
 *
 * { "sender": "u2","receiver": "u1","msg": "hello world","createTime":"2021-10-12 11:12:11"}
 *
 */
@SuppressWarnings("serial")
@Data
public class ChatMsg {
    private Integer id;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 发送时间
     */
    private Date createTime;
}
