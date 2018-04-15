package model;

import dao.ArticleDAO;
import java.sql.Timestamp;

public class Article {
    
    private Integer id;
    private String title;
    private String identifier;
    private User owner;
    private String data;
    private Integer displayIndex;
    private Timestamp createTime;
    private Timestamp modifiedTime;
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setIdentifier(String ident) {
        this.identifier = ident;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public User getOwner() {
        return this.owner;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getData() {
        return this.data;
    }
    
    public void setDisplayIndex(Integer index) {
        this.displayIndex = index;
    }
    
    public Integer getDisplayIndex() {
        return this.displayIndex;
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
    
    public void fetchData() {
        this.data = new ArticleDAO().getDataByArticleId(this.id);
    }
}