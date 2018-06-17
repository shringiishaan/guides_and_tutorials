package model;

public class Tutorial {
    
    private Integer id;
    private String key;
    private String title;
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
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
