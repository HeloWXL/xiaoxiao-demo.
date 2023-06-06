package xx.call.dto;

import lombok.Data;

@Data
public class SignalEntity {
    private String type;

    private String msg;

    private int status;

    private Object data;

    public SignalEntity() {
    }

    public SignalEntity(String type, String msg, int status, Object data) {
        this.type = type;
        this.msg = msg;
        this.status = status;
        this.data = data;
    }
}
