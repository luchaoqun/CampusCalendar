package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/18.
 */
public class Like {
    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    @Expose
    private Likes likes;
}
