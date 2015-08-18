package cn.luchaoqun.campuscalendar.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/9.
 */
public class Job {
    @Expose
    private String icon;
    @Expose
    private String company;
    @Expose
    private String deadline;
    @Expose
    private String objectId;
    @Expose
    private String website;
    @Expose
    private String content;
    @Expose
    private String place;
    @Expose
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Job(String icon, String company, String deadline) {
        this.icon = icon;
        this.company = company;
        this.deadline = deadline;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
