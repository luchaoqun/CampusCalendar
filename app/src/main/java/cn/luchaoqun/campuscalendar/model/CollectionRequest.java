package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/17.
 */
public class CollectionRequest {
    public CollectionRelation get$relatedTo() {
        return $relatedTo;
    }

    public void set$relatedTo(CollectionRelation $relatedTo) {
        this.$relatedTo = $relatedTo;
    }

    @Expose
    private CollectionRelation $relatedTo;
}
