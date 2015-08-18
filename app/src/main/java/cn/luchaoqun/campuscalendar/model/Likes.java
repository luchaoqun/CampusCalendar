package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/18.
 */
public class Likes {
    @Expose
    private String __op = "AddUnique";
    @Expose
    private List<String> objects = new ArrayList();

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }
}
