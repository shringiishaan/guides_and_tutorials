package model;

import java.sql.Timestamp;

public class Comment {
    
    private Integer id;
    private Integer owner_id;
    private String message;
    private String article_id;
    private Integer priority;
    private Timestamp createTime;
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setOwnerId(Integer owner_id) {
        this.owner_id = owner_id;
    }
    
    public Integer getOwnerId() {
        return this.owner_id;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage () {
        return this.message;
    }
    
    public void setArticleId(String article_id) {
        this.article_id = article_id;
    }
    
    public String getArticleId() {
        return this.article_id;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setCreateTime(Timestamp time) {
        this.createTime = time;
    }
    
    public Timestamp getCreateTime() {
        return this.createTime;
    }
}