package model;

import java.sql.Timestamp;

public class Comment {
    
    private Integer id;
    private String owner_key;
    private String status;
    private String message;
    private Integer article_id;
    private Integer priority;
    private Timestamp createTime;
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setOwnerKey(String owner_key) {
        this.owner_key = owner_key;
    }
    
    public String getOwnerKey() {
        return this.owner_key;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage () {
        return this.message;
    }
    
    public void setArticleId(Integer article_id) {
        this.article_id = article_id;
    }
    
    public Integer getArticleId() {
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