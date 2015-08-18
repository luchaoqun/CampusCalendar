package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/9.
 */
public class JobModel {
    @Expose
    private List<Job> results = new ArrayList<>();

    public List<Job> getResults() {
        return results;
    }

    public void setResults(List<Job> results) {
        this.results = results;
    }
}
