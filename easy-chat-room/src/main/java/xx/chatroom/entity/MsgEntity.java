package xx.chatroom.entity;

import lombok.Data;

@Data
public class MsgEntity {
    /**
     * 发送人
     */
    String sender;

    /**
     * 房间号
     */
    String roomId;

    /**
     * 系统消息类型 sys , text , roomList
     */
    String sysMsgType;
    /**
     * 消息体
     */
    Object data;

    public MsgEntity() {
    }

    public MsgEntity(String sysMsgType,  Object data) {
        this.sysMsgType = sysMsgType;
        this.data = data;
    }
}
