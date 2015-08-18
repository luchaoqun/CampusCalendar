package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/10.
 */
public class User {
    @Expose
    private String username;
    @Expose
    private String password;

    @Expose
    private CollectionObject collections;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CollectionObject getCollections() {
        return collections;
    }

    public void setCollections(CollectionObject collections) {
        this.collections = collections;
    }

}
