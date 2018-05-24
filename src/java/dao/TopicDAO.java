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
import model.Topic;

public class TopicDAO {
    
    DataSource dataSource;
    
    public TopicDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Topic getTopicById(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Topic topic = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title FROM topics where id=?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                topic = new Topic();
                topic.setId(resultSet.getString("id"));
                topic.setTitle(resultSet.getString("title"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topic;
    }
    
    public Topic getTopicByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Topic topic = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title FROM topics where title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                topic = new Topic();
                topic.setId(resultSet.getString("id"));
                topic.setTitle(resultSet.getString("title"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topic;
    }
    
    public String getTopicIdByTutorialId(String tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT topic_id FROM topic_tutorial_map where tutorial_id=?");
            statement.setString(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("topic_id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public String getTopicIdByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT topic_id FROM topic_article_map where article_id=?");
            statement.setString(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("topic_id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<Topic> getAllTopics() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Topic> topics = null;
        Topic topic;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title FROM topics");
            resultSet = statement.executeQuery();
            topics = new ArrayList<>();
            while(resultSet.next()) {
                topic = new Topic();
                topic.setId(resultSet.getString("id"));
                topic.setTitle(resultSet.getString("title"));
                topics.add(topic);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topics;
    }
    
    public void createNewTopic(String id, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO topics (id,title) VALUES (?,?)");
            statement.setString(1, id);
            statement.setString(2, title);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyTopicTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM topics WHERE title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyTopicId(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM topics WHERE id=?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deleteByTopicId(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topics WHERE id=?");
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void removeAllLinksByTopicId(String topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topic_tutorial_map WHERE topic_id=?");
            statement.setString(1, topicId);
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM topic_article_map WHERE topic_id=?");
            statement.setString(1, topicId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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