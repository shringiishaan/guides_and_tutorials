package model;

public class Topic {
    
    private Integer id;
    private String key;
    private String title;
    private Integer priority;
    private String status;
    
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
    
    public String getTitle() {
        return this.title;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
}
