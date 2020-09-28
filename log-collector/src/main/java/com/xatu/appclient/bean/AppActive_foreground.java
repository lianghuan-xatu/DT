package com.xatu.appclient.bean;

/**
 * 用户前台活跃
 */
public class AppActive_foreground {
    private String push_id;//推送的消息的id，如果不是从推送消息打开，传空
    private String access;//1.push 2.icon 3.其他

    @Override
    public String toString() {
        return "AppActive_foreground{" +
                "push_id='" + push_id + '\'' +
                ", access='" + access + '\'' +
                '}';
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}