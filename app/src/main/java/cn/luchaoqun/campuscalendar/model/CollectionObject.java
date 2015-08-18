package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/17.
 */
public class CollectionObject {
    @Expose
    private String __op = "AddRelation";
    @Expose
    private List<Collection> objects = new ArrayList<>();

    public List<Collection> getCollections() {
        return objects;
    }

    public void setCollections(List<Collection> collections) {
        this.objects = collections;
    }
}
