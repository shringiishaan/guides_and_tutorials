package model;

import java.sql.Timestamp;

public class Article {
    
    private Integer id;
    private String key;
    private String title;
    private Integer owner_id;
    private String status;
    private String scope;
    private String data;
    private String shortDescription;
    private Timestamp createTime;
    private Timestamp modifiedTime;
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKey () {
        return this.key;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setOwnerId(Integer owner_id) {
        this.owner_id = owner_id;
    }
    
    public Integer getOwnerId() {
        return this.owner_id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus () {
        return this.status;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public String getScope () {
        return this.scope;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getData() {
        return this.data;
    }
    
    public void setShortDescription(String desc) {
        this.shortDescription = desc;
    }
    
    public String getShortDescription() {
        return this.shortDescription;
    }
    
    public void setCreateTime(Timestamp time) {
        this.createTime = time;
    }
    
    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setModifiedTime(Timestamp time) {
        this.modifiedTime = time;
    }
    
    public Timestamp getModifiedTime() {
        return this.modifiedTime;
    }
}