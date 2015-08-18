package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/17.
 */
public class CollectionRelation {
    public Collection getObject() {
        return object;
    }

    public void setObject(Collection object) {
        this.object = object;
    }

    @Expose
    private Collection object;
    private String key = "collections";
}
