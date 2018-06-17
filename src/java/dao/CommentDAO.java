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
                comment.setOwnerKey(resultSet.getString("owner_key"));
                comment.setStatus(resultSet.getString("status"));
                comment.setMessage(resultSet.getString("message"));
                comment.setArticleId(resultSet.getInt("article_id"));
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
    
    public List<Comment> getAllComments() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Comment> comments = null;
        Comment comment;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM comments ORDER BY create_time DESC;");
            resultSet = statement.executeQuery();
            comments = new ArrayList<>();
            while(resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setOwnerKey(resultSet.getString("owner_key"));
                comment.setStatus(resultSet.getString("status"));
                comment.setMessage(resultSet.getString("message"));
                comment.setArticleId(resultSet.getInt("article_id"));
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
    
    public List<Comment> getCommentsByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Comment> comments = null;
        Comment comment;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM comments where article_id=? ORDER BY priority DESC;");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            comments = new ArrayList<>();
            while(resultSet.next()) {
                comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setOwnerKey(resultSet.getString("owner_key"));
                comment.setStatus(resultSet.getString("status"));
                comment.setMessage(resultSet.getString("message"));
                comment.setArticleId(resultSet.getInt("article_id"));
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
    
    public void createNewComment(String ownerKey, String status, String message, Integer articleId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO comments (owner_key,status,message,article_id,priority) VALUES (?,?,?,?,?)");
            statement.setString(1, ownerKey);
            statement.setString(2, status);
            statement.setString(3, message);
            statement.setInt(4, articleId);
            statement.setInt(5, priority);
            
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
    
    public Integer getMaximumCommentPriorityByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(priority) FROM comments where article_id=?");
            statement.setInt(1, articleId);
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
    
    public void updateStatus(Integer commentId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE comments SET status=? WHERE id=?");
            statement.setString(1,status);
            statement.setInt(2,commentId);
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
}