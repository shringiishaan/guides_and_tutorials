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
import model.Comment;

public class CommentDAO {
    
    DataSource dataSource;
    
    public CommentDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Comment getCommentById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Comment comment = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM comments where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setOwnerId(resultSet.getInt("owner_id"));
                comment.setMessage(resultSet.getString("message"));
                comment.setArticleId(resultSet.getString("article_id"));
                comment.setPriority(resultSet.getInt("priority"));
                comment.setCreateTime(resultSet.getTimestamp("create_time"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return comment;
    }
    
    public List<Comment> getCommentsByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Comment> comments = null;
        Comment comment;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM comments where article_id=? ORDER BY priority DESC;");
            statement.setString(1, articleId);
            resultSet = statement.executeQuery();
            comments = new ArrayList<>();
            while(resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setOwnerId(resultSet.getInt("owner_id"));
                comment.setMessage(resultSet.getString("message"));
                comment.setArticleId(resultSet.getString("article_id"));
                comment.setPriority(resultSet.getInt("priority"));
                comment.setCreateTime(resultSet.getTimestamp("create_time"));
                comments.add(comment);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return comments;
    }
    
    public void createNewComment(Integer ownerId, String message, String articleId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO comments (message,article_id,priority"
                    + ownerId==null?"":",owner_id"
                    + ") VALUES (?,?,?"
                    + ownerId==null?"":",?"
                    + ")");
            statement.setString(1, message);
            statement.setString(2, articleId);
            statement.setInt(3, priority);
            if(ownerId!=null)
                statement.setInt(4, ownerId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deleteByCommentId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM comments WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Integer getMaximumCommentPriorityByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(priority) FROM comments where article_id=?");
            statement.setString(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("max(priority)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, ex);
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