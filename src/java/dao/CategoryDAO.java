package dao;

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
import model.Category;

public class CategoryDAO {
    
    DataSource dataSource;
    
    public CategoryDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Category getCategoryById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name,identifier FROM categories where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return category;
    }
    
    public Category getCategoryByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name,identifier FROM categories where name=?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return category;
    }
    
    public Category getCategoryByIdentifier(String identifier) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = null;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name,identifier FROM categories where identifier=?");
            statement.setString(2, identifier);
            resultSet = statement.executeQuery();
            userdao = new UserDAO();
            if(resultSet.first()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return category;
    }
    
    public List<Category> getAllCategories() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> categories = null;
        Category category;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name,identifier FROM categories");
            resultSet = statement.executeQuery();
            categories = new ArrayList<>();
            while(resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setIdentifier(resultSet.getString("identifier"));
                categories.add(category);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return categories;
    }
    
    public List<Category> getCategoriesByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> categories = null;
        Category category;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name,identifier FROM categories");
            //statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            categories = new ArrayList<>();
            while(resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setIdentifier(resultSet.getString("identifier"));
                categories.add(category);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return categories;
    }
    
    public void createNewCategory(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String ident = name.toLowerCase().replace(" ", "-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO categories (name,identifier) VALUES (?,?)");
            statement.setString(1, name);
            statement.setString(2, ident);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyCategoryName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM categories WHERE name=?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }
    
    public Boolean verifyCategoryIdentifier(String identifier) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM categories WHERE identifier=?");
            statement.setString(1, identifier);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }
}
