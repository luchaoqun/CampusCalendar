package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/17.
 */
public class Collection {
    @Expose
    private String __type = "Pointer";
    @Expose
    private String className = "_User";
    @Expose
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
