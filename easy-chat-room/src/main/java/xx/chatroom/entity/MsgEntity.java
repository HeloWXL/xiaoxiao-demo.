package xx.chatroom.entity;

import lombok.Data;

@Data
public class MsgEntity {
    /**
     * 系统消息类型 sys , simpleText
     */
    String sysMsgType;
    /**
     * 消息类型  join leave ，text:
     */
    String msgType;
    /**
     * 消息体
     */
    Object data;

    public MsgEntity() {
    }

    public MsgEntity(String sysMsgType, String msgType, Object data) {
        this.sysMsgType = sysMsgType;
        this.msgType = msgType;
        this.data = data;
    }
}
