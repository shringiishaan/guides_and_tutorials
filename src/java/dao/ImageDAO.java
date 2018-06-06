package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Image;

public class ImageDAO {
    
    DataSource dataSource;
    
    public ImageDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Image getImageById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Image image = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM images WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setData(resultSet.getBlob("data"));
                image.setKeyword1(resultSet.getString("keyword1"));
                image.setKeyword2(resultSet.getString("keyword2"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return image;
    }
    
    public Image getImageByKeywords(String keyword1, String keyword2) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Image image = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM images WHERE keyword1=? and keyword2=?;");
            statement.setString(1, keyword1);
            statement.setString(2, keyword2);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setData(resultSet.getBlob("data"));
                image.setKeyword1(resultSet.getString("keyword1"));
                image.setKeyword2(resultSet.getString("keyword2"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return image;
    }
    
    public List<Image> getAllImages() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Image> images = null;
        Image image;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM images;");
            resultSet = statement.executeQuery();
            images = new ArrayList<>();
            while(resultSet.next()) {
                image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setKeyword1(resultSet.getString("keyword1"));
                image.setKeyword2(resultSet.getString("keyword2"));
                image.setData(resultSet.getBlob("data"));
                images.add(image);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return images;
    }
    
    public List<Image> getImagesByKeyword1(String keyword1) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Image> images = null;
        Image image;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM images where keyword1=?;");
            statement.setString(1, keyword1);
            resultSet = statement.executeQuery();
            images = new ArrayList<>();
            while(resultSet.next()) {
                image = new Image();
                image.setId(resultSet.getInt("id"));
                image.setKeyword1(resultSet.getString("keyword1"));
                image.setKeyword2(resultSet.getString("keyword2"));
                image.setData(resultSet.getBlob("data"));
                images.add(image);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return images;
    }
    
    public List<String> getAllDistinctKeyword1() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> keywords = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT distinct keyword1 from images;");
            resultSet = statement.executeQuery();
            keywords = new ArrayList<>();
            while(resultSet.next()) {
                keywords.add(resultSet.getString("keyword1"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return keywords;
    }
    
    public void createNewImage(String keyword1, String keyword2, InputStream image, Integer imageLength) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO images (keyword1,keyword2,data) VALUES (?,?,?)");
            statement.setString(1,keyword1);
            statement.setString(2,keyword2);
            statement.setBinaryStream(3, image, imageLength);
            statement.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
            } catch (SQLException e) {
            }
        }
    }
    
    public void deleteByImageId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM images WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
            } catch (SQLException e) {
            }
        }
    }
}