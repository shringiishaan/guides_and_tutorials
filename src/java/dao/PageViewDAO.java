package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageViewDAO {
    
    DataSource dataSource;
    
    public PageViewDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Boolean verifyPageKey(String pageKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM page_views WHERE page_key=?");
            statement.setString(1, pageKey);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM page_views WHERE article_id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void initializePageCountByPageKey(String pageKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO page_views (page_key,count) VALUES (?,0)");
            statement.setString(1, pageKey);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void initializePageCountByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO page_views (article_id,count) VALUES (?,0)");
            statement.setInt(1, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementCountForPageKey(String pageKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE page_views SET count=count+1 WHERE page_key=?");
            statement.setString(1,pageKey);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementCountForArticle(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE page_views SET count=count+1 WHERE article_id=?");
            statement.setInt(1, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Integer getViewsByPageKey(String pageKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT count FROM page_views where page_key=?");
            statement.setString(1, pageKey);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("count");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return null;
    }
    
    public Integer getViewsByArticle(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT count FROM page_views where article_id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("count");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PageViewDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return null;
    }
}