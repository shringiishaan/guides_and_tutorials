package model;

import java.sql.Blob;

public class Image {

    private Integer id;
    private String keyword1;
    private String keyword2;
    private Blob data;
    
    public void setId(Integer ident) {
        this.id = ident;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setKeyword1(String kw) {
        this.keyword1 = kw;
    }
    
    public String getKeyword1() {
        return this.keyword1;
    }
    
    public void setKeyword2(String kw) {
        this.keyword2 = kw;
    }
    
    public String getKeyword2() {
        return this.keyword2;
    }
    
    public void setData(Blob data) {
        this.data = data;
    }
    
    public Blob getData() {
        return this.data;
    }
}
