package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Feedback {
    @Expose
    private String content;
    @Expose
    private String connect;

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
