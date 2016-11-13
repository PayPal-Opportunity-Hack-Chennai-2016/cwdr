package in.devmetric.opportunityhackcwdr.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satyam on 13/11/2016.
 */

public class Source implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
//    @SerializedName("tags")
//    @Expose
//    private List<String> tags = new ArrayList<String>();
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("published")
    @Expose
    private Boolean published;
    @SerializedName("timeCreated")
    @Expose
    private long timeCreated;
    @SerializedName("timeLastUpdated")
    @Expose
    private long timeLastUpdated;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("lastUpdatedBy")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = new ArrayList<Object>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    @Expose
    private String url;
    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The tags
     */
//    public List<String> getTags() {
//        return tags;
//    }

    /**
     *
     * @param tags
     * The tags
     */
//    public void setTags(List<String> tags) {
//        this.tags = tags;
//    }

    /**
     *
     * @return
     * The data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The published
     */
    public Boolean getPublished() {
        return published;
    }

    /**
     *
     * @param published
     * The published
     */
    public void setPublished(Boolean published) {
        this.published = published;
    }

    /**
     *
     * @return
     * The timeCreated
     */
    public long getTimeCreated() {
        return timeCreated;
    }

    /**
     *
     * @param timeCreated
     * The timeCreated
     */
    public void setTimeCreated(Integer timeCreated) {
        this.timeCreated = timeCreated;
    }

    /**
     *
     * @return
     * The timeLastUpdated
     */
    public long getTimeLastUpdated() {
        return timeLastUpdated;
    }

    /**
     *
     * @param timeLastUpdated
     * The timeLastUpdated
     */
    public void setTimeLastUpdated(Integer timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy
     * The lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return
     * The comments
     */
    public List<Object> getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(List<Object> comments) {
        this.comments = comments;
    }

}