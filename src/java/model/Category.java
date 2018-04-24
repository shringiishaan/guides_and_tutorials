package model;

public class Category {
    
    private Integer id;
    private String name;
    private String identifier;
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setIdentifier(String ident) {
        this.identifier = ident;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
}
