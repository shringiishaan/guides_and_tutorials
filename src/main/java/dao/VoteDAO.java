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

public class VoteDAO {
    
    DataSource dataSource;
    
    public VoteDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(VoteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Boolean verifyArticleVoteType(Integer articleId, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM votes WHERE article_id=? and type=?");
            statement.setInt(1, articleId);
            statement.setString(2, type);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(VoteDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void newArticleVoteType(Integer articleId, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO votes (article_id,type,count) VALUES (?,?,0)");
            statement.setInt(1, articleId);
            statement.setString(2, type);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VoteDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementVoteForArticle(Integer articleId, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE votes SET count=count+1 WHERE article_id=? and type=?");
            statement.setInt(1, articleId);
            statement.setString(2, type);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VoteDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Integer getVoteCountByArticleAndType(Integer articleId, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT count FROM votes where article_id=? and type=?");
            statement.setInt(1, articleId);
            statement.setString(2, type);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("count");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(VoteDAO.class.getName()).log(Level.SEVERE, null, ex);
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