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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackDAO {
    
    DataSource dataSource;
    
    public FeedbackDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void newFeedback(String ownerKey, String status, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO messages (status,owner_key,data) VALUES (?,?,?)");
            statement.setString(1, status);
            statement.setString(2, ownerKey);
            statement.setString(3, data);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateFeedbackStatus(Integer feedbackId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE messages SET status=? WHERE id=?");
            statement.setString(1,status);
            statement.setInt(2,feedbackId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deleteFeedback(Integer feedbackId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE from messages WHERE id=?");
            statement.setInt(1,feedbackId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<HashMap<String,String>> getAllFeedbacks() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<HashMap<String,String>> feedbacks = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM `messages` ORDER BY `create_time` DESC;");
            resultSet = statement.executeQuery();
            feedbacks = new ArrayList<>();
            HashMap<String,String> tempFeedback = null;
            while(resultSet.next()) {
                tempFeedback = new HashMap<>();
                tempFeedback.put("data",resultSet.getString("data"));
                tempFeedback.put("ownerKey",resultSet.getString("owner_key"));
                tempFeedback.put("status",resultSet.getString("status"));
                tempFeedback.put("createTime",resultSet.getTimestamp("create_time").toString());
                tempFeedback.put("id",Integer.toString(resultSet.getInt("id")));
                
                feedbacks.add(tempFeedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return feedbacks;
    }
    
    public List<HashMap<String,String>> getFeedbacksByStatus(String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<HashMap<String,String>> feedbacks = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM `messages` WHERE `status`=? ORDER BY `create_time` DESC;");
            statement.setString(1, status);
            resultSet = statement.executeQuery();
            feedbacks = new ArrayList<>();
            HashMap<String,String> tempFeedback = null;
            while(resultSet.next()) {
                tempFeedback = new HashMap<>();
                tempFeedback.put("data",resultSet.getString("data"));
                tempFeedback.put("ownerKey",resultSet.getString("owner_key"));
                tempFeedback.put("status",resultSet.getString("status"));
                tempFeedback.put("createTime",resultSet.getTimestamp("create_time").toString());
                tempFeedback.put("id",Integer.toString(resultSet.getInt("id")));
                
                feedbacks.add(tempFeedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return feedbacks;
    }
}