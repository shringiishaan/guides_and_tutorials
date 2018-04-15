package model;

public class Tutorial {
    
    private Integer id;
    private String title;
    private String identifier;
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setIdentifier(String ident) {
        this.identifier = ident;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
}
