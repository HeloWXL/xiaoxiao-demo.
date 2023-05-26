package xx.notice.common;

import lombok.Data;

public enum NoticeEnum {

    PERSON(1,"个人"),
    ALL(2,"所有人"),
    REGULAR(3,"定时");

    Integer noticeType;

    String notice;

    NoticeEnum(Integer noticeType, String notice) {
        this.noticeType = noticeType;
        this.notice = notice;
    }
}
